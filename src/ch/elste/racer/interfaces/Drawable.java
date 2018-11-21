package ch.elste.racer.interfaces;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import ch.elste.racer.RenderLogic;

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
	public default void draw(Graphics g) {
		draw(g, RenderLogic.instance());
	}

	/**
	 * Verhält sich wie {@link #draw(Graphics)}.
	 * 
	 * @param g
	 *            das Graphics Objekt
	 * @param observer
	 *            der ImageObserver
	 */
	public void draw(Graphics g, ImageObserver observer);
}
