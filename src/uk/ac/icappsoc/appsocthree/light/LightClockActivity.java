package uk.ac.icappsoc.appsocthree.light;

import uk.ac.icappsoc.appsocthree.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

public class LightClockActivity extends Activity implements SensorEventListener {

	/**
	 *  A reference to the system SensorManager, which we use to access all the sensors we'll want.
	 *  In our case, we'll use it to start and stop listening for sensor events, as well as retrieve
	 *  references to the sensors we will use.
	 */
	private SensorManager sensorManager;
	
	private Time time;
	private TextView clockTextView;
	private TextView luxTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_clock);
		
		// Retrieve the SensorManager and get a reference to the sensor we want.
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		time = new Time();
		// Grab our clock TextView so we can display the current time in a slick 7-segment font!
		clockTextView = (TextView) findViewById(R.id.clock);
		Typeface lcdTypeface = Typeface.createFromAsset(getAssets(), "fonts/digital-7 (mono).ttf");
		clockTextView.setTypeface(lcdTypeface);
		// Grab our TextView so we can display the current light value (in lux) as well.
		luxTextView = (TextView) findViewById(R.id.text);
		luxTextView.setTypeface(lcdTypeface);
	}
	
	private Runnable updateClockTime = new Runnable(){
		@Override
		public void run() {
			time.setToNow();
			clockTextView.setText(time.format("%k:%M:%S"));
			clockTextView.postDelayed(this, 1000);
		}
	};
	
	// Note that onResume() is called every time an Activity comes to the foreground, including the first time it starts up.
	@Override
	protected void onResume(){
		super.onResume();
		// Register this Activity as the listener for our proximity sensor as soon as we resume running.
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
		// Start updating our time
		clockTextView.post(updateClockTime);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		// Unregister this Activity as soon as it's paused to prevent battery drain.
		sensorManager.unregisterListener(this);
		clockTextView.removeCallbacks(updateClockTime);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Typically we're not too interested in doing anything here.
	}

	private static final float DAYLIGHT_THRESHOLD = 20; // lux
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// Here's where the meat lies!
		if(event.sensor.getType() == Sensor.TYPE_LIGHT){
			float lightLux = event.values[0];
			luxTextView.setText(lightLux + " lux");
			
			if(lightLux > DAYLIGHT_THRESHOLD){
				luxTextView.setTextColor(Color.BLACK);
				clockTextView.setTextColor(Color.BLACK);
				getWindow().getDecorView().setBackgroundColor(Color.WHITE);
			} else {
				luxTextView.setTextColor(Color.WHITE);
				clockTextView.setTextColor(Color.WHITE);
				getWindow().getDecorView().setBackgroundColor(Color.BLACK);
			}
		}
		
	}

}
