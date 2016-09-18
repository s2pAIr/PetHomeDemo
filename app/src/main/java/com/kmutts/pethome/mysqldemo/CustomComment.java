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
public class CustomComment extends BaseAdapter {
    Context mContext;
    String[] strName;
    String[] strComm;
    String [] id;
    int[] resId;
    //

    public CustomComment(Context context, String[] strName,String[] strComm, int[] resId,String[] id) {
        this.mContext= context;
        this.strName = strName;
        this.strComm = strComm;
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
            view = mInflater.inflate(R.layout.listcomment,parent,false);

        TextView tvUse = (TextView) view.findViewById(R.id.tvUse);
        tvUse.setText(strName[position]);

        TextView tvComm = (TextView) view.findViewById(R.id.tvComm);
        tvComm.setText(strComm[position]);

        ImageView ivImg = (ImageView) view.findViewById(R.id.ivImg);
        ivImg.setBackgroundResource(resId[1]);
        view.setTag(id[position]);
        //View view = mInflater.inflate(R.layout.list_item_photo,parent,false);
        return view;
    }
}
