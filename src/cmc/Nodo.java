package cmc;

import graficos.Punto;

public class Nodo implements Comparable<Nodo> {

	private Punto punto;
	private Nodo predecesor;
	private int  costoAcumulado;
	private int  distanciaDestino;

	public Nodo(Punto p, Punto destino, int densidad, Nodo pre) {
		punto = p;
		distanciaDestino = (int) p.distance(destino);
		predecesor = pre; 
		int costoMovimiento;
		
		if (pre != null) {
			Punto predecesor = pre.getPunto();
			if (p.x != predecesor.x && p.y != predecesor.y) {
				costoMovimiento = 14;
			}else {
				costoMovimiento = 10;
			}
			costoAcumulado = pre.getCostoAcumulado() + (densidad + 1) * costoMovimiento;
		}
		else{
			costoAcumulado = 0;
		}
	}

	public Punto getPunto() {
		return punto;
	}

	public void setPunto(Punto punto) {
		this.punto = punto;
	}

	public Nodo getPredecesor() {
		return predecesor;
	}

	public int getDistanciaDestino() {
		return distanciaDestino;
	}

	public int getCostoAcumulado() {
		return costoAcumulado;
	}

	@Override
	public int compareTo(Nodo nodo) {
		int resultado = 0;
		if (costoAcumulado != nodo.getCostoAcumulado()) {
			if (costoAcumulado < nodo.getCostoAcumulado())
				resultado = -1;
			else if (costoAcumulado > nodo.getCostoAcumulado())
				resultado = 1;
		} else {
			if (getDistanciaDestino() < nodo.getDistanciaDestino())
				resultado = -1;
			else if (getDistanciaDestino() > nodo.getDistanciaDestino())
				resultado = 1;
		}
		return resultado;
	}

}