package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

// 물건 목록에서 뒤로가기 누르면 mainscreenActivity로 화면전환하는 코드 작성하기

        plustag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagListActivity.this,RegisterTagActivity.class);
                TagListActivity.this.startActivity(intent);
            }
        });
    }
}