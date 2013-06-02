package com.kevingil.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kevingil.wash.R;

public class ListAdapter extends ArrayAdapter<ListItem>{

    Context context; 
    int layoutResourceId;    
    ListItem data[] = null;
    
    public ListAdapter(Context context, int layoutResourceId, ListItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ListItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ListItemHolder)row.getTag();
        }
        
        ListItem listitem = data[position];
        holder.txtTitle.setText(listitem.title);
        holder.imgIcon.setImageResource(listitem.icon);
        
        return row;
    }
    
    static class ListItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
