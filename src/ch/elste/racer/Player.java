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
	private Image sprite;
	private double x, y;
	private double dx;
	private double speed;
	private Dimension spriteSize, size;
	private ImageObserver observer;

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

		speed = 60;

		loadImage();
	}

	/**
	 * Lädt das Bild aus dem Speicher.
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
		g2d.drawImage(sprite, (int) Math.round(x - size.width / 2), (int) Math.round(y - size.height / 2), size.width,
				size.height, observer);
	}

	@Override
	public void draw(Graphics2D g2d) {
		move();
		draw(g2d, observer);
	}

	public void move() {
		if (dx > 5)
			dx = 5;
		if (x < RenderLogic.WIDTH)
			x += dx;
		else
			x = 0;
	}

	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D)
				dx += speed * RenderLogic.getDeltaTime();
			else if(e.getKeyCode() == KeyEvent.VK_A)
				dx -= speed * RenderLogic.getDeltaTime();
		}
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
