package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.loginButton);
        TextView idSearch = findViewById(R.id.idSearchtxt);
        TextView pwSearch = findViewById(R.id.pwSearchtxt);
        TextView register = findViewById(R.id.enrolledtxt);
        TextView id = findViewById(R.id.editIDMain);
        TextView password = findViewById(R.id.editPasswordMain);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(id.getText() == NULL && password.getText()==NULL){
                    Toast toast = Toast.makeText(getApplicationContext(), " 아이디와 비밀번호를 입력하시오. ", Toast.LENGTH_LONG);
                }
                else if(id.getText() == NULL && password.getText()!=NULL){
                    Toast toast = Toast.makeText(getApplicationContext(), " 비밀번호를 입력하시오. ", Toast.LENGTH_LONG);
                }
                else if(id.getText() != NULL && password.getText()==NULL){
                    Toast toast = Toast.makeText(getApplicationContext(), " 아이디를 입력하시오. ", Toast.LENGTH_LONG);
                }else {} */
                /* 아이디와 패스워드 확인 코드 작성 */

                Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(MainActivity.this,RegisterActivity.class );
                MainActivity.this.startActivity(registerintent);
            }
        });

        idSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idintent = new Intent(MainActivity.this, IdSearchActivity.class);
                MainActivity.this.startActivity(idintent);
            }
        });

        pwSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pwintent = new Intent(MainActivity.this, PwSearchActivity.class);
                MainActivity.this.startActivity(pwintent);
            }
        });
    }


}