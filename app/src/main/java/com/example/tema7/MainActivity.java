package com.example.tema7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tema7.Logic.LogicLugar;
import com.example.tema7.Model.Lugar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    private static List<Lugar> lstLug;
    public ImageView imgMapa;
    Spinner categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgMapa = findViewById(R.id.imgMapa);
        listView = findViewById(R.id.card_listView);
        listView.addHeaderView(new View(this)); // añade espacio arriba de la primera card
        listView.addFooterView(new View(this)); // añade espacio debajo de la última card
        categorias = findViewById(R.id.categorias);

        List<String> list = new ArrayList<String>();
        list.add(getResources().getString(R.string.CategoriaElige));
        list.add(getResources().getString(R.string.CategoriaParque));
        list.add(getResources().getString(R.string.CategoriaBar));
        list.add(getResources().getString(R.string.CategoriaMuseo));
        list.add(getResources().getString(R.string.CategoriaBiblioteca));
        list.add(getResources().getString(R.string.CategoriaTienda));
        list.add(getResources().getString(R.string.CategoriaTodas));


        final int listsize = list.size();
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return (listsize); // Truncate the list
            }
        };
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(Adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        App.lugarActivo = lstLug.get(position - 1);
                        App.accion = App.INFORMACION;
                        startActivity(new Intent(getApplicationContext(), InformacionActivity.class));
                    }
                }
        );
        imgMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (categorias.getSelectedItem().toString()==getResources().getString(R.string.CategoriaTodas)){
                    mostrarTodasCategorias();
                }else{
                    mostrarCategoria();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);

        lstLug = LogicLugar.listaLugares(this);
        if (lstLug == null) {
            Toast.makeText(this, "La base de datos está vacía.", Toast.LENGTH_LONG).show();
        } else {
            for (Lugar l : lstLug) {
                listadoDeCards.add(l);
            }
            listView.setAdapter(listadoDeCards);
        }

    }

    public void clicNuevo(View view) {
        App.lugarActivo = new Lugar();
        App.accion = App.INSERTAR;
        startActivity(new Intent(this, EdicionActivity.class));
    }

    public void mostrarCategoria() {
        CardAdapter listadoCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);
        lstLug = LogicLugar.listaLugares2(this, categorias);
        if (lstLug == null) {
            listView.setAdapter(null);
        } else {
            for (Lugar l : lstLug) {
                listadoCards.add(l);
            }
            listView.setAdapter(listadoCards);
        }
    }

    public void mostrarTodasCategorias() {
        CardAdapter listadoCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);
        lstLug = LogicLugar.listaLugares(this);
        if (lstLug == null) {
            listView.setAdapter(null);
        } else {
            for (Lugar l : lstLug) {
                listadoCards.add(l);
            }
            listView.setAdapter(listadoCards);
        }
    }
}
