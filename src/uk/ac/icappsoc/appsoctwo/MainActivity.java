package uk.ac.icappsoc.appsoctwo;

import uk.ac.icappsoc.appsoctwo.circleimage.CircleImageActivity;
import uk.ac.icappsoc.appsoctwo.circleimage.SessionTitleActivity;
import uk.ac.icappsoc.appsoctwo.rainbowtv.RainbowTextViewActivity;
import uk.ac.icappsoc.appsoctwo.split.SplitActivity;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple list activity.
 * In this case, displays a list of other activities in this project
 * and lets you jump to those.
 */
public class MainActivity extends ListActivity {

	/** A list of all the other activities we want to put in our list. */
	private Class<?>[] activities = {SessionTitleActivity.class, RainbowTextViewActivity.class, SplitActivity.class, CircleImageActivity.class};
	/** An adapter that populates a list with Views that it creates. */
	private DemoAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adapter = new DemoAdapter();
		// Populate our list with the data in our adapter
		setListAdapter(adapter);
	}
	
	/** Called whenever one of the list items is clicked. */
	@Override
	protected void onListItemClick(ListView list, View view, int position, long id){
		// Start the selected Activity
		Intent intent = new Intent(this, activities[position]);
		startActivity(intent);
	}
	
	/* Extending BaseAdapter is a good choice when in need of an adapter. */
	private class DemoAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return activities.length;
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
				convertView = getLayoutInflater().inflate(R.layout.item_list_image, null);
			}
			// Update recycled or old view
			TextView label = (TextView) convertView.findViewById(R.id.text);
			label.setText(activities[position].getSimpleName());
			
			return convertView;
		}
		
	}
}
