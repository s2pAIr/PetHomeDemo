package com.kmutts.pethome.mysqldemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by ADMIN PC on 11/9/2559.
 */
public class PostActivity extends AppCompatActivity {
    EditText postname,description;
    RadioGroup g1;
    RadioButton r11 ;
    RadioButton r12;
    RadioButton r13;
    RadioButton r21 ;
    RadioButton r22;
    RadioButton r23;
    private String pettype;
    private String gender;
    private String username;
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
        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
             username =  String.valueOf(bundle.getInt("id"));
        }*/


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
        String str_gender = gender;
        String type = "post";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,str_postname,str_description,str_pettype,str_gender);
    }


}
