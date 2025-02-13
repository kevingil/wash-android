package com.kevingil.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.kevingil.wash.R;


public class CameraActivity extends SherlockActivity implements OnClickListener, OnTouchListener{
	
	private static final String TAG = "Capture";
	private View mainFrame;
	
	private ImageButton buttonClick, buttonDelete, buttonGallery; 
	private boolean pictureTakenFlag = false;
	private boolean goingOut = false; 
	private long timeStamp;
	private String extStorageDirectory;
	
	TextView mTitle;
	Preview preview;
	ImageButton btn_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		   {
		        this.setTheme(R.style.Theme_Kevin_TransparentActionBar);
		   }
		super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setContentView(R.layout.activity_camera);
		//setupActionBar();
		getSupportActionBar().hide();

		if(!isExternalStoragePresent()){
			Toast.makeText(this, "No External Storage Found", Toast.LENGTH_LONG).show();		
		}
		
		File folder = new File(Environment.getExternalStorageDirectory().toString()+"/DCIM/Homework");
		if(!folder.exists())
			folder.mkdirs();
		extStorageDirectory = folder.toString();

		preview = new Preview(this); 
		((FrameLayout) findViewById(R.id.preview)).addView(preview); 
		
		mainFrame = (FrameLayout)findViewById(R.id.preview);
		mainFrame.setOnTouchListener(this);
		
		buttonClick = (ImageButton) findViewById(R.id.buttonTakePicture);
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { // <5>
				if (!pictureTakenFlag) {
					timeStamp = System.currentTimeMillis();
					takeFocusedPicture();
					buttonClick.setBackgroundResource(R.drawable.newpic);
					buttonDelete.setClickable(true);					
				} else {
					buttonClick.setBackgroundResource(R.drawable.click);
					preview.camera.startPreview();
					pictureTakenFlag = false;
					buttonDelete.setClickable(false);					
					Toast.makeText(CameraActivity.this, "Image saved on "+extStorageDirectory+"/ as "+timeStamp+".jpg", Toast.LENGTH_LONG).show();
				}
			}
		});

		
		buttonDelete = (ImageButton) findViewById(R.id.buttonDelete);
		buttonDelete.setClickable(false); 
		buttonDelete.setOnClickListener(new	OnClickListener() { 
			public void onClick(View v) { // <5> 
				File photo = new File(String.format(extStorageDirectory+"/%d.jpg", timeStamp)); 
				if(photo.exists()) { 
					photo.delete(); 
					pictureTakenFlag = false;
					preview.camera.startPreview();
					Toast.makeText(CameraActivity.this, "Image deleted!", Toast.LENGTH_LONG).show();
				} 
			} 
		});	

		buttonGallery = (ImageButton) findViewById(R.id.buttonGallery);
		buttonGallery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivity(intent);
				preview.camera.startPreview();
			}
		});
		

	
		Log.d(TAG, "onCreate'd");
	}
	
	private boolean isExternalStoragePresent() {

        boolean externalStorageAvailable = false;
        boolean externalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            externalStorageAvailable = externalStorageWriteable = false;
        }
        if (!((externalStorageAvailable) && (externalStorageWriteable))) {
            Toast.makeText(this, "SD card not present", Toast.LENGTH_LONG)
                    .show();
        }
        return (externalStorageAvailable) && (externalStorageWriteable);
    }


	@Override
	public void onResume() {
		super.onResume();
		if (goingOut) {
			new Preview(this);
			goingOut = false;
			pictureTakenFlag = false;
			buttonDelete.setClickable(false); 
		}
	}

	@Override
	public void onPause() {

		if (!goingOut) {
			preview.camera.stopPreview();
		}
		goingOut = true;
		super.onPause();
	}
	


	    
	// Called when shutter is opened
	ShutterCallback shutterCallback = new ShutterCallback() { 
		public void onShutter() {
			pictureTakenFlag = true;
			Log.d(TAG, "onShutter'd");
		}
	};

	// Handles data for raw picture
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	// Handles data for jpeg picture
	PictureCallback jpegCallback = new PictureCallback() { 
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			try {
				// Write to SD Card
				outStream = new FileOutputStream(String.format(extStorageDirectory+"/%d.jpg", timeStamp)); 
				outStream.write(data);
				outStream.close();
				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
			} catch (FileNotFoundException e) { // <10>
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};

	public void onClick(View v)
	{
		Camera.Parameters p = preview.camera.getParameters();
		
		preview.camera.setParameters(p);
		try {
        	preview.camera.setPreviewDisplay(preview.getHolder());
        } catch (Exception e) { }
		preview.camera.setDisplayOrientation(90);
        preview.camera.startPreview();
	}

	public boolean onTouch(View v, MotionEvent me)
	{
		if(!pictureTakenFlag){
			if(me.getAction()==MotionEvent.ACTION_DOWN){
				//TODO add auto focus
				preview.camera.autoFocus(null);
				preview.camera.startPreview();
			}
		}
		return false;
	}
	
	// UI Methods
	void setupActionBar()
	{
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionbar.setCustomView(R.layout.actionbar_transparent);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		mTitle = (TextView) findViewById(R.id.ab_title);
		mTitle.setText("Capture");
		
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setImageResource(R.drawable.ic_back);
		btn_back.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
		
	}
	void takeFocusedPicture()
	{
			preview.camera.autoFocus(new AutoFocusCallback() {
		        @Override
		        public void onAutoFocus(boolean success, Camera camera) {
		        	preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
		        }
		    }); 
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
	

}