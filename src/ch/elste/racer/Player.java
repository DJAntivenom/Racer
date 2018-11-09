package ch.elste.racer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import ch.elste.racer.interfaces.Drawable;

public class Player implements Drawable {
	private Image sprite;
	private double x, y;
	private Dimension spriteSize, size;
	private ImageObserver observer;
	private ActionListener inputHandler;

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

		loadImage();

		inputHandler = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}

	/**
	 * Lädt das Bild aus dem Speicher.
	 * @throws InterruptedException
	 */
	public void loadImage() throws InterruptedException {
		Thread loadingThread = new Thread(()->{
			sprite = new ImageIcon(getClass().getResource("data/Player.png")).getImage();
			
			spriteSize.setSize(sprite.getWidth(observer), sprite.getHeight(observer));
		});
		loadingThread.start();
		loadingThread.join();
		System.out.println("joined");
	}

	@Override
	public void draw(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(sprite, (int) Math.round(x - size.width / 2), (int) Math.round(y - size.height / 2),
				size.width, size.height, observer);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		draw(g2d, observer);
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
