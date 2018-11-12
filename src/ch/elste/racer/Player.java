package ch.elste.racer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import ch.elste.racer.interfaces.Drawable;

public class Player implements Drawable {
	public static final int RIGHT = 1;
	public static final int LEFT = 2;
	public static final int STATIONARY = 0;

	private Image sprite;
	private double x, y;
	private double dx;
	private double speed;
	private int direction;
	private Dimension spriteSize, size;
	private ImageObserver observer;
	private KeyHandler keyHandler;

	/*
	 * Kreiert einen neuen Player.
	 */
	public Player() throws InterruptedException {
		observer = new ImageObserver() {
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		};

		spriteSize = new Dimension(0, 0);
		size = new Dimension(20, 20);

		speed = 1;

		loadImage();

		keyHandler = new KeyHandler();
	}

	/**
	 * L�dt das Bild aus dem Speicher.
	 * 
	 * @throws InterruptedException
	 */
	public void loadImage() throws InterruptedException {
		Thread loadingThread = new Thread(() -> {
			sprite = new ImageIcon(getClass().getResource("data/Player.png")).getImage();

			spriteSize.setSize(sprite.getWidth(observer), sprite.getHeight(observer));
		});
		loadingThread.start();
		loadingThread.join();
		System.out.println("joined");
	}

	@Override
	public void draw(Graphics2D g2d, ImageObserver observer) {
		move();
		
		g2d.drawImage(sprite, (int) Math.round(x - size.width / 2), (int) Math.round(y - size.height / 2), size.width,
				size.height, observer);
	}

	@Override
	public void draw(Graphics2D g2d) {
		draw(g2d, observer);
	}

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
		else if(x < 0)
			x = RenderLogic.WIDTH;
		else
			x += dx * RenderLogic.getDeltaTime();
	}

	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D)
				direction = RIGHT;
			else if (e.getKeyCode() == KeyEvent.VK_A)
				direction = LEFT;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A)
				direction = STATIONARY;
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
