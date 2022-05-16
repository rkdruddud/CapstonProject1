package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroupRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_register);

        Button register = findViewById(R.id.registerGtagbtn);
        Button searchperson = findViewById(R.id.searchFreindbtn);

        searchperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sintent = new Intent(GroupRegisterActivity.this, SearchSharePersonActivity.class);
                GroupRegisterActivity.this.startActivity(sintent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent = new Intent(GroupRegisterActivity.this, TagListActivity.class);
                GroupRegisterActivity.this.startActivity(shareintent);
                finish();
            }
        });
    }
}