package com.example.lp.lastpictures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hwang on 2016-12-19.
 */

public class CafeDataAdapter extends BaseAdapter{

    ArrayList<FindAddressActivity.CafeData> datas;
    LayoutInflater inflater;

    public CafeDataAdapter(LayoutInflater inflater, ArrayList<FindAddressActivity.CafeData> datas){
        this.datas = datas;
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.list_low, null);
        }

        TextView text_title = (TextView)view.findViewById(R.id.text_title);
        TextView text_address = (TextView)view.findViewById(R.id.text_address);

        text_title.setText(datas.get(i).getTitle());
        text_address.setText(datas.get(i).getAddress());

        return view;
    }
}
