package uk.ac.icappsoc.appsocthree;

import java.util.List;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays a list of all the device's available sensors.
 */
public class SensorListActivity extends ListActivity {
	
	private SensorManager sensorManager;
	private List<Sensor> sensors;
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		
		setListAdapter(new SensorListAdapter());
		
		Toast.makeText(this, "This device has temperature sensor: " + deviceHasSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), Toast.LENGTH_SHORT).show();
	}
	
	private boolean deviceHasSensor(int type){
		// If the device does not have a 'default' sensor of the given type, then it doesnt' have any of that type!
		return sensorManager.getDefaultSensor(type) != null;
	}
	
	/* Extending BaseAdapter is a good choice when in need of an adapter. */
	private class SensorListAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return sensors.size();
		}

		// No need to return anything special.
		@Override
		public Object getItem(int position) {
			return null;
		}

		// No need to anything special.
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		// Creates the Views that actually populate the list
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				// Our view has not been recycled, let's create a new one!
				convertView = getLayoutInflater().inflate(R.layout.item_list_sensor_detail, null);
			}
			// Update recycled or old view
			TextView nameLabel = (TextView) convertView.findViewById(R.id.name);
			TextView vendorLabel = (TextView) convertView.findViewById(R.id.manufacturer);
			TextView detailsLabel = (TextView) convertView.findViewById(R.id.details);
			Sensor sensor = sensors.get(position);
			
			nameLabel.setText(sensor.getName());
			vendorLabel.setText("by " + sensor.getVendor() + " | v" + sensor.getVersion());
			detailsLabel.setText(
					"resolution: " + sensor.getResolution() + " | max range: " + sensor.getMaximumRange() + "\n" +
					"min delay: " + sensor.getMinDelay() + "us | power req: " + sensor.getPower() + "mA");
			
			return convertView;
		}
		
	}
}