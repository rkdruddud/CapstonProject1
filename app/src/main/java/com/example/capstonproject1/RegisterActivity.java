package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    boolean dID = false;
    private EditText mName, mPw, mPwTest, mHpNumber, mId;

// <uses-library android:name="org.json.JSONObject" android:required="false" />
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mId = findViewById(R.id.editTextTextId12);
        mPw = findViewById(R.id.editTextTextPassword22);
        mName = findViewById(R.id.editTextPhone);
        mHpNumber = findViewById(R.id.editTextPhone);
        mPwTest = findViewById(R.id.editTextTextPassword3);
        Button mBtnRegister = findViewById(R.id.complitebtn);
        Button doubleCheckBtn = findViewById(R.id.button2);

        doubleCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = mId.getText().toString();

                Response.Listener<String> responseListener2 = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                dID = false;
                                Toast.makeText(getApplicationContext(), "중복된 아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                dID = true;
                                Toast.makeText(getApplicationContext(), "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DoubleCheckRequest doubleCheckRequest = new DoubleCheckRequest(userID, responseListener2);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(doubleCheckRequest);
            }
        });


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = mId.getText().toString();
                String userPassword = mPw.getText().toString();
                String strPwTest = mPwTest.getText().toString();
                String userName = mName.getText().toString();
                String userPhonNumber = mHpNumber.getText().toString();

                if(!strPwTest.equals(userPassword) ){
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }else if(dID == false){
                    Toast.makeText(getApplicationContext(), "아이디 중복을 확인하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userPhonNumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}