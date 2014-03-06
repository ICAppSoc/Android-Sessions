package uk.ac.icappsoc.appsoctwo.rainbowtv;

import uk.ac.icappsoc.appsoctwo.R;
import android.os.Bundle;
import android.app.Activity;

/** Nothing special, just displays a flamboyant {@link RainbowTextView}. */
public class RainbowTextViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rainbow_text_view);
	}

}
