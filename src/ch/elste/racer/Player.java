package ch.elste.racer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import ch.elste.racer.interfaces.Drawable;
import ch.elste.racer.interfaces.Movable;

public class Player implements Drawable, Movable {
	public static final int RIGHT = 1;
	public static final int LEFT = 2;
	public static final int STATIONARY = 0;

	private KeySet keySet;
	private Image sprite;
	private double x, y;
	private double dx;
	private double speed;
	private int direction;
	private Dimension spriteSize, size;
	private KeyHandler keyHandler;

	/*
	 * Kreiert einen neuen Player.
	 */
	public Player(KeySet keySet, String imageLocation) throws InterruptedException {
		this.keySet = keySet;
		spriteSize = new Dimension(0, 0);
		size = new Dimension(20, 20);

		speed = 1;

		loadImage(imageLocation);

		keyHandler = new KeyHandler();
	}

	/**
	 * Lädt das Bild aus dem Speicher.
	 * 
	 * @throws InterruptedException
	 */
	public void loadImage(String imageLocation) throws InterruptedException {
		SwingWorker<Void, Void> loadingWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				RenderLogic.renderable = false;
				sprite = new ImageIcon(getClass().getResource(imageLocation)).getImage();

				spriteSize.setSize(sprite.getWidth(RenderLogic.instance()), sprite.getHeight(RenderLogic.instance()));
				return null;
			}

			@Override
			protected void done() {
				super.done();
				RenderLogic.renderable = true;
			}
		};

		loadingWorker.execute();
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		move();

		g.drawImage(sprite, (int) Math.round(x), (int) Math.round(y), size.width, size.height, observer);
	}

	@Override
	public void draw(Graphics g) {
		draw(g, RenderLogic.instance());
	}

	@Override
	public void move() {
		if (direction == RIGHT)
			dx = speed;
		else if (direction == LEFT)
			dx = -speed;
		else if (direction == STATIONARY)
			dx = 0;

		if (dx > 5)
			dx = 5;

		if (x > RenderLogic.WIDTH)
			x = 0;
		else if (x < 0)
			x = RenderLogic.WIDTH;
		else
			x += dx * RenderLogic.getDeltaTime();
	}

	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == keySet.getRight())
				direction = RIGHT;
			else if (e.getKeyCode() == keySet.getLeft())
				direction = LEFT;

			RenderLogic.instance().repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.getKeyCode() == keySet.getRight() && direction == RIGHT)
					|| (e.getKeyCode() == keySet.getLeft() && direction == LEFT))
				direction = STATIONARY;
		}
	}

	public enum KeySet {
		WASD(KeyEvent.VK_A, KeyEvent.VK_D),
		ARROWS(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		
		KeySet(int left, int right) {
			this.left = left;
			this.right = right;
		}

		private final int left, right;
		
		public int getLeft() {
			return left;
		}
		
		public int getRight() {
			return right;
		}
	}

	public KeyHandler getKeyHandlder() {
		return keyHandler;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getHeight() {
		return size.getHeight();
	}
}
