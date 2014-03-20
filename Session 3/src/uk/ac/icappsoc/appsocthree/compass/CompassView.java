package uk.ac.icappsoc.appsocthree.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/** The simplest compass - a rotating ImageView. */
public class CompassView extends ImageView {

	private float azimuth;
	
	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * Updates the compass, but performs a simple low-pass filter on the input.<p>
	 * Note that this causes the compass to jump around when the provided angle wraps.
	 * @param newAzimuth relative to magnetic north, in degrees.
	 * @param alpha dumb low-pass filter coefficient. 0 - no damping, immediate change; 1 - absolute damping, no change.
	 */
	public void setAzimuthDamped(float newAzimuth, float alpha){
		azimuth = alpha * azimuth + (1 - alpha) * newAzimuth;

		// Re-draw ourselves!
		invalidate();
	}
	
	/**
	 * Updates the compass, performing a simple IIR filter of order 1
	 * and ensuring wrapping angle values do not cause jumpiness.
	 * @param newAzimuth relative to magnetic north, in degrees.
	 * @param alpha IIR filter coefficient, 0->1. Lower values give smoother response but greater phase lag.
	 */
	public void setAzimuthDampedSmart(float newAzimuth, float alpha){
		float diff = newAzimuth - azimuth;
		
		/*
		 * When we pass the wrap-around boundary for angular values,
		 * Math.abs(difference) will be greater than 180.
		 * Catch that, and handle it such that we have smooth angle transitions.
		 */
		while(diff >= 180) diff -= 360;
		while(diff < -180) diff += 360;
		
		azimuth += alpha * diff;
		
		// Do same as above for the resulting angle.
		while(azimuth >= 180) azimuth -= 360;
		while(azimuth < -180) azimuth += 360;
		
		// Re-draw ourselves!
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.save();
		canvas.rotate(-azimuth, getWidth() / 2, getHeight() / 2);
		super.onDraw(canvas);
		canvas.restore();
	}

}
