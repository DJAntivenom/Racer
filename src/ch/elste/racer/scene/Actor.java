package ch.elste.racer.scene;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import ch.elste.racer.RenderLogic;
import ch.elste.racer.interfaces.Drawable;
import ch.elste.racer.interfaces.Movable;
import ch.elste.racer.util.Vector2D;

public class Actor implements Drawable, Movable {
	public static final Vector2D RIGHT_DOWN = new Vector2D(Math.sqrt(2), (float) (Math.PI * 7 / 4));
	public static final Vector2D RIGHT_UP = new Vector2D(Math.sqrt(2), (float) (Math.PI / 4));
	public static final Vector2D LEFT_DOWN = new Vector2D(Math.sqrt(2), (float) (Math.PI * 5 / 4));
	public static final Vector2D LEFT_UP = new Vector2D(Math.sqrt(2), (float) (Math.PI * 7 / 4));
	public static final Vector2D RIGHT = new Vector2D(1d, 0d);
	public static final Vector2D LEFT = new Vector2D(-1d, 0d);
	public static final Vector2D UP = new Vector2D(0d, 1d);
	public static final Vector2D DOWN = new Vector2D(0d, 1d);
	public static final Vector2D STATIONARY_X = new Vector2D(0d, 0d);
	public static final Vector2D STATIONARY_Y = new Vector2D(0d, 0d);

	protected Vector2D position;
	protected Vector2D velocity;

	@Override
	public void move() {
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		draw(g, RenderLogic.instance());
	}

	public Vector2D getPosition() {
		return position;
	}
}
