package com.kevingil.slidelistviewheader;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity
	{

		public final static String ITEM_TITLE = "title";
		public final static String ITEM_CAPTION = "caption";

		// SectionHeaders
		private final static String[] days = new String[]{"schoolloop", "wash", "developer"};

		// Section Contents
		private final static String[] schoolloop_items = new String[]{"home", "loopmail", "assignemts"};
		
		private final static String[] wash_items = new String[]{"bulletin", "news", "schedule"};

		
		// Adapter for ListView Contents
		private SeparatedListAdapter adapter;
		private SeparatedListAdapter adapter2;

		// ListView Contents
		private ListView journalListView;



		@Override
		public void onCreate(Bundle icicle)
			{
				super.onCreate(icicle);

				// Sets the View Layer
				setContentView(R.layout.activity_main);


				// Create the ListView Adapter
				adapter = new SeparatedListAdapter(this);
				ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, R.layout.list_item, schoolloop_items);

				// Add Sections
						adapter.addSection("schoolloop", listadapter);
						
				adapter2 = new SeparatedListAdapter(this);
						ArrayAdapter<String> listadapter2 = new ArrayAdapter<String>(this, R.layout.list_item, wash_items);

						// Add Sections
					     adapter2.addSection("wash", listadapter2);
						

				// Get a reference to the ListView holder
				//journalListView = (ListView) this.findViewById(R.id.list_journal);

				// Set the adapter on the ListView holder
				journalListView.setAdapter(adapter);

				// Listen for Click events
				journalListView.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
							{
							 if (position == 1){
								String item = (String) adapter.getItem(position);
								Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
							 }
							}
					});
			}

	}