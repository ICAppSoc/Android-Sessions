package uk.ac.icappsoc.appsoctwo.split;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;

/**
 * An incomplete version of Split - the game.
 * Tap a ball to split it.
 * 
 * TASK - Make this a complete game, with win and lose states!
 * TASK - Give each ball its own, random color.
 * TASK - Split ball only on tap, not swipe, etc.
 * 			- See http://developer.android.com/training/gestures/detector.html
 */
public class SplitGameView extends View {

	/** Each ball has a position (x, y), velocity (vx, vy) and a radius. */
	private class Ball {
		private float x, y;
		private float vx, vy;
		private float radius;
		
		/** If not active, SplitGameView will not update its position. */
		private boolean active;
		
		public Ball(float x, float y, float vx, float vy, float radius, boolean active){
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.radius = radius;
			this.active = active;
		}
	}
	
	/** Gravity constant that felt right. Tweak to your heart's content. */
	private static final float G = 98f;
	/** Radius of the first ball. */
	private static final float START_RADIUS = 100f;
	/** Factor by which a ball's radius shrinks when split. */
	private static final float RADIUS_DECAY = 0.6f;
	/** The portion of velocity that is lost when a ball bounces off the ground. */
	private static final float FRICTION = 0.85f;
	/** Velocities of newly split balls. */
	private static final float HORIZONTAL_VELOCITY = 40f;
	private static final float VERTICAL_VELOCITY = 100f;
	
	/**
	 * Density scale.
	 * Since all drawing operations take pixel parameters,
	 * our view will look / behave differently on devices with different screen sizes.
	 * Scaling by a device's density is one way to alleviate this.
	 * See http://developer.android.com/guide/practices/screens_support.html for more.
	 */
	private float density;
	
	/** List of ball objects. */
	private List<Ball> balls;
	
	/** The Paint object that determines how each ball looks. */
	private Paint ballPaint;
	
	/**
	 * Absolute time (in milliseconds) of the last onDraw() call (i.e. frame).
	 * This allows us to measure delta-time (dt) each frame,
	 * and scale the rate at which positions / velocities are updated (integrated).
	 * This is necessary to ensure that our game behaves more or less the same
	 * even if the rate at which onDraw() is called (framerate / fps) fluctuates and
	 * is not constant (which is indeed pretty much always the case) - i.e. our
	 * game is "framerate independent."
	 * 
	 * NOTE: This is outside the scope of Android - it is applicable for game development in general.
	 * See https://www.scirra.com/tutorials/67/delta-time-and-framerate-independence for a more
	 * in-depth discussion of the matter.
	 */
	private long lastFrameMillis;
	
	// We override one of the custom view constructors, as usual..
	public SplitGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// Define how each ball will look like
		ballPaint = new Paint();
		ballPaint.setColor(Color.WHITE);
		ballPaint.setAntiAlias(true);
		
		// Retrieve the density of the device running the application
		density = getResources().getDisplayMetrics().density;
		
		// Set up our list, and put the first ball in there
		balls = new ArrayList<Ball>();
		/*
		 * NOTE: Slightly outside scope of the lesson, this may be do-able in other ways.
		 * 
		 * We want to position the ball on the center of the screen.. however,
		 * at this point in the View's life-cycle (in its constructor), it
		 * has not been assigned a size (been 'laid out'). We can attach
		 * an OnPreDrawListener to our View - it will be notified *right* before
		 * the system is ready to start drawing our View, i.e. we can be
		 * sure that it has been laid out by now, and has a width and height.
		 */
		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(){
			@Override
			public boolean onPreDraw() {
				// We only want this code to run once (when our Activity starts), so we can remove the listener here
				getViewTreeObserver().removeOnPreDrawListener(this);
				
				// Add our first ball
				balls.add(new Ball(getWidth()/2, getHeight()/2, 0, 0, START_RADIUS * density, false));
				
				// Start counting frame durations
				lastFrameMillis = System.currentTimeMillis();
				return true;
			}
			
		});
	}
	
	/** Called when the system is ready to draw our custom view! */
	@Override
	public void onDraw(Canvas canvas){
		// Clear view background
		canvas.drawColor(Color.parseColor("#A4C639"));
		
		// Calculate delta-time since last frame
		final long currentTime = System.currentTimeMillis();
		final float dt = (System.currentTimeMillis() - lastFrameMillis) / 1000f;
		lastFrameMillis = currentTime;
		
		// For each ball..
		for(int i = 0; i < balls.size(); i++){
			Ball ball = balls.get(i);
			// Draw it
			canvas.drawCircle(ball.x, ball.y, ball.radius, ballPaint);
			
			// Update it
			if(!ball.active) continue;
			ball.vy += density * G * dt; // gravity
			ball.x += ball.vx * dt;
			ball.y += ball.vy * dt;
			
			// Side wall collision
			if(ball.x + ball.radius > getWidth()){
				ball.x = getWidth() - ball.radius; // put ball back within bounds
				ball.vx *= -1; // flip velocity
			}
			if(ball.x - ball.radius < 0){
				ball.x = ball.radius;
				ball.vx *= -1;
			}
			
			// Ground collision
			if(ball.y + ball.radius > getHeight()){
				ball. y = getHeight() - ball.radius;
				ball.vy *= -FRICTION; // bounce with a bit of friction, i.e. flip velocity and scale down
			}
		}
		
		// Tell Android system to draw us again, ASAP! This will result in onDraw() being called again..
		invalidate();
	}
	
	/** Called when any sort of touch event takes place on our custom view! */
	@Override
	public boolean onTouchEvent(MotionEvent event){
		// Figure out what sort of touch event this was, and if we want to handle it..
		switch(event.getActionMasked()){
		// If ACTION_DOWN, i.e. finger was just placed down on the screen..
		case MotionEvent.ACTION_DOWN:
			// Find location of touch event (in pixels)
			final float x = event.getX();
			final float y = event.getY();
			
			// For each ball..
			for(int i = 0; i < balls.size(); i++){
				Ball ball = balls.get(i);
				// Check if our tap is within the ball's radius
				final float dx = ball.x - x;
				final float dy = ball.y - y;
				if(dx*dx + dy*dy < ball.radius*ball.radius){
					// Tapped within ball!
					balls.remove(ball);
					balls.add(new Ball(ball.x, ball.y, HORIZONTAL_VELOCITY * density, -VERTICAL_VELOCITY * density, ball.radius * RADIUS_DECAY, true));
					balls.add(new Ball(ball.x, ball.y, -HORIZONTAL_VELOCITY * density, -VERTICAL_VELOCITY * density, ball.radius * RADIUS_DECAY, true));
					break; // Quit looping through balls - we want the player to tap only one ball at a time
				}
			}
			return true;
		}
		// Otherwise, we don't care about this touch event, do default behavior
		return super.onTouchEvent(event);
	}
}
