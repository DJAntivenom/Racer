package ch.elste.racer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderLogic extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6195642600853724715L;

	public static final int WIDTH = 800, HEIGHT = 800;

	private JFrame window;
	private static RenderLogic gameScreen;

	private Player player;

	private static long frameStartTime, frameEndTime;
	private static double deltaTime;
	private static int frameCounter;

	private RenderLogic() throws InterruptedException {
		player = new Player();
	}

	public static RenderLogic instance() {
		if (gameScreen == null)
			try {
				gameScreen = new RenderLogic();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return gameScreen;
	}

	@Override
	public void run() {
		window = new JFrame("Racer");
		window.setSize(WIDTH, HEIGHT);
		window.setContentPane(gameScreen);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		player.setX(gameScreen.getWidth() / 2);
		player.setY(gameScreen.getHeight() - player.getHeight() * 2);

		window.addKeyListener(player.getKeyHandlder());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		frameStartTime = System.nanoTime();

		player.draw((Graphics2D) g);

		g.drawString(String.format("FPS: %3.1f", RenderLogic.getFPS()), 10, 20);

		frameEndTime = System.nanoTime();

		deltaTime = (frameEndTime - frameStartTime) / Math.pow(10, 6);
	}

	/**
	 * Gibt die Zeit für die Berechnung des letzten Frames in Millisekunden zurück.
	 * 
	 * @return die Zeit für die Berechnung des letzten Frames in Millisekunden.
	 */
	public static double getDeltaTime() {
		return deltaTime;
	}

	/**
	 * Gibt die durchschnittlichen FPS der letzten 10 frames zurück.
	 * 
	 * @return die durchschnittlichen FPS der letzten 10 frames.
	 */
	public static double getFPS() {
		return 1000d / (deltaTime);
	}
}