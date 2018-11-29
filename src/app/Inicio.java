package app;

import vista.CanvasFrame;

public class Inicio {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	            CanvasFrame frame = new CanvasFrame("Programación III");
	            frame.configurar();
	            frame.presentar();
	          };
		});
	}
} 
