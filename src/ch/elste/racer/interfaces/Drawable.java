package ch.elste.racer.interfaces;

import java.awt.Graphics2D;

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
}
