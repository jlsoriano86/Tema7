package com.example.tema7;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tema7.Logic.LogicLugar;
import com.example.tema7.Model.Lugar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EdicionActivity extends AppCompatActivity implements LocationListener {
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    private EditText txtNombre;
    private Spinner spCategoria;
    private EditText txtLongitud;
    private EditText txtLatitud;
    private RatingBar rbValoracion;
    private EditText txtComentarios;
    public ImageView imgLocalizacion;
    LocationManager locationManager;
    Location loc;

    ArrayList permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        setTitle(App.accion == 1 ? R.string.accionNuevo : R.string.accionEditar);

        txtNombre = findViewById(R.id.txtNombre);
        spCategoria = findViewById(R.id.spCategoria);
        txtLongitud = findViewById(R.id.txtLongitud);
        txtLatitud = findViewById(R.id.txtLatitud);
        rbValoracion = findViewById(R.id.rbValoracion);
        txtComentarios = findViewById(R.id.txtComentarios);
        imgLocalizacion = findViewById(R.id.imgLocalizacion);
        List<String> list = App.getListCategorias(this);
        final int listsize = list.size();
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return (listsize); // Truncate the list
            }
        };
/*
        List categorias = Arrays.asList("Parque", "Bar", "Museo", "Biblioteca", "Tienda", "Todas");
        spCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias));
        */


        txtNombre.setText(App.lugarActivo.getNombre());
        spCategoria.setSelection(App.lugarActivo.getCategoria());
        txtLongitud.setText(App.lugarActivo.getLongitud().toString());
        txtLatitud.setText(App.lugarActivo.getLatitud().toString());
        rbValoracion.setRating(App.lugarActivo.getValoracion());
        txtComentarios.setText(App.lugarActivo.getComentarios());

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);


        //Dar funcionalidad al botón del gps
        imgLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGPS && !isNetwork) {
                    Log.i(TAG, "Conexión OFF");
                    showSettingsAlert();
                    getLastLocation();
                } else {
                    Log.i(TAG, "Conexión ON");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionsToRequest.size() > 0) {
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                            canGetLocation = false;
                        }
                    }
                    getLocation();
                }
            }

        });

        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(Adapter);
        txtNombre.setText(App.lugarActivo.getNombre());
        spCategoria.setSelection(App.lugarActivo.getCategoria() - 1);
        txtLongitud.setText(App.lugarActivo.getLongitud().toString());
        txtLatitud.setText(App.lugarActivo.getLatitud().toString());
        rbValoracion.setRating(App.lugarActivo.getValoracion());
        txtComentarios.setText(App.lugarActivo.getComentarios());


    }


    private void getLocation() {
        try {
            if (canGetLocation) {
                if (isGPS) { // recibiendo señal desde GPS_PROVIDER
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) { // recibiendo señal desde NETWORK_PROVIDER
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(Location loc) {
        txtLatitud.setText(Double.toString(loc.getLatitude()));
        txtLongitud.setText(Double.toString(loc.getLongitude()));
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("Se requiere permisos para ejecutar la aplicación.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("El GPS no está activado");
        alertDialog.setMessage("¿Quieres activar el GPS?");
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edicion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        clicInsertar();
        return true;
    }


    public void clicInsertar() {
        String nombre = txtNombre.getText().toString();
        Integer categoria = spCategoria.getSelectedItemPosition();
        String longitud = txtLongitud.getText().toString();
        String latitud = txtLatitud.getText().toString();
        Float valoracion = rbValoracion.getRating();
        String comentarios = txtComentarios.getText().toString();
        if (nombre.equals("") || categoria.equals(0) || longitud.equals(0.0f) || latitud.equals(0.0f) || valoracion.equals(0.0f) || comentarios.equals("")) {
            mostrarMensaje("Faltan datos obligatorios.");
        } else {
            App.lugarActivo.setNombre(nombre);
            App.lugarActivo.setCategoria(categoria);
            App.lugarActivo.setLongitud(Float.parseFloat(longitud));
            App.lugarActivo.setLatitud(Float.parseFloat(latitud));
            App.lugarActivo.setValoracion(valoracion);
            App.lugarActivo.setComentarios(comentarios);
            App.accion = App.INSERTAR;
            LogicLugar.insertarLugar(getApplicationContext(), App.lugarActivo);
            mostrarMensaje("Producto " + nombre + " ha sido almacenado.");
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void mostrarMensaje(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
