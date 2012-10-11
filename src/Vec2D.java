public final class Vec2D {
	public final double x, y;
	
	public Vec2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vec2D(Vec2D v){
		this(v.x, v.y);
	}
	
	public static final Vec2D add(Vec2D v0, Vec2D v1){
		final double x = v0.x + v1.x;
		final double y = v0.y + v1.y;
		
		return new Vec2D(x, y);
	}
	
	public static final Vec2D mul(Vec2D v0, double n){
		final double x = v0.x * n;
		final double y = v0.y * n;
		
		return new Vec2D(x, y);
	}
	
	public final String toString(){
		return("(" + x + "," + y +")");
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param p
	 * @param i
	 * @param j
	 * @return
	 */
	public static final Vec2D mul(Vec2D p, double x, double y) {
		return new Vec2D(p.x * x, p.y * y);
	}
}
