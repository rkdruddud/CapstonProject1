package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class IdSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);

        Button searchID = findViewById(R.id.searchID);
        TextView name = findViewById(R.id.editNameID);
        TextView phonNumber = findViewById(R.id.editPhoneNumber);

        searchID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* 이름과 폰 번호 디비에서 확인 */
                Intent idintent = new Intent(IdSearchActivity.this, MainActivity.class);
                IdSearchActivity.this.startActivity(idintent);
                /*  찾는 아이디를 토스트메시지 or 엑티비티로 보여줌. */
                finish();
            }
        });

    }
}