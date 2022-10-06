package com.example.capstonproject1;

import static app.akexorcist.bluetotohspp.library.BluetoothState.REQUEST_ENABLE_BT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;


public class TagActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    DrawerLayout drawerLayout2;
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView TagShareFriendList;
    ShareFriendListAdapter adapter;

    private BluetoothSPP bt;

    private String ptagID;
    private String puserID;
    private Float flatitude;
    private Float flongitude;

    private GoogleMap mMap;
    private Marker currentMarker = null;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초
    private static final int PRIORITY_HIGH_ACCURACY = 100;

    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;


    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    Location mCurrentLocatiion;
    LatLng currentPosition;


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;


    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.

    //거리계산
    LatLng previousPosition = null;
    Marker addedMarker = null;
    int tracking = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);




        Intent gintent = getIntent();
        String tagid = gintent.getStringExtra("tagID");
        String tagName = gintent.getStringExtra("tagName");
        String userid = gintent.getStringExtra("userID");

        TextView distancetxt = findViewById(R.id.distanceShowtxt);
        TextView sharUsertext = findViewById(R.id.shareUserName);
        TextView titleTagName = findViewById(R.id.sharedtitleTagName12);
        titleTagName.setText(tagName);

        LinearLayout container = findViewById(R.id.itemLayout12);
        drawerLayout2 = findViewById(R.id.drawerLayoutTag);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout2, R.string.tag_menu_Open, R.string.tag_menu_Close);

        Button sharebtn = findViewById(R.id.Sharebtn3);


        TagShareFriendList = findViewById(R.id.shareFriendNameRrView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        TagShareFriendList.setLayoutManager(layoutManager);

        adapter = new ShareFriendListAdapter(getApplicationContext());
        TagShareFriendList.setAdapter(adapter);

        setPuserID(userid);
        setPtagID(tagid);

//----- bluetooth--------------------------------------------------------------------

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("블루투스에 대한 액세스가 필요합니다");
                builder.setMessage("어플리케이션이 블루투스를 감지 할 수 있도록 위치 정보 액세스 권한을 부여하십시오.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2 );
                    }
                });
                builder.show();
            }
        }


        bt = new BluetoothSPP(this); //Initializing
        try {

            if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
                Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (SecurityException e){
            Toast.makeText(getApplicationContext(), "앱 사용 권한을 허용해주세요", Toast.LENGTH_SHORT).show();

        }

        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        ActivityCompat.requestPermissions(TagActivity.this, permission_list,  1);

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else {
            // 블루투스 활성화 하도록
          Intent aaintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(aaintent, 1);
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신


            public void onDataReceived(byte[] data, String message) {
                String[] array = message.split(",");
                if(array[0].equals("$GPGGA")) {
                    String lat1 = array[2].substring(0,2);
                    String lat2 = array[2].substring(2);
                    String lon1 = array[4].substring(0,3);
                    String lon2 = array[4].substring(3);
                    double LatF = Double.parseDouble(lat1) + Double.parseDouble(lat2)/60;
                    float LongF = Float.parseFloat(lon1) + Float.parseFloat(lon2)/60;

                    setFlatitude((float)LatF);
                    setFlongitude(LongF);

                    String latitude = Double.toString(LatF);
                    String longitude = Float.toString(LongF);
                    String tagID = bt.getConnectedDeviceAddress();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(LatF,LongF));


                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            String tagClickID = marker.getId();
                            LatLng tagClickPos = marker.getPosition();


                            if ( (marker != null) && tracking == 1 ) {
                                double radius = 500; // 500m distance.

                                double distance = SphericalUtil.computeDistanceBetween(currentPosition, marker.getPosition());

                                if ((distance < radius) && (!previousPosition.equals(currentPosition))) {

                                    distancetxt.setText(distance + "m");
                                }
                            }

                            distancetxt.setText("");
                            return false;
                        }
                    });


                    // 마커 찍는 함수 넣기

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            String tagClickID = marker.getId();
                            LatLng tagClickPos = marker.getPosition();

                            Response.Listener<String> responseListener = new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");

                                        if(success){

                                        }else{
                                            Toast.makeText(getApplicationContext(),"태그 등록 실패",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            };

                            String userId = getPuserID();

                            String gtagID = bt.getConnectedDeviceAddress();

                            AddTagRequest addTagRequest = new AddTagRequest( userId, tagName, gtagID, latitude, longitude, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(TagActivity.this);
                            queue.add(addTagRequest);


                            if ( (marker != null) && tracking == 1 ) {
                                double radius = 500; // 500m distance.

                                double distance = SphericalUtil.computeDistanceBetween(currentPosition, marker.getPosition());

                                if ((distance < radius) && (!previousPosition.equals(currentPosition))) {

                                    distancetxt.setText(distance + "m");
                                }
                            }

                            distancetxt.setText("");
                            return false;
                        }
                    });
                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success){


                                }else{
                                    Toast.makeText(getApplicationContext(),"태그 등록 실패",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };

                    String ggtagID = bt.getConnectedDeviceAddress();
                    AddTagLocationRequest addTagLocationRequest = new AddTagLocationRequest(ggtagID,latitude ,longitude,  responseListener);
                    RequestQueue queue4 = Volley.newRequestQueue(TagActivity.this);
                    queue4.add(addTagLocationRequest);
                    //AddTagLocation Request 클래스 사용
                    //ㅏ데이터베이스에 넘겨주는 코드
                }


            }
        });

      /*  Response.Listener<String> responseListener2 = new Response.Listener<String>(){
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
        SearchTagLocationRequest searchTagLocationRequest = new SearchTagLocationRequest(getPtagID(), responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(TagActivity.this);
        queue2.add(searchTagLocationRequest);*/


