package com.kmutts.pethome.mysqldemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
public class GuestActivity extends AppCompatActivity {
    private ListView jsonListview;
    private ArrayList<String> exData;
    private ArrayList<String> exData2;
    private ArrayList<String> exData3;
    private ArrayList<String> imgUrl;
    private ArrayList<String> imgPId;
    private ArrayList<String> reUrl;
    int [] re =new int[imgUrl.size()];
    private ProgressDialog progressDialog;

    private Session session;
    int[] resId = {R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,R.drawable.cat01,R.drawable.cat02,R.drawable.cat03,R.drawable.cat04,R.drawable.cat05};
    String[] name ={"NameDog01","NameDog02","NameDog03","NameDog04","NameDog05","NameCat01","NameCat02","NameCat03","NameCat04","NameCat05"};
    String[] description = {"DescriptionDog01","DescriptionDog02","DescriptionDog03","DescriptionDog04","DescriptionDog05","DescriptionCat01","DescriptionCat02","DescriptionCat03","DescriptionCat04","DescriptionCat05"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        jsonListview = (ListView) findViewById(R.id.json_listview);

        exData = new ArrayList<String>();
        exData2 = new ArrayList<String>();
        exData3 = new ArrayList<String>();
        imgUrl = new ArrayList<String>();
        imgPId = new ArrayList<String>();
        reUrl = new ArrayList<String>();


        new AsyncTask<Void,Void,Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(GuestActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading ...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://pethome.kmutts.com/post_json.php");

                    URLConnection urlConnection = url.openConnection();

                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream = null;

                    if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                        inputStream = httpURLConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);

                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line=reader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    Log.d("JSON Result",stringBuilder.toString());

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray exArray = jsonObject.getJSONArray("result");

                    for(int i=0;i<exArray.length();i++){
                        JSONObject jsonObj = exArray.getJSONObject(i);
                        exData.add(jsonObj.getString("postname"));
                        exData2.add(jsonObj.getString("description"));
                        exData3.add(jsonObj.getString("id"));
                    }
                    url = new URL("http://pethome.kmutts.com/upload_json.php");

                    urlConnection = url.openConnection();

                    httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    inputStream = null;

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                        inputStream = httpURLConnection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);

                    stringBuilder = new StringBuilder();
                    line = null;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    Log.d("JSON Result", stringBuilder.toString());

                    jsonObject = new JSONObject(stringBuilder.toString());
                    exArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < exArray.length(); i++) {
                        JSONObject jsonObj = exArray.getJSONObject(i);
                        imgUrl.add(jsonObj.getString("image"));
                        imgPId.add(jsonObj.getString("post_id"));
                        //Log.d("id",jsonObj.getString("id"));
                        //Log.d("size",postName.size()+"");
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
                ListView listView = (ListView) findViewById(R.id.json_listview);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),exData.toArray(new String[exData.size()]),exData2.toArray(new String[exData2.size()]),reUrl.toArray(new String[reUrl.size()]),exData3.toArray(new String[exData3.size()]));
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
    }
    public void OpenPost(View view){
        startActivity(new Intent(this,PostActivity.class));
    }
}
