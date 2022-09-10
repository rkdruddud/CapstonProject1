package com.example.capstonproject1;

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

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwactivity);

        Button btn = findViewById(R.id.searchPW2);
        EditText newPW = findViewById(R.id.editTextTextPassword);
        EditText confirmPW = findViewById(R.id.editTextTextPassword4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inIntent = getIntent();
                final String userID = inIntent.getStringExtra("userID");
                final String userName = inIntent.getStringExtra("userName");
                final String userPhonNumber = inIntent.getStringExtra("userPhonNumber");
                String userPassword = newPW.getText().toString();
                String confirmPassword = confirmPW.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean success = jsonObject.getBoolean("success");
                           if(!userPassword.equals(confirmPassword)){
                               Toast.makeText(getApplicationContext(), "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                           }
                            else if (success) {
                                Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent bintent = new Intent(ChangePWActivity.this, MainActivity.class);
                                ChangePWActivity.this.startActivity(bintent);
                                finish();
                            } else {

                                Toast.makeText(getApplicationContext(), "비밀번호 변경에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                PasswordInfoRequest passwordInfoRequest= new PasswordInfoRequest(userID, userPassword, userName, userPhonNumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangePWActivity.this);
                queue.add(passwordInfoRequest);


            }
        });
    }
}