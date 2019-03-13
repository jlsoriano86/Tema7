package com.example.tema7;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tema7.Logic.LogicLugar;
import com.example.tema7.Model.Lugar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    LatLng Marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        mostrarMarcadores();
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
       mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
        mMap.setOnMarkerClickListener(this);
    }

    private void mostrarMarcadores() {
        List<Lugar> listaLugares = LogicLugar.listaLugares(this);
        if (listaLugares == null) {
        } else {

            for (Lugar l : listaLugares) {
                Marcador = new LatLng(l.getLatitud(), l.getLongitud());
                if (l.getCategoria() == 1){
                    mMap.addMarker(new MarkerOptions().position(Marcador).title(l.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                }else if (l.getCategoria() == 2){
                    mMap.addMarker(new MarkerOptions().position(Marcador).title(l.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                }else if (l.getCategoria() == 3){
                    mMap.addMarker(new MarkerOptions().position(Marcador).title(l.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                }else if (l.getCategoria() == 4){
                    mMap.addMarker(new MarkerOptions().position(Marcador).title(l.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }else if (l.getCategoria() == 5){
                    mMap.addMarker(new MarkerOptions().position(Marcador).title(l.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                }else if (l.getCategoria() == 6){
                    mMap.addMarker(new MarkerOptions().position(Marcador).title(l.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                }

            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }





}
