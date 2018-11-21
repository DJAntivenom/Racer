package ch.elste.racer.util;

public class Vector2D {
	private double x, y;
	private double r, phi;

	/**
	 * Erstellt einen neuen {@link Vector2D Vektor} mit Koordinaten <code>x</code>
	 * und <code>y</code>.
	 * 
	 * @param x
	 * @param y
	 * 
	 * @see #Vector2D(double, float)
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.atan(y / x);
	}

	/**
	 * Erstellt einen neuen {@link Vector2D Vektor} mit Länge <code>r</code> und
	 * Winkel <code>phi</code>.
	 * 
	 * @param r
	 *            die Länge
	 * @param phi
	 *            die Phase
	 * 
	 * @see #Vector2D(double, double)
	 */
	public Vector2D(double r, float phi) {
		this.r = r;
		this.phi = phi;

		this.x = Math.cos(phi) * r;
		this.y = Math.sin(phi) * r;
	}

	/**
	 * Addiert 2 Vektoren ohne diese zu veändern.
	 * 
	 * @param v1
	 *            der erste Vektor
	 * @param v2
	 *            der zweite Vektor
	 * @return die Summe der Vektoren
	 */
	public static Vector2D add(final Vector2D v1, final Vector2D v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}

	/**
	 * Skaliert einen Vektor um <code>factor</code>.
	 * 
	 * @param v
	 *            der zu skalierende Vektor
	 * @param factor
	 *            der Faktor
	 * @return der skalierte Vektor
	 */
	public static Vector2D scale(final Vector2D v, final double factor) {
		return new Vector2D(v.x * factor, v.y * factor);
	}

	/**
	 * Addiert den Vektor v zu diesem und gibt den entstehenden Vektor zurück.
	 * 
	 * @param v
	 *            den zu addierenden Vektor
	 * @return einen neuen Vektor mit diesen Werten.
	 */
	public Vector2D add(final Vector2D v) {
		this.x += v.x;
		this.y += v.y;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.atan(y / x);
		return this;
	}

	/**
	 * Skaliert diesen Vektor v um <code>factor</code>.
	 * 
	 * @param factor
	 *            der Faktor
	 * @return diesen Vektor
	 */
	public Vector2D scale(final double factor) {
		x *= factor;
		y *= factor;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.atan(y / x);
		return this;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getR() {
		return r;
	}

	public double getPhi() {
		return phi;
	}

	public void setX(double x) {
		this.x = x;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.atan(y / x);
	}

	public void setY(double y) {
		this.y = y;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.atan(y / x);
	}

	public void setR(double r) {
		this.r = r;

		this.x = Math.cos(phi) * r;
		this.y = Math.sin(phi) * r;
	}

	public void setPhi(double phi) {
		this.phi = phi;

		this.x = Math.cos(phi) * r;
		this.y = Math.sin(phi) * r;
	}

	public void set(final Vector2D v) {
		this.x = v.x;
		this.y = v.y;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.atan(y / x);
	}

	@Override
	public String toString() {
		return String.format("Vector2D #%s %n x: %3.3f \t r: %3.3f %n y: %3.3f\t phi: %3.3f", super.toString(), x, r, y,
				phi);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(getClass()))
			return equals((Vector2D) obj);
		else
			return false;
	}

	private boolean equals(Vector2D v) {
		return (x == v.x && y == v.y);
	}
}
