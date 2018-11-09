package ch.elste.racer;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderLogic implements Runnable {
	private static final int WIDTH = 800, HEIGHT = 800;
	
	private JFrame window;
	private JPanel gameScreen;
	
	private Player player;
	
	public RenderLogic() throws InterruptedException {
		gameScreen = new JPanel();
		
		window = new JFrame("Racer");
		window.setSize(WIDTH, HEIGHT);
		window.setContentPane(gameScreen);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		player = new Player();
		player.setX(gameScreen.getWidth() / 2);
		player.setY(gameScreen.getHeight() - player.getHeight()*2);
	}

	@Override
	public void run() {
		while(window.isVisible()) {
			draw((Graphics2D) gameScreen.getGraphics());
		}
	}
	
	private void draw(Graphics2D g2d) {
		player.draw(g2d, gameScreen);
	}

}
