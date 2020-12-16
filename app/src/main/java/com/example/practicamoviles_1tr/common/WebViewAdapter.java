package com.example.practicamoviles_1tr.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.models.MapPoint;

import java.util.List;

public class WebViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> webs;

    public WebViewAdapter(Context mContext, List<String> webs) {
        this.mContext = mContext;
        this.webs = webs;
    }
    @Override
    public int getCount() {
        return webs.size();
    }

    @Override
    public Object getItem(int position) {
        return webs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_web_view, null);
        }
        TextView txt = convertView.findViewById(R.id.txtWeb);
        txt.setText(webs.get(position));
        return convertView;
    }
}
