package com.kevingil.wash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Social extends Activity {
	WebView myWebView;
    
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
      	     this.setTheme(R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_groups);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // this loads the web view 
        myWebView = (WebView) findViewById(R.id.webViewFacebookGroups);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "loading..", true);
        //myWebView.setBackgroundResource(R.drawable.dark_gray_bg);
        myWebView.setBackgroundColor(0);
        myWebView.loadUrl("http://kevingil.github.com/wash/social.html");
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        // i dont mind the javascript vulnerabilities so don't erase!!
       myWebView.getSettings().setJavaScriptEnabled(true);
       myWebView.setWebViewClient(new WebViewClient(){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
    		   webViewProgress.dismiss();}});}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_about:
                Intent i = new Intent(this, AboutDeveloper.class);
                this.startActivity(i);
            	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = this.getMenuInflater();
       inflater.inflate(R.menu.menu_main, menu);
       return true;
    }
    

}
