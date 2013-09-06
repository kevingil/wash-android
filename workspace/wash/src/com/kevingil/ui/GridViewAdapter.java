package com.kevingil.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.kevingil.wash.R;
 
public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private final String[] GridViewValues;
 
	public GridViewAdapter(Context context, String[] GridViewValues) {
		this.context = context;
		this.GridViewValues = GridViewValues;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.gridviewadapter, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(GridViewValues[position]);
 
			// set image based on selected text\
			
			// these will have to be custom for each
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
 
			String gridItemTitle = GridViewValues[position];
 
			/*
            new ListItem(R.drawable.ic_schoolloop, "Schoolloop"),
            new ListItem(R.drawable.ic_eaglenews, "Eagle News"),
            new ListItem(R.drawable.ic_schedule, "Schedule"),
            new ListItem(R.drawable.ic_bulletin, "Bulletin"),
            new ListItem(R.drawable.ic_social, "Social"),
            new ListItem(R.drawable.ic_places, "Places"),
            new ListItem(R.drawable.ic_settings, "Settings")
			*/
			
			
			if (gridItemTitle.equals("Schoolloop")) {
				imageView.setImageResource(R.drawable.ic_schoolloop);
			} else if (gridItemTitle.equals("Eagle News")) {
				imageView.setImageResource(R.drawable.ic_eaglenews);
			} else if (gridItemTitle.equals("Schedule")) {
				imageView.setImageResource(R.drawable.ic_schedule);
			} else if (gridItemTitle.equals("Bulletin")) {
				imageView.setImageResource(R.drawable.ic_bulletin);
			} else if (gridItemTitle.equals("Social")) {
				imageView.setImageResource(R.drawable.ic_social);
			} else if (gridItemTitle.equals("Places")) {
				imageView.setImageResource(R.drawable.ic_places);
			} else if (gridItemTitle.equals("Settings")) {
				imageView.setImageResource(R.drawable.ic_settings);
			} else {
				imageView.setImageResource(R.drawable.ic_launcher);
			}
 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return GridViewValues.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}