package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건1", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건2", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건3", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건4", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건5", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건6", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건7", "개인용"));
        adapter.addItem(new TagListItem(R.drawable.ic_baseline_contactless_24,"물건8", "개인용"));






        plustag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TagListActivity.this,RegisterTagActivity.class);
                TagListActivity.this.startActivity(intent);
            }
        });

    }

}