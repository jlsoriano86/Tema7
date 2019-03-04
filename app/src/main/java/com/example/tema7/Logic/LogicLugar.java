package com.example.tema7.Logic;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tema7.DataBaseManager.DB_SQLite;
import com.example.tema7.DataBaseManager.Esquema;
import com.example.tema7.Model.Lugar;

import java.util.ArrayList;
import java.util.List;

public class LogicLugar {
    public static void insertarLugar(Context context, Lugar l) {
        ContentValues content = new ContentValues();
        content.put(Esquema.Lugar.COLUMN_NAME_NOMBRE, l.getNombre());
        content.put(Esquema.Lugar.COLUMN_NAME_CATEGORIA, l.getCategoria());
        content.put(Esquema.Lugar.COLUMN_NAME_LONGITUD, l.getLongitud());
        content.put(Esquema.Lugar.COLUMN_NAME_LATITUD, l.getLatitud());
        content.put(Esquema.Lugar.COLUMN_NAME_VALORACION, l.getValoracion());
        content.put(Esquema.Lugar.COLUMN_NAME_COMENTARIOS, l.getComentarios());
        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_WRITE);
        conn.insert(Esquema.Lugar.TABLE_NAME, null, content);
        DB_SQLite.desconectar(conn);
    }

    public static void eliminarLugar(Context context, Lugar l) {
        String sqlWhere = Esquema.Lugar.COLUMN_NAME_ID + " = " + l.getId();
        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_WRITE);
        conn.delete(Esquema.Lugar.TABLE_NAME, sqlWhere, null);
        DB_SQLite.desconectar(conn);
    }

    public static void editarLugar(Context context, Lugar l) {
        ContentValues content = new ContentValues();
        content.put(Esquema.Lugar.COLUMN_NAME_NOMBRE, l.getNombre());
        content.put(Esquema.Lugar.COLUMN_NAME_CATEGORIA, l.getCategoria());
        content.put(Esquema.Lugar.COLUMN_NAME_LONGITUD, l.getLongitud());
        content.put(Esquema.Lugar.COLUMN_NAME_LATITUD, l.getLatitud());
        content.put(Esquema.Lugar.COLUMN_NAME_VALORACION, l.getValoracion());
        content.put(Esquema.Lugar.COLUMN_NAME_COMENTARIOS, l.getComentarios());
        String sqlWhere = Esquema.Lugar.COLUMN_NAME_ID + " = " + l.getId();
        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_WRITE);
        conn.update(Esquema.Lugar.TABLE_NAME, content, sqlWhere, null);
        DB_SQLite.desconectar(conn);
    }

    public static List listaLugares(Context context) {
        List lug = new ArrayList<>();
        String[] sqlFields = {Esquema.Lugar.COLUMN_NAME_ID, Esquema.Lugar.COLUMN_NAME_NOMBRE, Esquema.Lugar.COLUMN_NAME_CATEGORIA, Esquema.Lugar.COLUMN_NAME_LONGITUD, Esquema.Lugar.COLUMN_NAME_LATITUD, Esquema.Lugar.COLUMN_NAME_VALORACION, Esquema.Lugar.COLUMN_NAME_COMENTARIOS};
        String sqlWhere = "";
        String sqlOrderBy = Esquema.Lugar.COLUMN_NAME_NOMBRE + " ASC";

        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_READ);
        Cursor cursor = conn.query(Esquema.Lugar.TABLE_NAME, sqlFields, sqlWhere, null, null, null, sqlOrderBy);
        if (cursor.getCount() == 0) {
            lug = null;
        } else {
            cursor.moveToFirst();
            do {
                Long dataId = cursor.getLong(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_NOMBRE));
                Integer dataCategoria = cursor.getInt(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_CATEGORIA));
                Float dataLongitud = cursor.getFloat(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_LONGITUD));
                Float dataLatitud = cursor.getFloat(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_LATITUD));
                Float dataValoracion = cursor.getFloat(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_VALORACION));
                String dataComentarios = cursor.getString(cursor.getColumnIndex(Esquema.Lugar.COLUMN_NAME_COMENTARIOS));
                lug.add(new Lugar(dataId, dataNombre, dataCategoria, dataLongitud, dataLatitud, dataValoracion, dataComentarios));
            } while (cursor.moveToNext());
        }
        cursor.close();
        DB_SQLite.desconectar(conn);
        return lug;
    }
}
