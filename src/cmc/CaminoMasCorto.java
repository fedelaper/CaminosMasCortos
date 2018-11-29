package cmc;

import java.awt.Color;
/**
 * Obtiene la lista de los puntos marcados en la matriz (mapa)
 * Itera los mismos de la siguiente forma:
 * Obtiene los 2 primeros y expande los contiguos entre ambos.
 * Primero expande eje x, segundo expande el eje y.
 * Reitera la lista expandiendo el siguiente (siempre expandiendo de a pares)
 * El recorrido es secuencial (conforme al orden de marcado de los puntos en el mapa)
 * Invoca al método dibujar para cerrar el ciclo.
 * No contempla las densidades definidas en la matriz (mapa)
 */
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import graficos.Punto;
import mapa.MapaInfo;

public class CaminoMasCorto {

	private MapaInfo mapa;
	private CmcImple cmc;

	private PriorityQueue<Nodo> caminosPosibles = new PriorityQueue<Nodo>();
	private boolean[][] visitados;
	private Nodo nodoOrigen = null;

	public CaminoMasCorto(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		obtenerCamino();
	}

	private void obtenerCamino() {
		List<Punto> mejorCamino = new ArrayList<Punto>();
		visitados = new boolean[MapaInfo.LARGO][MapaInfo.ALTO];
		this.inicializarVisitados();
		
		Punto origen  = mapa.getPuntos().get(0);
		Punto destino = mapa.getPuntos().get(1);

		nodoOrigen = new Nodo(origen, destino, mapa.getDensidad(origen), null);
		caminosPosibles.add(nodoOrigen);
		Nodo celdaOrigen = caminosPosibles.poll();
		visitados[origen.x][origen.y] = true;
			
		while ((celdaOrigen != null) && ((celdaOrigen.getPunto().x != destino.x) || (celdaOrigen.getPunto().y != destino.y))) {

			expandirNodo(celdaOrigen, destino); 
			celdaOrigen = caminosPosibles.poll(); 
		}

		if (celdaOrigen != null) {
				mejorCamino = armarCamino(celdaOrigen, mejorCamino);
				cmc.dibujarCamino(mejorCamino, Color.RED);
				mapa.enviarMensaje("Camino minimo: " + mejorCamino.size() + " puntos");
			}
	}

	private void expandirNodo(Nodo nodo, Punto destino) {
		int nodoX = nodo.getPunto().x;
		int nodoY = nodo.getPunto().y;
		for (int i = nodoX - 1; i < nodoX + 2; i++) {
			for (int j = nodoY - 1; j < nodoY + 2; j++) {
				if (i != nodoX || j != nodoY) { 
					if (esPuntoValido(i, j) && !visitados[i][j]) {
						visitados[i][j] = true;
						Punto p = new Punto(i, j);
						if (puedeMoverse(p, nodo)) {
							caminosPosibles.add(new Nodo(p, destino, mapa.getDensidad(p), nodo));
						}
					}
				}
			}
		}
	}

	private boolean puedeMoverse(Punto p, Nodo predecesora) {
		return (esTraspasable(p) && esDiagonal(p, predecesora));
	}

	private boolean esPuntoValido(int x, int y) {
		return (x >= 0 && x < MapaInfo.LARGO && y >= 0 && y < MapaInfo.ALTO);
	}

	private boolean esTraspasable(Punto p) {
		return mapa.getDensidad(p) != 4;
	}

	private boolean esDiagonal(Punto p, Nodo c) {
		if (p.x != c.getPunto().x && p.y != c.getPunto().y) {
			Punto a = new Punto(p.x, c.getPunto().y);
			Punto b = new Punto(c.getPunto().x, p.y);
			if (esTraspasable(a) && esTraspasable(b))
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	private List<Punto> armarCamino(Nodo f, List<Punto> camino) {
		if (f.getPredecesor() != null) {
			camino.add(f.getPunto());
			armarCamino(f.getPredecesor(), camino);
		}
		return camino;
	}

	private void inicializarVisitados() {
		for (int i = 0; i < MapaInfo.LARGO; i++)
			for (int j = 0; j < MapaInfo.ALTO; j++)
				visitados[i][j] = false;
	}
}