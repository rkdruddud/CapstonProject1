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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TagListActivity extends AppCompatActivity {

    RecyclerView taglistRecyclerView;
    TagListAdapter adapter;
    private String ptagID;
    private String puserID;
    private String ptagName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        Button plustag = findViewById(R.id.plusTagbutton);

        taglistRecyclerView = findViewById(R.id.taglistRecyclerView54321);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        taglistRecyclerView.setLayoutManager(layoutManager);

        adapter = new TagListAdapter(getApplicationContext());
        taglistRecyclerView.setAdapter(adapter);

        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");

        String tagID = gintent.getStringExtra("tagID");
        String tagName = gintent.getStringExtra("tagName");
        setPtagName(tagName);

        Response.Listener<String> responseListener6 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        int length2 = jsonObject.length();
                        for(int i =0; i< length2-1; i++) {

                            //String tagName = jsonObject.getString("tagName");
                            String ggtagID = jsonObject.getString(String.valueOf(i));
                            setPtagID(ggtagID);

                            Log.d("ggtagID",ggtagID);
                            String gggtagName = getPtagName();

                            adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24, ggtagID, "공유 받는 중"));
                            adapter.notifyItemRangeRemoved(0, 1);

                            adapter.setOnItemLongClickListener(new TagListAdapter.OnItemLongClickListener() {
                                @Override
                                public void onItemLongClick(View v, int position) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TagListActivity.this);
                                    String gettagName=adapter.items.get(position).tagname;

                                    builder.setMessage("등록된 태그를 삭제 하시겠습니까?");

                                    builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });

                                    builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            adapter.remove(position);
                                            //TagID찾기
                                            Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {
                                                            String gtagID = jsonObject.getString("tagID");
                                                            setPtagID(gtagID);


                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            SearchTaginfoIDRequest searchTaginfoIDRequest = new SearchTaginfoIDRequest(gettagName, responseListener4);
                                            RequestQueue queue5 = Volley.newRequestQueue(TagListActivity.this);
                                            queue5.add(searchTaginfoIDRequest);
                                            //TagInfo 의 정보 삭제
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {


                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            String pptagID = getPtagID();
                                            Log.d("pptagID", pptagID);
                                            DeleteTaginfoRequest deleteTaginfoRequest = new DeleteTaginfoRequest(userID ,pptagID, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(TagListActivity.this);
                                            queue.add(deleteTaginfoRequest);

                                            //UserTag 정보 삭제
                                            Response.Listener<String> responseListener7 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {


                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            DeleteUserTagRowRequest deleteUserTagRowRequest = new DeleteUserTagRowRequest(userID ,pptagID, responseListener7);
                                            RequestQueue queue7 = Volley.newRequestQueue(TagListActivity.this);
                                            queue.add(deleteUserTagRowRequest);


                                            //TagLocation 의 정보 삭제
                                            Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {

                                                            Log.d("위치 정보 삭제 : ", "성공");
                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            DeleteTagLocationRequest deleteTagLocationRequest = new DeleteTagLocationRequest(pptagID, responseListener1);
                                            RequestQueue queue2 = Volley.newRequestQueue(TagListActivity.this);
                                            queue2.add(deleteTagLocationRequest);

                                            //ShareFriend의 정보 삭제
                                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {

                                                            Log.d("공유 중인 친구 삭제 : ", "성공");
                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            DeleteShareFriendRequest deleteShareFriendRequest = new DeleteShareFriendRequest(userID ,pptagID, responseListener2);
                                            RequestQueue queue3 = Volley.newRequestQueue(TagListActivity.this);
                                            queue3.add(deleteShareFriendRequest);
                                        }
                                    });
                                    builder.show();
                                }
                            });

                            adapter.setOnItemClickListener(new TagListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    String tagName = adapter.items.get(position).tagname;
                                    Intent dintent = new Intent(TagListActivity.this, SharedTagActivity.class);
                                    dintent.putExtra("tagName", tagName);
                                    dintent.putExtra("tagID", ggtagID);
                                    dintent.putExtra("userID", userID);
                                    startActivity(dintent);

                                }
                            });

                        }
                    } else {

                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SearchShareFriendTagIDRequest searchShareFriendTagIDRequest = new SearchShareFriendTagIDRequest(userID, responseListener6);
        RequestQueue queue4 = Volley.newRequestQueue(TagListActivity.this);
        queue4.add(searchShareFriendTagIDRequest);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    boolean success = jsonObject1.getBoolean("success");

                    if(success){

                        // tagId를userID로 검색 해서 반복문 써서 태그 아이디 마다의 태그 이름을 가져오는 반복문 작성하기
                        int length = jsonObject1.length();
                        for(int i =0; i< length-1; i++) {
                            String tagID = jsonObject1.getString(String.valueOf(i));


                            setPuserID(userID);
                            Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) {
                                            String tagName = jsonObject.getString("tagName");
                                            setPtagName(tagName);

                                        } else {

                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            SearchTagNameRequest searchTagNameRequest = new SearchTagNameRequest(tagID, responseListener3);
                            RequestQueue queue6 = Volley.newRequestQueue(TagListActivity.this);
                            queue6.add(searchTagNameRequest);

                            adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,tagName, "개인용"));
                            adapter.notifyItemRangeRemoved(0,1);

                            adapter.setOnItemLongClickListener(new TagListAdapter.OnItemLongClickListener() {
                                @Override
                                public void onItemLongClick(View v, int position) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TagListActivity.this);
                                    String gettagName=adapter.items.get(position).tagname;

                                    builder.setMessage("등록된 태그를 삭제 하시겠습니까?");

                                    builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });

                                    builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            adapter.remove(position);
                                            //TagID찾기
                                            Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {
                                                            String gtagID = jsonObject.getString("tagID");
                                                            setPtagID(gtagID);

                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            SearchTaginfoIDRequest searchTaginfoIDRequest = new SearchTaginfoIDRequest(gettagName, responseListener4);
                                            RequestQueue queue5 = Volley.newRequestQueue(TagListActivity.this);
                                            queue5.add(searchTaginfoIDRequest);
                                            //TagInfo 의 정보 삭제
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {


                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            String pptagID = getPtagID();
                                            DeleteTaginfoRequest deleteTaginfoRequest = new DeleteTaginfoRequest(userID ,pptagID, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(TagListActivity.this);
                                            queue.add(deleteTaginfoRequest);

                                            //TagLocation 의 정보 삭제
                                            Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {

                                                            Log.d("위치 정보 삭제 : ", "성공");
                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            DeleteTagLocationRequest deleteTagLocationRequest = new DeleteTagLocationRequest(pptagID, responseListener1);
                                            RequestQueue queue2 = Volley.newRequestQueue(TagListActivity.this);
                                            queue2.add(deleteTagLocationRequest);

                                            //ShareFriend의 정보 삭제
                                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {

                                                            Log.d("공유 중인 친구 삭제 : ", "성공");
                                                        } else {

                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            DeleteShareFriendRequest deleteShareFriendRequest = new DeleteShareFriendRequest(userID ,pptagID, responseListener2);
                                            RequestQueue queue3 = Volley.newRequestQueue(TagListActivity.this);
                                            queue3.add(deleteShareFriendRequest);
                                        }
                                    });
                                    builder.show();
                                }
                            });

                            adapter.setOnItemClickListener(new TagListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    String tagName = adapter.items.get(position).tagname;
                                    Intent dintent = new Intent(TagListActivity.this, TagActivity.class);
                                    dintent.putExtra("tagName", tagName);
                                    dintent.putExtra("tagID", tagID);
                                    dintent.putExtra("userID", userID);
                                    startActivity(dintent);

                                }
                            });
                        }


                    }
                    else {
                        Toast.makeText(getApplicationContext(),"등록된 태그가 없습니다.",Toast.LENGTH_SHORT).show();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        String userID2 = gintent.getStringExtra("userID");
        setPuserID(userID2);

        SearchUserTagRequest searchUserTagRequest = new SearchUserTagRequest(userID2, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TagListActivity.this);
        queue.add(searchUserTagRequest);








        plustag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TagListActivity.this,TagSearchAddActivity.class);
                TagListActivity.this.startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent bintent = new Intent(TagListActivity.this, MainScreenActivity.class);
        String ggguserID = getPuserID();

        bintent.putExtra("userID",ggguserID);
        TagListActivity.this.startActivity(bintent);
        finish();
    }

    public String getPtagID(){
        return ptagID;
    }
    public void setPtagID(String ptagID){
        this.ptagID = ptagID;
    }

    public String getPuserID(){
        return puserID;
    }
    public void setPuserID(String puserID){
        this.puserID = puserID;
    }

    public String getPtagName(){
        return ptagName;
    }
    public void setPtagName(String ptagName){
        this.ptagName = ptagName;
    }


}