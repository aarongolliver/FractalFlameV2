/**
 * I've designed this histogram to be allocated as fast as possible. It would be
 * much easier to reason about if they histogram was a 3D array. IE:
 * histogram[x][y][color] but when it is so large (1920x1080x4) it takes an
 * unreasonable time to allocate the space for it. By making it a 1D array, it
 * allocates almost instantly, and access to cells is much faster.
 * 
 * To get the cell in the 1D array in the same way as the 3D array, you the
 * transformation is
 * 
 * histogram[x][y][color]
 * to
 * histogram[total_colors * (x + total_colors * y * width) + color]
 * 
 * so if your array is 1920x1080x4 the formula becomes
 * f(x,y,c) = histogram[4 * (x + y *4 * 1920) + c]
 * 
 * @author gollivam.
 *         Created Oct 18, 2012.
 */
public final class Histogram {
	double[]	histo;
	
	public Histogram() {
		histo = new double[GLB.hwid * GLB.hhei * 4];
		for (int i = 0; i < histo.length; i++) {
			histo[0] = 0;
		}
	}
	
	public void hit(final Vec2D p, final ColorSet c) {
		final int x = (int) (((p.x + GLB.cameraXOffset) * (GLB.hwid / GLB.cameraXShrink)) + (GLB.hwid / 2));
		final int y = (int) (((p.y + GLB.cameraYOffset) * (GLB.hhei / GLB.cameraYShrink)) + (GLB.hhei / 2));
		
		if ((x < 0) || (x >= GLB.hwid) || (y < 0) || (y >= GLB.hhei))
			return;
		
		histo[(4 * x) + (4 * y * GLB.hwid) + 0] += c.r;
		histo[(4 * x) + (4 * y * GLB.hwid) + 0] /= 2;
		histo[(4 * x) + (4 * y * GLB.hwid) + 1] += c.g;
		histo[(4 * x) + (4 * y * GLB.hwid) + 1] /= 2;
		histo[(4 * x) + (4 * y * GLB.hwid) + 2] += c.b;
		histo[(4 * x) + (4 * y * GLB.hwid) + 2] /= 2;
		histo[(4 * x) + (4 * y * GLB.hwid) + 3] += 1;
	}
}
