package com.example.tema7;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tema7.Logic.LogicLugar;

public class InformacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        TextView txtNombre = findViewById(R.id.txtNombre);
        TextView txtCategoria = findViewById(R.id.txtCategoria);
        TextView txtLongitud = findViewById(R.id.txtLongitud);
        TextView txtLatitud = findViewById(R.id.txtLatitud);
        RatingBar rbValoracion = findViewById(R.id.rbValoracion);
        TextView txtComentarios = findViewById(R.id.txtComentarios);

        txtNombre.setText(App.lugarActivo.getNombre());
        int indexCategoria = App.lugarActivo.getCategoria();
        txtCategoria.setText(App.getListCategorias(this.getApplicationContext()).get(indexCategoria));
        txtLongitud.setText(App.lugarActivo.getLongitud().toString());
        txtLatitud.setText(App.lugarActivo.getLatitud().toString());
        rbValoracion.setRating(App.lugarActivo.getValoracion());
        txtComentarios.setText(App.lugarActivo.getComentarios());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_informacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcEditar: clicEditar(); break;
            case R.id.opcBorrar: clicBorrar(); break;
        }
        return true;
    }

    public void clicEditar() {
        App.accion = App.EDITAR;
        startActivity(new Intent(this, EdicionActivity.class));
        finish();
    }

    public void clicBorrar() {
        new AlertDialog.Builder(this)
                .setMessage("¿ Quieres borrar el lugar " + App.lugarActivo.getNombre() + " ?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LogicLugar.eliminarLugar(getApplicationContext(), App.lugarActivo);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}