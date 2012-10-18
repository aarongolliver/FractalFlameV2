/**
 * This class represets a set of colors associated with a point.
 * 
 * It holds the value of red, green, and blue in doubles.
 * 
 * red, green, and blue should all be between 0 and 1.
 * 
 * @author gollivam. Created Sep 10, 2012.
 */
public final class ColorSet {
	/**
	 * r, g, b should all be betwen 0 and 1. They all start at 0 (ie, black)
	 */
	double	r	= 0, g = 0, b = 0;
	
	/**
	 * Creats a new ColorSet with fields initialized to r, g, and b
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 */
	public ColorSet(final double r, final double g, final double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Creats a new ColorSet with all fields initialized to c
	 * 
	 * @param c
	 *            value to set all fields of the ColorSet
	 */
	public ColorSet(final double c) {
		this(c, c, c);
	}
	
	/**
	 * averages the current values of the ColorSet with each input value
	 * respectivily.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 */
	public final void hit(final double r, final double g, final double b) {
		this.r += r;
		this.r /= 2.0;
		
		this.g += g;
		this.g /= 2.0;
		
		this.b += b;
		this.b /= 2.0;
	}
	
	/**
	 * averages the current values of the ColorSet with the input color set
	 * 
	 * @param c
	 *            ColorSet to average the current ColorSet with
	 */
	public void hit(final ColorSet c) {
		hit(c.r, c.g, c.b);
	}
	
	public final String toString() {
		return "(" + r + "," + g + "," + b + ")";
		
	}
}
