package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class PersonMangementActivity extends AppCompatActivity {


    RecyclerView AddPersonManageList;
    PersonManageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_mangement);

        AddPersonManageList = findViewById(R.id.friendARecycleView13);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AddPersonManageList.setLayoutManager(layoutManager);

        adapter = new PersonManageListAdapter(getApplicationContext());
        AddPersonManageList.setAdapter(adapter);


        /* 아이템 버튼과 텍스트들 아이디 ㅣ
        Button deletebtn = findViewById(R.id.deletePersonbtn);
        TextView friendName = findViewById(R.id.textView25);
        TextView friendPhonNumber = findViewById(R.id.textView23);
        TextView friendAcceptance = findViewById(R.id.textView26);
        */

        Intent gintent = getIntent();

        String userName = gintent.getStringExtra("userName");
        String userPhonNumber = gintent.getStringExtra("userPhonNumber");
        int num = adapter.getItemCount();
/*
        for(int i = 0; i<num; i++) {
            adapter.addItem(new PersonManageItem(R.drawable.ic_baseline_person_24, userName, userPhonNumber));
        }
*/





    }
}