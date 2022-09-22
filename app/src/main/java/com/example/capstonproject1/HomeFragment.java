package com.example.capstonproject1;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;


public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

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

