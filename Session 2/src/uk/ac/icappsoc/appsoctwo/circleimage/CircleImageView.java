package uk.ac.icappsoc.appsoctwo.circleimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A circular ImageView.
 * WARNINGS:
 * - This may not work with an ImageView that is initialized without an image.
 * - If the size of the image does not match the size of this View, the circular
 * image will NOT be centered! To alleviate this, the Bitmap that the BitmapShader
 * is initialized with needs to be scaled to the size of the View (there are multiple
 * ways this may be accomplished).
 * - The shader needs to be updated if the image changes at runtime,
 * e.g. when {@link #setImageBitmap(Bitmap)} is called.
 */
public class CircleImageView extends ImageView {
	
	// A shader that we will use to draw our circle.
	private BitmapShader shader;
	// The paint to which we'll apply the shader.
	private Paint paint;
	
	// Provide the appropriate constructor
	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// Create the BitmapShader, pointing it to the bitmap of the image this ImageView started with
		shader = new BitmapShader(((BitmapDrawable)getDrawable()).getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		// Create our paint and attach the shader to it
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);
	}
	
	@Override
	public void setImageBitmap(Bitmap bm){
		super.setImageBitmap(bm);

		shader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		paint.setShader(shader);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawCircle(getWidth()/2, getHeight()/2, Math.min(getWidth(), getHeight())/2, paint);
	}
	
}
