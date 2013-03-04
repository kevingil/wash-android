package com.kevingil.utils;


import com.kevingil.wash.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//This is the password!
// 5124400069

public class Slide_List_Adapter extends ArrayAdapter<Slide_List>{

	Context context; 
    int layoutResourceId;    
    Slide_List data[] = null;
    
    public Slide_List_Adapter(Context context, int layoutResourceId, Slide_List[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
        
        Slide_List weather = data[position];
        holder.txtTitle.setText(weather.title);
        holder.imgIcon.setImageResource(weather.icon);
        //holder.imgIcon.setImageSize();
        
        return row;
    }
    
    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}