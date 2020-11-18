package com.example.practicamoviles_1tr.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.models.Pools;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PoolsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Pools> listPools;


    @Override
    public int getCount() {
        return listPools.size();
    }

    @Override
    public Object getItem(int position) {
        return listPools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_pools, null);
        }
        TextView textView=convertView.findViewById(R.id.txtPools);
        textView.setText(listPools.get(position).getTitle());

        return convertView;
    }
}
