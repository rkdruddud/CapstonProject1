package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TagListActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent bintent = new Intent(TagListActivity.this, MainScreenActivity.class);
        TagListActivity.this.startActivity(bintent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        long click_time = System.currentTimeMillis();

        Button plustag = findViewById(R.id.plusTagbutton);

        final ArrayList<String> tagList = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.taglistView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tagList);
        list.setAdapter(adapter);

        plustag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TagListActivity.this,RegisterTagActivity.class);
                TagListActivity.this.startActivity(intent);
            }
        });

    }

}