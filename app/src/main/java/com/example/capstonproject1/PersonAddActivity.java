package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonAddActivity extends AppCompatActivity {

    RecyclerView AddPersonList;
    PersonListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_add);

        AddPersonList = findViewById(R.id.frecyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AddPersonList.setLayoutManager(layoutManager);

        adapter = new PersonListAdapter(getApplicationContext());
        AddPersonList.setAdapter(adapter);


        Button searchImage = findViewById(R.id.searchimagebtn);
        Button register = findViewById(R.id.button4);
        EditText searchPerson = findViewById(R.id.editPhonSearch);

        final String[] f = new String[1];
        final String[] userName1 = new String[1];
        final String[] userPhonNumber1 = new String[1];

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phonNumber = searchPerson.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String userName = jsonObject.getString("userName");
                                String userPhonNumber = jsonObject.getString("userPhonNumber");
                                String frindid = jsonObject.getString("userID");

                                userName1[0]=userName;
                                userPhonNumber1[0] = userPhonNumber;
                                f[0] = frindid;

                                if(adapter.getItemCount() == 0) {
                                    adapter.addItem(new PersonRecycleItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber));
                                }else{
                                    adapter.notifyItemChanged(-1);
                                    adapter.addItem(new PersonRecycleItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber));
                                }




                            }else{
                                Toast.makeText(getApplicationContext(),"검색된 친구가 없습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                PersonaddRequest personaddRequest = new PersonaddRequest(phonNumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PersonAddActivity.this);
                queue.add(personaddRequest);
            }
        });
        // 친구 신청 버튼 클리시 이벤트 발생 == > 에러 클릭 이벤트가 발생안함
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gintent = getIntent();


                final String userID = gintent.getStringExtra("userID");

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"친구 신청 완료",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PersonAddActivity.this, PersonMangementActivity.class);
                                intent.putExtra("userName", userName1[0]);
                                intent.putExtra("userPhonNumber", userPhonNumber1[0]);
                                PersonAddActivity.this.startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"친구 신청에 에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                FriendAddRequest friendAddRequest = new FriendAddRequest(userID, f[0], responseListener);
                RequestQueue queue = Volley.newRequestQueue(PersonAddActivity.this);
                queue.add(friendAddRequest);
            }
        });

    }
}