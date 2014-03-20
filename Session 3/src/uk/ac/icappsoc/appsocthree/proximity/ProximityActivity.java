package uk.ac.icappsoc.appsocthree.proximity;

import uk.ac.icappsoc.appsocthree.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

// We implement SensorEventListener so we get notified when new sensor data is available.
public class ProximityActivity extends Activity implements SensorEventListener {

	/**
	 *  A reference to the system SensorManager, which we use to access all the sensors we'll want.
	 *  In our case, we'll use it to start and stop listening for sensor events, as well as retrieve
	 *  references to the sensors we will use.
	 */
	private SensorManager sensorManager;
	private Sensor proximitySensor;
	
	private float maxDistance;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proximity);
		
		// Retrieve the SensorManager and get a reference to the sensor we want.
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		
		// Retrieve the maximum distance our proximity sensor can return.
		maxDistance = proximitySensor.getMaximumRange();
		
		// Grab our TextView so we can display our data to the world!
		textView = (TextView) findViewById(R.id.text);
	}
	
	// Note that onResume() is called every time an Activity comes to the foreground, including the first time it starts up.
	@Override
	protected void onResume(){
		super.onResume();
		// Register this Activity as the listener for our proximity sensor as soon as we resume running.
		sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
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

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Here's where the meat lies!
		if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
			// Aha! This is our proximity sensor.
			float distanceCm = event.values[0];
			
			if(maxDistance == distanceCm){
				textView.setText("Far");
				getWindow().getDecorView().setBackgroundColor(Color.argb(255, 237, 30, 36)); // app_soc_red
			} else if(distanceCm > 0){
				textView.setText(distanceCm + " cm away");
				getWindow().getDecorView().setBackgroundColor(Color.argb(255, 164, 198, 57)); // app_soc_green
			} else if(0 == distanceCm){
				textView.setText("Near");
				getWindow().getDecorView().setBackgroundColor(Color.argb(255, 164, 198, 57)); // app_soc_green
			}
		}
		
	}

}
