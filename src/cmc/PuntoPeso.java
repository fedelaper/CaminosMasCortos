/**
 * 
 */
package cmc;

/**
 * @author alu603
 *
 */
public class PuntoPeso {

	int x;
	int y;
	int peso;
	int pesoAcumulado;
	int distanciaAlDestino;
	int totalPesoDistancia;
	PuntoPeso predecesor;
	
	public PuntoPeso(int x, int y, int peso, int pesoAcumulado, int distanciaAlDestino) {
		super();
		this.x = x;
		this.y = y;
		this.peso = peso;
		this.pesoAcumulado = pesoAcumulado;
		this.distanciaAlDestino = distanciaAlDestino;
		this.totalPesoDistancia = pesoAcumulado + distanciaAlDestino;
	}
	
	
	public PuntoPeso getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(PuntoPeso predecesor) {
		this.predecesor = predecesor;
	}
	public int getTotalPesoDistancia() {
		return totalPesoDistancia;
	}
	public void setTotalPesoDistancia(int totalPesoDistancia) {
		this.totalPesoDistancia = totalPesoDistancia;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPeso() {
		return peso;
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
	public int getDistanciaAlDestino() {
		return distanciaAlDestino;
	}
	public void setDistanciaAlDestino(int distanciaAlDestino) {
		this.distanciaAlDestino = distanciaAlDestino;
	}
	
}
