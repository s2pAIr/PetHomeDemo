package com.kmutts.pethome.mysqldemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ADMIN PC on 12/9/2559.
 */
public class CustomAdapter extends BaseAdapter {
    Context mContext;
    String[] strName;
    String[] strDescription;
    String [] id;
    String [] reUrl;
    int[] reId;

    //

    public CustomAdapter(Context context, String[] strName,String[] strDescription, String[] reUrl,String[] id) {
        this.mContext= context;
        this.strName = strName;
        this.strDescription = strDescription;
        this.reUrl = reUrl;
        this.reId = reId;
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




        ImageView ivImg = (ImageView)view.findViewById(R.id.ivImg);
        new DownloadImageTask(ivImg).execute(reUrl[position]);

        //ivImg.setBackgroundResource(reId[position]);
        //Log.d("j","koko");
        view.setTag(id[position]);
        //Log.d("gg",id[position]);
        //View view = mInflater.inflate(R.layout.list_item_photo,parent,false);*/
        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
