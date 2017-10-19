package com.tianyuhou.paymo;

import android.app.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu on 10/13/2017.
 */

public class DairyAdapter extends ArrayAdapter {

    List<Dairy> list = new ArrayList();
    Activity activity;
    int id;

    public DairyAdapter(@NonNull Activity context, int resource, List object) {
        super(context, resource, object);
        this.activity = context;
        this.id = resource;
        this.list = object;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(id, null);
        }
        Dairy dairy = list.get(position);
        TextView dairytitle = convertView.findViewById(R.id.dairytitle);
        TextView dairydesc = convertView.findViewById(R.id.dairydesc);
        TextView dairydate = convertView.findViewById(R.id.dairydate);
        TextView dairytime = convertView.findViewById(R.id.dairytime);
        TextView dairycost = convertView.findViewById(R.id.dairycost);
        TextView dairycategory = convertView.findViewById(R.id.dairycategory);

        dairytitle.setText(dairy.getTitle());
        dairydesc.setText(dairy.getDesc());
        dairytime.setText(dairy.getDate());
        dairycategory.setText(dairy.getCategory());
        String date = dairy.getDate();
        String newdate = date.substring(0,16);
        dairydate.setText(newdate);
        if(dairy.getCost()<0){
            dairycost.setTextColor(Color.GREEN);
        }else if(dairy.getCost()>0){
            dairycost.setTextColor(Color.RED);
        }else{
            dairycost.setTextColor(Color.WHITE);
        }
        dairycost.setText(String.valueOf(dairy.getCost()));

        return convertView;
    }


}
