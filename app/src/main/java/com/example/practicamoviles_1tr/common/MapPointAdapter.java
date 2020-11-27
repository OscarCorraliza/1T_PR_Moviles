package com.example.practicamoviles_1tr.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.models.MapPoint;

import java.util.List;


public class MapPointAdapter extends BaseAdapter {

    private Context mContext;
    private List<MapPoint> mapPoints;

    public MapPointAdapter(Context mContext, List<MapPoint> listPools) {
        this.mContext = mContext;
        this.mapPoints = listPools;
    }

    @Override
    public int getCount() {
        return mapPoints.size();
    }

    @Override
    public Object getItem(int position) {
        return mapPoints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_mappoints, null);
        }
        TextView textView=convertView.findViewById(R.id.txtPools);
        textView.setText(mapPoints.get(position).getTitle());

        return convertView;
    }
}