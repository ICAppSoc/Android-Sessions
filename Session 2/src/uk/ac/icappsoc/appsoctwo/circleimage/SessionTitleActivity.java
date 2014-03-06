package uk.ac.icappsoc.appsoctwo.circleimage;

import uk.ac.icappsoc.appsoctwo.R;
import android.os.Bundle;
import android.app.Activity;

/** Displays two {@link CircleImageView}s. */
public class SessionTitleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
	}

}
