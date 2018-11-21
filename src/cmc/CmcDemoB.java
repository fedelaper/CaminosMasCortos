package cmc;

import java.awt.Color;
/**
* Obtiene los dos primeros puntos marcados en la matriz (mapa)
* El primero se define como origen, el segundo como destino- 
* Expande los puntos contiguos del origen al destino.
* Primero expande eje x, luego expande el eje y.
* El recorrido es secuencial y ortogonal (no contempla recorrido en diagonal).
* Los puntos expandidos se retornan en una List<Punto>.
* Invoca la método dibujar.
* No contempla las densidades definidas en la matriz (mapa)
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graficos.Punto;
import mapa.MapaInfo;

public class CmcDemoB {
	private MapaInfo mapa;
	private CmcImple cmc;

	public CmcDemoB(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		demoObtenerCamino();
	}

	private void demoObtenerCamino() {
		Punto a = mapa.getPuntos().get(0);
		Punto b = mapa.getPuntos().get(1);

		List<PuntoPeso> path = findPath(puntoToPuntoPeso(a, mapa.getDensidad(a.x, a.y)), puntoToPuntoPeso(b, mapa.getDensidad(b.x, b.y)));

//		searchPath(puntoToPuntoPeso(a, mapa.getDensidad(a.x, a.y)), puntoToPuntoPeso(b, mapa.getDensidad(b.x, b.y)),
//				visitados);
//		this.obtenerElMejorCamino(visitados)
		List<Punto> listaPuntos = listaPPtoPunto(path);
		
		cmc.dibujarCamino(listaPuntos, Color.red);
		mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
	}

	private boolean searchPath(PuntoPeso origen, PuntoPeso destino, List<PuntoPeso> visitados) {
		List<PuntoPeso> adyacentes = origen.getAdyacentes(destino, visitados, mapa);
		boolean encontroB = false;

		Collections.sort(adyacentes);

		while (!adyacentes.isEmpty() && !encontroB) {

			PuntoPeso primero = adyacentes.remove(0);

			visitados.add(primero);

			if (!primero.equals(destino)) {
				encontroB = this.searchPath(primero, destino, visitados);
			} else if (primero.equals(destino)) {
				encontroB = true;
			}

			if(adyacentes == null || adyacentes.isEmpty()) {
				System.out.println("error");
			}
			primero.setPredecesor(adyacentes.get(0));
		}
		return encontroB;

	}
	
	public List<PuntoPeso> findPath(PuntoPeso origen, PuntoPeso destino) {
		List<PuntoPeso> openList = new ArrayList<PuntoPeso>();
		List<PuntoPeso> closedList = new ArrayList<PuntoPeso>();
	
//		origen.setPesoAcumulado(0);
//		origen.setDistanciaAlDestino(calcularDistanciaAlDestino(origen,destino));
//		origen.setPredecesor(null);
	    openList.add(origen);
	    PuntoPeso puntoDestino = null;
	    boolean destinoEncontrado = Boolean.FALSE;
	    while (!openList.isEmpty() && !destinoEncontrado) {
	    	Collections.sort(openList);
	    	PuntoPeso punto = openList.remove(0);
	    	closedList.add(punto);
	    	
	        if (punto.equals(destino)) {
	          // construct the path from start to goal
	        	puntoDestino = punto;
	        	destinoEncontrado = Boolean.TRUE;
	        	
	         // return constructPath(destino);
	        }else {
	        
		        List<PuntoPeso> adyacentes = punto.getAdyacentes(destino, closedList, mapa); 
		        Collections.sort(adyacentes);
		        for (int i=0; i<adyacentes.size(); i++) {
		        	PuntoPeso puntoAdyacente = adyacentes.get(i);
	//	        	boolean estaAbierto = openList.contains(puntoAdyacente);
	//	        	boolean estaCerrado = closedList.contains(puntoAdyacente);
		        	int totalPesoDistancia = punto.getPesoAcumulado() + puntoAdyacente.getPesoCalculado();
		        	
		        	if (!openList.contains(puntoAdyacente) /*|| totalPesoDistancia < puntoAdyacente.getTotalPesoDistancia()*/){
	//	        		puntoAdyacente.setPredecesor(punto);  
	//	        		puntoAdyacente.setTotalPesoDistancia(totalPesoDistancia);
	//	        		puntoAdyacente.setDistanciaAlDestino(calcularDistanciaAlDestino(puntoAdyacente,destino));
		        		if(openList.contains(puntoAdyacente)) {
		        			PuntoPeso puntoAbierto = openList.get(openList.indexOf(puntoAdyacente));
		        			if(puntoAdyacente.getPesoAcumulado() < puntoAbierto.getPesoAcumulado()) {
		        				openList.remove(puntoAbierto);
		        				openList.add(puntoAdyacente);
		        			}
		        		}else {
		        			if (closedList.contains(puntoAdyacente)) {
		        				closedList.remove(puntoAdyacente);
		        			}
		        			openList.add(puntoAdyacente);
		        			
		        		}
		              }
		            }
		            closedList.add(punto);
		        }
	          }

	          // no path found
		if(destinoEncontrado) {
			return constructPath(puntoDestino);
		}else {
			return new ArrayList<PuntoPeso>();
		}
	}
	
	protected List<PuntoPeso> constructPath(PuntoPeso punto) {
		List<PuntoPeso> path = new ArrayList<PuntoPeso>();
		while (punto.getPredecesor() != null) {
			path.add(punto);
			punto = punto.getPredecesor();
		}
		return path;
	}

