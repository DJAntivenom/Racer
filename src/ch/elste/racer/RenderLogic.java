package ch.elste.racer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class RenderLogic extends JPanel implements Runnable {
	public static final int SUCCESS_CODE = 0x0000;
	public static final int OBSTACLE_SPEED = 5;
	public volatile static boolean renderable;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6195642600853724715L;

	public static final int WIDTH = 800, HEIGHT = 800;

	private JFrame window;
	private static RenderLogic gameScreen;

	private Player player;
	private Obstacle obstacleTest;

	private static long frameStartTime, frameEndTime;
	private static double deltaTime;
	private static long frameCounter;

	private RenderLogic() throws InterruptedException {
		player = new Player();
		obstacleTest = new Obstacle();
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
		window.setResizable(false);

		player.setX(gameScreen.getWidth() / 2);
		player.setY(gameScreen.getHeight() - player.getHeight() * 2);

		window.addKeyListener(player.getKeyHandlder());

		SwingWorker<Void, Void> renderThread = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				while (window.isVisible()) {
					if (renderable)
						RenderLogic.render();
				}
				return null;
			}

			@Override
			protected void done() {
				super.done();

				if (window.isVisible())
					window.dispose();
				System.exit(SUCCESS_CODE);
			}
		};

		renderThread.execute();
	}

	public static void render() {
		frameStartTime = System.nanoTime();

		gameScreen.paintComponent(gameScreen.getGraphics());

		frameEndTime = System.nanoTime();
		deltaTime = (frameEndTime - frameStartTime) / Math.pow(10, 6);
		
		frameCounter++;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		player.draw(g);
		obstacleTest.draw(g);
		
		g.drawString(String.format("FPS: %3.1f", RenderLogic.getFPS()), 10, 20);
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
	
	/**
	 * Gibt die Anzahl an angezeigten Frames zurück.
	 * @return die Anzahl an angezeigten Frames.
	 */
	public static long getFrameCounter() {
		return frameCounter;
	}
}