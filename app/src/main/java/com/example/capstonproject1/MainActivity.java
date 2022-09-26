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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
   // private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
   // private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mPw, mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mFirebaseAuth = FirebaseAuth.getInstance();
        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("CapstonProject1");

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



                String strId = mId.getText().toString();
                String strPw = mPw.getText().toString();

                /* 아이디와 패스워드 확인 코드 작성 */
                if(mId.getText().toString().isEmpty()&&mPw.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), " 아이디와 비밀번호를 확인하시오. ", Toast.LENGTH_SHORT);
                }
                else {

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success){
                                    String userID = jsonObject.getString("userID");
                                    String userPass = jsonObject.getString("userPassword");
                                    Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                                   intent.putExtra("userID", strId);
                                    intent.putExtra("userPass", userPass);
                                    MainActivity.this.startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(strId, strPw, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
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