//	private List<PuntoPeso> elegirMejorCamino(List<PuntoPeso> visitados) {
//		
//		List<PuntoPeso> mejorCamino = new ArrayList<PuntoPeso>();
//		int mxvalue =visitados.size();
//		
//		mejorCamino.add(visitados.get(mxvalue-1));
//		
//		for (int i = mxvalue-2; i > 0; i--) {
//			if(mejorCamino)
//		}
//		
//		
//		return mejorCamino;
//	}

	private List<PuntoPeso> obtenerElMejorCamino(List<PuntoPeso> posiblesCaminos) {
		List<PuntoPeso> mejorCamino = new ArrayList<PuntoPeso>();
		Collections.reverse(posiblesCaminos);
		PuntoPeso ultimo = posiblesCaminos.get(0);
		mejorCamino.add(ultimo);
		while(ultimo.getPredecesor() != null) {
			ultimo = ultimo.getPredecesor();
			mejorCamino.add(ultimo);
		}		
//		for(PuntoPeso punto: posiblesCaminos) {
//			if(punto.getTotalPesoDistancia() - ) {
//				
//			}
//		}
		return mejorCamino;
	}

	private List<Punto> listaPPtoPunto(List<PuntoPeso> puntoPesoList) {
		List<Punto> puntoList = new ArrayList<Punto>();
		for (PuntoPeso puntoPeso : puntoPesoList) {
			puntoList.add(PuntoPesoToPunto(puntoPeso));
		}
		return puntoList;
	}

	private Punto PuntoPesoToPunto(PuntoPeso puntoPeso) {
		Punto punto = new Punto(puntoPeso.getX(), puntoPeso.getY());
		return punto;
	}

	private PuntoPeso puntoToPuntoPeso(Punto punto, int peso) {
		PuntoPeso puntoPeso = new PuntoPeso(punto, mapa.getDensidad(punto.x, punto.y) + 1, peso, 0, null);
		return puntoPeso;
	}

//	private List<PuntoPeso> calcularMenor(List<PuntoPeso> listaA, List<PuntoPeso> listaB) {
//		int a = 0;
//		int b = 0;
//		if (!listaB.isEmpty()) {
//			for (PuntoPeso punto : listaA) {
//				a += (punto.getPesoCalculado());
//			}
//			for (PuntoPeso punto : listaB) {
//				b += (punto.getPesoCalculado());
//			}
//			if (a > b) {
//				return listaB;
//			}
//		}
//		return listaA;
//	}

}