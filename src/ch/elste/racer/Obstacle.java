package ch.elste.racer;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import ch.elste.racer.interfaces.Drawable;
import ch.elste.racer.interfaces.Movable;

public class Obstacle implements Drawable, Movable {
	private double x, y;
	private final double startY;
	private double width, height;
	private double dy;

	/**
	 * Kreiert ein neues Obstacle Objekt im Punkt <code>(x, y)</code> mit gegebener
	 * Breite und Höhe.
	 * 
	 * @param x
	 *            die x-Position
	 * @param y
	 *            die y-Position
	 * @param width
	 *            die Breite
	 * @param height
	 *            die höhe
	 */
	public Obstacle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.startY = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Kreiert ein Obstacle Objekt im Punkt <code>(x, y)</code> mit gegebener Breite
	 * und zufälliger Höhe.
	 * 
	 * @param x
	 *            die x-Position
	 * @param y
	 *            die y-Position
	 * @param width
	 *            die Breite
	 * 
	 * @see #Obstacle(double, double, double, double)
	 */
	public Obstacle(double x, double y, double width) {
		this(x, y, width, Math.round(Math.random() * 3) + 5);
	}

	/**
	 * Kreiert ein neues Obstacle im Punkt <code>(x, y)</code> mit zufälliger Breite
	 * und Höhe.
	 * 
	 * @param x
	 *            die x-Position
	 * @param y
	 *            die y-Position
	 * 
	 * @see #Obstacle(double, double, double)
	 * @see #Obstacle(double, double, double, double)
	 */
	public Obstacle(double x, double y) {
		this(x, y, Math.round(Math.random() * (RenderLogic.WIDTH / 4d)) + RenderLogic.WIDTH / 8);
	}

	/**
	 * Kreiert ein neues Obstacle in der Mitte des Bildschirms mit zufälliger Breite
	 * und Höhe.
	 * 
	 * @see #Obstacle(double, double)
	 * @see #Obstacle(double, double, double)
	 * @see #Obstacle(double, double, double, double)
	 */
	public Obstacle() {
		this(RenderLogic.WIDTH / 2, RenderLogic.HEIGHT / 2);
	}

	@Override
	public void draw(Graphics g) {
		draw(g, RenderLogic.instance());
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		// g.drawLine((int) Math.round(x - width / 2), (int) Math.round(y - height / 2),
		// (int) Math.round(x + width / 2),
		// (int) Math.round(y - height / 2));

		g.fillRect((int) Math.round(x - width / 2), (int) Math.round(y - height / 2), (int) Math.round(width),
				(int) Math.round(height));
		
		move();
	}

	@Override
	public void move() {
		dy = RenderLogic.OBSTACLE_SPEED;
		
		if (y < RenderLogic.HEIGHT + height)
			y += dy * RenderLogic.getDeltaTime();
		else
			y = startY;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return x;
	}
	
	public double getWidth() {
		return width;
	}
}
