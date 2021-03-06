package com.kmutts.pethome.mysqldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String MY_PREFS = "my_prefs";

    EditText etUsername,etPassword;
    //String session ;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(session.getLogin("login",getApplicationContext())){
            //startActivity(new Intent(this,UploadActivity.class));
            startActivity(new Intent(this,UserCustomListView.class));
        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }
    public void OnLogin(View view){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String type = "login";
        /*session = username;
        Intent x = new Intent(getApplicationContext(), PostActivity.class);

        x.putExtra("username",username);
        startActivity(x);*/
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);

    }

    public void OpenRegister(View view){
        startActivity(new Intent(this,Register.class));
    }

    public void onGuest(View view){
        startActivity(new Intent(this,GuestActivity.class));
    }

    public void onPhoto(View view){
        startActivity(new Intent(this,UploadActivity.class));
    }
}
