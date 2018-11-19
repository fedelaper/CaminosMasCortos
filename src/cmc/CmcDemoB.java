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

		List<PuntoPeso> visitados = new ArrayList<PuntoPeso>();

		searchPath(puntoToPuntoPeso(a, mapa.getDensidad(a.x, a.y)), puntoToPuntoPeso(b, mapa.getDensidad(b.x, b.y)),
				visitados);
//		this.obtenerElMejorCamino(visitados)
		List<Punto> listaPuntos = listaPPtoPunto(visitados);
		
		cmc.dibujarCamino(listaPuntos, Color.red);
		mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
	}
	/*
	 * private List<PuntoPeso> buscarCamino(PuntoPeso a, PuntoPeso b,
	 * List<PuntoPeso> visitados) { List<PuntoPeso> listaPuntos = new
	 * ArrayList<PuntoPeso>(); List<PuntoPeso> posiblesCaminos = buscarPuntos(a, b);
	 * List<PuntoPeso> menor = new ArrayList<PuntoPeso>(); for (PuntoPeso punto :
	 * posiblesCaminos) { if (!visitados.contains(punto)) { //TODO: seleccionar el
	 * que tenga menor totalPesoDistancia. // de existir dos, selecionar el que
	 * tenga menor seleccionar el de menor peso. if (!sonPuntosIguales(punto, b)) {
	 * visitados.add(punto); menor = buscarCamino(punto, b, visitados);
	 * menor.add(punto); listaPuntos = calcularMenor(menor, listaPuntos); } else {
	 * listaPuntos.add(b); return listaPuntos; } } } return listaPuntos; }
	 */

//	private List<PuntoPeso> buscarCamino2(PuntoPeso a, PuntoPeso b, List<PuntoPeso> visitados) {
//		List<PuntoPeso> listaPuntos = new ArrayList<PuntoPeso>();
//		List<PuntoPeso> posiblesCaminos = buscarPuntos(a, b);
//		List<PuntoPeso> menor = new ArrayList<PuntoPeso>();
//		for (PuntoPeso punto : posiblesCaminos) {
//			if (!visitados.contains(punto)) {
//				// TODO: seleccionar el que tenga menor totalPesoDistancia.
//				// de existir dos, selecionar el que tenga menor seleccionar el de menor peso.
//				if (!sonPuntosIguales(punto, b)) {
//					if (esElMejorCamino(punto, posiblesCaminos)) {
//						visitados.add(punto);
//						menor = buscarCamino2(punto, b, visitados);
//						menor.add(punto);
//						listaPuntos = calcularMenor(menor, listaPuntos);
//					}
//				} else {
//					listaPuntos.add(b);
//					return listaPuntos;
//				}
//			}
//		}
//		return listaPuntos;
//	}

	private boolean searchPath(PuntoPeso origen, PuntoPeso destino, List<PuntoPeso> visitados) {
		List<PuntoPeso> adyacentes = this.buscarPuntos(origen, destino, visitados);
		boolean encontroB = false;

		ordenarPorHeuristica(adyacentes);

		while (!adyacentes.isEmpty() && !encontroB) {

			PuntoPeso primero = adyacentes.remove(0);

			visitados.add(primero);

			if (!primero.equals(destino)) {
				encontroB = this.searchPath(primero, destino, visitados);
			} else if (primero.equals(destino)) {
				encontroB = true;
			}

		}
		return encontroB;
//		PuntoPeso primero = adyacentes.remove(0);
//
//		visitados.add(origen);
//		if (primero.equals(destino)) {
//			visitados.add(destino);
//		}else {
//			if(!visitados.contains(primero)) {
//				this.searchPath(adyacentes.get(0), destino, visitados);
//			}else{
//				
//			}
//		}

//		if (!visitados.contains(primero) && !primero.equals(destino)) {
//			this.searchPath(adyacentes.get(0), destino, visitados);
//		} else if (primero.equals(destino)) {
//			visitados.add(destino);
//		}

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

	// Ordena de menor a mayor segun el totalPesoDistancia
	private void ordenarPorHeuristica(List<PuntoPeso> adyacentes) {
		Collections.sort(adyacentes);
	}

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

	// Devuelve una lista de puntos adyacentes al punto "a".
	// No devuelve puntos inválidos.
	private List<PuntoPeso> buscarPuntos(PuntoPeso a, PuntoPeso b, List<PuntoPeso> visitados) {
		List<PuntoPeso> listaPuntos = new ArrayList<PuntoPeso>();
		PuntoPeso auxiliar = new PuntoPeso(a);
		boolean esDiagonal = true;

		// punto(oginal.x,(original.y)-1)
		auxiliar.setY(auxiliar.getYmenos());
		auxiliar.setPredecesor(a);
		
		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, !esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			listaPuntos.add(auxiliar);
		}

		// punto((oginal.x)-1,(original.y)-1)
		auxiliar.setX(auxiliar.getXmenos());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar2 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar2);
		}

		// punto((oginal.x)-1,(original.y))
		auxiliar.setY(auxiliar.getYmas());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, !esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar3 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar3);
		}

		auxiliar.setY(auxiliar.getYmas());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar4 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar4);
		}

		auxiliar.setX(auxiliar.getXmas());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, !esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar5 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar5);
		}

		auxiliar.setX(auxiliar.getXmas());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar6 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar6);
		}

		auxiliar.setY(auxiliar.getYmenos());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, !esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar7 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar7);
		}

		auxiliar.setY(auxiliar.getYmenos());

		if (esPuntoValido(auxiliar) && !estaVisitado(auxiliar, visitados)) {
			auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));
			auxiliar.setPesoAcumulado(a, esDiagonal);
			auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(auxiliar, b));
			auxiliar.setTotalPesoDistancia();
			PuntoPeso auxiliar8 = new PuntoPeso(auxiliar);
			listaPuntos.add(auxiliar8);
		}

		return listaPuntos;
	}

	// Devuelve la distancia del punto a, al punto b. No coincidera paredes ni
	// densidades.
	private int calcularDistanciaAlDestino(PuntoPeso a, PuntoPeso b) {
		int distancia = 0;
		if (a.getX() < b.getX()) {
			for (int x = a.getX(); x < b.getX(); x++) {
				distancia+=10;
			}
		} else {
			for (int x = a.getX(); x > b.getX(); x--) {
				distancia+=10;
			}
		}
		if (a.getY() < b.getY()) {
			for (int y = a.getY(); y < b.getY(); y++) {
				distancia+=10;
			}
		} else {
			for (int y = a.getY(); y > b.getY(); y--) {
				distancia+=10;
			}
		}
		return distancia;
	}

	// Devuelve verdadero si el punto no es una pared, ni se sale del mapa.
	private boolean esPuntoValido(PuntoPeso punto) {
		if (punto.getY() < 0 || punto.getX() < 0 || punto.getX() > MapaInfo.LARGO || punto.getY() > MapaInfo.ALTO) {
			return false;
		}
		if (punto.getPeso()-1 == MapaInfo.MAX_DENSIDAD) {
			return false;
		}
		return true;
	}

	private boolean estaVisitado(PuntoPeso pp, List<PuntoPeso> lista) {
		return lista.contains(pp);
	}

	// Devuelve verdadero, si los dos puntos son iguales.
	private boolean sonPuntosIguales(PuntoPeso a, PuntoPeso b) {
		return (a.getX() == b.getX() && a.getY() == b.getY());
	}
}