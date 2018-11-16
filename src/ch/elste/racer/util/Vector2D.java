package ch.elste.racer.util;

public class Vector2D {
	public double x, y;
	private double r, phi;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;

		this.r = Math.sqrt(x * x + y * y);
		this.phi = Math.asin(x / r);
	}

	public Vector2D(double r, float phi) {
		this.r = r;
		this.phi = phi;

		this.x = Math.cos(phi) * r;
		this.y = Math.sin(phi) * r;
	}

	public static Vector2D add(final Vector2D v1, final Vector2D v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}

	public static Vector2D scale(final Vector2D v, final double factor) {
		return new Vector2D(v.x * factor, v.y * factor);
	}

	public void add(Vector2D v) {
		this.x += v.x;
		this.y += v.y;
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
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setR(double r) {
		this.r = r;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}
}
