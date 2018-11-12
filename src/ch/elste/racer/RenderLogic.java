package ch.elste.racer;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderLogic implements Runnable {
	public static final int WIDTH = 800, HEIGHT = 800;

	private JFrame window;
	private JPanel gameScreen;

	private Player player;

	private static long frameStartTime, frameEndTime;
	private static int deltaTime;

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
		player.setY(gameScreen.getHeight() - player.getHeight() * 2);
	}

	@Override
	public void run() {
		while (window.isVisible()) {
			draw((Graphics2D) gameScreen.getGraphics());
		}
	}

	private void draw(Graphics2D g2d) {
		frameStartTime = System.nanoTime();
		player.draw(g2d, gameScreen);

		frameEndTime = System.nanoTime();
		deltaTime = (int) (frameStartTime - frameEndTime * Math.pow(10, 6));
	}

	/**
	 * Gibt die Zeit für die Berechnung des letzten Frames in Millisekunden zurück.
	 * 
	 * @return die Zeit für die Berechnung des letzten Frames in Millisekunden.
	 */
	public static int getDeltaTime() {
		return deltaTime;
	}
}