//----- google map----------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //setContentView(R.layout.activity_tag);

        locationRequest = new LocationRequest()
                .setPriority(PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_Taginfo_fragment);
        mapFragment.getMapAsync(this);


        Response.Listener<String> responseListener8 = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        String lat = jsonObject.getString("latitude");
                        String lon = jsonObject.getString("longitude");
                        Float flatitude = Float.parseFloat(lat);
                        Float flongitude = Float.parseFloat(lon);




                    }else{

                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        Intent sintent = getIntent();
        String tagID2 = sintent.getStringExtra("tagID");
        String tagID22 = bt.getConnectedDeviceAddress();
        SearchTagLocationRequest searchTagLocationRequest2 = new SearchTagLocationRequest(tagID22, responseListener8);
        RequestQueue queue6 = Volley.newRequestQueue(TagActivity.this);
        queue6.add(searchTagLocationRequest2);

//google map---------------------------------------------------------------------------------------

        sharUsertext.setText(userid);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject12 = new JSONObject(response);
                    boolean success = jsonObject12.getBoolean("success");

                    if(success){

                        int length = jsonObject12.length();
                        for(int i =0; i< length-1; i++) {

                            String friendID = jsonObject12.getString(String.valueOf(i));

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject1 = new JSONObject(response);
                                        boolean success = jsonObject1.getBoolean("success");

                                        if(success){
                                            String friendName = jsonObject1.getString("userName");
                                            Log.d("friendName : ", friendName);
                                            adapter.addItem(new ShareFriendListItem(R.drawable.ic_baseline_person_24, friendName));
                                            adapter.notifyItemRangeRemoved(0,1);


                                            adapter.setOnItemClickListener(new ShareFriendListAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View v, int position) {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this);
                                                    builder.setMessage("위치 공유를 멈추겠습니까?");
                                                    builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    });

                                                    builder.setNegativeButton("차단", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {


                                                            Response.Listener<String> responseListener2 = new Response.Listener<String>(){
                                                                @Override
                                                                public void onResponse(String response){
                                                                    try {
                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                        boolean success = jsonObject.getBoolean("success");
                                                                        if(success){


                                                                            Toast.makeText(getApplicationContext(),"차단 완료",Toast.LENGTH_SHORT).show();
                                                                            adapter.remove(position);
                                                                        }else{
                                                                            Toast.makeText(getApplicationContext(),"차단 실패",Toast.LENGTH_SHORT).show();
                                                                            return;
                                                                        }
                                                                    }catch (JSONException e){
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            };
                                                            String tagID = getPtagID();
                                                           // Log.d("tagID", tagID);
                                                            StopShareFriendRequest stopShareFriendRequest = new StopShareFriendRequest(friendID, tagID, responseListener2);
                                                            RequestQueue queue2 = Volley.newRequestQueue(TagActivity.this);
                                                            queue2.add(stopShareFriendRequest);

                                                        }
                                                    });
                                                    builder.show();
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

                            SearchPhNameRequest searchPhNameRequest = new SearchPhNameRequest(friendID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(TagActivity.this);
                            queue.add(searchPhNameRequest);

                        }

                    }
                    else {


                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        String userID = getPuserID();
        String tagID = getPtagID();

        SearchShareFriendIDRequest searchShareFriendIDRequest = new SearchShareFriendIDRequest(userID, tagID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TagActivity.this);
        queue.add(searchShareFriendIDRequest);




        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagActivity.this, ShareActivity.class);
               intent.putExtra("tagID",tagID);

                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });






    }
