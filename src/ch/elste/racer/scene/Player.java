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
	private Vector2D spriteSize;
	private KeyHandler keyHandler;

	public static final boolean COLLIDING = true;

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

		speed = 500;
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

		g.drawImage(sprite, (int) Math.round(position.getX() - size.getX() / 2),
				(int) Math.round(position.getY() - size.getY() / 2), (int) Math.round(size.getX()),
				(int) Math.round(size.getY()), observer);
	}

	@Override
	public void move() {
		position.add(Vector2D.scale(velocity, RenderLogic.getDeltaTime() * speed / 1000d));

		if (COLLIDING)
			checkMovementConstraints();

		if (position.getX() > RenderLogic.WIDTH - size.getX()/2)
			position.setX(RenderLogic.WIDTH - size.getX()/2);
		else if (position.getX() < size.getX()/2)
			position.setX(size.getX()/2);

		if (position.getY() < size.getY() / 2)
			position.setY(size.getY() / 2);
		else if (position.getY() > RenderLogic.HEIGHT + size.getY() / 2)
			RenderLogic.endGame(this);
	}

	private void checkMovementConstraints() {
		for (Obstacle o : RenderLogic.getObstacles()) {
			if (o.position.getY() < position.getY() && collides(o)) {
				position.setY(o.position.getY() + o.size.getY() / 2 + size.getY() / 2);
			} else if (o.position.getY() > position.getY() && collides(o)) {
				position.setY(o.position.getY() - o.size.getY() / 2 - size.getY() / 2);
			}
		}
	}

	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == keySet.getRight()) {
				if (velocity.equals(DOWN))
					velocity.set(RIGHT_DOWN);
				else if (velocity.equals(UP))
					velocity.set(RIGHT_UP);
				else
					velocity.set(RIGHT);
			} else if (e.getKeyCode() == keySet.getLeft()) {
				if (velocity.equals(DOWN))
					velocity.set(LEFT_DOWN);
				else if (velocity.equals(UP))
					velocity.set(LEFT_UP);
				else
					velocity.set(LEFT);
			}

			if (e.getKeyCode() == keySet.getUp()) {
				if (velocity.equals(LEFT))
					velocity.set(LEFT_UP);
				else if (velocity.equals(RIGHT))
					velocity.set(RIGHT_UP);
				else
					velocity.set(UP);
			} else if (e.getKeyCode() == keySet.getDown()) {
				if (velocity.equals(LEFT))
					velocity.set(LEFT_DOWN);
				else if (velocity.equals(RIGHT))
					velocity.set(RIGHT_DOWN);
				else
					velocity.set(DOWN);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.getKeyCode() == keySet.getRight()
					&& (velocity.equals(RIGHT) || velocity.equals(RIGHT_UP) || velocity.equals(RIGHT_DOWN)))
					|| (e.getKeyCode() == keySet.getLeft()
							&& (velocity.equals(LEFT) || velocity.equals(LEFT_UP) || velocity.equals(LEFT_DOWN)))) {
				velocity.setX(0);
			}

			if ((e.getKeyCode() == keySet.getUp()
					&& (velocity.equals(UP) || velocity.equals(RIGHT_UP) || velocity.equals(LEFT_UP)))
					|| (e.getKeyCode() == keySet.getDown()
							&& (velocity.equals(DOWN) || velocity.equals(RIGHT_DOWN) || velocity.equals(LEFT_DOWN)))) {
				velocity.setY(0);
			}
		}
	}

	public enum KeySet {
		WASD(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_SHIFT),
		ARROWS(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SPACE),
		JKLI(KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_B);

		KeySet(int left, int right, int up, int down, int boost) {
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
