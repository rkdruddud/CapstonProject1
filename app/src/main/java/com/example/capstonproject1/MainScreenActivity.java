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
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;


//import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigatorExtrasKt;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonproject1.databinding.ActivityMainScreenBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;
import org.json.JSONObject;


public class MainScreenActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private String puserID;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainScreenBinding binding;
    private DrawerLayout drawer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent ggintent = getIntent();
        String gguserID = ggintent.getStringExtra("userID");
        setPuserID(gguserID);


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

                    case R.id.nav_person_add:

                        Intent gintent = getIntent();
                        String userID = gintent.getStringExtra("userID");


                        Intent intent = new Intent(MainScreenActivity.this, PersonAddActivity.class);
                        intent.putExtra("userID", userID);
                        MainScreenActivity.this.startActivity(intent);

                        // startActivity(new Intent(getApplicationContext(),PersonAddActivity.class));
                        break;
                    case R.id.nav_person_management:
                        Intent pintent = getIntent();
                        String userID2 = pintent.getStringExtra("userID");


                        Intent aintent = new Intent(MainScreenActivity.this, PersonMangementActivity.class);
                        aintent.putExtra("userID", userID2);
                        MainScreenActivity.this.startActivity(aintent);

                        //startActivity(new Intent(getApplicationContext(), PersonMangementActivity.class));
                        break;
                    case R.id.nav_tag_add:
                        Intent tintent = getIntent();
                        String userID3 = tintent.getStringExtra("userID");


                        Intent aaintent = new Intent(MainScreenActivity.this, TagSearchAddActivity.class);
                        aaintent.putExtra("userID", userID3);
                        MainScreenActivity.this.startActivity(aaintent);
                        // startActivity(new Intent(getApplicationContext(), PersonalTagActivity.class));
                        break;

                    case R.id.nav_tag_list:
                        Intent ointent = getIntent();
                        String userID4 = ointent.getStringExtra("userID");


                        Intent iintent = new Intent(MainScreenActivity.this, TagListActivity.class);
                        iintent.putExtra("userID", userID4);
                        MainScreenActivity.this.startActivity(iintent);

                        //startActivity(new Intent(getApplicationContext(), TagListActivity.class));
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

                Intent gintent = getIntent();
                String userID = gintent.getStringExtra("userID");
                String userPassword = gintent.getStringExtra("userPassword");
                Intent intent = new Intent(MainScreenActivity.this, FriendAcceptActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPassword", userPassword);
                MainScreenActivity.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getPuserID(){
        return puserID;
    }
    public void setPuserID(String puserID){
        this.puserID = puserID;
    }

}