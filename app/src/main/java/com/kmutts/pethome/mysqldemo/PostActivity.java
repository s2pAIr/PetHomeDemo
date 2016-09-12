package com.kmutts.pethome.mysqldemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ADMIN PC on 11/9/2559.
 */
public class PostActivity extends AppCompatActivity {
    EditText postname,description,pettype;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initInstances();

    }

    private void initInstances() {
        postname = (EditText) findViewById(R.id.etPostName);
        description = (EditText) findViewById(R.id.etDescription);
        pettype = (EditText) findViewById(R.id.etPetType);

    }

    public void onPost(View view){
        String str_postname = postname.getText().toString();
        String str_description = description.getText().toString();
        String str_pettype = pettype.getText().toString();
        String type = "post";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,str_postname,str_description,str_pettype);
    }
}
