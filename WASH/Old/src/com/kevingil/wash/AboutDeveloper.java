package com.kevingil.wash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class AboutDeveloper extends Activity {
    WebView myWebView;
	ProgressBar progressBar;
	WebSettings WebViewSettings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
   	     this.setTheme(R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);getActionBar().setDisplayHomeAsUpEnabled(true);
        myWebView = (WebView) findViewById(R.id.webViewAboutDeveloper);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "le loading... lol", true);
        myWebView.setBackgroundColor(0);myWebView.loadUrl("file:///android_asset/aboutdeveloper.html");
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        WebViewSettings = myWebView.getSettings();
        WebViewSettings.setSavePassword(true); WebViewSettings.setSaveFormData(true);
       myWebView.getSettings().setJavaScriptEnabled(true);
       myWebView.setWebViewClient(new MyWebViewClient(){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
    		   webViewProgress.dismiss();
    		   }
    	   }
       );
       }


    /* THIS IS FOR GOING BACK IN HISTORY IN WEBVIEW | NOT NEEDED
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack() {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }*/

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.example.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}

