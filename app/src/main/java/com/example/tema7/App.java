package com.example.tema7;
import android.content.Context;

import com.example.tema7.Model.Lugar;

import java.util.ArrayList;
import java.util.List;

public class App {
    public final static int INSERTAR = 1;
    public final static int EDITAR = 2;
    public final static int INFORMACION = 3;

    public static int accion;

    public static Lugar lugarActivo;

    public static List<String> getListCategorias(Context context) {
        List<String> list = new ArrayList<String>();
        list.add(context.getResources().getString(R.string.CategoriaParque));
        list.add(context.getResources().getString(R.string.CategoriaBar));
        list.add(context.getResources().getString(R.string.CategoriaMuseo));
        list.add(context.getResources().getString(R.string.CategoriaBiblioteca));
        list.add(context.getResources().getString(R.string.CategoriaTienda));
        list.add(context.getResources().getString(R.string.CategoriaTodas));
        return list;
    }
}
