package ch.elste.racer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import ch.elste.racer.scene.Obstacle;
import ch.elste.racer.scene.Player;

public class RenderLogic extends JPanel implements Runnable {
	public static final int SUCCESS_CODE = 0x0000;
	public static double obstacleSpeed = 00;
	public static final int OBSTACLE_DISTANCE = 140;
	public static final int WIDTH = 800, HEIGHT = 800;
	public volatile static boolean renderable;

	private static final long serialVersionUID = -6195642600853724715L;

	private JFrame window;
	private BufferedImage frame;
	private static RenderLogic gameScreen;

	private static Player player1, player2;

	private static Obstacle[] obstacles;

	private static long frameStartTime, frameEndTime;
	private static double deltaTime;
	private static long frameCounter;
	private static boolean stopped = false;

	private RenderLogic() throws InterruptedException {
		player1 = new Player(Player.KeySet.WASD, "../data/Player.png");
		player2 = new Player(Player.KeySet.ARROWS, "../data/Player2.png");
		obstacles = new Obstacle[Math.round((float) HEIGHT / OBSTACLE_DISTANCE) * 4];
		for (int i = 0; i < obstacles.length; i++) {
			obstacles[i] = new Obstacle(Math.random() * WIDTH, -i * OBSTACLE_DISTANCE);
			if (obstacles[i].getX() + obstacles[i].getWidth() / 2 > WIDTH) {
				obstacles[i].setX(WIDTH - obstacles[i].getX() / 2);
			}
		}

		frame = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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

	public static void endGame(Player loser) {
		if (loser == null) {
			stopped = true;
		} else {
			renderable = false;
			JOptionPane.showMessageDialog(gameScreen.window,
					String.format("Spieler %s gewinnt!", loser == player1 ? "Spieler 2" : "Spieler 1"), "Sieg",
					JOptionPane.INFORMATION_MESSAGE);
			stopped = true;
		}
	}

	@Override
	public void run() {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				obstacleSpeed += 100;
			}
		}, 20000, 20000);

		window = new JFrame("Racer");
		gameScreen.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window.setContentPane(gameScreen);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);

		player1.setX(WIDTH * 1 / 3);
		player1.setY(HEIGHT - player1.getHeight() * 2);

		player2.setX(WIDTH * 2 / 3);
		player2.setY(HEIGHT - player2.getHeight() * 2);

		window.addKeyListener(player1.getKeyHandlder());
		window.addKeyListener(player2.getKeyHandlder());

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				endGame(null);
			}
		});

		SwingWorker<Void, Void> renderThread = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				while (!hasStopped()) {
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

	public static synchronized boolean hasStopped() {
		return stopped;
	}

	public static void render() {
		frameStartTime = System.nanoTime();

		gameScreen.render(gameScreen.getGraphics());

		frameEndTime = System.nanoTime();
		deltaTime = (frameEndTime - frameStartTime) / Math.pow(10, 6);

		frameCounter++;
	}

	private void render(Graphics g) {
		frame.createGraphics().clearRect(0, 0, WIDTH, HEIGHT);

		player1.draw(frame.createGraphics());
		player2.draw(frame.createGraphics());

		for (int i = 0; i < obstacles.length; i++) {
			obstacles[i].draw(frame.createGraphics());
		}

		frame.createGraphics().drawString(String.format("FPS: %4.1f", RenderLogic.getFPS()), 10, 20);
		g.drawImage(frame, 0, 0, this);
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
	 * 
	 * @return die Anzahl an angezeigten Frames.
	 */
	public static long getFrameCounter() {
		return frameCounter;
	}

	public static Player getPlayer1() {
		return player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public static Obstacle[] getObstacles() {
		return obstacles;
	}
}