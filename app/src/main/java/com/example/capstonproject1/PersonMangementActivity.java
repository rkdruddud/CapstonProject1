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

public class PersonMangementActivity extends AppCompatActivity {


    RecyclerView AddPersonManageList;
    PersonManageListAdapter adapter;
    private String afriendID;
    private String ffriendID;
    private String puserID;
    boolean confirmSEND = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_mangement);

        AddPersonManageList = findViewById(R.id.friendManageRecycleView123);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AddPersonManageList.setLayoutManager(layoutManager);

        adapter = new PersonManageListAdapter(getApplicationContext());
        AddPersonManageList.setAdapter(adapter);


        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");
        setPuserID(userID);
        String friendID = gintent.getStringExtra("friendID");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    boolean success = jsonObject1.getBoolean("success");
                    if(success){

                                        int length = jsonObject1.length();
                                        for(int i =0; i< length-1; i++) {
                                            String userID2 = jsonObject1.getString(String.valueOf(i));
                                            setAfriendID(userID2);

                                            Response.Listener<String> responseListener = new Response.Listener<String>(){
                                                @Override
                                                public void onResponse(String response){
                                                    try {

                                                        JSONObject jsonObject1 = new JSONObject(response);
                                                        boolean success = jsonObject1.getBoolean("success");

                                                        if(success){
                                                            String userPhonNumber = jsonObject1.getString("userPhonNumber");
                                                            String userName = jsonObject1.getString("userName");

                                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    try {
                                                                        JSONObject jsonObject1 = new JSONObject(response);
                                                                        boolean success = jsonObject1.getBoolean("success");

                                                                        if(success){
                                                                            confirmSEND = false;
                                                                        }
                                                                        else {
                                                                            confirmSEND = true; // 없을 때

                                                                        }
                                                                    }catch (JSONException e){
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            };

                                                            String selectuserID = getAfriendID();
                                                            setAfriendID(selectuserID);
                                                            Toast.makeText(getApplicationContext(),selectuserID,Toast.LENGTH_SHORT).show();
                                                            Log.d("selectuserID : ", selectuserID);

                                                            SearchSENDidRequest searchSENDidRequest = new SearchSENDidRequest(userID, selectuserID, responseListener);
                                                            RequestQueue queue = Volley.newRequestQueue(PersonMangementActivity.this);
                                                            queue.add(searchSENDidRequest);

                                                            if(confirmSEND == false) {

                                                             String accept = "친구";
                                                                adapter.addItem(new PersonManageItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber, accept));
                                                                adapter.notifyItemRangeRemoved(0, 1);
                                                            }
                                                            else if (confirmSEND == true) {
                                                                //SEND에서 찾은 아이디로 반복문 돌려서 true false 값을 찾아야함.

                                                                String accept = "친구 요청 중..";
                                                                // 여기에 친구요청 중인 친구를 찾는 리스폰스 넣기
                                                                adapter.addItem(new PersonManageItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber, accept));
                                                                adapter.notifyItemRangeRemoved(0, 1);
                                                            }

                                                            adapter.setOnItemClickListener(new PersonManageListAdapter.OnItemClickListener() {

                                                                @Override
                                                                public void onItemClick(View v, int position) {

                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonMangementActivity.this);

                                                                    builder.setMessage("친구를 삭제하시겠습니까?");

                                                                    builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {

                                                                        public void onClick(DialogInterface dialog, int id) {

                                                                        }
                                                                    });
                                                                    builder.setNegativeButton("친구삭제", new DialogInterface.OnClickListener() {

                                                                        public void onClick(DialogInterface dialog, int id) {

                                                                            String phonNumber=adapter.items.get(position).PhonNumber;

                                                                            Response.Listener<String> responseListener = new Response.Listener<String>(){
                                                                                @Override
                                                                                public void onResponse(String response){
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                                        boolean success = jsonObject.getBoolean("success");
                                                                                        if(success){
                                                                                            String fuserID = jsonObject.getString("userID");
                                                                                            setFfriendID(fuserID);

                                                                            Response.Listener<String> responseListener3 = new Response.Listener<String>(){
                                                                                @Override
                                                                                public void onResponse(String response){
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                                        boolean success = jsonObject.getBoolean("success");
                                                                                        if(success){
                                                                                            adapter.remove(position);
                                                                                        }else{

                                                                                            return;
                                                                                        }
                                                                                    }catch (JSONException e){
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                }
                                                                            };

                                                                            String selectuserID2 = getFfriendID();
                                                                            Log.d("selectuserID2 : ", selectuserID2);
                                                                            Toast.makeText(getApplicationContext(),selectuserID2,Toast.LENGTH_SHORT).show();

                                                                            Intent pIntent = getIntent();
                                                                           String sUserID = pIntent.getStringExtra("userID");


                                                                            DeleteFriendlistRequest deleteFriendlistRequest = new DeleteFriendlistRequest(selectuserID2, sUserID, responseListener3);
                                                                            RequestQueue queue4 = Volley.newRequestQueue(PersonMangementActivity.this);
                                                                            queue4.add(deleteFriendlistRequest);

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
                                                                            RequestQueue queue = Volley.newRequestQueue(PersonMangementActivity.this);
                                                                            queue.add(personaddRequest);
                                                                        }
                                                                    });
                                                                    builder.show();
                                                                }
                                                            });
                                                        }

                                            else {
                                                Toast.makeText(getApplicationContext(),"친구 목록에 에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                SearchPhNameRequest searchPhNameRequest = new SearchPhNameRequest(userID2 ,responseListener); // 친구 요청을 보낸 userid 검색후 그 사람의 정보를 리스트로 표현
                                RequestQueue queue = Volley.newRequestQueue(PersonMangementActivity.this);
                                queue.add(searchPhNameRequest);




                                        }

                    }else {

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };


        MakePersonManagelistRequest makePersonManagelistRequest = new MakePersonManagelistRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PersonMangementActivity.this);
        queue.add(makePersonManagelistRequest);




    }

    public String getFfriendID(){
        return ffriendID;
    }
    public void setFfriendID(String ffriendID){
        this.ffriendID = ffriendID;
    }

    public String getAfriendID(){
        return afriendID;
    }
    public void setAfriendID(String afriendID){
        this.afriendID = afriendID;
    }


    public String getPuserID(){
        return puserID;
    }
    public void setPuserID(String puserID){
        this.puserID = puserID;
    }
}