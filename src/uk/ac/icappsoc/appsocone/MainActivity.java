package uk.ac.icappsoc.appsocone;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Activity - 
 * Application component that provides a screen with which users
 * can interact in order to do something.
 */
public class MainActivity extends Activity {
	
	// A reference to a TextView that we keep around after it is created in onCreate()
	TextView myText;

	// This part runs as soon as the application begins
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Inflate our layout (places all widgets from /res/layout/activity_main.xml onto the screen)
		setContentView(R.layout.activity_main);
		
		// Retrieve views that we want to make interactive from the screen
		final ImageView myImage = (ImageView) findViewById(R.id.imageView1);
		myText = (TextView) findViewById(R.id.textView1);
		final Button myButton = (Button) findViewById(R.id.button1);
		EditText myTextField = (EditText) findViewById(R.id.editText1);
		
		// Change the text in our TextView to something different
		myText.setText("Doughnuts please!");
		
		// Create an OnClickListener, which we will use for our Button
		OnClickListener myOnClickListener = new OnClickListener(){
			
			// This method runs when the View this listener is attached to is clicked
			@Override
			public void onClick(View view) {
				// Show a small pop-up with some text that will disappear after some time
				Toast.makeText(MainActivity.this, "You are an Android master!", Toast.LENGTH_SHORT).show();
				
				// Print something to LogCat
				Log.d("AppSocOne", "You are a debugging master!");
				
				// Change the image in our ImageView
				myImage.setImageResource(R.drawable.krispy);
				
				// Change the text in our TextView
				myText.setText("Thank you!");
				
				// Change the text in our Button
				myButton.setText("Stop clicking me");
			}
			
		};
		
		// Attach our OnClickListener to our Button, so it is called every time the Button is clicked
		myButton.setOnClickListener(myOnClickListener);
		
		// Create a TextWatcher, which we will use for our EditText
		TextWatcher myTextWatcher = new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				// We don't do anything..
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// We don't do anything..
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int count,int after) {
				// Call a custom method in our Activity
				updateMyTextBox(s);
			}
		};
		
		// Attach our TextWatcher to our EditText, so it is called every time the EditText changes
		myTextField.addTextChangedListener(myTextWatcher);
	}
	
	// This is an unnecessary, custom method just to demonstrate how you can store references to Views (and use them) outside onCreate()
	private void updateMyTextBox(CharSequence newText){
		// Update our TextView with the new text in the EditText
		myText.setText(newText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
