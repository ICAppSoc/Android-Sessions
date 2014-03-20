package uk.ac.icappsoc.appsocthree.accel;

import java.util.Random;

import uk.ac.icappsoc.appsocthree.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class ShakeActivity extends Activity implements SensorEventListener {

	/**
	 *  A reference to the system SensorManager, which we use to access all the sensors we'll want.
	 *  In our case, we'll use it to start and stop listening for sensor events, as well as retrieve
	 *  references to the sensors we will use.
	 */
	private SensorManager sensorManager;
	
	/** A reference to the background so we can change its color when we detect a shake! */
	private View background;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shake);
		
		// Retrieve the SensorManager and get a reference to the sensor we want.
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		background = findViewById(R.id.container);
	}

	// Note that onResume() is called every time an Activity comes to the foreground, including the first time it starts up.
	@Override
	protected void onResume(){
		super.onResume();
		// Register this Activity as the listener for our acceleration sensor as soon as we resume running.
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause(){
		super.onPause();
		// Unregister this Activity as soon as it's paused to prevent battery drain.
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Typically we're not too interested in doing anything here.
	}
	
	private static final double ACCELERATION_THRESHOLD = 20; // m/s
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// Here's where the meat lies!
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			// Let's calculate the magnitude of acceleration.
			float accelerationX = event.values[0];
			float accelerationY = event.values[1];
			float accelerationZ = event.values[2];
			
			double magnitude = Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY + accelerationZ * accelerationZ);
			if(magnitude > ACCELERATION_THRESHOLD){
				// A friend of our old RainbowTextView
				Random random = new Random();
				background.setBackgroundColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			}
		}
	}
}
