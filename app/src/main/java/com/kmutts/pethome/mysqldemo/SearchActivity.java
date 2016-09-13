package com.kmutts.pethome.mysqldemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SearchActivity extends AppCompatActivity {
    EditText search;
    Button btSearch;
    RadioGroup g1;
    RadioButton r11 ;
    RadioButton r12;
    RadioButton r13;
    RadioButton r21 ;
    RadioButton r22;
    RadioButton r23;
    private String pettype;
    private String gender;
    private ArrayList<String> exData;
    private ArrayList<String> exData2;
    private ArrayList<String> exData3;
    private ArrayList<String> exData4;
    private ArrayList<String> exData5;
    private ProgressDialog progressDialog;
    int mealId = -1;
    int [] sResult;
    int[] resId = {R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,R.drawable.cat01,R.drawable.cat02,R.drawable.cat03,R.drawable.cat04,R.drawable.cat05};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        r11 = (RadioButton) findViewById(R.id.radioButton);
        r12 = (RadioButton) findViewById(R.id.radioButton2);
        r13 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton.OnClickListener func2 = null;
        r11.setOnClickListener(func2);
        r12.setOnClickListener(func2);
        r13.setOnClickListener(func2);
        r21 = (RadioButton) findViewById(R.id.radioButton4);
        r22 = (RadioButton) findViewById(R.id.radioButton5);
        r23 = (RadioButton) findViewById(R.id.radioButton6);
        RadioButton.OnClickListener func = null;
        r21.setOnClickListener(func);
        r22.setOnClickListener(func);
        r23.setOnClickListener(func);
        //sResult = new int[0];

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(SearchActivity.this);
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
                search =(EditText)findViewById(R.id.etSearch);
                btSearch =(Button)findViewById(R.id.btSearch);
                btSearch.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0;i<exData.size();i++){
                            if(pettype.equals(exData4)){
                                sResult [i]=Integer.parseInt(exData2.get(i));
                            }
                        };
                    }
                });

                progressDialog.dismiss();
            }

        }.execute();
        }
    RadioButton.OnClickListener func = new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r11.isChecked()) {
                    gender = "เพศผู้";

                } else if (r12.isChecked()) {
                    gender = "เพศเมีย";

                } else {
                    gender = "ไม่ระบุ";

                }

            }
        };
    RadioButton.OnClickListener func2 = new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r11.isChecked()) {
                    pettype = "แมว";

                } else if (r12.isChecked()) {
                    pettype = "สุนัข";


                } else {
                    pettype = "ไม่ระบุ";


                }

            }
        };
    }
