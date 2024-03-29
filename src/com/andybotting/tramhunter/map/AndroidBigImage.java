/* 
 * Copyright AndroidBigImage
 * 
 * http://code.google.com/p/androidbigimage/
 *   
 * The code below is licenced under Creative Commons 3.0 BY-SA
 * http://creativecommons.org/licenses/by-sa/3.0/
 * 
 */

package com.andybotting.tramhunter.map;

import java.util.Timer;

import com.andybotting.tramhunter.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class AndroidBigImage extends Activity {
	private AndroidBigImageView 	androidBigImageView;
	private ImageButton	zoomInButton;
	private ImageButton	zoomOutButton;
	private Matrix 		matrix;
	private RectF 		sourceRect;
	private RectF 		destinationRect;
	private Bitmap		bitmap;
	private Timer		timer;
	private Animation	animation;
	private Handler handle = new Handler();
	
//	private int		imageSizeX = 2047;
//	private int		imageSizeY = 2047;
	private int		imageSizeX = 1584;
	private int		imageSizeY = 1815;

	private static final float 	INITIAL_SCALE = (float)1;
	private static final float 	MAGNIFY_SCALE = (float)1.9;
	
	private float	current_scale = INITIAL_SCALE;
	private int		current_centerX = imageSizeX/2;
	private int 	current_centerY = imageSizeY/2;
	private int		current_drawable = R.drawable.tram_map;
	
	private int		moveHistorySize;
	private float	lastTwoXMoves[] = new float[2];
	private float	lastTwoYMoves[] = new float[2];
	private long	downTimer;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.network_map);
        androidBigImageView = (AndroidBigImageView)findViewById(R.id.map_view);
        zoomInButton = (ImageButton)findViewById(R.id.zoomIn);
        zoomOutButton = (ImageButton)findViewById(R.id.zoomOut);
               
        sourceRect = new RectF();
        destinationRect = new RectF();
        matrix = new Matrix();
        
        if(savedInstanceState != null){
        	current_centerX = savedInstanceState.getInt("centerX");
        	current_centerY = savedInstanceState.getInt("centerY");
        	current_scale = savedInstanceState.getFloat("scale");
        	current_drawable = savedInstanceState.getInt("drawable");
        	imageSizeX = savedInstanceState.getInt("sizeX");
        	imageSizeY = savedInstanceState.getInt("sizeY");
        }
        
        timer = new Timer();
        animation = new Animation(handle, current_centerX, current_centerY, current_scale);
        
        androidBigImageView.setHandle(handle);
        androidBigImageView.setCallBack(sizeCallback);
        
        animation.stopProcess();
        animation.setCallBack(animationCallBack);
        timer.scheduleAtFixedRate(animation, 200, 30);
        
        androidBigImageView.setOnTouchListener(metroListener);
        zoomInButton.setOnClickListener(zoomInListener);
        zoomOutButton.setOnClickListener(zoomOutListener);
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTempStorage = new byte[16*1024];
        bitmap = BitmapFactory.decodeResource(getResources(), current_drawable, options);

        imageSizeX = bitmap.getWidth();
        imageSizeY = bitmap.getHeight();

        androidBigImageView.setImageBitmap(bitmap);
        androidBigImageView.getDrawable().setFilterBitmap(true);
        androidBigImageView.setImageMatrix(matrix);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle inState){
    	current_centerX = inState.getInt("centerX");
    	current_centerY = inState.getInt("centerY");
    	current_scale = inState.getFloat("scale");
    	current_drawable = inState.getInt("drawable");
    	imageSizeX = inState.getInt("sizeX");
    	imageSizeY = inState.getInt("sizeY");
    }
    
    @Override
    public void  onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	outState.putInt("centerX", current_centerX);
    	outState.putInt("centerY", current_centerY);
    	outState.putFloat("scale", current_scale);
    	outState.putInt("drawable", current_drawable);
    	outState.putInt("sizeX", imageSizeX);
    	outState.putInt("sizeY", imageSizeY);
    }
    
    public void onPause() {    	
    	System.gc();
    	super.onPause();
    }    
    
    public void onDestroy() {    	
    	if(!bitmap.isRecycled())
    		bitmap.recycle();
    	System.gc();
    	super.onDestroy();
    }
    
    private OnTouchListener metroListener = new OnTouchListener() {
    	public boolean onTouch(View v, MotionEvent event) {
    		
    		if((event.getAction() == MotionEvent.ACTION_MOVE)){
    			
    			moveHistorySize++;
    			lastTwoXMoves[1] = lastTwoXMoves[0];
    			lastTwoXMoves[0] = event.getX();
    			lastTwoYMoves[1] = lastTwoYMoves[0];
    			lastTwoYMoves[0] = event.getY();
    			
    			if(moveHistorySize >= 2){
	    			current_centerX += (int)((lastTwoXMoves[1] - lastTwoXMoves[0]) * (imageSizeX / current_scale) / androidBigImageView.getWidth());
	    			current_centerY += (int)((lastTwoYMoves[1] - lastTwoYMoves[0]) * (imageSizeY / current_scale) / androidBigImageView.getHeight());
		    		
		    		updateDisplay();
    			}
    		}
    		else if(event.getAction() == MotionEvent.ACTION_DOWN){
    			animation.stopProcess();
    			lastTwoXMoves[0] = event.getX();
    			lastTwoYMoves[0] = event.getY();
    			downTimer = event.getEventTime();
    			moveHistorySize = 1;
    		}
    		else if((event.getAction() == MotionEvent.ACTION_UP) && (moveHistorySize >= 1)) {
    			
    			if(event.getEventTime() != downTimer){
	    			float speedX = (lastTwoXMoves[1] - lastTwoXMoves[0]) * (imageSizeX / current_scale) / androidBigImageView.getWidth();
	    			float speedY = (lastTwoYMoves[1] - lastTwoYMoves[0]) * (imageSizeY / current_scale) / androidBigImageView.getHeight();
	    			
	    			speedX /= event.getEventTime() - downTimer;
	    			speedY /= event.getEventTime() - downTimer;
	    			
	    			speedX *= 30;
	    			speedY *= 30;
	    			
	    			animation.setInfo(speedX, speedY, current_centerX, current_centerY);
    			}
    		}
    		
    		return true;
    	}
    };
    
    private OnClickListener zoomInListener = new OnClickListener() { 
    	public void onClick(View v) {
    		animation.stopProcess();
    		if(current_scale <= 5)
    			animation.setScaleInfo(current_scale, current_scale * MAGNIFY_SCALE);
    	}
    };
    
    private OnClickListener zoomOutListener = new OnClickListener() { 
    	public void onClick(View v) {
    		animation.stopProcess();
    		if(current_scale >= MAGNIFY_SCALE * INITIAL_SCALE){
    			animation.setScaleInfo(current_scale, current_scale / MAGNIFY_SCALE);
    		}
    		else if((current_scale > INITIAL_SCALE)){
    			animation.setScaleInfo(current_scale, INITIAL_SCALE);
    		}
    	}
    };
    
    private AnimationCallBack animationCallBack = new AnimationCallBack() {
    	public void onTimer(int centerX, int centerY, float scale){
			current_centerX = centerX;
			current_centerY = centerY;
			current_scale = scale;
			updateDisplay();
    	}
    };
    
    private SizeCallBack sizeCallback = new SizeCallBack() {
    	public void onSizeChanged(int w, int h){
    		destinationRect.set((float)0, (float)0, (float)w, (float)h);
    		updateDisplay();
    	}
    };
    
    private void updateDisplay(){
    	calculateSourceRect(current_centerX, current_centerY, current_scale);
		matrix.setRectToRect(sourceRect, destinationRect, Matrix.ScaleToFit.FILL);
		androidBigImageView.setImageMatrix(matrix);
    }
    
    private void calculateSourceRect(int centerX, int centerY, float scale){
    	int xSubValue;
    	int ySubValue;
    	
    	if(destinationRect.bottom >= destinationRect.right){
	    	ySubValue = (int)((imageSizeY/2) / scale);
	    	xSubValue = ySubValue;
	    	
	    	xSubValue = (int) (xSubValue * ((float)androidBigImageView.getWidth() / (float)androidBigImageView.getHeight()));
    	}
    	else{
    		xSubValue = (int)((imageSizeX/2) / scale);
	    	ySubValue = xSubValue;
	    	
	    	ySubValue = (int) (ySubValue * ((float)androidBigImageView.getHeight() / (float)androidBigImageView.getWidth()));
    	}
    	
    	if(centerX - xSubValue < 0) {
    		animation.stopProcess();
    		centerX = xSubValue;
    	}
    	if(centerY - ySubValue < 0) {
    		animation.stopProcess();
    		centerY = ySubValue;
    	}
    	if(centerX + xSubValue >= imageSizeX) {
    		animation.stopProcess();
    		centerX = imageSizeX - xSubValue - 1;
    	}
    	if(centerY + ySubValue >= imageSizeY) {
    		animation.stopProcess();
    		centerY = imageSizeY - ySubValue - 1;
    	}
    	
    	current_centerX = centerX;
    	current_centerY = centerY;
    	
    	sourceRect.set(centerX - xSubValue, centerY - ySubValue, centerX + xSubValue, centerY + ySubValue);
    }
    
    public void setNewDrawable(int resId){
    	current_drawable = resId;
    	bitmap.recycle();
    	bitmap = BitmapFactory.decodeResource(getResources(), resId);
    	androidBigImageView.setImageBitmap(bitmap);
    	androidBigImageView.getDrawable().setFilterBitmap(true);
    	
    	current_scale = INITIAL_SCALE;
    	imageSizeX = bitmap.getWidth();
    	imageSizeY = bitmap.getHeight();
    	current_centerX = imageSizeX/2;
    	current_centerY = imageSizeY/2;
    	
    	animation.setInfo(0, 0, current_centerX, current_centerY);
    	animation.setScaleInfo(current_scale, current_scale);
    	
    	updateDisplay();
    }
}
