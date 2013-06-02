package com.kevingil.wash;


import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kevingil.ui.SeparatedListAdapter;
import com.kevingil.wash.more.Info;
import com.slidingmenu.lib.SlidingMenu;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Bulletin extends SherlockActivity {

	// starts slide list ints
	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";
	private final static String[] items_schoolloop = new String[]{"grades", "mail", "news", "bulletin"};
	private final static String[] items_wash = new String[]{"schedule", "social", "places"};
	private final static String[] items_info = new String[]{"about"};
	private SeparatedListAdapter adapter;
	private ListView SlideListView;
	public Map<String, ?> createItem(String title, String caption)
		{
			Map<String, String> item = new HashMap<String, String>();
			item.put(ITEM_TITLE, title);
			item.put(ITEM_CAPTION, caption);
			return item;
		}
	// ends slide list ints
	
	SlidingMenu mSlideMenu;
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ListView mListView;
	ActionBar ab;
	Toast stillloading;
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in_bottom, 0);
		setTitle("bulletin");
		setContentView(R.layout.webview_generic);
		setUpBulletinWebView();
		setUpSlideMenu();
		setUpSlideList();
		setupActionBar();
		
		ImageButton btw_left = (ImageButton) findViewById(R.id.btw_slidemenutoggle_main_left);
		btw_left.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	mSlideMenu.toggle();
		    }
		});
		
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
		ab.setCustomView(R.layout.ab_bulletin);
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
           Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
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
        } else {
        	Intent i = new Intent(Bulletin.this, Main.class);
        	startActivity(i);
        	finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
	void setUpSlideMenu(){
		mSlideMenu = new SlidingMenu(this); 
		mSlideMenu.setMode(SlidingMenu.LEFT);
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
		//mSlideMenu.setShadowDrawable(R.drawable.shadow);
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlideMenu.setBehindScrollScale(0.0f);
		mSlideMenu.setFadeDegree(0.35f);
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		mSlideMenu.setMenu(R.layout.slide_menu_main_new);
	}

public void setUpSlideList(){
		
		adapter = new SeparatedListAdapter(this);
		ArrayAdapter<String> listadapter_schoolloop = new ArrayAdapter<String>(this, R.layout.list_item, items_schoolloop);
		ArrayAdapter<String> listadapter_wash = new ArrayAdapter<String>(this, R.layout.list_item, items_wash);
		ArrayAdapter<String> listadapter_info = new ArrayAdapter<String>(this, R.layout.list_item, items_info);
				adapter.addSection("schoolloop", listadapter_schoolloop);
				adapter.addSection("wash"      , listadapter_wash);
				adapter.addSection("extra"      , listadapter_info);
				SlideListView = (ListView) this.findViewById(R.id.list_journal);
				SlideListView.setAdapter(adapter);
				SlideListView.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
					
					if(position == 0){
						mSlideMenu.toggle();
						} //header schoolloop

	                if(position == 1){ // goes home
	                	Intent i = new Intent(view.getContext(), Schoolloop.class);
	                	startActivity(i);
	                	finish();
	                	} // l home
	                if(position == 2){
	                	mSlideMenu.toggle();
	                	} // l mail
	                if(position == 3){ //l news
	                         Intent i = new Intent(view.getContext(), News.class);
	                         startActivity(i);
	                         finish();
	                         }
	                if(position == 4){ // l bulletin
	                	mSlideMenu.toggle();
	                         }
	                if(position == 5){
	                	mSlideMenu.toggle();
	                	} //header wash
	                if(position == 6){ // l schedule
	                	Intent i = new Intent(view.getContext(), Schedule.class);
                        startActivity(i);
                        finish();
	                             }
	                if(position == 7){ // l social
                        Intent i = new Intent(view.getContext(), Social.class);
                        startActivity(i);
                        finish();
                        }
	                if(position == 8){
	                	Intent i = new Intent(view.getContext(), Places.class);
                        startActivity(i);
                        finish();
	                	} // places
	                if(position == 9){
	                	mSlideMenu.toggle();
	                } //header info
	                if(position == 10){
                        Intent i = new Intent(view.getContext(), Info.class);
                        startActivity(i);
                        finish();
                        }
					}
			});
				Button dashboard = (Button) findViewById(R.id.btw_back_to_dashboard);
				
				dashboard.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View view) {
		                Intent i = new Intent(getApplicationContext(), Main.class);
		                startActivity(i);
		                finish();
		            }
		        });
	}
@Override
public void finish(){
	super.finish();
	overridePendingTransition(0, R.anim.slide_out_down);
}

}
