package com.kevingil.wash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Schedule extends SherlockActivity {

	SlidingMenu mSlideMenu;
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog webViewProgress;
	ListView mListView;
	Bundle translate_up;
	ImageButton btw_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setTitle("schedule");
		setContentView(R.layout.webview_generic);
		setupActionBar();
		setUpSlideList();
		
		btw_left = (ImageButton) findViewById(R.id.btw_slidemenutoggle_main_left);
		btw_left.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
		
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        webViewProgress = ProgressDialog.show(this, "", "loading...", true);
        mWebView.loadUrl("file:///android_asset/bellschedule.html");
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient(){
        	
            @Override
            public void onPageFinished(WebView view, String url){
                webViewProgress.dismiss();
             }
            
        });
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                            String contentDisposition, String mimetype,
                            long contentLength) {

                                          Uri uri = Uri.parse(url);
           Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
            }
    });
        // settings 
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
        } 
        /*
        else {
        	Intent i = new Intent(Schoolloop.this, Main.class);
        	startActivity(i);
        	finish();
        }
        */
        return super.onKeyDown(keyCode, event);
    }
    
	private void setupActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.ab_schedule);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
	
public void setUpSlideList(){
		//TODO add home view here!
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
	
	
	
}
