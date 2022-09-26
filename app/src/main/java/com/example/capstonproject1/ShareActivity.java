package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Button share = findViewById(R.id.shareButton);

        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");
        String tagID = gintent.getStringExtra("tagID");



        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){


                        int length = jsonObject.length();
                        for(int i =0; i< length-1; i++) {
                            String friendID = jsonObject.getString(String.valueOf(i));

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) {


                                        } else {
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            SearchFriendRequest searchFriendRequest = new SearchFriendRequest(userID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ShareActivity.this);
                            queue.add(searchFriendRequest);

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) {


                                        } else {
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            AddShareFriendRequest addShareFriendRequest = new AddShareFriendRequest(userID, tagID, friendID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ShareActivity.this);
                            queue.add(addShareFriendRequest);
                        }
                    }else{

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

         SearchPhNameRequest searchPhNameRequest= new SearchPhNameRequest(userID, responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(ShareActivity.this);
        queue2.add(searchPhNameRequest);




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sintent = new Intent(ShareActivity.this, MainScreenActivity.class);
                ShareActivity.this.startActivity(sintent);
                finish();
            }
        });
    }
}