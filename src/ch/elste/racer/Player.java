package ch.elste.racer;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import ch.elste.racer.interfaces.Drawable;

public class Player implements Drawable {
	private Image sprite;
	private double x,y;
	
	/*
	 * Kreiert einen neuen Player.
	 */
	public Player() throws InterruptedException {
		loadImage();
	}
	
	/**
	 * Lädt ein 
	 * @throws InterruptedException
	 */
	public void loadImage() throws InterruptedException {
		Thread loadingThread = new Thread(()->{
			sprite = new ImageIcon("/data/player.png").getImage();
		});
		loadingThread.start();
		loadingThread.join();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(sprite, (int) Math.round(x), (int) Math.round(y), null);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
