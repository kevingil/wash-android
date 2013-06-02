package com.kevingil.wash;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kevingil.ui.SeparatedListAdapter;
import com.kevingil.wash.more.Info;
import com.slidingmenu.lib.SlidingMenu;

public class Places extends SherlockActivity {

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
	ProgressDialog webViewProgress;
	ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in_bottom, 0);
		setTitle("schedule");
		setContentView(R.layout.webview_generic);
		setupActionBar();
		setUpSlideMenu();
		setUpSlideList();
		
		ImageButton btw_left = (ImageButton) findViewById(R.id.btw_slidemenutoggle_main_left);
		btw_left.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	mSlideMenu.toggle();
		    }
		});
		
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        webViewProgress = ProgressDialog.show(this, "", "loading...", true);
        mWebView.loadUrl("file:///android_asset/places.html");
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient(){
        	
            @Override
            public void onPageFinished(WebView view, String url){
                webViewProgress.dismiss();
             }
        });
        // settings 
        mWebViewSettings = mWebView.getSettings();
        mWebViewSettings.setSavePassword(true);
        mWebViewSettings.setSaveFormData(true);
        mWebViewSettings.setJavaScriptEnabled(true);
        
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
    
	private void setupActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.ab_places);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
	
	public void setUpSlideMenu(){
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

	                if(position == 1){
	                	Intent i = new Intent(view.getContext(), Schoolloop.class);
	                	startActivity(i);
	                	finish();
	                	} // l home
	                if(position == 2){
	                	Intent i = new Intent(view.getContext(), Main.class);
	                	startActivity(i);
	                	} // l mail
	                if(position == 3){ //l news
	                         Intent i = new Intent(view.getContext(), News.class);
	                         startActivity(i);
	                         finish();
	                         }
	                if(position == 4){ // l bulletin
	                         Intent i = new Intent(view.getContext(), Bulletin.class);
	                         startActivity(i);
	                         finish();
	                         }
	                if(position == 5){
	                	mSlideMenu.toggle();
	                	} //header wash
	                if(position == 6){ // l schedule
	                	mSlideMenu.toggle();
	                             }
	                if(position == 7){ // l social
                        Intent i = new Intent(view.getContext(), Social.class);
                        startActivity(i);
                        finish();
                        }
	                if(position == 8){
	                	mSlideMenu.toggle();
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
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
        mWebView.goBack();
        return true;
    } else {
    	Intent i = new Intent(Places.this, Main.class);
    	startActivity(i);
    	finish();
    }
    return super.onKeyDown(keyCode, event);
}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(0, R.anim.slide_out_down);
	}
	
	
	
}
