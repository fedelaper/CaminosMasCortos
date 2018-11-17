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
		List<PuntoPeso> listaPuntosP = buscarCamino(puntoToPuntoPeso(a, mapa.getDensidad(a.x, a.y)),
				puntoToPuntoPeso(b, mapa.getDensidad(b.x, b.y)), visitados);
		List<Punto> listaPuntos = listaPPtoPunto(listaPuntosP);
		cmc.dibujarCamino(listaPuntos, Color.red);
		mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");

	}

	private List<PuntoPeso> buscarCamino(PuntoPeso a, PuntoPeso b, List<PuntoPeso> visitados) {
		List<PuntoPeso> listaPuntos = new ArrayList<PuntoPeso>();
		List<PuntoPeso> posiblesCaminos = buscarPuntos(a,b);

		List<PuntoPeso> menor = new ArrayList<PuntoPeso>();
		for (PuntoPeso punto : posiblesCaminos) {
			if (!visitados.contains(punto)) {
				//TODO: seleccionar el que tenga menor totalPesoDistancia.
				//      de existir dos, selecionar el que tenga menor seleccionar el de menor peso.
				if (punto.getX() != b.getX() && punto.getY() != b.getY()) {
					visitados.add(punto);
					menor = buscarCamino(punto, b, visitados);
					menor.add(punto);
					listaPuntos = calcularMenor(menor, listaPuntos);

				} else {
					listaPuntos.add(b);
					return listaPuntos;
				}
			}
		}

		return listaPuntos;
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

	private List<PuntoPeso> calcularMenor(List<PuntoPeso> listaA, List<PuntoPeso> listaB) {

		int a = 0;
		int b = 0;

		if (!listaB.isEmpty()) {

			for (PuntoPeso punto : listaA) {
				a += (punto.getPesoCalculado());
			}

			for (PuntoPeso punto : listaB) {
				b += (punto.getPesoCalculado());
			}
			if (a > b) {
				return listaB;
			}
		}
		return listaA;
	}

	private List<PuntoPeso> buscarPuntos(PuntoPeso a, PuntoPeso b) {

		List<PuntoPeso> listaPuntos = new ArrayList<PuntoPeso>();
		PuntoPeso auxiliar = a;

		auxiliar.setY(auxiliar.getYmenos());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 10 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				listaPuntos.add(auxiliar);
			}
		}

		auxiliar.setX(auxiliar.getXmenos());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 14 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar2 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar2);
			}
		}

		auxiliar.setY(auxiliar.getYmas());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 10 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar3 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar3);
			}
		}

		auxiliar.setY(auxiliar.getYmas());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 14 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar4 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar4);
			}
		}

		auxiliar.setX(auxiliar.getXmas());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 10 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar5 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar5);
			}
		}

		auxiliar.setX(auxiliar.getXmas());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 14 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar6 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar6);
			}
		}

		auxiliar.setY(auxiliar.getYmenos());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 10 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar7 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar7);
			}
		}

		auxiliar.setY(auxiliar.getYmenos());
		auxiliar.setPeso(mapa.getDensidad(auxiliar.getX(), auxiliar.getY()));

		if (auxiliar.getY() > 0 && auxiliar.getX() > 0 && auxiliar.getX() < mapa.LARGO
				&& ((auxiliar.getY()) < mapa.ALTO)) {
			if (auxiliar.getPeso() != mapa.MAX_DENSIDAD) {
				auxiliar.setPesoAcumulado(a.getPesoAcumulado() + 14 * auxiliar.getPeso());
				auxiliar.setDistanciaAlDestino(calcularDistanciaAlDestino(PuntoPesoToPunto(auxiliar),PuntoPesoToPunto(b)));
				PuntoPeso auxiliar8 = new PuntoPeso(auxiliar);
				listaPuntos.add(auxiliar8);
			}
		}

		return listaPuntos;
	}

	private int calcularDistanciaAlDestino(Punto a, Punto b) { //cambiar Punto por PuntoPeso
		int distancia = 0;
		if (a.x < b.x) {
			for (int x = a.x; x < b.x; x++) {
				distancia++;
			}
		} else {
			for (int x = a.x; x > b.x; x--) {
				distancia++;
			}
		}
		if (a.y < b.y) {
			for (int y = a.y; y < b.y; y++) {
				distancia++;
			}
		} else {
			for (int y = a.y; y > b.y; y--) {
				distancia++;
			}
		}
		return distancia;
	}

}
