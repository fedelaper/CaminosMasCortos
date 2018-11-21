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

		List<PuntoPeso> path = buscarCamino(PuntoUtils.puntoToPuntoPeso(a, mapa.getDensidad(a.x, a.y)),
				PuntoUtils.puntoToPuntoPeso(b, mapa.getDensidad(b.x, b.y)));

		List<Punto> listaPuntos = listaPPtoPunto(path);

		cmc.dibujarCamino(listaPuntos, Color.red);
		mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
	}
	

	public List<PuntoPeso> buscarCamino(PuntoPeso origen, PuntoPeso destino) {
		List<PuntoPeso> listaAbiertos = new ArrayList<PuntoPeso>();
		List<PuntoPeso> listaCerrados = new ArrayList<PuntoPeso>();

		listaAbiertos.add(origen);
		PuntoPeso puntoDestino = null;
		boolean destinoEncontrado = Boolean.FALSE;
		
		while (!listaAbiertos.isEmpty() && !destinoEncontrado) {
			Collections.sort(listaAbiertos);
			PuntoPeso punto = listaAbiertos.remove(0);
			listaCerrados.add(punto);

			if (punto.equals(destino)) {

				puntoDestino = punto;
				destinoEncontrado = Boolean.TRUE;

			} else {

				List<PuntoPeso> adyacentes = punto.getAdyacentes(destino, mapa);
				Collections.sort(adyacentes);
				for (int i = 0; i < adyacentes.size(); i++) {
					PuntoPeso puntoAdyacente = adyacentes.get(i);

					if (!listaAbiertos.contains(puntoAdyacente)) {
						if (listaAbiertos.contains(puntoAdyacente)) {
							PuntoPeso puntoAbierto = listaAbiertos.get(listaAbiertos.indexOf(puntoAdyacente));
							
							if (puntoAdyacente.getPesoAcumulado() < puntoAbierto.getPesoAcumulado()) {
								listaAbiertos.remove(puntoAbierto);
								listaAbiertos.add(puntoAdyacente);
							}
							
						} else {
							
							if (listaCerrados.contains(puntoAdyacente)) {
								listaCerrados.remove(puntoAdyacente);
							}
							
							listaAbiertos.add(puntoAdyacente);
						}
					}
				}
				
				listaCerrados.add(punto);
			}
		}

		if (destinoEncontrado) {
			// construct the path from start to goal
			return crearCamino(puntoDestino);
		} else {
			// no path found
			return new ArrayList<PuntoPeso>();
		}
	}

	protected List<PuntoPeso> crearCamino(PuntoPeso punto) {
		List<PuntoPeso> path = new ArrayList<PuntoPeso>();
		while (punto.getPredecesor() != null) {
			path.add(punto);
			punto = punto.getPredecesor();
		}
		return path;
	}

	private List<Punto> listaPPtoPunto(List<PuntoPeso> puntoPesoList) {
		List<Punto> puntoList = new ArrayList<Punto>();
		for (PuntoPeso puntoPeso : puntoPesoList) {
			puntoList.add(PuntoUtils.puntoPesoToPunto(puntoPeso));
		}
		return puntoList;
	}



}