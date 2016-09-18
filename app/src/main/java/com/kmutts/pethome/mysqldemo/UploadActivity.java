package com.kmutts.pethome.mysqldemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ADMIN PC on 18/9/2559.
 */
public class UploadActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonUpload;
    private Button buttonChoose;
    private TextView url;
    Context context;
    private EditText editText;
    private ImageView imageView;
    Session session;
    private int postId;
    private static String update;

    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "postid";
    public static final String UPLOAD_URL = "http://kmutts.com/pethome/upload.php";


    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;

    /*public UploadActivity(Context context) {
        this.context = context;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonChoose = (Button) findViewById(R.id.buttonChooseImage);

        // = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        url = (TextView)findViewById(R.id.imgurl);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            postId = bundle.getInt("postid");
        }
        Log.d("recive",postId+"");
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void uploadImage(){
        //final String text = editText.getText().toString().trim();
        final  String text = postId+"";
        final String image = getStringImage(bitmap);
        update = image;
        class UploadImage extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UploadActivity.this,"Please wait...","uploading",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UploadActivity.this,s,Toast.LENGTH_LONG).show();
                /*Intent x = new Intent(getApplicationContext(), PostActivity.class);
                x.putExtra("url", image);
                startActivity(x);*/
                //Update();
                //startActivity(new Intent(getApplicationContext(),PostActivity.class));
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> param = new HashMap<String,String>();
                param.put(KEY_ID,text);
                param.put(KEY_IMAGE,image);
                String result = rh.sendPostRequest(UPLOAD_URL, param);
                return result;
            }
        }
        UploadImage u = new UploadImage();
        u.execute();
    }


    @Override
    public void onClick(View v) {

        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
    }
   /* public void Update(){
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.text);
        txtView.setText(update);
    }*/

}