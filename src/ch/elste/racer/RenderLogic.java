package ch.elste.racer;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderLogic implements Runnable {
	private static final int WIDTH = 800, HEIGHT = 800;
	
	private JFrame window;
	private JPanel gameScreen;
	
	public RenderLogic() {
		gameScreen = new JPanel();
		
		
		window = new JFrame("Racer");
		window.setSize(WIDTH, HEIGHT);
		window.setContentPane(gameScreen);
	}

	@Override
	public void run() {
		while(window.isVisible()) {
			draw((Graphics2D) gameScreen.getGraphics());
		}
	}
	
	private void draw(Graphics2D g2d) {
		
	}

}
