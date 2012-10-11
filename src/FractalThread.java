import java.util.concurrent.ThreadLocalRandom;
public final class FractalThread extends Thread{
	final ThreadLocalRandom r = ThreadLocalRandom.current();
	
	//int iters = 0;
	private Vec2D p = new Vec2D(r.nextDouble(-1,1), r.nextDouble(-1,1));
	private final ColorSet col = new ColorSet(0);
	
	public final void run(){
		System.out.println("Creating thread");
		try {
			sleep(100);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
		while(true){
			if(GLB.threadStopSignal == true){
				//iters = 0;
				return;
			}
			
			int j = r.nextInt(0, GLB.aProbability.length);
			j = GLB.aProbability[j];
			
			final Vec2D pAffined = affine(p, GLB.a[j]);
			
			if(GLB.enableVariations){
				Vec2D vSum = new Vec2D(0, 0);
				vSum = Vec2D.add(Vec2D.mul(v0(pAffined), GLB.vWeight[0]), vSum);
				vSum = Vec2D.add(Vec2D.mul(v1(pAffined), GLB.vWeight[1]), vSum);
				//vSum = Vec2D.add(Vec2D.mul(v2(pAffined), GLB.vWeight[1]), vSum);
				vSum = Vec2D.add(Vec2D.mul(v5(pAffined), GLB.vWeight[5]), vSum);
				//vSum = Vec2D.mul(Vec2D.add(vSum, v5(pAffined)), GLB.vWeight[3]);
				vSum = Vec2D.add(Vec2D.mul(v6(pAffined), GLB.vWeight[6]), vSum);
				

				p = new Vec2D(vSum);
			} else {
				p = new Vec2D(pAffined);
			}
			
			/*
			double flip = r.nextDouble();
			 
			if(flip <= 1/.0/4.0){
				p = Vec2D.mul(p, -1, 1);
			} else if(flip <= 2.0/4.0){
				p = Vec2D.mul(p, 1, -1);
			} else if(flip <= 3.0/4.0){
				p = Vec2D.mul(p, -1, -1);
			}
			*/
			col.hit(GLB.affineColor[j]);

			//if(iters >= 20)
				GLB.h.hit(p, col);
			
			//iters++;
		}
	}
	
	private final Vec2D affine(Vec2D p, double[][] a){

		double x = (p.x * a[0][0]) + (p.y * a[0][1]) + (a[0][2]);
		double y = (p.x * a[1][0]) + (p.y * a[1][1]) + (a[1][2]);
		
		return new Vec2D(x, y);
	}
	
	private final Vec2D v0(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		return new Vec2D(x, y);
	}
	
	private final Vec2D v1(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		return new Vec2D(Math.cos(x),Math.sin(y));
	}
	
	private final Vec2D v2(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		final double rsq = (x * x + y * y);
		return new Vec2D(y/rsq,x/rsq);
	}
	
	private final Vec2D v3(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		final double rsq = (x * x + y * y);
		final double r = Math.sqrt(rsq);
		return new Vec2D(x*Math.sin(rsq)-y*Math.cos(rsq),
				         x*Math.cos(rsq)+y*Math.sin(rsq));
	}

	private final Vec2D v4(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		final double rsq = (x * x + y * y);
		final double r = Math.sqrt(rsq);
		return new Vec2D(1/r * (x - y) * (x + y),
				         2 * x * y);
	}

	private final Vec2D v5(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		final double rsq = (x * x + y * y);
		final double r = Math.sqrt(rsq);
		final double theta = Math.atan(x/y);
		return new Vec2D(theta/Math.PI,
				         r - 1);
	}

	private final Vec2D v6(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		final double rsq = (x * x + y * y);
		final double r = Math.sqrt(rsq);
		final double theta = Math.atan(x/y);
		return new Vec2D(r * Math.sin(theta + r),
				         r * Math.cos(theta - r));
	}
	
	private final Vec2D v13(Vec2D p){
		final double x = p.x;
		final double y = p.y;
		final double rsq = (x * x + y * y);
		final double r = Math.sqrt(rsq);
		final double theta = Math.atan(x/y);
		final double omega = (this.r.nextDouble() > .5) ? 0 : Math.PI;

		return new Vec2D(Math.sqrt(r) * Math.sin(theta/2 + omega),
				         Math.sqrt(r) * Math.cos(theta/2 + omega));
		
	}
	
}
