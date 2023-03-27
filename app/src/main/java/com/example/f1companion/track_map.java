package com.example.f1companion;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.f1companion.databinding.ActivityTrackMapBinding;

import java.io.IOException;
import java.util.List;

public class track_map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackMapBinding binding;
    private LocationSource.OnLocationChangedListener mListener;
    String track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrackMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        track = bundle.getString("Track name");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Address place = null;
        // Add a marker in Sydney and move the camera
        Geocoder geocoder = new Geocoder(this);
        try {
            place = geocoder.getFromLocationName(track, 1).get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        LatLng track = new LatLng(place.getLatitude(), place.getLongitude());
        mMap.addMarker(new MarkerOptions().position(track).title("Circuit location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(track, 12.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(track, 15.0f));
    }
}