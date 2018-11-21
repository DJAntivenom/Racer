package ch.elste.racer.scene;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import ch.elste.racer.RenderLogic;
import ch.elste.racer.util.Vector2D;

public class Obstacle extends Actor {
	private double width, height;

	/**
	 * Kreiert ein neues Obstacle Objekt im Punkt <code>(x, y)</code> mit gegebener
	 * Breite und H�he.
	 * 
	 * @param x
	 *            die x-Position
	 * @param y
	 *            die y-Position
	 * @param width
	 *            die Breite
	 * @param height
	 *            die h�he
	 */
	public Obstacle(double x, double y, double width, double height) {
		position = new Vector2D(x, y);
		velocity = new Vector2D(0d, 0d);
		
		
		this.width = width;
		this.height = height;
	}

	/**
	 * Kreiert ein Obstacle Objekt im Punkt <code>(x, y)</code> mit gegebener Breite
	 * und zuf�lliger H�he.
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
	 * Kreiert ein neues Obstacle im Punkt <code>(x, y)</code> mit zuf�lliger Breite
	 * und H�he.
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
	 * Kreiert ein neues Obstacle in der Mitte des Bildschirms mit zuf�lliger Breite
	 * und H�he.
	 * 
	 * @see #Obstacle(double, double)
	 * @see #Obstacle(double, double, double)
	 * @see #Obstacle(double, double, double, double)
	 */
	public Obstacle() {
		this(RenderLogic.WIDTH / 2, RenderLogic.HEIGHT / 2);
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		move();

		g.fillRect((int) Math.round(position.getX() - width / 2), (int) Math.round(position.getY() - height / 2),
				(int) Math.round(width), (int) Math.round(height));
	}

	@Override
	public void move() {
		if (position.getY() < RenderLogic.HEIGHT + height)
			position.add(Vector2D.scale(DOWN, RenderLogic.getDeltaTime() * RenderLogic.OBSTACLE_SPEED / 1000));
		else
			position.setY(-RenderLogic.HEIGHT);
	}

	public void setX(double x) {
		position.setX(x);
	}

	public double getX() {
		return position.getX();
	}

	public double getWidth() {
		return width;
	}
}
