package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PwSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_search);

        Button pwsearch = findViewById(R.id.searchPW);
        TextView id = findViewById(R.id.editPersonIDtxt);
        TextView name = findViewById(R.id.editPersonNametxt);
        TextView phoneNumber = findViewById(R.id.editTextPhonepw);

        pwsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 아이디 , 이름 , 핸드폰 번호 일치시 비밀번호 알려주는 기능 작성*/
                Intent searchintent = new Intent(PwSearchActivity.this, ChangePWActivity.class);
                PwSearchActivity.this.startActivity(searchintent);
                finish();
            }
        });
    }
}