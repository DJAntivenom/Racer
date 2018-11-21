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
				RenderLogic.render();
				RenderLogic.renderable = false;
			}
		};

		loadingWorker.execute();
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		move();
		g.drawImage(sprite, 400, 400, observer);
//		g.drawImage(sprite, (int) Math.round(position.getX()), (int) Math.round(position.getY()),
//				(int) Math.round(size.getX()), (int) Math.round(size.getY()), observer);
	}

	@Override
	public void move() {
		position.add(velocity);

		if (position.getX() > RenderLogic.WIDTH)
			position.setX(0);
		else if (position.getX() < 0)
			position.setX(RenderLogic.WIDTH);

		if (position.getY() < size.getY() / 2)
			position.setY(size.getY());
		else if (position.getY() > RenderLogic.HEIGHT + size.getY() / 2)
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
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

	public void setX(double x) {
		position.setX(x);
	}

	public void setY(double y) {
		position.setY(y);
	}

	public double getHeight() {
		return size.getY();
	}
	
	public Image getSprite() {
		return sprite;
	}
}
