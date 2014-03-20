package uk.ac.icappsoc.appsocthree.compass;

import uk.ac.icappsoc.appsocthree.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class CompassActivity extends Activity implements SensorEventListener {

	/**
	 *  A reference to the system SensorManager, which we use to access all the sensors we'll want.
	 *  In our case, we'll use it to start and stop listening for sensor events, as well as retrieve
	 *  references to the sensors we will use.
	 */
	private SensorManager sensorManager;
	
	private CompassView compass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);
		
		// Retrieve the SensorManager and get a reference to the sensor we want.
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// Grab our CompassView so we can update it with the latest orientation!
		compass = (CompassView) findViewById(R.id.compass);
	}

	// Note that onResume() is called every time an Activity comes to the foreground, including the first time it starts up.
	@Override
	protected void onResume(){
		super.onResume();
		// Register this Activity as the listener for our gravity and magnetic sensor as soon as we resume running.
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_UI);
		// Register for ACCELEROMETER as well as GRAVITY sensor, in case GRAVITY sensor is not present.
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
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

	
	// 1 radian = 57.2957795 degrees
	private static final float DEGREES_PER_RADIAN = 57.2957795f;
	
	// All the necessary arrays to store and compute orientation values.
	private float[] rotationMatrix = new float[9];
	private float[] orientation = new float[3];
	private float[] latestAccelerations = new float[3];
	private float[] latestMagFields = new float[3];

	private boolean haveAccData = false;
	private boolean haveGravData = false;
	private boolean haveMagData = false;
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// Here's where the meat lies!
		switch(event.sensor.getType()){
		case Sensor.TYPE_GRAVITY:
			System.arraycopy(event.values, 0, latestAccelerations, 0, 3);
			haveGravData = true;
			break;
		case Sensor.TYPE_ACCELEROMETER:
			if(haveGravData) break; // GRAVITY sensor data is better!
			System.arraycopy(event.values, 0, latestAccelerations, 0, 3);
			haveAccData = true;
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			System.arraycopy(event.values, 0, latestMagFields, 0, 3);
			haveMagData = true;
			break;
		}
		
		if((haveGravData || haveAccData) && haveMagData){
			// We've got all the data we need to calculate orientation relative to magnetic north!
			if(SensorManager.getRotationMatrix(rotationMatrix, null, latestAccelerations, latestMagFields)){
				SensorManager.getOrientation(rotationMatrix, orientation);
				
				/* orientation[0] : azimuth, rotation around Z axis
				 * orientation[1] : pitch, rotation around X axis
				 * orientation[2] : roll, rotation around Y axis */
				float azimuth = orientation[0] * DEGREES_PER_RADIAN;
				
				// Update our compass!
				compass.setAzimuthDampedSmart(azimuth, 0.05f);
			}
		}
	}

}
