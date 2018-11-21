/**
 * 
 */
package cmc;

import java.util.ArrayList;
import java.util.List;

import graficos.Punto;
import mapa.MapaInfo;

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
	
	// Devuelve una lista de puntos adyacentes al punto "a".
	// No devuelve puntos inválidos.
	public List<PuntoPeso> getAdyacentes(PuntoPeso destino, MapaInfo mapa) {
		List<PuntoPeso> listaPuntos = new ArrayList<PuntoPeso>();
		boolean esDiagonal = true;

		//Punto x y-1
		PuntoPeso puntoYmenos = new PuntoPeso(this);
		puntoYmenos.setY(puntoYmenos.getYmenos());
		puntoYmenos.setPredecesor(this);
		
		if (puntoYmenos.esPuntoValido() /*&& !visitados.contains(puntoYmenos)*/) {
			puntoYmenos.setPeso(mapa.getDensidad(puntoYmenos.getX(), puntoYmenos.getY()));
			puntoYmenos.setPesoAcumulado(this, !esDiagonal);
			puntoYmenos.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoYmenos, destino));
			puntoYmenos.setTotalPesoDistancia();
			puntoYmenos.setPredecesor(this);
			listaPuntos.add(puntoYmenos);
		}

		//Punto x-1 y-1 
		PuntoPeso puntoYmenosXmenos = new PuntoPeso(this);
		puntoYmenosXmenos.setX(puntoYmenosXmenos.getXmenos());
		puntoYmenosXmenos.setY(puntoYmenosXmenos.getYmenos());

		if (puntoYmenosXmenos.esPuntoValido() /*&& !visitados.contains(puntoYmenosXmenos)*/) {
			puntoYmenosXmenos.setPeso(mapa.getDensidad(puntoYmenosXmenos.getX(), puntoYmenosXmenos.getY()));
			puntoYmenosXmenos.setPesoAcumulado(this, esDiagonal);
			puntoYmenosXmenos.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoYmenosXmenos, destino));
			puntoYmenosXmenos.setTotalPesoDistancia();
			puntoYmenosXmenos.setPredecesor(this);
			listaPuntos.add(puntoYmenosXmenos);
		}

		// punto x-1 y
		PuntoPeso puntoXmenos = new PuntoPeso(this);
		puntoXmenos.setX(puntoXmenos.getXmenos());

		if (puntoXmenos.esPuntoValido() /*&& !visitados.contains(puntoXmenos)*/) {
			puntoXmenos.setPeso(mapa.getDensidad(puntoXmenos.getX(), puntoXmenos.getY()));
			puntoXmenos.setPesoAcumulado(this, !esDiagonal);
			puntoXmenos.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoXmenos, destino));
			puntoXmenos.setTotalPesoDistancia();
			puntoXmenos.setPredecesor(this);
			listaPuntos.add(puntoXmenos);
		}

		//punto x-1 y+1 
		PuntoPeso puntoYmasXmenos = new PuntoPeso(this);
		puntoYmasXmenos.setX(puntoYmasXmenos.getXmas());
		puntoYmasXmenos.setY(puntoYmasXmenos.getYmas());

		if (puntoYmasXmenos.esPuntoValido() /*&& !visitados.contains(puntoYmasXmenos)*/) {
			puntoYmasXmenos.setPeso(mapa.getDensidad(puntoYmasXmenos.getX(), puntoYmasXmenos.getY()));
			puntoYmasXmenos.setPesoAcumulado(this, esDiagonal);
			puntoYmasXmenos.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoYmasXmenos, destino));
			puntoYmasXmenos.setTotalPesoDistancia();
			puntoYmasXmenos.setPredecesor(this);
			listaPuntos.add(puntoYmasXmenos);
		}

		//punto x y+1
		PuntoPeso puntoYmas = new PuntoPeso(this);
		puntoYmas.setY(puntoYmas.getYmas());

		if (puntoYmas.esPuntoValido()/* && !visitados.contains(puntoYmas)*/) {
			puntoYmas.setPeso(mapa.getDensidad(puntoYmas.getX(), puntoYmas.getY()));
			puntoYmas.setPesoAcumulado(this, !esDiagonal);
			puntoYmas.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoYmas, destino));
			puntoYmas.setTotalPesoDistancia();
			puntoYmas.setPredecesor(this);
			listaPuntos.add(puntoYmas);
		}

		//punto x+1 y+1
		PuntoPeso puntoYmasXmas = new PuntoPeso(this);
		puntoYmasXmas.setY(puntoYmasXmas.getYmas());
		puntoYmasXmas.setX(puntoYmasXmas.getXmas());

		if (puntoYmasXmas.esPuntoValido() /*&& !visitados.contains(puntoYmasXmas)*/) {
			puntoYmasXmas.setPeso(mapa.getDensidad(puntoYmasXmas.getX(), puntoYmasXmas.getY()));
			puntoYmasXmas.setPesoAcumulado(this, esDiagonal);
			puntoYmasXmas.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoYmasXmas, destino));
			puntoYmasXmas.setTotalPesoDistancia();
			puntoYmasXmas.setPredecesor(this);
			listaPuntos.add(puntoYmasXmas);
		}

		//punto x+1 y
		PuntoPeso puntoXmas = new PuntoPeso(this);
		puntoXmas.setX(puntoXmas.getXmas());

		if (puntoXmas.esPuntoValido() /*&& !visitados.contains(puntoXmas)*/) {
			puntoXmas.setPeso(mapa.getDensidad(puntoXmas.getX(), puntoXmas.getY()));
			puntoXmas.setPesoAcumulado(this, !esDiagonal);
			puntoXmas.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoXmas, destino));
			puntoXmas.setTotalPesoDistancia();
			puntoXmas.setPredecesor(this);
			listaPuntos.add(puntoXmas);
		}

		//punto x+1 y-1
		PuntoPeso puntoXmasYmenos = new PuntoPeso(this);
		puntoXmasYmenos.setX(puntoXmasYmenos.getXmas());
		puntoXmasYmenos.setY(puntoXmasYmenos.getYmenos());

		if (puntoXmasYmenos.esPuntoValido() /*&& !visitados.contains(puntoXmasYmenos)*/) {
			puntoXmasYmenos.setPeso(mapa.getDensidad(puntoXmasYmenos.getX(), puntoXmasYmenos.getY()));
			puntoXmasYmenos.setPesoAcumulado(this, esDiagonal);
			puntoXmasYmenos.setDistanciaAlDestino(PuntoUtils.calcularDistanciaAlDestino(puntoXmasYmenos, destino));
			puntoXmasYmenos.setTotalPesoDistancia();
			puntoXmasYmenos.setPredecesor(this);
			listaPuntos.add(puntoXmasYmenos);
		}

		return listaPuntos;
	}
	
	// Devuelve verdadero si el punto no es una pared, ni se sale del mapa.
		private boolean esPuntoValido() {
			if (this.getY() < 0 || this.getX() < 0 || this.getX() > MapaInfo.LARGO || this.getY() > MapaInfo.ALTO) {
				return false;
			}
			if (this.getPeso() == MapaInfo.MAX_DENSIDAD) {
				return false;
			}
			return true;
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