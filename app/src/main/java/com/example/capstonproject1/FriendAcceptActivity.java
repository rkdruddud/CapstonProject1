package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendAcceptActivity extends AppCompatActivity {

    RecyclerView AddAcceptList;
    FriendAcceptListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_accept);


        AddAcceptList = findViewById(R.id.friendARecycleView13);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AddAcceptList.setLayoutManager(layoutManager);

        adapter = new FriendAcceptListAdapter(getApplicationContext());
        AddAcceptList.setAdapter(adapter);

        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");
        String userPassword = gintent.getStringExtra("userPassword");

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        String userName = jsonObject.getString("userName");
                        String userPhonNumber = jsonObject.getString("userPhonNumber");

                        Response.Listener<String> responseListener2 = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response){
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){
                                            adapter.addItem(new FriendAcceptItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber ));


                                    }else{
                                        Toast.makeText(getApplicationContext(),"친구 신청에 에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        FriendAcceptRequest friendAcceptRequest = new FriendAcceptRequest(userID ,responseListener2); // 친구 요청을 보낸 userid 검색후 그 사람의 정보를 리스트로 표현
                        RequestQueue queue = Volley.newRequestQueue(FriendAcceptActivity.this);
                        queue.add(friendAcceptRequest);

                    }else{
                        Toast.makeText(getApplicationContext(),"error!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FriendAcceptActivity.this);
        queue.add(loginRequest);




    }
}