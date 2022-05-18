package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tag);

        Button registerbtn = findViewById(R.id.registertagbtn);
        EditText tagname = (EditText) findViewById(R.id.editTextTextPersonName4);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rintent = new Intent(PersonalTagActivity.this, TagListActivity.class);
                PersonalTagActivity.this.startActivity(rintent);
                finish();
            }
        });
    }
}