public final class Vec2D
{
	public final double	x, y;
	
	public Vec2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2D(final Vec2D v) {
		this(v.x, v.y);
	}
	
	public static final Vec2D add(final Vec2D v0, final Vec2D v1) {
		final double x = v0.x + v1.x;
		final double y = v0.y + v1.y;
		
		return new Vec2D(x, y);
	}
	
	public static final Vec2D mul(final Vec2D v0, final double n) {
		final double x = v0.x * n;
		final double y = v0.y * n;
		
		return new Vec2D(x, y);
	}
	
	public final String toString() {
		return ("(" + x + "," + y + ")");
	}
	
	public static final Vec2D mul(final Vec2D p, final double x, final double y) {
		return new Vec2D(p.x * x, p.y * y);
	}
}
