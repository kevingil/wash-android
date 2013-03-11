package com.kevingil.wash;

import java.util.HashMap;
import java.util.Map;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.kevingil.utils.SeparatedListAdapter;
import com.slidingmenu.lib.SlidingMenu;
//import com.kevingil.utils.Slide_List;
//import com.kevingil.utils.Slide_List_Adapter;

public class Main extends SherlockActivity {
	// starts slide list ints
	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";
	private final static String[] items_schoolloop = new String[]{"home", "mail", "news", "bulletin"};
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
	public ListView mListView;
	public ListView sListView;
    Button btn_dashboard_schoolloop;
    Button btn_dashboard_bulletin;
    Button btn_dashboard_schedule;
    Button btn_dashboard_social;
    TextView mTitle;
    ActionBar ab;
    LayoutInflater inflator;
    Bundle translate_up;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		overridePendingTransition(R.anim.slide_in_bottom, 0);
		super.onCreate(savedInstanceState);
		setTitle("home");
		setContentView(R.layout.activity_main);
		setupActionBar();
		setUpSlideMenu();
		setUpSlideList();
		setUpDashboard();
		
		translate_up = ActivityOptions.makeCustomAnimation(
        		getApplicationContext(), R.anim.slide_in_bottom, 0).toBundle();
		
		ImageButton btw_left = (ImageButton) findViewById(R.id.btw_slidemenutoggle_main_left);
		btw_left.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	mSlideMenu.toggle();
		    }
		});
		
		
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
	                	startActivity(i, translate_up);
	                	finish();
	                	} // l home
	                if(position == 2){
	                	mSlideMenu.toggle();
	                	} // l mail
	                if(position == 3){ //l news
	                         Intent i = new Intent(view.getContext(), News.class);
	                         startActivity(i, translate_up);
	                         finish();
	                         }
	                if(position == 4){ // l bulletin
	                         Intent i = new Intent(view.getContext(), Bulletin.class);
	                         startActivity(i, translate_up);
	                         finish();
	                         }
	                if(position == 5){
	                	mSlideMenu.toggle();
	                	} //header wash
	                if(position == 6){ // l schedule
	                	Intent i = new Intent(view.getContext(), Schedule.class);
                        startActivity(i, translate_up);
                        finish();
	                             }
	                if(position == 7){ // l social
                        Intent i = new Intent(view.getContext(), Social.class);
                        startActivity(i, translate_up);
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
                        startActivity(i, translate_up);
                        finish();
                        }
					}
			});
		
	}

    
    void setUpSlideMenu() 
    {
		mSlideMenu = new SlidingMenu(this); 
		mSlideMenu.setMode(SlidingMenu.LEFT);
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
		//mSlideMenu.setShadowDrawable(R.drawable.shadow);
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlideMenu.setBehindScrollScale(0.0f);
		mSlideMenu.setFadeDegree(0.35f);
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		mSlideMenu.setMenu(R.layout.slide_menu_main);
    }
    
	void setupActionBar() {
		ab = getSupportActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.actionbar_centered_title);
		ab.setDisplayHomeAsUpEnabled(true);
	}

	void setUpDashboard(){
        btn_dashboard_schoolloop = (Button) findViewById(R.id.btn_dashboard_schoolloop);
        btn_dashboard_bulletin = (Button) findViewById(R.id.btn_dashboard_bulletin);
        btn_dashboard_schedule = (Button) findViewById(R.id.btn_dashboard_schedule);
        btn_dashboard_social = (Button) findViewById(R.id.btn_dashboard_social);
 
        btn_dashboard_schoolloop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Schoolloop.class);
                startActivity(i,translate_up);
                finish();
            }
        });
        
        btn_dashboard_bulletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Bulletin.class);
                startActivity(i, translate_up);
                finish();
            }
        });

        btn_dashboard_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Schedule.class);
                startActivity(i, translate_up);
                finish();
            }
        });

        btn_dashboard_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	
                Intent i = new Intent(getApplicationContext(), Social.class);
                startActivity(i, translate_up);
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
