package cmc;

import graficos.Punto;

public class PuntoUtils {

	// Devuelve la distancia del punto a, al punto b. No considera paredes ni
		// densidades.
		public static int calcularDistanciaAlDestino(PuntoPeso a, PuntoPeso b) {
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
		public static Punto puntoPesoToPunto(PuntoPeso puntoPeso) {
			Punto punto = new Punto(puntoPeso.getX(), puntoPeso.getY());
			return punto;
		}

		public static PuntoPeso puntoToPuntoPeso(Punto punto, int peso) {
			PuntoPeso puntoPeso = new PuntoPeso(punto, peso, 0, 0, null);
			return puntoPeso;
		}
}
