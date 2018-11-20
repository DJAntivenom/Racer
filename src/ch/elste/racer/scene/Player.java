package ch.elste.racer.scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import ch.elste.racer.RenderLogic;
import ch.elste.racer.util.Vector2D;

public class Player extends Actor {
	private KeySet keySet;
	private Image sprite;
	private Vector2D spriteSize, size;
	private KeyHandler keyHandler;

	/*
	 * Kreiert einen neuen Player.
	 */
	public Player(KeySet keySet, String imageLocation) throws InterruptedException {
		this.keySet = keySet;
		spriteSize = new Vector2D(0d, 0d);
		size = new Vector2D(40d, 40d);

		loadImage(imageLocation);

		keyHandler = new KeyHandler();

		velocity = new Vector2D(0d, 0d);
		position = new Vector2D(0d, 0d);
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

				spriteSize.setX(sprite.getWidth(RenderLogic.instance()));
				spriteSize.setY(sprite.getHeight(RenderLogic.instance()));
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

		g.drawImage(sprite, (int) Math.round(position.x), (int) Math.round(position.y), (int) Math.round(size.x),
				(int) Math.round(size.y), observer);
	}
	
	@Override
	public void draw(Graphics g) {
		draw(g, RenderLogic.instance());
	}

	@Override
	public void move() {
		position.add(velocity);

		if (position.x > RenderLogic.WIDTH)
			position.x = 0;
		else if (position.x < 0)
			position.x = RenderLogic.WIDTH;

		if (position.y < size.getY() / 2)
			position.y = size.getY();
		else if (position.y > RenderLogic.HEIGHT + size.getY() / 2)
			RenderLogic.endGame(this);
	}

	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == keySet.getRight())
				if (velocity.equals(DOWN))
					velocity.set(RIGHT_DOWN);
				else if (velocity.equals(UP))
					velocity.set(RIGHT_UP);
				else
					velocity.set(RIGHT);
			else if (e.getKeyCode() == keySet.getLeft())
				if (velocity.equals(DOWN))
					velocity.set(LEFT_DOWN);
				else if (velocity.equals(UP))
					velocity.set(LEFT_UP);
				else
					velocity.set(LEFT);

			if (e.getKeyCode() == keySet.getUp())
				velocity.set(UP);
			else if (e.getKeyCode() == keySet.getDown())
				velocity.set(DOWN);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.getKeyCode() == keySet.getRight() && velocity.equals(RIGHT))
					|| (e.getKeyCode() == keySet.getLeft() && velocity.equals(LEFT)))
				velocity.set(STATIONARY_X);

			if ((e.getKeyCode() == keySet.getUp() && velocity.equals(UP))
					|| (e.getKeyCode() == keySet.getDown() && velocity.equals(DOWN)))
				velocity.set(STATIONARY_Y);
		}
	}

	public enum KeySet {
		WASD(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S),
		ARROWS(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);

		KeySet(int left, int right, int up, int down) {
			this.left = left;
			this.right = right;
			this.up = up;
			this.down = down;
		}

		private final int left, right, up, down;

		public int getLeft() {
			return left;
		}

		public int getRight() {
			return right;
		}

		public int getUp() {
			return up;
		}

		public int getDown() {
			return down;
		}
	}

	public KeyHandler getKeyHandlder() {
		return keyHandler;
	}

	public double getX() {
		return position.x;
	}

	public double getY() {
		return position.y;
	}

	public void setX(double x) {
		position.x = x;
	}

	public void setY(double y) {
		position.y = y;
	}

	public double getHeight() {
		return size.getY();
	}
}
