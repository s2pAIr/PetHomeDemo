package com.kmutts.pethome.mysqldemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by ADMIN PC on 12/9/2559.
 */
public class UserCustomListView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        int[] resId = {R.drawable.dog01,R.drawable.dog02,R.drawable.dog03,R.drawable.dog04,R.drawable.dog05,R.drawable.cat01,R.drawable.cat02,R.drawable.cat03,R.drawable.cat04,R.drawable.cat05};
        String[] name ={"NameDog01","NameDog02","NameDog03","NameDog04","NameDog05","NameCat01","NameCat02","NameCat03","NameCat04","NameCat05"};
        String[] description = {"DescriptionDog01","DescriptionDog02","DescriptionDog03","DescriptionDog04","DescriptionDog05","DescriptionCat01","DescriptionCat02","DescriptionCat03","DescriptionCat04","DescriptionCat05"};

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),name,description,resId);

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
