package com.kmutts.pethome.mysqldemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by ADMIN PC on 11/9/2559.
 */
public class UserActivity extends AppCompatActivity {
    DrawerLayout drawerLayout; //Hamburger
    ActionBarDrawerToggle actionBarDrawerToggle; //Hamburger
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initInstances();
    }
    private void initInstances() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout); //Hamburger
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                UserActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        ); //Hamburger
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //Hamburger

        getSupportActionBar().setHomeButtonEnabled(true); //Hamburger
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Hamburger
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState(); //Hamburger
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig); //Hamburger
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;//Hamburger
        return super.onOptionsItemSelected(item);
    }

    public void OpenPost(View view){
        startActivity(new Intent(this,PostActivity.class));
    }



    public void goLogout(View view){
        this.finish();
    }

    public void goCustomViewGroup(View view){
        startActivity(new Intent(this,UserCustomListView.class));
    }
    public class UsersAdapter extends ArrayAdapter<String> {


        public UsersAdapter(Context context, int resource,int resource2,ArrayList a) {
            super(context, resource,resource2,a );
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return convertView;
        }
    }
}
