package com.kevingil.gwhs;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
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
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.kevingil.gwhs.R;

public class SchoolloopActivity extends SherlockActivity {
	
    WebView myWebView;
	ProgressBar progressBar;
	WebSettings WebViewSettings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
    	        this.setTheme(com.actionbarsherlock.R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolloop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myWebView = (WebView) findViewById(R.id.webViewSchoolloop);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "loading...", "Your password will not be saved", true);
        //myWebView.setBackgroundResource(R.drawable.lightgray3);
        //myWebView.setBackgroundColor(0);
        myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/index");
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // settings 
        WebViewSettings = myWebView.getSettings();
        WebViewSettings.setSavePassword(true);
        WebViewSettings.setSaveFormData(true);
        WebViewSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new SchoolloopWebclientActivity(this){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
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
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = this.getSupportMenuInflater();
       inflater.inflate(R.layout.schoolloop_menu, menu);
       return true;
    }
}



