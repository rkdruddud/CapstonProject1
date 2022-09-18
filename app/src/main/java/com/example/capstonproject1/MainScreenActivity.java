package com.example.capstonproject1;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigatorExtrasKt;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonproject1.databinding.ActivityMainScreenBinding;

import org.json.JSONException;
import org.json.JSONObject;


public class MainScreenActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainScreenBinding binding;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        MenuItem menuItem = findViewById(R.id.itemMenu);

        setSupportActionBar(binding.appBarMainScreen.toolbar);



        binding.appBarMainScreen.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_tag_add)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_screen);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(androidx.fragment.R.id.fragment_container_view_tag,
                                new HomeFragment()).commit();
                        break;
                    case R.id.nav_person_add:

                        Intent gintent = getIntent();
                        String userID = gintent.getStringExtra("userID");


                        Intent intent = new Intent(MainScreenActivity.this, PersonAddActivity.class);
                        intent.putExtra("userID", userID);
                        MainScreenActivity.this.startActivity(intent);

                        // startActivity(new Intent(getApplicationContext(),PersonAddActivity.class));
                        break;
                    case R.id.nav_person_management:
                        startActivity(new Intent(getApplicationContext(), PersonMangementActivity.class));
                        break;
                    case R.id.nav_tag_add:
                        startActivity(new Intent(getApplicationContext(), RegisterTagActivity.class));
                        break;
                    case R.id.nav_share_tag:
                        startActivity(new Intent(getApplicationContext(), ShareActivity.class));
                        break;

                    case R.id.nav_tag_list:
                        startActivity(new Intent(getApplicationContext(), TagListActivity.class));
                        break;

                    case R.id.nav_logout:
                        //로그아웃 하기
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_screen);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemMenu:
                Toast.makeText(getApplicationContext(),"이동ㅂ.",Toast.LENGTH_SHORT).show();
                Intent sintent = getIntent();
                String userID = sintent.getStringExtra("userID");
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            Intent gintent = getIntent();
                            String userID = gintent.getStringExtra("userID");
                            String userPassword = gintent.getStringExtra("userPassword");
                            //boolean success = jsonArray.getBoolean(0);
                            if(success){
                                // int length = jsonArray.length();
                                //int length = jsonObject.length();
                                //for(int i =0; i<= length; i++){
                                //String friendAcceptID = jsonArray.getString(i);
                                String friendAcceptID = jsonObject.getString("userID");
                                Log.d("fffffffffffff",friendAcceptID);
                                // }
                                // String friendAcceptID = jsonArray.getString(count);


                                Intent intent = new Intent(MainScreenActivity.this, FriendAcceptActivity.class);
                                intent.putExtra("friendID", friendAcceptID);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                MainScreenActivity.this.startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"친구 신청에 에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };


                FriendAcceptRequest friendAcceptRequest = new FriendAcceptRequest(userID ,responseListener); // 친구 요청을 보낸 userid 검색후 그 사람의 정보를 리스트로 표현
                RequestQueue queue = Volley.newRequestQueue(MainScreenActivity.this);
                queue.add(friendAcceptRequest);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}