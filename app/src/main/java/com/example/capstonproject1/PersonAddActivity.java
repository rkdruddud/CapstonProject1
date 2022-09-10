package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



        Button register = findViewById(R.id.button4);
        EditText searchPerson = findViewById(R.id.editPhonSearch);

        register.setOnClickListener(new View.OnClickListener() {
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

                                adapter.addItem(new PersonRecycleItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber));
/*
                                Intent intent = new Intent(PersonAddActivity.this, PersonMangementActivity.class);
                                intent.putExtra("userName", userName);
                                intent.putExtra("userPhonNumber", userPhonNumber);
                                PersonAddActivity.this.startActivity(intent); */
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


    }
}