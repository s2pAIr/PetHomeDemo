package com.kmutts.pethome.mysqldemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

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

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by ADMIN PC on 12/9/2559.
 */
public class UserCustomListView extends AppCompatActivity {
    DrawerLayout drawerLayout; //Hamburger
    ActionBarDrawerToggle actionBarDrawerToggle; //Hamburger
    private ArrayList<String> exData;
    private ArrayList<String> exData2;
    private ArrayList<String> exData3;
    private ProgressDialog progressDialog;
    int[] resId = {R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,R.drawable.cat01,R.drawable.cat02,R.drawable.cat03,R.drawable.cat04,R.drawable.cat05};
    String[] name ={"NameDog01","NameDog02","NameDog03","NameDog04","NameDog05","NameCat01","NameCat02","NameCat03","NameCat04","NameCat05"};
    String[] description = {"DescriptionDog01","DescriptionDog02","DescriptionDog03","DescriptionDog04","DescriptionDog05","DescriptionCat01","DescriptionCat02","DescriptionCat03","DescriptionCat04","DescriptionCat05"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        initInstances();
        exData = new ArrayList<String>();
        exData2 = new ArrayList<String>();
        exData3 = new ArrayList<String>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Log.d("55",fab.toString());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCustomListView.this,PostActivity.class);
                startActivity(intent);
            }
        });
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(UserCustomListView.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading ...");
                progressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://pethome.kmutts.com/post_json.php");

                    URLConnection urlConnection = url.openConnection();

                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream = null;

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                        inputStream = httpURLConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);

                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    Log.d("JSON Result", stringBuilder.toString());

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray exArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < exArray.length(); i++) {
                        JSONObject jsonObj = exArray.getJSONObject(i);
                        exData.add(jsonObj.getString("postname"));
                        exData2.add(jsonObj.getString("description"));
                        exData3.add(jsonObj.getString("id"));
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ListView listView = (ListView) findViewById(R.id.listView1);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),exData.toArray(new String[exData.size()]),exData2.toArray(new String[exData2.size()]),resId,exData3.toArray(new String[exData3.size()]));
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent x = new Intent(getApplicationContext(), DetailActivity.class);

                        x.putExtra("id",Integer.parseInt(view.getTag()+""));
                        startActivity(x);
                    }
                });

                progressDialog.dismiss();
            }

        }.execute();
       /* CustomAdapter adapter = new CustomAdapter(getApplicationContext(),name,description,resId);

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }

    private void initInstances() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout); //Hamburger
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                UserCustomListView.this,
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
        int id = item.getItemId();

        if (id == R.id.serach) {
            Toast.makeText(UserCustomListView.this,"Search", LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goLogout(View view){
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=this.getMenuInflater();
        inflater.inflate(R.menu.menuitem,menu);
        MenuItem menuSerach = menu.findItem(R.id.serach);
        menuSerach.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(UserCustomListView.this,SearchActivity.class);
                startActivity(intent);
                return false;
            }
        } );
        return true;
    }



}
