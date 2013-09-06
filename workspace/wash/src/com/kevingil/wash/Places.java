package com.kevingil.wash;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;



public class Places extends SherlockActivity {

	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setTitle("social");
		setContentView(R.layout.webview_generic);
		setupActionBar();
		setUpWebView();
        
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
	}
    
	void setUpWebView() 
	{
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        mWebViewProgress = ProgressDialog.show(this, "", "loading...", true);
        mWebView.loadUrl("file:///android_asset/places.html");
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
            	mWebViewProgress.dismiss();
             }
        });
        // webview settings 
        mWebViewSettings = mWebView.getSettings();
        mWebViewSettings.setSavePassword(true);
        mWebViewSettings.setSaveFormData(true);
        mWebViewSettings.setJavaScriptEnabled(true);
		
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        } else {
        	finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
	void setupActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.ab_places);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
	}
    
@Override
public void finish(){
	super.finish();
	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
}    

}
