import java.util.concurrent.ThreadLocalRandom;

import processing.core.PApplet;

public final class FractalFlameV2 extends PApplet {
	ThreadLocalRandom	r		= ThreadLocalRandom.current();
	
	int					iters	= 0;
	
	public static final void main(final String args[]) {
		System.out.println(java.lang.Runtime.getRuntime().maxMemory());
		PApplet.main(new String[] { "--present", "FractalFlameV2" });
	}
	
	public final void setup() {
		size(GLB.swid, GLB.shei);
		background(0);
		fill(0);
		stroke(0);
		GLB.newHistogram();
		GLB.reset();
	}
	
	public final void keyPressed() {
		if ((key == 'r') || (key == 'R')) {
			GLB.reset();
			return;
		}
		
		if ((key == 'f') || (key == 'F')) {
			GLB.enableVariations = !GLB.enableVariations;
			GLB.resetHistogram();
		}
		
		GLB.stopThreads();
		
		if ((key == 'h') || (key == 'H')) {
			GLB.ss = GLB.ss != GLB.ssMAX ? GLB.ssMAX : GLB.ssMIN;
			GLB.ssSquared = GLB.ss * GLB.ss;
			GLB.hwid = GLB.swid * GLB.ss;
			GLB.hhei = GLB.shei * GLB.ss;
			GLB.newHistogram();
			GLB.resetHistogram();
		}
		
		if ((key == 'c') || (key == 'C')) {
			GLB.resetAffineColorMap();
			GLB.resetHistogram();
		}
		
		if ((key == 'g') || (key == 'G')) {
			GLB.resetGamma();
		}
		
		if ((key == 't') || (key == 'T')) {
			GLB.nThreads = (GLB.nThreads == GLB.maxThreads) ? 1 : GLB.maxThreads;
		}
		
		if ((key == '-') || (key == '_')) {
			GLB.cameraXShrink *= 1.05;
			GLB.cameraYShrink *= 1.05;
			GLB.resetHistogram();
		}
		
		if ((key == '+') || (key == '=')) {
			GLB.cameraXShrink *= .95;
			GLB.cameraYShrink *= .95;
			GLB.resetHistogram();
		}
		
		if (keyCode == UP) {
			GLB.cameraYOffset += .05 * GLB.cameraYShrink;
			GLB.resetHistogram();
		}
		if (keyCode == DOWN) {
			GLB.cameraYOffset -= .05 * GLB.cameraYShrink;
			GLB.resetHistogram();
		}
		
		if (keyCode == LEFT) {
			GLB.cameraXOffset += .05 * GLB.cameraXShrink;
			GLB.resetHistogram();
		}
		
		if (keyCode == RIGHT) {
			GLB.cameraXOffset -= .05 * GLB.cameraXShrink;
			GLB.resetHistogram();
		}
		GLB.startThreads();
		
	}
	
	public final void draw() {
		if (frameCount <= 5) {
			loadPixels();
		}
		
		double maxA = 0;
		
		for (int y = 0; y < GLB.hhei; y++) {
			final int hy_index = y * GLB.hwid;
			final int py = y / GLB.ss;
			for (int x = 0; x < GLB.hwid; x++) {
				final int px = x / GLB.ss;
				
				final int h_index = 4 * (x + hy_index);
				final double r = GLB.h.histo[h_index + 0];
				final double g = GLB.h.histo[h_index + 1];
				final double b = GLB.h.histo[h_index + 2];
				final double a = GLB.h.histo[h_index + 3];
				
				final int s_index = 4 * (px + py * GLB.swid);
				GLB.image[s_index + 0] += r;
				GLB.image[s_index + 1] += g;
				GLB.image[s_index + 2] += b;
				GLB.image[s_index + 3] += a;
				
				// grab the alpha of the current image pixel to see if it's larger than any other pixel
				final double imga = GLB.image[s_index + 3];
				maxA = (maxA >= imga) ? maxA : imga;
			}
		}
		// maxA holds the sum of each histogram-block per pixel, so we divide the sum by the number of bins per pixel
		// (supersamples squared) to get the average
		maxA /= (GLB.ssSquared);
		
		final double logMaxA = Math.log(maxA);
		
		for (int y = 0; y < GLB.shei; y++) {
			for (int x = 0; x < GLB.swid; x++) {

				final int i_index = (x + y * GLB.swid);
				final int s_index = 4 * i_index;
				double a_avg = GLB.image[s_index + 3] / (GLB.ssSquared);
				
				if (a_avg > 1) {
					final double r_avg = GLB.image[s_index + 0] / (GLB.ssSquared);
					final double g_avg = GLB.image[s_index + 1] / (GLB.ssSquared);
					final double b_avg = GLB.image[s_index + 2] / (GLB.ssSquared);
					double color_scale_factor = Math.log(a_avg) / logMaxA;
					if (GLB.gamma != 1) {
						color_scale_factor = Math.pow(color_scale_factor, 1 / GLB.gamma);
					}
					
					final short a = 0xFF;
					final short r = (short) ((r_avg * color_scale_factor) * 0xFF);
					final short g = (short) ((g_avg * color_scale_factor) * 0xFF);
					final short b = (short) ((b_avg * color_scale_factor) * 0xFF);
					
					pixels[i_index] = (a << 24) | (r << 16) | (g << 8) | (b << 0);
				} else {
					pixels[i_index] = 0xFF << 24;
				}
				GLB.image[s_index + 0] = 0;
				GLB.image[s_index + 1] = 0;
				GLB.image[s_index + 2] = 0;
				GLB.image[s_index + 3] = 0;
			}
		}
		
		updatePixels();
		if (((frameCount % 3) == 0) && (GLB.ss == GLB.ssMAX)) {
			saveFrame(GLB.uFlameID + ".bmp");
		}
	}
}
