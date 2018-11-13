package ch.elste.racer.interfaces;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Jedes Drawable Objekt ist von einem Graphics Objekt zeichenbar.
 * 
 * @author Dillon Elste
 *
 */
public interface Drawable {
	/**
	 * Die Funktion zum zeichnen des Drawable Objekts.
	 * 
	 * @param g
	 *            das Graphics Objekt.
	 */
	public abstract void draw(Graphics g);

	/**
	 * @see #draw(Graphics)
	 */
	public default void render(Graphics g) {
		draw(g);
	}

	/**
	 * Verhält sich wie {@link #draw(Graphics)}.
	 * 
	 * @param g
	 *            das Graphics Objekt
	 * @param observer
	 *            der ImageObserver
	 */
	void draw(Graphics g, ImageObserver observer);
}
