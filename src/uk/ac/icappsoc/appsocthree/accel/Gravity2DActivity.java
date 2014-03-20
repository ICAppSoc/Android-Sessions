package uk.ac.icappsoc.appsocthree.accel;

import uk.ac.icappsoc.appsocthree.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class Gravity2DActivity extends Activity implements SensorEventListener {

	/**
	 *  A reference to the system SensorManager, which we use to access all the sensors we'll want.
	 *  In our case, we'll use it to start and stop listening for sensor events, as well as retrieve
	 *  references to the sensors we will use.
	 */
	private SensorManager sensorManager;

	private BouncyBallView gravityView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gravity);

		// Retrieve the SensorManager and get a reference to the sensor we want.
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		gravityView = (BouncyBallView) findViewById(R.id.gravity);
	}

	// Note that onResume() is called every time an Activity comes to the foreground, including the first time it starts up.
	@Override
	protected void onResume(){
		super.onResume();
		Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		// Register this Activity as the listener for our gravity sensor as soon as we resume running.
		// Note that we use SensorManager.SENSOR_DELAY_GAME to request faster sensor data than usual,
		// ca. 30fps or 35ms interval for SENSOR_DELAY_GAME vs 10fps or 100ms for SENSOR_DELAY_UI.
		sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_GAME);
		// On devices that may not have a GRAVITY sensor, we can fall back to ACCELEROMETER
		// Lucky for us, they return values that are almost equivalent.
		if(null == gravitySensor){
			Toast.makeText(this, "Device does not have GRAVITY sensor, using ACCELEROMETER instead.", Toast.LENGTH_SHORT).show();
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		}
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
		switch(event.sensor.getType()){
		case Sensor.TYPE_GRAVITY:
		case Sensor.TYPE_ACCELEROMETER:
			// We only want 2D gravity, so grab that.
			float gravityX = event.values[0];
			float gravityY = event.values[1];

			// Note - we flip gravityX
			gravityView.setGravity(-gravityX, gravityY);
			break;
		}
	}

}
