package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
                String userID = id.getText().toString();
                String userName = name.getText().toString();
                String userPhonNumber = phoneNumber.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                Intent searchintent = new Intent(PwSearchActivity.this, ChangePWActivity.class);
                                searchintent.putExtra("userID", userID);
                                searchintent.putExtra("userPhonNumber", userPhonNumber);
                                searchintent.putExtra("userName", userName);
                                PwSearchActivity.this.startActivity(searchintent);
                                finish();
                            } else {

                                Toast.makeText(getApplicationContext(), "입력하신 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ConfirmPWRequest confirmPWRequest= new ConfirmPWRequest(userID, userName, userPhonNumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PwSearchActivity.this);
                queue.add(confirmPWRequest);


                /* 아이디 , 이름 , 핸드폰 번호 일치시 비밀번호 알려주는 기능 작성*/

            }
        });
    }
}