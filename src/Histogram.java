public final class Histogram
{
	double[]	histo;
	
	public Histogram() {
		histo = new double[GLB.hwid * GLB.hhei * 4];
		for (int i = 0; i < histo.length; i++) {
			histo[0] = 0;
		}
	}
	
	public void hit(Vec2D p, ColorSet c) {
		final int x = (int) ((p.x + GLB.cameraXOffset) * GLB.hwid / GLB.cameraXShrink + GLB.hwid / 2);
		final int y = (int) ((p.y + GLB.cameraYOffset) * GLB.hhei / GLB.cameraYShrink + GLB.hhei / 2);
		
		if (x < 0 || x >= GLB.hwid || y < 0 || y >= GLB.hhei)
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
