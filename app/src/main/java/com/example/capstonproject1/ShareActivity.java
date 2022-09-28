package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ShareActivity extends AppCompatActivity {
    RecyclerView ShareFriendList;
    SharelistAdapter adapter;
    private String puserID;
    private String pfriendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ShareFriendList = findViewById(R.id.ShareRecyclerView15555);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ShareFriendList.setLayoutManager(layoutManager);

        adapter = new SharelistAdapter(getApplicationContext());
        ShareFriendList.setAdapter(adapter);

        Button share = findViewById(R.id.shareButton);

        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");
        String tagID = gintent.getStringExtra("tagID");

        setPuserID(userID);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {

                        int length = jsonObject.length();
                        for(int i =0; i< length-1; i++) {
                            String friendID = jsonObject.getString(String.valueOf(i));
                            // 친구의 이름 가져와서 아이템 만들기
                            Log.d("FriendID",friendID);

                            Response.Listener<String> responseListener2 = new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if(success){
                                            String friendName = jsonObject.getString("userName");
                                         setPfriendName(friendName);



                                            adapter.addItem(new Sharelistitem(R.drawable.ic_baseline_person_24, friendName));
                                            adapter.notifyItemChanged(0);


                                        }else{

                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            };

                            SearchPhNameRequest searchPhNameRequest= new SearchPhNameRequest(friendID, responseListener2);
                            RequestQueue queue2 = Volley.newRequestQueue(ShareActivity.this);
                            queue2.add(searchPhNameRequest);

                            adapter.setOnItemClickListener(new SharelistAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
                                    builder.setMessage("물건의 위치를 공유하시겠습니까?");
                                    builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    builder.setNegativeButton("공유", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //데이터베이스에 공유하는 친구 저장
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
                                    });
                                    builder.show();
                                }

                            });

                        }

                        } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String ppuserID = getPuserID();
        Log.d("userID : " , ppuserID);
        SearchFriendRequest searchFriendRequest = new SearchFriendRequest(ppuserID, responseListener);
        RequestQueue queue3 = Volley.newRequestQueue(ShareActivity.this);
        queue3.add(searchFriendRequest);



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Intent sintent = new Intent(ShareActivity.this, MainScreenActivity.class);
                sintent.putExtra("userID", userID);
                setResult(RESULT_OK,sintent);
                ShareActivity.this.startActivity(sintent);
                finish();
            }
        });
    }


    public String getPuserID(){
        return puserID;
    }
    public void setPuserID(String puserID){
        this.puserID = puserID;
    }

    public String getPfriendName(){
        return pfriendName;
    }
    public void setPfriendName(String pfriendName){
        this.pfriendName = pfriendName;
    }
}