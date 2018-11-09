package ch.elste.racer.interfaces;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

/**
 * Jedes Drawable Objekt ist von einem Graphics2D Objekt zeichenbar.
 * 
 * @author Dillon Elste
 *
 */
public interface Drawable {
	/**
	 * Die Funktion zum zeichnen des Drawable Objekts.
	 * 
	 * @param g2d
	 *            das Graphics2D Objekt.
	 */
	public abstract void draw(Graphics2D g2d);

	/**
	 * @see #draw(Graphics2D)
	 */
	public default void render(Graphics2D g2d) {
		draw(g2d);
	}

	/**
	 * Verhält sich wie {@link #draw(Graphics2D)}.
	 * 
	 * @param g2d
	 *            das Graphics2D Objekt
	 * @param observer
	 *            der ImageObserver
	 */
	void draw(Graphics2D g2d, ImageObserver observer);
}
