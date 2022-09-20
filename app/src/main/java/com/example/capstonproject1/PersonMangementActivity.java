package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonMangementActivity extends AppCompatActivity {


    RecyclerView AddPersonManageList;
    PersonManageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_mangement);

        AddPersonManageList = findViewById(R.id.friendManageRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AddPersonManageList.setLayoutManager(layoutManager);

        adapter = new PersonManageListAdapter(getApplicationContext());
        AddPersonManageList.setAdapter(adapter);


        Intent gintent = getIntent();
        String userName = gintent.getStringExtra("userName");
        String userPhonNumber = gintent.getStringExtra("userPhonNumber");
        String userID = gintent.getStringExtra("userID");
        String friendID = gintent.getStringExtra("friendID");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    boolean success = jsonObject1.getBoolean("success");
                    if(success){

                      String userAccept = "친구 요청 중";

                        adapter.addItem(new PersonManageItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber,userAccept));
                    }else {

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        MakePersonManagelistRequest makePersonManagelistRequest = new MakePersonManagelistRequest(userID, friendID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PersonMangementActivity.this);
        queue.add(makePersonManagelistRequest);




    }
}