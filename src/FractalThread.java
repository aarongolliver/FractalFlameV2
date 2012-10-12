import java.util.concurrent.ThreadLocalRandom;

public final class FractalThread extends Thread {
	
	final ThreadLocalRandom	r	= ThreadLocalRandom.current();
	
	private Vec2D			p	= new Vec2D(r.nextDouble(-1, 1), r.nextDouble(-1, 1));
	private final ColorSet	col	= new ColorSet(0);
	
	public final void run() {
		System.out.println("Creating thread");
		
		while (true) {
			if (GLB.threadStopSignal == true)
				return;
			
			int j = r.nextInt(0, GLB.aProbability.length);
			j = GLB.aProbability[j];
			
			final Vec2D pAffined = affine(p, GLB.a[j]);
			
			if (GLB.enableVariations) {
				Vec2D vSum = new Vec2D(0, 0);
				vSum = Vec2D.add(Vec2D.mul(v0(pAffined), GLB.vWeight[0]), vSum);
				vSum = Vec2D.add(Vec2D.mul(v1(pAffined), GLB.vWeight[1]), vSum);
				vSum = Vec2D.add(Vec2D.mul(v2(pAffined), GLB.vWeight[2]), vSum);
				vSum = Vec2D.add(Vec2D.mul(v5(pAffined), GLB.vWeight[5]), vSum);
				vSum = Vec2D.add(Vec2D.mul(v6(pAffined), GLB.vWeight[6]), vSum);
				
				p = new Vec2D(vSum);
			} else {
				p = new Vec2D(pAffined);
			}
			
			col.hit(GLB.affineColor[j]);
			
			GLB.h.hit(p, col);
		}
	}
	
	private final Vec2D affine(final Vec2D p, final double[][] a) {
		
		final double x = (p.x * a[0][0]) + (p.y * a[0][1]) + (a[0][2]);
		final double y = (p.x * a[1][0]) + (p.y * a[1][1]) + (a[1][2]);
		
		return new Vec2D(x, y);
	}
	
	private final Vec2D v0(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		return new Vec2D(x, y);
	}
	
	private final Vec2D v1(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		return new Vec2D(Math.cos(x), Math.sin(y));
	}
	
	private final Vec2D v2(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		final double rsq = ((x * x) + (y * y));
		return new Vec2D(y / rsq, x / rsq);
	}
	
	private final Vec2D v3(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		final double rsq = ((x * x) + (y * y));
		final double r = Math.sqrt(rsq);
		return new Vec2D((x * Math.sin(rsq)) - (y * Math.cos(rsq)), (x * Math.cos(rsq)) + (y * Math.sin(rsq)));
	}
	
	private final Vec2D v4(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		final double rsq = ((x * x) + (y * y));
		final double r = Math.sqrt(rsq);
		return new Vec2D((1 / r) * (x - y) * (x + y), 2 * x * y);
	}
	
	private final Vec2D v5(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		final double rsq = ((x * x) + (y * y));
		final double r = Math.sqrt(rsq);
		final double theta = Math.atan(x / y);
		return new Vec2D(theta / Math.PI, r - 1);
	}
	
	private final Vec2D v6(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		final double rsq = ((x * x) + (y * y));
		final double r = Math.sqrt(rsq);
		final double theta = Math.atan(x / y);
		return new Vec2D(r * Math.sin(theta + r), r * Math.cos(theta - r));
	}
	
	private final Vec2D v13(final Vec2D p) {
		final double x = p.x;
		final double y = p.y;
		final double rsq = ((x * x) + (y * y));
		final double r = Math.sqrt(rsq);
		final double theta = Math.atan(x / y);
		final double omega = (this.r.nextDouble() > .5) ? 0 : Math.PI;
		
		return new Vec2D(Math.sqrt(r) * Math.sin((theta / 2) + omega), Math.sqrt(r) * Math.cos((theta / 2) + omega));
	}
	
}
