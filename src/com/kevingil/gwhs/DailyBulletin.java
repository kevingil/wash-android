package com.kevingil.gwhs;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;



/// this all works so dont move for now :)

public class DailyBulletin extends SherlockActivity {
	// some variables here:
    WebView myWebView;
	ProgressBar progressBar;
	WebSettings WebViewSettings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
    	        this.setTheme(com.actionbarsherlock.R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_bulletin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // this changes the background color of the action bar
       //  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.black_textured));
        // this loads the web view 
        myWebView = (WebView) findViewById(R.id.webviewDailyBulletin);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "your phone's slow lol", true);
        myWebView.setBackgroundResource(R.drawable.dark_gray_bg);
        myWebView.setBackgroundColor(0);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // settings 
        WebViewSettings = myWebView.getSettings();
        WebViewSettings.setLoadsImagesAutomatically(false);
        WebViewSettings.setUseWideViewPort(true);
        WebViewSettings.setLoadWithOverviewMode(true);
        WebViewSettings.setSavePassword(true);
        WebViewSettings.setSaveFormData(true);
        WebViewSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient(){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
    		   webViewProgress.dismiss();
    		   myWebView.loadUrl(
    		   "javascript:(function() { document.getElementsByTagName('body')[0].style.color = 'red';})()"
    		   );
    	    }
    	   
       });
       myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/bulletin");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override // this is to use go back buttom to navigate in webview history, to exit the webview people will use the back button on actionbar
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()){
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
