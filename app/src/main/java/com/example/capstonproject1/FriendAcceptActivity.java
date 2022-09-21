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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendAcceptActivity extends AppCompatActivity {

    RecyclerView AddAcceptList;
    FriendAcceptListAdapter adapter;
    private String ffriendID;
    private String afriendID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_accept);


        AddAcceptList = findViewById(R.id.friendManageRecycleView123);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AddAcceptList.setLayoutManager(layoutManager);

        adapter = new FriendAcceptListAdapter(getApplicationContext());
        AddAcceptList.setAdapter(adapter);


        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success){
                        int length = jsonObject.length();
                        for(int i =0; i< length-1; i++){
                        String friendAcceptID = jsonObject.getString(String.valueOf(i));


                            Response.Listener<String> responseListener = new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response){
                                    try {

                                        JSONObject jsonObject1 = new JSONObject(response);
                                        boolean success = jsonObject1.getBoolean("success");

                                        if(success){

                                            String userPhonNumber = jsonObject1.getString("userPhonNumber");
                                            String userName = jsonObject1.getString("userName");


                                                adapter.addItem(new FriendAcceptItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber));
                                                adapter.notifyItemRangeRemoved(0,1);

                                                adapter.setOnItemClickListener(new FriendAcceptListAdapter.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(View v, int position) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendAcceptActivity.this);
                                                        builder.setMessage("친구요청을 수락하시겠습니까?");
                                                        builder.setPositiveButton("거절", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                String phonNumber=adapter.items.get(position).PhonNumber;
                                                                Log.d("phonNumber : ",phonNumber);

                                                                Response.Listener<String> responseListener = new Response.Listener<String>(){
                                                                    @Override
                                                                    public void onResponse(String response){
                                                                        try {
                                                                            JSONObject jsonObject = new JSONObject(response);
                                                                            boolean success = jsonObject.getBoolean("success");
                                                                            if(success){
                                                                                String friendID = jsonObject.getString("userID");
                                                                                Log.d("friendID",friendID);
                                                                                setFfriendID(friendID);


                                                                                Response.Listener<String> responseListener2 = new Response.Listener<String>(){
                                                                                    @Override
                                                                                    public void onResponse(String response){
                                                                                        try {
                                                                                            JSONObject jsonObject = new JSONObject(response);
                                                                                            boolean success = jsonObject.getBoolean("success");
                                                                                            if(success){
                                                                                                adapter.remove(position);
                                                                                            }else{
                                                                                                Toast.makeText(getApplicationContext(),"친구 거절 실패",Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }
                                                                                        }catch (JSONException e){
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                    }
                                                                                };
                                                                                String selectfriendID = getFfriendID();
                                                                                DeleteSENDlistRequest deleteSENDlistRequest = new DeleteSENDlistRequest(selectfriendID, userID, responseListener2);
                                                                                RequestQueue queue2 = Volley.newRequestQueue(FriendAcceptActivity.this);
                                                                                queue2.add(deleteSENDlistRequest);

                                                                            }else{
                                                                                Toast.makeText(getApplicationContext(),"친구 거절 실패",Toast.LENGTH_SHORT).show();
                                                                                return;
                                                                            }
                                                                        }catch (JSONException e){
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                };
                                                                PersonaddRequest personaddRequest = new PersonaddRequest(phonNumber, responseListener);
                                                                RequestQueue queue = Volley.newRequestQueue(FriendAcceptActivity.this);
                                                                queue.add(personaddRequest);

                                                            }
                                                        });
                                                        builder.setNegativeButton("수락", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                String phonNumber=adapter.items.get(position).PhonNumber;
                                                                Log.d("phonNumber : ", phonNumber);

                                                                Response.Listener<String> responseListener = new Response.Listener<String>(){
                                                                    @Override
                                                                    public void onResponse(String response){
                                                                        try {
                                                                            JSONObject jsonObject = new JSONObject(response);
                                                                            boolean success = jsonObject.getBoolean("success");
                                                                            if(success){
                                                                                String friendID = jsonObject.getString("userID");
                                                                                Log.d("friendID",friendID);
                                                                                setAfriendID(friendID);

                                                                                Response.Listener<String> responseListener2 = new Response.Listener<String>(){
                                                                                    @Override
                                                                                    public void onResponse(String response){
                                                                                        try {
                                                                                            JSONObject jsonObject = new JSONObject(response);
                                                                                            boolean success = jsonObject.getBoolean("success");
                                                                                            if(success){
                                                                                                Toast.makeText(getApplicationContext(),"친구 등록 완료",Toast.LENGTH_SHORT).show();
                                                                                                adapter.remove(position);
                                                                                            }else{
                                                                                                Toast.makeText(getApplicationContext(),"친구 등록 실패",Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }
                                                                                        }catch (JSONException e){
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                    }
                                                                                };

                                                                                String selectfriendID = getAfriendID();
                                                                                OkayMyFriendRequest okayMyFriendRequest = new OkayMyFriendRequest(userID ,selectfriendID,  responseListener2);
                                                                                OkayMyFriendRequest okayMyFriendRequest2 = new OkayMyFriendRequest(selectfriendID,userID ,  responseListener2);
                                                                                RequestQueue queue2 = Volley.newRequestQueue(FriendAcceptActivity.this);
                                                                                queue2.add(okayMyFriendRequest);
                                                                                RequestQueue queue3 = Volley.newRequestQueue(FriendAcceptActivity.this);
                                                                                queue3.add(okayMyFriendRequest2);

                                                                                Response.Listener<String> responseListener3 = new Response.Listener<String>(){
                                                                                    @Override
                                                                                    public void onResponse(String response){
                                                                                        try {
                                                                                            JSONObject jsonObject = new JSONObject(response);
                                                                                            boolean success = jsonObject.getBoolean("success");
                                                                                            if(success){

                                                                                            }else{
                                                                                                Toast.makeText(getApplicationContext(),"친구 등록 실패",Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }
                                                                                        }catch (JSONException e){
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                    }
                                                                                };

                                                                                DeleteSENDlistRequest deleteSENDlistRequest = new DeleteSENDlistRequest(selectfriendID, userID,  responseListener2);
                                                                                RequestQueue queue4 = Volley.newRequestQueue(FriendAcceptActivity.this);
                                                                                queue4.add(deleteSENDlistRequest);

                                                                            }
                                                                            else{
                                                                                Toast.makeText(getApplicationContext(),"친구 등록 실패",Toast.LENGTH_SHORT).show();
                                                                                return;
                                                                            }
                                                                        }catch (JSONException e){
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                };

                                                                PersonaddRequest personaddRequest = new PersonaddRequest(phonNumber, responseListener);
                                                                RequestQueue queue = Volley.newRequestQueue(FriendAcceptActivity.this);
                                                                queue.add(personaddRequest);

                                                            }
                                                        });
                                                        builder.show();


                                                    }
                                                });
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"친구 신청에 에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            SearchPhNameRequest searchPhNameRequest = new SearchPhNameRequest(friendAcceptID ,responseListener); // 친구 요청을 보낸 userid 검색후 그 사람의 정보를 리스트로 표현
                            RequestQueue queue = Volley.newRequestQueue(FriendAcceptActivity.this);
                            queue.add(searchPhNameRequest);


                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"친구 요청이 없습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };


        FriendAcceptRequest friendAcceptRequest = new FriendAcceptRequest(userID ,responseListener); // 친구 요청을 보낸 userid 검색후 그 사람의 정보를 리스트로 표현
        RequestQueue queue = Volley.newRequestQueue(FriendAcceptActivity.this);
        queue.add(friendAcceptRequest);



    }

    public String getAfriendID(){
        return afriendID;
    }
    public void setAfriendID(String afriendID){
        this.afriendID = afriendID;
    }

    public String getFfriendID(){
        return ffriendID;
    }
    public void setFfriendID(String ffriendID){
        this.ffriendID = ffriendID;
    }

}
