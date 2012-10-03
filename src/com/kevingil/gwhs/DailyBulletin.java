package com.kevingil.gwhs;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
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
        myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/bulletin");
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "your phone's slow lol", true);
        myWebView.setWebViewClient(new WebViewClient(){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
    		   myWebView.loadUrl("javascript:" +
    		   		"document.getElementById('container_footer').style.display=\"none\";" +
    		   		"document.getElementById('container_header').style.display=\"none\";" +
    		   		"document.getElementById('block_standard_left').style.display=\"none\";" +
    		   		"document.getElementById('block_wide_main').style.backgroundcolor=\"white\";" +
    		   		"document.getElementById('block_wide_main').style.width=\"50%\";" +
    		   		"document.getElementById('block_wide_main').style.text-align=\"center\";" +
    		   		"document.getElementById('block_wide_main').style.margin-left=\"auto\";" +
    		   		"document.getElementById('block_wide_main').style.margin-right=\"auto\";" +
    		   		"document.getElementById('body').style.margin=\"0px\";" +
    		   		"document.getElementsByClass('block_text').style.width=\"1000px\";" +
    		   		"document.getElementsByClass('block_content_main').style.width=\"100%\";"
    		   		);
    		   webViewProgress.dismiss();
    	    }//block_content_main

       });
       myWebView.setBackgroundResource(R.drawable.dark_gray_bg);
       myWebView.setBackgroundColor(0);
       myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
       WebViewSettings = myWebView.getSettings();
       //WebViewSettings.setLoadsImagesAutomatically(false);
       WebViewSettings.setUseWideViewPort(true);
       WebViewSettings.setLoadWithOverviewMode(true);
       WebViewSettings.setSavePassword(true);
       WebViewSettings.setSaveFormData(true);
       WebViewSettings.setJavaScriptEnabled(true);

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