//------------------------------------------------------------------------------------------ google map
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");

        mMap = googleMap;
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                .position(new LatLng(36.7345128, 127.0791511))
                .title(ptagID); // 타이틀.

        // 2. 마커 생성 (마커를 나타냄)
        mMap.addMarker(makerOptions);



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.7345128, 127.0791511), 15));
        LatLng latLng = new LatLng(36.7345128, 127.0791511);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.7345128, 127.0791511),15));


       // double distance = SphericalUtil.computeDistanceBetween(currentPosition, latLng);

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신


            public void onDataReceived(byte[] data, String message) {
                String[] array = message.split(",");
                if(array[0].equals("$GPGGA")) {
                    String lat1 = array[2].substring(0, 2);
                    String lat2 = array[2].substring(2);
                    String lon1 = array[4].substring(0, 3);
                    String lon2 = array[4].substring(3);
                    double LatF = Double.parseDouble(lat1) + Double.parseDouble(lat2) / 60;
                    float LongF = Float.parseFloat(lon1) + Float.parseFloat(lon2) / 60;

                    String latitude = Double.toString(LatF);
                    String longitude = Float.toString(LongF);
                    String tagID = bt.getConnectedDeviceAddress();
                    Log.d(TAG, "tagLatitude : " + latitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(LatF, LongF));

                    MarkerOptions makerOptions = new MarkerOptions();
                    makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                            .position(new LatLng(LatF, LongF))
                            .title(tagID);


                    // 2. 마커 생성 (마커를 나타냄)
                    mMap.addMarker(makerOptions);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatF, LongF), 15));
                    LatLng latLng = new LatLng(LatF, LongF);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatF, LongF),15));
                    double distance = SphericalUtil.computeDistanceBetween(currentPosition, latLng);
                    TextView showdistance = findViewById(R.id.distanceShowtxt);
                    String distancestr = Double.toString(distance);
                    showdistance.setText(distancestr);

                    //AddTagLocation Request 클래스 사용
                    //ㅏ데이터베이스에 넘겨주는 코드
                }


            }
        });
        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        //setDefaultLocation();
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String strlatitude = jsonObject.getString("latitude");
                        String strlongitude = jsonObject.getString("longitude");

                      //  if(!strlongitude.equals(null) && !strlatitude.equals(null)) {
                            float latitude = Float.parseFloat(strlatitude);
                            float longitude = Float.parseFloat(strlongitude);



                            MarkerOptions makerOptions = new MarkerOptions();
                            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                                    .position(new LatLng(36.734504, 127.0791551))
                                    .title(ptagID); // 타이틀.

                            // 2. 마커 생성 (마커를 나타냄)
                            mMap.addMarker(makerOptions);

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.734504, 127.0791551), 15));
                            LatLng latLng = new LatLng(36.734504, 127.0791551);

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.734504, 127.0791551),15));
                            double distance = SphericalUtil.computeDistanceBetween(currentPosition, latLng);
                            TextView showdistance = findViewById(R.id.distanceShowtxt);
                            String distancestr = Double.toString(distance);
                            showdistance.setText(distancestr);
                      //  }
                    } else {

                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String tagID = getPtagID();
        GetTagLocationRequest getTagLocationRequest = new GetTagLocationRequest(tagID, responseListener2);
        RequestQueue queue3 = Volley.newRequestQueue(TagActivity.this);
        queue3.add(getTagLocationRequest);
            // 1. 마커 옵션 설정 (만드는 과정)





        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            startLocationUpdates(); // 3. 위치 업데이트 시작


        }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions( TagActivity.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }


        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 현재 오동작을 해서 주석처리

        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                Log.d( TAG, "onMapClick :");
            }
        });
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),15));
                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                        + " 경도:" + String.valueOf(location.getLongitude());

                Log.d(TAG, "onLocationResult : " + markerSnippet);


                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet);

                mCurrentLocatiion = location;
            }


        }

    };



    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        if (checkPermission()) {

            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap!=null)
                mMap.setMyLocationEnabled(true);

        }


    }
    public void setup() {
        ImageView volumbtn = (ImageView) findViewById(R.id.volumbtn);
        volumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.send("o",true);
            }
        });
    }

    @Override
    protected void onStop() {

        super.onStop();

        if (mFusedLocationClient != null) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }




    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
/*
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        currentMarker = mMap.addMarker(markerOptions);
*/
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);

    }

    // 여기를 태그 위치 표시로 바꾸기
    public void setDefaultLocation() {

        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(36.7345128, 127.0791511);
        String markerTitle = "서울";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";

        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
            return true;
        }

        return false;

    }



    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                } else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }


    private class PRIORITY_HIGH_ACCURACY {
    }
    public String getPuserID(){
        return puserID;
    }
    public void setPuserID(String puserID){
        this.puserID = puserID;
    }

    public String getPtagID(){
        return ptagID;
    }
    public void setPtagID(String ptagID){
        this.ptagID = ptagID;
    }

    public Float getFlatitude(){
        return flatitude;
    }
    public void setFlatitude(Float flatitude){
        this.flatitude = flatitude;
    }

    public Float getFlongitude(){
        return flongitude;
    }
    public void setFlongitude(Float flongitude){
        this.flongitude = flongitude;
    }
}