package ch.elste.racer.scene;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import ch.elste.racer.RenderLogic;
import ch.elste.racer.interfaces.Drawable;
import ch.elste.racer.interfaces.Movable;
import ch.elste.racer.util.Vector2D;

public class Actor implements Drawable, Movable {
	public static final Vector2D RIGHT_DOWN = new Vector2D(Math.sqrt(2), (float) (Math.PI * 1 / 4));
	public static final Vector2D RIGHT_UP = new Vector2D(Math.sqrt(2), (float) (Math.PI * 7 / 4));
	public static final Vector2D LEFT_DOWN = new Vector2D(Math.sqrt(2), (float) (Math.PI * 3 / 4));
	public static final Vector2D LEFT_UP = new Vector2D(Math.sqrt(2), (float) (Math.PI * 5 / 4));
	public static final Vector2D RIGHT = new Vector2D(1d, 0d);
	public static final Vector2D LEFT = new Vector2D(-1d, 0d);
	public static final Vector2D UP = new Vector2D(0d, -1d);
	public static final Vector2D DOWN = new Vector2D(0d, 1d);
	public static final Vector2D STATIONARY = new Vector2D(0d, 0d);

	protected Vector2D position;
	protected Vector2D velocity;
	protected Vector2D size;
	protected double speed;

	@Override
	public void move() {
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		draw(g, RenderLogic.instance());
	}

	/**
	 * Überprüft, ob der Actor mit einem anderen Actor kollidiert. In anderen Worten
	 * wird überprüft, ob die Koordinaten überlappen.
	 * 
	 * @param a
	 *            der andere Actor
	 * @return
	 *         <li>true, wenn die Actors überlappen</li>
	 *         <li>false, wenn die Actors nicht überlappen</li>
	 */
	public boolean collides(Actor a) {
		return collidesX(a) && collidesY(a);
	}

	private boolean collidesX(Actor a) {
		return Math.abs(position.getX() - a.position.getX()) - size.getX() / 2 - a.size.getX() / 2 < 0;
	}

	private boolean collidesY(Actor a) {
		return Math.abs(position.getY() - a.position.getY()) - size.getY() / 2 - a.size.getY() / 2 < 0;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
