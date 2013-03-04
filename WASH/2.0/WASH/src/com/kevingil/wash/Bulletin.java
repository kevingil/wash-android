package com.kevingil.wash;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kevingil.utils.Slide_List;
import com.kevingil.utils.Slide_List_Adapter;
import com.slidingmenu.lib.SlidingMenu;

public class Bulletin extends SherlockActivity {

	
	SlidingMenu mSlideMenu;
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ListView mListView;
	Button homeAsUp;
	ActionBar ab;
	Toast stillloading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		
		setTitle("bulletin");
		
		setContentView(R.layout.webview_generic);
		
		setUpBulletinWebView();
		
		setUpSlideMenu();
		
		setUpSlideList();
		
		setupActionBar();
		
        
	}
    
    @Override // this is to use go back buttom to navigate in webview history, to exit the webview people will use the back button on actionbar
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	mSlideMenu.toggle();
            return true;
        }
        return true;
    }
    
	void setupActionBar() {
		ab = getSupportActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.actionbar_centered_title_bulletin);
		ab.setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
    }
	
	void setUpBulletinWebView(){
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        
        mWebViewProgress = ProgressDialog.show(this, null, "optimizing for mobile", true);
        
        mWebView.loadUrl("http://gwhs-sfusd-ca.schoolloop.com/bulletin");
        
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        
        mWebView.setWebViewClient(new WebViewClient(){
        	
        	public boolean onTouch(View v, MotionEvent event) {
        		
        	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	    	
        	    	Context context = getApplicationContext();
        	    	CharSequence text = "page will finish loading";
        	    	int duration = Toast.LENGTH_SHORT;

        	    	stillloading = Toast.makeText(context, text, duration);
        	    	stillloading.show();

        	        return true;
        	    }
        	    return false;
        	}   
        	
            @Override
            public void onPageFinished(WebView view, String url){
            	
     		   mWebView.loadUrl("javascript:"
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
     		   
            	mWebViewProgress.dismiss();
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
	
	void setUpSlideMenu(){
		mSlideMenu = new SlidingMenu(this); 
        
		mSlideMenu.setMode(SlidingMenu.LEFT);
        
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
        
		mSlideMenu.setShadowDrawable(R.drawable.shadow);
		
		mSlideMenu.setBehindScrollScale(0.0f);
        
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        
		mSlideMenu.setFadeDegree(0.35f);
        
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        
		mSlideMenu.setMenu(R.layout.slide_menu_main);
	}
	
    void setUpSlideList(){
        Slide_List values[] = new Slide_List[]
                {
                    new Slide_List(R.drawable.dashboard_logo_small, "home"),
                    new Slide_List(R.drawable.schoolloop_logo_small, "schoolloop"),
                    new Slide_List(R.drawable.bulletin_small, "bulletin"),
                    new Slide_List(R.drawable.clock_small, "schedule"),
                    new Slide_List(R.drawable.groups_logo_small, "social"),
                    new Slide_List(R.drawable.about_logo_small, "about")
                };
                
        Slide_List_Adapter adapter = new Slide_List_Adapter(this, 
                        R.layout.list_adapter, values);
                
        
        mListView = (ListView)findViewById(R.id.slide_menu_wash);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0) // arrays start with 0. So 0 = 1, 1 = 2 and so on.
        { 
        	Intent i = new Intent(Bulletin.this, Main.class);
        	startActivity(i);
        }

        if(position == 1)
        {
            Intent mintent2 = new Intent(view.getContext(), Schoolloop.class);
            startActivityForResult(mintent2, 0);
        	mSlideMenu.toggle();
        }
        
        if(position == 2)
        {
        	mSlideMenu.toggle();
        }
        
        if(position == 3)
        {  
                 Intent mintent3 = new Intent(view.getContext(), Schedule.class);
                     startActivityForResult(mintent3, 0);
        }
        
        if(position == 4)
        {  
                 Intent mintent4 = new Intent(view.getContext(), Social.class);
                     startActivityForResult(mintent4, 0);
        }

        if(position == 5)
        {  
                 Intent mintent5 = new Intent(view.getContext(), Info.class);
                     startActivityForResult(mintent5, 0);
        }
        
            }
          });   
    	
    }
    
    
}
