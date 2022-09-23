package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchSharePersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_share_person);

        Button sharebtn = findViewById(R.id.shareButton2);

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ssintent = new Intent(SearchSharePersonActivity.this, MainScreenActivity.class);
                SearchSharePersonActivity.this.startActivity(ssintent);
                finish();
            }
        });
    }
}