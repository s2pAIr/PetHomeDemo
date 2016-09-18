package com.kmutts.pethome.mysqldemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by ADMIN PC on 11/9/2559.
 */
public class PostActivity extends AppCompatActivity {
    EditText postname,description;
    Context context;
    RadioGroup g1;
    RadioButton r11;
    RadioButton r12;
    RadioButton r13;
    RadioButton r21;
    RadioButton r22;
    RadioButton r23;
    private String pettype;
    private String gender;
    private String url = "URL";
    private String username;
    private String pn;
    private int id ;
    private ArrayList<String> postId;
    private ArrayList<String> postName;
    private ProgressDialog progressDialog;
    private ArrayList<String> imgUrl;
    private ArrayList<String> imgPId;
    private  static TextView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initInstances();
        r11 = (RadioButton)findViewById(R.id.radioButton) ;
        r12 = (RadioButton)findViewById(R.id.radioButton2) ;
        r13 = (RadioButton)findViewById(R.id.radioButton3) ;
        r11.setOnClickListener(func2);
        r12.setOnClickListener(func2);
        r13.setOnClickListener(func2);
        r21 = (RadioButton)findViewById(R.id.radioButton4) ;
        r22 = (RadioButton)findViewById(R.id.radioButton5) ;
        r23 = (RadioButton)findViewById(R.id.radioButton6) ;
        r21.setOnClickListener(func);
        r22.setOnClickListener(func);
        r23.setOnClickListener(func);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
             url =  String.valueOf(bundle.getInt("url"));
        }

        postId = new ArrayList<String>();
        postName = new ArrayList<String>();
        imgPId = new ArrayList<String>();
        imgUrl = new ArrayList<String>();
        img = (TextView)findViewById(R.id.imgurl);
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(PostActivity.this);
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
                    postId.add(jsonObj.getString("id"));
                    postName.add(jsonObj.getString("postname"));
                    //Log.d("id",jsonObj.getString("id"));
                    //Log.d("size",postName.size()+"");
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
            //Log.d("url",url);
            progressDialog.dismiss();
        }

    }.execute();


    }
    RadioButton.OnClickListener func = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(r11.isChecked()){
                gender ="เพศผู้";

            }else if(r12.isChecked()){
                gender = "เพศเมีย";

            }else {
                gender = "ไม่ระบุ";

            }

        }
    };
    RadioButton.OnClickListener func2 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(r11.isChecked()){
                pettype ="แมว";

            }else if(r12.isChecked()){
                pettype = "สุนัข";


            }else {
                pettype = "ไม่ระบุ";


            }

        }
    };

    private void initInstances() {
        postname = (EditText) findViewById(R.id.etPostName);
        description = (EditText) findViewById(R.id.etDescription);
        g1 = (RadioGroup) findViewById(R.id.pettype);

    }

    public void onPost(View view){

    String str_postname = postname.getText().toString();
    String str_description = description.getText().toString();
    String str_pettype = pettype;
    Session useN = new Session();
    username = useN.getDefaults("username",getApplicationContext());
        //Log.d("gg",username);
    String str_gender = gender;
    String type = "post";
    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
    backgroundWorker.execute(type,str_postname,str_description,str_pettype,username,str_gender);

}
    public void onUpload(View view) {
        //startActivity(new Intent(this,UploadActivity.class));String pn = postname.getText().toString();
        pn =postname.getText().toString();
        Log.d("size",pn);
        if ( !(pn.equals(""))) {
            for (int i = 0; i < postId.size(); i++) {
                int max = 0;
                if (Integer.parseInt(postId.get(i))>max) {
                    max = Integer.parseInt(postId.get(i));
                    id = (Integer.parseInt(postId.get(i))+1);


                }

            }
            /*if(id!=0){
                for(int i = 0;i<imgUrl.size();i++) {
                    if(Integer.parseInt(imgPId.get(i))==id) {
                        img.setText(imgUrl.get(i).toString());
                        Log.d("url",imgUrl.get(i).toString());
                    }
                }
            }*/
            //Log.d("maxjaa",id+"");
            Intent x = new Intent(getApplicationContext(), UploadActivity.class);
            //Log.d("gg", postname.getText().toString());
            x.putExtra("postid", id);
            startActivity(x);

        } else {
            Toast.makeText(PostActivity.this, "Please enter Postname and Description", LENGTH_SHORT).show();
        }
    }

}
