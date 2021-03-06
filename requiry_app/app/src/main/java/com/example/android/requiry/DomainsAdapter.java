package com.example.android.requiry;

/**
 * Created by tito on 26/3/17.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DomainsAdapter extends ArrayAdapter<Domains>{

    private Context mContext; //Adapter context
    private int mLayoutResourceId; //Adapter View Layout


    /*  ResearchProjectAdapter that calls constructor of ArrayAdapter with mContext and
        mLayoutResourceId as parameters */

    public DomainsAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }


    // Returns view of one item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final Domains currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);

        final TextView tv1 = (TextView) row.findViewById(R.id.dName);
        tv1.setText(currentItem.getdName());

        final TextView tv2 = (TextView) row.findViewById(R.id.dNumOfProj);
        tv2.setText(""+currentItem.getdNumOfProj());

        return row;
    }
}