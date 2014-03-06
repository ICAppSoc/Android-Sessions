package uk.ac.icappsoc.appsoctwo.circleimage;

import uk.ac.icappsoc.appsoctwo.R;
import android.os.Bundle;
import android.app.Activity;

/** Nothing special, simply contains a single {@link CircleImageView}. */
public class CircleImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_image);
	}

}
