package com.example.capstonproject1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TagListActivity extends AppCompatActivity {

    Intent inIntent = getIntent();
    String personalTagName = (String) inIntent.getStringExtra("TagName");
    int classficationNum = inIntent.getIntExtra("Num1",0) + inIntent.getIntExtra("Num2",0);

    @Override
    public void onBackPressed() {
        Intent bintent = new Intent(TagListActivity.this, MainScreenActivity.class);
        TagListActivity.this.startActivity(bintent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(classficationNum == 1){
            Toast.makeText(getApplicationContext(),"개인",Toast.LENGTH_SHORT).show();
        }
        else if(classficationNum == 2){
            Toast.makeText(getApplicationContext(),"그룹",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        final ArrayList<String> tagList = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.taglistView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tagList);
        list.setAdapter(adapter);

        long click_time = System.currentTimeMillis();

        Button plustag = findViewById(R.id.plusTagbutton);




        plustag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TagListActivity.this,RegisterTagActivity.class);
                TagListActivity.this.startActivity(intent);
            }
        });

    }

}