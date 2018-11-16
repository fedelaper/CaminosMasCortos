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
		List<Punto> listaPuntos = expandirPuntosContiguos(a, b);
		cmc.dibujarCamino(listaPuntos,Color.red);
		mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
		
	}
	
	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		List<Punto> listaPuntos = new ArrayList<Punto>();
		if (a.x < b.x) {
			for(int x = a.x ; x < b.x; x++) {
				listaPuntos.add(new Punto(x, a.y));	
				mapa.getDensidad(x, a.y);
			}
		} else {
			for(int x = a.x ; x > b.x; x--) {
				listaPuntos.add(new Punto(x, a.y));
			}
		}
		if (a.y < b.y) {
			for(int y = a.y ; y < b.y; y++) {
				listaPuntos.add(new Punto(b.x, y));
			}
		} else {
			for(int y = a.y ; y > b.y; y--) {
				listaPuntos.add(new Punto(b.x, y));
			}
		}
		cmc.dibujarCamino(listaPuntos);
		return listaPuntos;
	}
	
	//hay que agregar el primer punto a la lista "camino". El punto actual es el primer punto.
	//BFS
	private boolean expandirCaminoIdeal(PuntoPeso actual, PuntoPeso b, List<PuntoPeso> camino)  //modificar para que devuelva una lista de puntos -> hacer un metodo que convierta.
	{
		if(actual == b)
		{
			camino.add(actual);
			return true;
		}
		
		if(!actual.visitado)
		{
			actual.setVisitado();  //hay que definir como resolver este problema.
			
			PuntoPeso auxiliar = actual;	
			auxiliar.setX(auxiliar.x - 1);
			expandirCaminoIdeal(auxiliar, b, camino);
			
			auxiliar = actual;
			auxiliar.setX(auxiliar.x + 1);
			expandirCaminoIdeal(auxiliar, b, camino);
			
			auxiliar = actual;
			auxiliar.setY(auxiliar.y - 1);
			expandirCaminoIdeal(auxiliar, b, camino);
			
			auxiliar = actual;
			auxiliar.setY(auxiliar.y + 1);
			expandirCaminoIdeal(auxiliar, b, camino);
		}
		return false;		
	}
}
