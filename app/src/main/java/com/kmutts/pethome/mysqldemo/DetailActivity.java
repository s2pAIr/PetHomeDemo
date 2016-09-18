package com.kmutts.pethome.mysqldemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ArrayList<String> exData;
    private ArrayList<String> exData2;
    private ArrayList<String> exData3;
    private ArrayList<String> exData4;
    private ArrayList<String> exData5;
    private ArrayList<String> exData6;
    private ArrayList<String> commentdata;
    private ArrayList<String> commentUsername;
    private ArrayList<String> postId;
    int[] resId = {R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,R.drawable.cat01,R.drawable.cat02,R.drawable.cat03,R.drawable.cat04,R.drawable.cat05};
    Session useID;
     int mealId = -1;
    private ProgressDialog progressDialog;
    EditText comm;
    Button btComm;
    private String fakeuseId = 1+"";
    protected void onCreate(Bundle savedInstanceState) {
        exData = new ArrayList<String>();
        exData2 = new ArrayList<String>();
        exData3 = new ArrayList<String>();
        exData4 = new ArrayList<String>();
        exData5 = new ArrayList<String>();
        exData6 = new ArrayList<String>();
        commentUsername = new ArrayList<String>();
        commentdata = new ArrayList<String>();
        postId = new ArrayList<String>();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            mealId = bundle.getInt("id");
        }

        comm = (EditText)findViewById(R.id.comm) ;
        btComm = (Button)findViewById(R.id.btComm);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(DetailActivity.this);
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
                        exData4.add(jsonObj.getString("pettype"));
                        exData5.add(jsonObj.getString("gender"));
                        exData6.add(jsonObj.getString("username"));


                    }
                     url = new URL("http://pethome.kmutts.com/comment_json.php");

                     urlConnection = url.openConnection();

                     httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.connect(); OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(mealId+"","UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

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
                    //Log.d("JSON Result", stringBuilder.toString());

                     jsonObject = new JSONObject(stringBuilder.toString());
                     exArray = jsonObject.getJSONArray("result");
                    //Log.d("gg","wp");
                    for (int i = 0; i < exArray.length(); i++) {

                        JSONObject jsonObj = exArray.getJSONObject(i);
                        commentdata.add(jsonObj.getString("comment"));
                        commentUsername.add(jsonObj.getString("username"));
                        postId.add(jsonObj.getString("id"));
                        Log.d("gg",jsonObj.getString("id"));
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
                int x = -1;
                ListView listView = (ListView) findViewById(R.id.listView);
                //listView.smoothScrollToPosition(arr.getCount‌​() - 1);
                //ArrayAdapter<String> arr = new ArrayAdapter<String>(DetailActivity.this,android.R.layout.simple_list_item_1,commentdata);
                //listView.setAdapter(arr);
                CustomComment adapter = new CustomComment(getApplicationContext(),commentUsername.toArray(new String[commentUsername.size()]),commentdata.toArray(new String[commentdata.size()]),resId,postId.toArray(new String[postId.size()]));
                listView.setAdapter(adapter);
                TextView text1 = (TextView) findViewById(R.id.textView3);
                TextView text2 = (TextView) findViewById(R.id.textView4);
                TextView text3 = (TextView) findViewById(R.id.textView6);
                TextView text4 = (TextView) findViewById(R.id.textView7);
                TextView dtUse = (TextView) findViewById(R.id.dtUsername);
                for(int i =0;i<exData.size();i++){
                   if((mealId+"").equals(exData3.get(i))){
                       x = i;
                   }
                }

                text1.setText(exData.get(x)+"");
                text2.setText(exData2.get(x)+"");
                text3.setText("ชนิด : "+exData4.get(x));
                text4.setText("เพศ :  "+exData5.get(x));
                dtUse.setText("ผู้โพส :  "+exData6.get(x));
                progressDialog.dismiss();
            }

        }.execute();
    }

    public void onComment(View view){
        int first = (int)((Math.random() *10)+1);
        int second = (int)((Math.random() *10)+1);
        int third = (int)((Math.random() *10)+1);
        String comment = comm.getText().toString();
        String postid = mealId+"";
        //Log.d("id",mealId+"");
        String type = "comment";
        String username;
        if(useID.getLogin("login",getApplicationContext())){
            username = useID.getDefaults("username",getApplicationContext());
        }else{
            username = "guest"+first+second+third;
        }
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type ,username,comment,postid);
    }
}