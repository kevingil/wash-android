package com.kevingil.wash;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;



/// this all works so dont move for now :)

public class DailyBulletin extends Activity {
	// some variables here:
    WebView myWebView;
	ProgressBar progressBar;
	WebSettings WebViewSettings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
      	     this.setTheme(R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_bulletin);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // this changes the background color of the action bar
       //  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.black_textured));
        // this loads the web view 
        myWebView = (WebView) findViewById(R.id.webviewDailyBulletin);
        myWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/bulletin");


        final ProgressDialog webViewProgress = ProgressDialog.show(this, "loading...", "optimizing for mobile", true);

        myWebView.setWebViewClient(new WebViewClient(){
    	   @Override 
    	   public void onPageFinished(WebView view, String url){

    		   myWebView.loadUrl("javascript:"
    		   		+ "$('head link, head style').remove();"
    		   		+ "document.body.style.background=\"white\";"
    		   		+ "document.getElementById('page_title').style.display=\"none\";"
    		   	    + "document.getElementById('skip').style.display=\"none\";"
    		   	    + "document.getElementById('container_footer').style.display=\"none\";"
    		   	    + "document.getElementById('block_standard_left').style.display=\"none\";"
    		   	    + "document.getElementById('container_header').style.display=\"none\";" 
    		   	    + "document.getElementsByClass('block_text').style.width=\"100%\";" 
    		   	    + "document.getElementByTagName('div').setAttribute(\"style\",\"width:100%\");" 
    		   	    + "document.getElementByTagName('span').setAttribute(\"style\",\"width:100%\");" 
    		   	    + "document.getElementByTagName('p').setAttribute(\"style\",\"font-family:'Arial'\");" 
    		   	    + "document.getElementByTagName('font').setAttribute(\"style\",\"width:100%\");" 
    		   	    + "document.getElementByTagName('span').setAttribute(\"style\",\"font-family:'Arial'\");" 
    		   	    + "docmunet.getElementsByClass('block_content_main').setAttribute(\"style\", \"width:100%;\");" 
    		   	    + "document.getElementById('container_page').setAttribute(\"style\", \"width:100%;\");" 
    		   	    + "document.getElementById('content_margin_right').setAttribute(\"style\", \"width:100%;\");"
    		   	    + "document.getElementByTagName('table').setAttribute(\"style\",\"font-family:'Arial'\");"
    		   	    + "document.getElementByTagName('tbody').setAttribute(\"style\",\"width:100%\");"
    		   	    + "document.getElementByTagName('tr').setAttribute(\"style\",\"width:100%\");"
    		   	    + "document.getElementById('block_wide_main').setAttribute(\"style\", \"width:100%;\");" 
       		   		);
       		   webViewProgress.dismiss();
    	    }//block_content_main
    	   

       });
       //myWebView.setBackgroundResource(R.drawable.lightgray3);
       //myWebView.setBackgroundColor(0);
       myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
       WebViewSettings = myWebView.getSettings();
       WebViewSettings.setLoadsImagesAutomatically(false);
       //WebViewSettings.setUseWideViewPort(true);
       //WebViewSettings.setLoadWithOverviewMode(true);
       WebViewSettings.setSavePassword(true);
       WebViewSettings.setSaveFormData(true);
       WebViewSettings.setJavaScriptEnabled(true);
       // THIS WILL DIABLE HORIZONTAL SCROLL BECAUSE WASN NOT POSSIBLE WITH JAVASCRIPT INJECTION
    
    }
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


    @Override // this is to use go back buttom to navigate in webview history, to exit the webview people will use the back button on actionbar
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()){
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
