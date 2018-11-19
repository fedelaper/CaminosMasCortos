/**
 * 
 */
package cmc;

import graficos.Punto;

/**
 * @author alu603
 *
 */
public class PuntoPeso implements Comparable<PuntoPeso> {
	private Punto punto;
	private int peso;
	private int pesoAcumulado;
	private int distanciaAlDestino;
	private int totalPesoDistancia;
	private PuntoPeso predecesor;

	public PuntoPeso(Punto punto, int peso, int pesoAcumulado, int distanciaAlDestino, PuntoPeso predecesor) {
		super();
		this.punto = punto;
		this.peso = peso;
		this.pesoAcumulado = pesoAcumulado;
		this.distanciaAlDestino = distanciaAlDestino;
		this.totalPesoDistancia = pesoAcumulado + distanciaAlDestino;
		this.predecesor = predecesor;
	}

	public PuntoPeso() {

	}

	public PuntoPeso(PuntoPeso aux) {
		super();
		this.punto = new Punto((int) aux.getPunto().getX(), (int) aux.getPunto().getY());
		this.peso = aux.getPeso();
		this.pesoAcumulado = aux.getPesoAcumulado();
		this.distanciaAlDestino = aux.getDistanciaAlDestino();
		this.totalPesoDistancia = aux.getPesoAcumulado() + aux.getDistanciaAlDestino();
		this.predecesor = aux.getPredecesor();
	}

	@Override
	public int compareTo(PuntoPeso puntoPeso) {
		int valorDeRetorno = 0;
		if (this.totalPesoDistancia < puntoPeso.getTotalPesoDistancia()) {
			valorDeRetorno = -1;
		} else if (this.totalPesoDistancia > puntoPeso.getTotalPesoDistancia()) {
			valorDeRetorno = 1;
		} else {
			if (this.distanciaAlDestino < puntoPeso.getDistanciaAlDestino()) {
				valorDeRetorno = -1;
			} else {
				valorDeRetorno = 1;
			}
		}
		return valorDeRetorno;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this)
			return true;
		if (!(o instanceof PuntoPeso)) {
			return false;
		}

		PuntoPeso pp = (PuntoPeso) o;
		return (pp.getPunto().getX() == this.getPunto().getX() && pp.getPunto().getY() == this.getPunto().getY());
	}

	public int getX() {
		return this.punto.x;
	}

	public int getXmenos() {
		return this.punto.x - 1;
	}

	public int getXmas() {
		return this.punto.x + 1;
	}

	public int getY() {
		return this.punto.y;
	}

	public int getYmenos() {
		return this.punto.y - 1;
	}

	public int getYmas() {
		return this.punto.y + 1;
	}

	public void setX(int x) {
		this.punto.x = x;
	}

	public void setY(int y) {
		this.punto.y = y;
	}

	public int getPeso() {
		return peso;
	}

	public int getPesoCalculado() {
		return peso + 1;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getPesoAcumulado() {
		return pesoAcumulado;
	}

	public void setPesoAcumulado(int pesoAcumulado) {
		this.pesoAcumulado = pesoAcumulado;
	}

	public void setPesoAcumulado(PuntoPeso anterior, boolean esDiagonal) {
		int nuevoPesoAcumulado = 0;

		if (!esDiagonal)
			nuevoPesoAcumulado = anterior.getPesoAcumulado() + this.getPesoCalculado() * 10;
		else
			nuevoPesoAcumulado = anterior.getPesoAcumulado() + this.getPesoCalculado() * 14;

		this.pesoAcumulado = nuevoPesoAcumulado;
	}

	public int getDistanciaAlDestino() {
		return distanciaAlDestino;
	}

	public void setDistanciaAlDestino(int distanciaAlDestino) {
		this.distanciaAlDestino = distanciaAlDestino;
	}

	public int getTotalPesoDistancia() {
		return totalPesoDistancia;
	}

	public void setTotalPesoDistancia() {
		this.totalPesoDistancia = this.pesoAcumulado + this.distanciaAlDestino;
	}

	public PuntoPeso getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(PuntoPeso predecesor) {
		this.predecesor = predecesor;
	}

	public Punto getPunto() {
		return punto;
	}

	public void setPunto(Punto punto) {
		this.punto = punto;
	}

}