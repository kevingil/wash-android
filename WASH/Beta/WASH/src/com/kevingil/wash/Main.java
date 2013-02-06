package com.kevingil.wash;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kevingil.utils.Slide_List;
import com.kevingil.utils.Slide_List_Adapter;

import com.slidingmenu.lib.SlidingMenu;

public class Main extends Activity {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("home");
		
		setContentView(R.layout.activity_main);
		
		setupActionBar();
		
		setUpSlideMenu();
		
		setUpSlideListSchoolloop();
		
		setUpSlideListWash();
		
		setUpDashboard();
     
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
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
    
    void setUpSlideMenu() {
		mSlideMenu = new SlidingMenu(this); 
        
		mSlideMenu.setMode(SlidingMenu.LEFT);
        
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
        
		mSlideMenu.setShadowDrawable(R.drawable.shadow);
        
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        
		mSlideMenu.setFadeDegree(0.35f);
        
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        
		mSlideMenu.setMenu(R.layout.slide_menu_main);
    }
    
	void setupActionBar() {
		ab = getActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.actionbar_centered_title);
		ab.setDisplayHomeAsUpEnabled(true);
	}
	
	void onHomeAsUpClicked(){
		mSlideMenu.toggle();
	}
	
	void setUpSlideListWash(){
		
		Slide_List values_wash[] = new Slide_List[]
                {
                    new Slide_List(R.drawable.dashboard_logo_small, "home"),
                    new Slide_List(R.drawable.schoolloop_logo_small, "schoolloop"),
                    new Slide_List(R.drawable.bulletin_small, "bulletin"),
                    new Slide_List(R.drawable.clock_small, "schedule"),
                    new Slide_List(R.drawable.groups_logo_small, "social"),
                    new Slide_List(R.drawable.about_logo_small, "about")
                };
                
                Slide_List_Adapter adapter = new Slide_List_Adapter(this, 
                        R.layout.list_adapter, values_wash);
                
                
                sListView = (ListView)findViewById(R.id.slide_menu_wash);
                sListView.setAdapter(adapter);
                sListView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) // arrays start with 0. So 0 = 1, 1 = 2 and so on.
                { 
                	mSlideMenu.toggle();
                }

                if(position == 1)
                {  
                	 Intent i = new Intent(Main.this, Schoolloop.class);
                	 startActivity(i);
                	 mSlideMenu.toggle();
                }
                
                if(position == 2)
                {  
                         Intent mintent2 = new Intent(view.getContext(), Bulletin.class);
                             startActivityForResult(mintent2, 0);
                             mSlideMenu.toggle();
                }
                
                if(position == 3)
                {  
                         Intent mintent3 = new Intent(view.getContext(), Schedule.class);
                             startActivityForResult(mintent3, 0);
                             mSlideMenu.toggle();
                }
                
                if(position == 4)
                {  
                         Intent mintent4 = new Intent(view.getContext(), Social.class);
                             startActivityForResult(mintent4, 0);
                             mSlideMenu.toggle();
                }

                if(position == 5)
                {  
                         Intent mintent5 = new Intent(view.getContext(), Info.class);
                             startActivityForResult(mintent5, 0);
                             mSlideMenu.toggle();
                }
                
                    }
                  });    
		
	}
	
	
    
	void setUpSlideListSchoolloop(){
		
		Slide_List values_schoolloop[] = new Slide_List[]
                {
                    new Slide_List(R.drawable.dashboard_logo_small, "home"),
                    new Slide_List(R.drawable.schoolloop_logo_small, "schoolloop"),
                    new Slide_List(R.drawable.bulletin_small, "bulletin"),
                    new Slide_List(R.drawable.clock_small, "schedule"),
                    new Slide_List(R.drawable.groups_logo_small, "social"),
                    new Slide_List(R.drawable.about_logo_small, "about")
                };
                
                Slide_List_Adapter adapter = new Slide_List_Adapter(this, 
                        R.layout.list_adapter, values_schoolloop);
                
                
                mListView = (ListView)findViewById(R.id.slide_menu_schoolloop);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) // arrays start with 0. So 0 = 1, 1 = 2 and so on.
                { 
                	mSlideMenu.toggle();
                }

                if(position == 1)
                {  
                	 Intent i = new Intent(Main.this, Schoolloop.class);
                	 startActivity(i);
                	 mSlideMenu.toggle();
                }
                
                if(position == 2)
                {  
                         Intent mintent2 = new Intent(view.getContext(), Bulletin.class);
                             startActivityForResult(mintent2, 0);
                             mSlideMenu.toggle();
                }
                
                if(position == 3)
                {  
                         Intent mintent3 = new Intent(view.getContext(), Schedule.class);
                             startActivityForResult(mintent3, 0);
                             mSlideMenu.toggle();
                }
                
                if(position == 4)
                {  
                         Intent mintent4 = new Intent(view.getContext(), Social.class);
                             startActivityForResult(mintent4, 0);
                             mSlideMenu.toggle();
                }

                if(position == 5)
                {  
                         Intent mintent5 = new Intent(view.getContext(), Info.class);
                             startActivityForResult(mintent5, 0);
                             mSlideMenu.toggle();
                }
                
                    }
                  });    
		
	}
	
	void setUpDashboard(){
		


        // Dashboard News feed button
        btn_dashboard_schoolloop = (Button) findViewById(R.id.btn_dashboard_schoolloop);
 
        // Dashboard Friends button
        btn_dashboard_bulletin = (Button) findViewById(R.id.btn_dashboard_bulletin);
 
        // Dashboard Messages button
        btn_dashboard_schedule = (Button) findViewById(R.id.btn_dashboard_schedule);
 
        // Dashboard Places button
        btn_dashboard_social = (Button) findViewById(R.id.btn_dashboard_social);
        
 
        // Listening to Schoolloop button click
        btn_dashboard_schoolloop.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), Schoolloop.class);
                startActivity(i);
            }
        });
 
       // Listening Bulletin button click
        btn_dashboard_bulletin.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), Bulletin.class);
                startActivity(i);
            }
        });
 
        // Listening Schedule button click
        btn_dashboard_schedule.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), Schedule.class);
                startActivity(i);
            }
        });
 
        // Listening to Social button click
        btn_dashboard_social.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), Social.class);
                startActivity(i);
            }
        });
        
		
	}
}
