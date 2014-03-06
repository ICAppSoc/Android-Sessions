package uk.ac.icappsoc.appsoctwo.split;

import uk.ac.icappsoc.appsoctwo.R;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;

/**
 * A full-screen activity that hides all system UI.
 * In this case, it holds a single {@link SplitGameView}.
 */
public class SplitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_split);
	}
	
	/*
	 * This is called every time your activity receives focus (e.g. when you open it up)
	 * or loses it (e.g. when you get a call while inside this activity).
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus) hideUi();
	}
	
	/** Hides all system UI, including the navigation and status bars. */
	@TargetApi(19) // used to suppress warnings
	private void hideUi(){
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}


}
