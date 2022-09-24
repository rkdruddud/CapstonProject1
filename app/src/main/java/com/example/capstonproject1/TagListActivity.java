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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TagListActivity extends AppCompatActivity {

    RecyclerView taglistRecyclerView;
    TagListAdapter adapter;

    @Override
    public void onBackPressed() {
        Intent bintent = new Intent(TagListActivity.this, MainScreenActivity.class);
        TagListActivity.this.startActivity(bintent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        Button plustag = findViewById(R.id.plusTagbutton);

        taglistRecyclerView = findViewById(R.id.taglistRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        taglistRecyclerView.setLayoutManager(layoutManager);

        adapter = new TagListAdapter(getApplicationContext());
        taglistRecyclerView.setAdapter(adapter);

        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");
        String tagName = gintent.getStringExtra("tagName");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    boolean success = jsonObject1.getBoolean("success");

                    if(success){

                        // tagId를userID로 검색 해서 반복문 써서 태그 아이디 마다의 태그 이름을 가져오는 반복문 작성하기


                        Response.Listener<String> responseListener3 = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response){
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){


                                    }else{

                                        return;
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };

                        SearchTagNameRequest searchTagNameRequest = new SearchTagNameRequest(tagID,  responseListener3);
                        RequestQueue queue4 = Volley.newRequestQueue(TagListActivity.this);
                        queue4.add(searchTagNameRequest);



                        adapter.setOnItemClickListener(new TagListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(TagListActivity.this);

                                builder.setMessage("등록된 태그를 삭제 하시겠습니까?");

                                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                                builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                            }
                        });



                    }
                    else {


                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        SearchTagIDRequest searchTagIDRequest = new SearchTagIDRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TagListActivity.this);
        queue.add(searchTagIDRequest);


        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건8", "개인용"));






        plustag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TagListActivity.this,PersonalTagActivity.class);
                TagListActivity.this.startActivity(intent);
            }
        });

    }

}