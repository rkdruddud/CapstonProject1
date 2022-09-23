package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalTagActivity extends AppCompatActivity {

    private String latitude;
    private String longitude;
    EditText tagid = (EditText) findViewById(R.id.editTextSearchTag1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tag);

        Button registerbtn = findViewById(R.id.registertagbtn);
        Button searchTagbtn = findViewById(R.id.searchTagbutton123);
        EditText tagname = (EditText) findViewById(R.id.editTextTextPersonName4);

        Intent gintent = getIntent();
        String userID = gintent.getStringExtra("userID");
        String tagName = tagname.getText().toString();
        String tagID = tagid.getText().toString();

        searchTagbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent sintent =  new Intent(PersonalTagActivity.this, TagSearchAddActivity.class);
            PersonalTagActivity.this.startActivity(sintent);
            finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");


                            if(success){

                                Response.Listener<String> responseListener = new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response){
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");


                                            if(success){
                                                String latitude1 = jsonObject.getString("latitude");
                                                String longitude1 = jsonObject.getString("longitude");





                                            }else{

                                                return;
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                GetTagLocationRequest getTagLocationRequest = new GetTagLocationRequest( tagID, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(PersonalTagActivity.this);
                                queue.add(getTagLocationRequest);


                            }else{
                                Toast.makeText(getApplicationContext(),"친구 거절 실패",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                String getlatitude = getLatitude();
                String getlongitude = getLongitude();
                AddTagRequest addTagRequest = new AddTagRequest( userID, tagName, tagID, getlatitude, getlongitude, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PersonalTagActivity.this);
                queue.add(addTagRequest);


                Intent rintent = new Intent(PersonalTagActivity.this, MainScreenActivity.class);
                PersonalTagActivity.this.startActivity(rintent);
                finish();
            }
        });
    }
    public String getLatitude(){
        return latitude;
    }
    public void setLatitude(String latitude){
        this.latitude = latitude;
    }



    public String getLongitude(){
        return longitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
}