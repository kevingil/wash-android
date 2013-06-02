package com.kevingil.wash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class SchoolloopActivity extends Activity {
	
    WebView myWebView;
	ProgressBar progressBar;
	WebSettings WebViewSettings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
      	     this.setTheme(R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolloop);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        myWebView = (WebView) findViewById(R.id.webViewSchoolloop);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "loading...", "Your password will not be saved", true);
        //myWebView.setBackgroundResource(R.drawable.lightgray3);
        //myWebView.setBackgroundColor(0);
        myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/index");
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // settings 
        WebViewSettings = myWebView.getSettings();
        WebViewSettings.setSavePassword(true);WebViewSettings.setSaveFormData(true);
        WebViewSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient(){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
    		   myWebView.loadUrl("javascript:"
       		   		+ "document.body.style.background=\"white\";"
       		   		+ "document.getElementById('page_title').style.display=\"none\";"
       		   	    + "document.getElementById('container_footer').style.display=\"none\";"
       		   	    + "document.getElementById('field_mobile').style.display=\"none\";"
       		   	    + "document.getElementById('block_standard_left').style.display=\"none\";"
       		   	    + "document.getElementById('container_header').style.display=\"none\";" 
       		   	    + "document.getElementsByClass('block_text').style.width=\"100%\";" 
          		   		);
    		   
    		   webViewProgress.dismiss();
    	    }
    	   
       });
       myWebView.setDownloadListener(new DownloadListener() {
           public void onDownloadStart(String url, String userAgent,
                           String contentDisposition, String mimetype,
                           long contentLength) {

                                         Uri uri = Uri.parse(url);
          Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                   startActivity(intent);
           }
   });
    }
    @Override // this is to use go back buttom to navigate in webview history, to exit the webview people will use the back button on actionbar
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()){
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.schoolloop_refresh:
            	myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/index");
            	return true;
            case R.id.schoolloop_logout:
            	myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/portal/logout");
            	myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/index");
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
       inflater.inflate(R.layout.schoolloop_menu, menu);
       return true;
    }
}



