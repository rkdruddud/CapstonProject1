package com.example.capstonproject1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements OnMapReadyCallback {
    /*
        private HomeViewModel mViewModel;
        private GoogleMap mMap;
        private Marker currentMarker = null;


        MapView sView = null;



        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.home_fragment , container, false);

            //mapview 부름
            sView = view.findViewById(R.id.mapView);
            sView.onCreate(savedInstanceState);
            sView.getMapAsync(this);


            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            // TODO: Use the ViewModel
        }

        @Override
        public void onStart() {
            super.onStart();
            sView.onStart();
        }

        @Override
        public void onStop () {
            super.onStop();
            sView.onStop();

        }

        @Override
        public void onSaveInstanceState (@Nullable Bundle outState){
            super.onSaveInstanceState(outState);
            sView.onSaveInstanceState(outState);
        }

        @Override
        public void onResume() {
            super.onResume();
            sView.onResume();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            sView.onLowMemory();
        }

        //맵뷰 설정
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //마커찍기(위도,경도)
            double latitude = 35.869253;
            double longitude = 127.129026;
            LatLng tagPosition = new LatLng(latitude, longitude);

            //마커 옵션
            MarkerOptions marker = new MarkerOptions();
            marker.position(tagPosition); //마커 위치
            marker.title("솔내청소년수련관");

            //맵에 마커표시, 인포윈도우 보여줌
            googleMap.addMarker(marker).showInfoWindow();

            //인포윈도우 클릭
            //googleMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) this);

            //맵뷰 카메라위치, 줌 설정
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tagPosition, 20));
        }
    }


    */
    private FragmentActivity mContext;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private GoogleMap mMap;
    private MapView mapView = null;
    private Marker currentMarker = null;

    private FusedLocationProviderClient mFusedLocationProviderClient;// Deprecated된 FusedLocationApi를 대체
    private LocationRequest locationRequest;
    private Location mCurrentLocatiion;

    private final LatLng mDefaultLocation = new LatLng(37.56, 126.97);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000 * 60 * 1;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 1000 * 30;
    // 30초 단위로 화면 갱신
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    public HomeFragment() {
    }

    @Override
    public void onAttach(Activity activity) { // Fragment 가 Activity에 attach 될 때 호출된다.
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }// 초기화 해야 하는 리소스들을 여기서 초기화 해준다.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            mCurrentLocatiion = savedInstanceState.getParcelable(KEY_LOCATION);
            CameraPosition mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        View layout = inflater.inflate(R.layout.home_fragment, container, false);
        mapView = (MapView) layout.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(this);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // Fragement에서의 OnCreateView를 마치고, Activity에서 onCreate()가 호출되고 나서 호출되는 메소드이다.
        // Activity와 Fragment의 뷰가 모두 생성된 상태로, View를 변경하는 작업이 가능한 단계다.
        super.onActivityCreated(savedInstanceState);//액티비티가 처음 생성될 때 실행되는 함수
        MapsInitializer.initialize(mContext);
        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setDefaultLocation(); // GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요함.
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        //setDefaultLocation();




        for (int idx = 0; idx < 2; idx++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(37.20850 + idx*0.001, 127.07990))
                    .title("마커" + idx*0.001); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions);
        }
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.20850, 127.07990),15));





    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mCurrentLocatiion = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setDefaultLocation() {
        if (currentMarker != null) currentMarker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mDefaultLocation);
        markerOptions.title("위치정보 가져올 수 없음");
        markerOptions.snippet("위치 퍼미션과 GPS 활성 여부 확인하세요");
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15);
        mMap.moveCamera(cameraUpdate);
    }

    String getCurrentAddress(LatLng latlng) { // 위치 정보와 지역으로부터 주소 문자열을 구한다.
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault()); // 지오코더를 이용하여 주소 리스트를 구한다.
        try {
            addressList = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
        } catch (IOException e) {
            Toast.makeText(mContext, "위치로부터 주소를 인식할 수 없습니다. 네트워크가 연결되어 있는지 확인해 주세요.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return "주소 인식 불가";
        }
        if (addressList.size() < 1) { // 주소 리스트가 비어있는지 비어 있으면
            return "해당 위치에 주소 없음";
        }// 주소를 담는 문자열을 생성하고 리턴
        Address address = addressList.get(0);
        StringBuilder addressStringBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressStringBuilder.append(address.getAddressLine(i));
            if (i < address.getMaxAddressLineIndex())
                addressStringBuilder.append("\n");
        }
        return addressStringBuilder.toString();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);
                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "위도:" + String.valueOf(location.getLatitude()) + " 경도:" + String.valueOf(location.getLongitude());
                Log.d(TAG, "Time :" + CurrentTime() + " onLocationResult : " + markerSnippet);
                setCurrentLocation(location, markerTitle, markerSnippet);
                mCurrentLocatiion = location;
            }
        }
    };

    private String CurrentTime() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
        return time.format(today);
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) currentMarker.remove();
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        /*MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);*/
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
    }


    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @Nullable String permissions[], @Nullable int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if (mFusedLocationProviderClient != null) {
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
