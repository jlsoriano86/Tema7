package com.example.tema7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tema7.Logic.LogicLugar;

import java.util.Arrays;
import java.util.List;

public class EdicionActivity extends AppCompatActivity {

    private EditText txtNombre;
    private Spinner spCategoria;
    private EditText txtLongitud;
    private EditText txtLatitud;
    private RatingBar rbValoracion;
    private EditText txtComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);
        txtNombre = findViewById(R.id.txtNombre);
        spCategoria = findViewById(R.id.spCategoria);
        txtLongitud = findViewById(R.id.txtLongitud);
        txtLatitud = findViewById(R.id.txtLatitud);
        rbValoracion = findViewById(R.id.rbValoracion);
        txtComentarios = findViewById(R.id.txtComentarios);

        List categorias = Arrays.asList("Parque", "Bar", "Museo", "Biblioteca", "Tienda");
       spCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias));

        txtNombre.setText(App.lugarActivo.getNombre());
        spCategoria.setSelection(categorias.indexOf(App.lugarActivo.getCategoria()));
        txtLongitud.setText(App.lugarActivo.getLongitud().toString());
        txtLatitud.setText(App.lugarActivo.getLatitud().toString());
        rbValoracion.setRating(App.lugarActivo.getValoracion());
        txtComentarios.setText(App.lugarActivo.getComentarios().toString());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edicion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcInsertar: clicInsertar(); break;

        }
        return true;
    }

    public void clicInsertar() {
        App.accion = App.INSERTAR;
        startActivity(new Intent(this, EdicionActivity.class));
        finish();
    }


/*
    public void clicGuardar(View view) {
        String nombre = txtNombre.getText().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        String longitud = txtLongitud.getText().toString();
        String latitud = txtLatitud.getText().toString();
        Float valoracion = rbValoracion.getRating();
        String comentarios = txtComentarios.getText().toString();

        if (nombre.equals("") || categoria.equals("")) {
            mostrarMensaje("Faltan datos obligatorios.");
        } else {
            App.lugarActivo.setNombre(nombre);
            App.lugarActivo.setCategoria(Integer.parseInt(categoria));
            App.lugarActivo.setLongitud(Float.parseFloat(longitud));
            App.lugarActivo.setLatitud(Float.parseFloat(latitud));
            App.lugarActivo.setValoracion(valoracion);
            App.lugarActivo.setComentarios(comentarios);

            switch (App.accion) {
                case App.INSERTAR:
                    LogicLugar.insertarLugar(this, App.lugarActivo);
                    break;

            }
            mostrarMensaje("Lugar " + nombre + " ha sido almacenado.");
            finish();
        }
    }
*/

  /*  private void mostrarMensaje(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    */
}
