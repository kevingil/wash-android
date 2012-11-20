package com.kevingil.gwhs;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FacebookGroupsActivity extends SherlockActivity {
	WebView myWebView;
    
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
      	     this.setTheme(com.kevingil.gwhs.R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_groups);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // this loads the web view 
        myWebView = (WebView) findViewById(R.id.webViewFacebookGroups);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "loading..", true);
        //myWebView.setBackgroundResource(R.drawable.dark_gray_bg);
        myWebView.setBackgroundColor(0);
        myWebView.loadUrl("file:///android_asset/facebookgroups.html");
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
       MenuInflater inflater = this.getSupportMenuInflater();
       inflater.inflate(R.menu.menu_main, menu);
       return true;
    }
    

}
