package uk.ac.icappsoc.appsocthree.accel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/** A ball that expects {@link #setGravity(float, float)} and consists of a bouncing ball. */
public class BouncyBallView extends View {

	// position, velocity and acceleration of ball
	private float x, y;
	private float vx, vy;
	private float ax, ay;
	
	// 1 radian = 57.2957795 degrees
	private static final float DEGREES_PER_RADIAN = 57.2957795f;
	private static final float FRICTION = 0.98f;
	private static final float RESTITUTION = 0.9f;
	
	private float radius;
	private Paint paint;
	
	public BouncyBallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		float density = getResources().getDisplayMetrics().density;
		radius = density * 50;
		
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(3 * density);
	}
	
	/**
	 * Updates the latest gravity values.
	 */
	public void setGravity(float gravx, float gravy){
		this.ax = gravx;
		this.ay = gravy;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		// update ball dynamics
		vx += ax;
		vy += ay;
		
		x += vx;
		y += vy;
		
		vx *= FRICTION;
		vy *= FRICTION;
		
		// collide with view boundaries
		if(x < radius){
			x = radius;
			vx *= -RESTITUTION;
		} else if(x > getWidth() - radius){
			x = getWidth() - radius;
			vx *= -RESTITUTION;
		}
		if(y < radius){
			y = radius;
			vy *= -RESTITUTION;
		} else if(y > getHeight() - radius){
			y = getHeight() - radius;
			vy *= -RESTITUTION;
		}
		
		// draw the ball
		canvas.drawCircle(x, y, radius, paint);
		// draw the direction of our acceleration
		drawGravDirection(canvas, getWidth() / 2, getHeight() / 2);
		
		// draw us again ASAP!
		invalidate();
	}

	private void drawGravDirection(Canvas canvas, float originX, float originY){
		float gravAngleDegrees = (float) (Math.atan2(ay, ax)) * DEGREES_PER_RADIAN;
		float gravMag = (float) Math.sqrt(ax * ax + ay * ay);
		
		// save canvas for future Canvas#restore() 'cause we'll be messing with it
		canvas.save();
		
		// offset start to origin
		canvas.translate(originX, originY);
		// rotate around origin
		canvas.rotate(gravAngleDegrees);
		// draw straight line (which will end up with the angle of the previous rotation)
		canvas.drawLine(0, 0, gravMag * radius / 5, 0, paint);
		
		// offset to tip of 'arrow'
		canvas.translate(gravMag * radius / 5, 0);
		// draw arrow tips
		canvas.rotate(135);
		canvas.drawLine(0, 0, radius / 5, 0, paint);
		canvas.rotate(90);
		canvas.drawLine(0, 0, radius / 5, 0, paint);
		
		// restore canvas to normal
		canvas.restore();
	}
}
