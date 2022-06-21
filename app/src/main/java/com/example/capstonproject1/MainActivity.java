package com.example.capstonproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mPw, mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("CapstonProject1");

        mId = findViewById(R.id.editIDMain);
        mPw = findViewById(R.id.editPasswordMain);

        Button login = findViewById(R.id.loginButton);
        TextView idSearch = findViewById(R.id.idSearchtxt);
        TextView pwSearch = findViewById(R.id.pwSearchtxt);
        TextView register = findViewById(R.id.enrolledtxt);
        TextView id = findViewById(R.id.editIDMain);
        TextView password = findViewById(R.id.editPasswordMain);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = mId.getText().toString();
                String Password = mPw.getText().toString();
               /* if(mId.toString().length() <= 0 && mPw.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), " 아이디와 비밀번호를 입력하시오. ", Toast.LENGTH_SHORT);
                }
                else if(mId.toString().length() <= 0 && mPw.toString().length() >= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), " 비밀번호를 입력하시오. ", Toast.LENGTH_SHORT);
                }
                else if(mId.toString().length() >= 0 && mPw.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), " 아이디를 입력하시오. ", Toast.LENGTH_SHORT);
                }*/
                /* 아이디와 패스워드 확인 코드 작성 */
                if(mId.toString().trim().isEmpty()||mPw.toString().trim().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), " 아이디와 비밀번호를 확인하시오. ", Toast.LENGTH_SHORT);
                }
                else {

                    mFirebaseAuth.signInWithEmailAndPassword(ID, Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                                MainActivity.this.startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

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