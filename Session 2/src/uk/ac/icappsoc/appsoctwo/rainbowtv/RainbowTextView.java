package uk.ac.icappsoc.appsoctwo.rainbowtv;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/** An excited TextView. */
public class RainbowTextView extends TextView {
	
	// One way to generate random numbers in Java - we'll use this for random color generation
	private Random random = new Random();
	
	// Provide the appropriate constructor
	public RainbowTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// I'm red when I begin, no matter what you tell me to do!
		setTextColor(Color.RED);
	}
	
	// Called whenever the system is ready to draw us!
	@Override
	public void onDraw(Canvas canvas){
		// Do the default drawing
		super.onDraw(canvas);
		
		// Notify LogCat, in case anyone's interested in seeing how often this is called
		Log.d("RainbowTextView", "onDraw()");
		
		// Change the text color to something random!
		setTextColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		
		// Tell Android system to draw us again, as soon as possible!
		// This will result in onDraw() being called again.. and we get (random) animation!
		invalidate();
	}
	
	/** Called when any sort of touch event takes place on our custom view! */
	@Override
	public boolean onTouchEvent(MotionEvent event){
		// Figure out what sort of touch event this was..
		switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			// Finger put down on View, i.e. touch just started
			setText("I was touched!");
			return true;
		case MotionEvent.ACTION_UP:
			// Finger lifted from View, i.e. touch just ended
			setText("Hello World!");
			return true;
		case MotionEvent.ACTION_MOVE:
			// Finger moved on View, i.e. drag
			setTextColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			return true;
		}
		
		// If we didn't find anything we want, just use the default behavior
		return super.onTouchEvent(event);
	}

}