package com.kmutts.pethome.mysqldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ADMIN PC on 12/9/2559.
 */
public class CustomAdapter extends BaseAdapter {
    Context mContext;
    String[] strName;
    String[] strDescription;
    String [] id;
    int[] resId;
    //

    public CustomAdapter(Context context, String[] strName,String[] strDescription, int[] resId,String[] id) {
        this.mContext= context;
        this.strName = strName;
        this.strDescription = strDescription;
        this.resId = resId;
        this.id = id;
    }

    @Override
    public int getCount() {
        return strName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view==null)
            view = mInflater.inflate(R.layout.listview_row,parent,false);

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(strName[position]);

        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(strDescription[position]);

        ImageView ivImg = (ImageView) view.findViewById(R.id.ivImg);
        ivImg.setBackgroundResource(resId[1]);
        view.setTag(id[position]);
        //View view = mInflater.inflate(R.layout.list_item_photo,parent,false);
        return view;
    }
}
