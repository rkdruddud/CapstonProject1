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

import java.nio.charset.StandardCharsets;


public class IdSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);

        Button idsearchbtn = findViewById(R.id.idsearchbtn);
        Button searchID = findViewById(R.id.searchID);
        TextView name = findViewById(R.id.editNameID);
        TextView phonNumber = findViewById(R.id.editPhoneNumber);
        TextView idinfo = findViewById(R.id.textViewidinfo);

        searchID.setVisibility(searchID.INVISIBLE);
        idsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strName = name.getText().toString();
                String strPhon = phonNumber.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                String userName = jsonObject.getString("userName");
                                String userPhonNumber = jsonObject.getString("userPhonNumber");
                                String id = jsonObject.getString("userID");
                                idsearchbtn.setVisibility(idsearchbtn.INVISIBLE);
                                searchID.setVisibility(searchID.VISIBLE);

                                idinfo.setVisibility(idinfo.VISIBLE);
                                idinfo.setText("아이디 : " + id);
                            }else{
                                Toast.makeText(getApplicationContext(),"등록된 회원이 아닙니다. 회원가입을 해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                IdInfoRequest idInfoRequest = new IdInfoRequest(strName, strPhon, responseListener);
                RequestQueue queue = Volley.newRequestQueue(IdSearchActivity.this);
                queue.add(idInfoRequest);

            }
        });

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