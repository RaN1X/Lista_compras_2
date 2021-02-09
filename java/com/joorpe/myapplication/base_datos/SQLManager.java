package com.joorpe.myapplication.base_datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.joorpe.myapplication.Producto;

import java.util.ArrayList;
import java.util.List;

public class SQLManager {
    public static ProductoDBHelper personaDBHelper;

    Context c;

    public SQLManager(Context c) {
        this.c = c;
    }

    public ProductoDBHelper getInstance(){
        if(personaDBHelper == null){
            personaDBHelper=  new ProductoDBHelper(c);
        }
        return personaDBHelper;
    }
    public long insert(Producto p){
        ProductoDBHelper dbHelper = getInstance();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE, p.getNombre());
        values.put(ProductoContract.ProductoInfo.COLUMN_NAME_CANTIDAD, p.getCantidad());
        values.put(ProductoContract.ProductoInfo.COLUMN_NAME_PRECIO, p.getPrecio());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ProductoContract.ProductoInfo.TABLE_NAME, null, values);

        return newRowId;


    }

    public List<Producto> selectAll(){
        ProductoDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Seleccionamos los campos que queremos recupearr
        String[] projection = {
                BaseColumns._ID,
                ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE,
                ProductoContract.ProductoInfo.COLUMN_NAME_CANTIDAD,
                ProductoContract.ProductoInfo.COLUMN_NAME_PRECIO
        };

        // Elegimos el orden, en este caso por apellidos ascendente
        String sortOrder =
                ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE + " ASC";

        //Lanzamos la consulta
        Cursor cursor = db.query(
                ProductoContract.ProductoInfo.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        //Leemos el cursor
        List<Producto> productos = new ArrayList<Producto>();
        while(cursor.moveToNext()) {
            //Buscamos todas por el _ID
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE));
            int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo.COLUMN_NAME_CANTIDAD));
            int preciounitario = cursor.getInt(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo.COLUMN_NAME_PRECIO));
            productos.add(new Producto(itemId,nombre,cantidad,preciounitario));
        }
        cursor.close();

        return productos;
    }

    public List<Producto> selectbyName(String name){
        ProductoDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Seleccionamos los campos que queremos recupearr
        String[] projection = {
                BaseColumns._ID,
                ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE,
                ProductoContract.ProductoInfo.COLUMN_NAME_CANTIDAD,
                ProductoContract.ProductoInfo.COLUMN_NAME_PRECIO
        };

        // Filtramos los resultados donde el nombre = name (parámetro)
        String selection = ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE + " = ?";
        String[] selectionArgs = {name};

        // Elegimos el orden, en este caso por apellidos ascendente
        String sortOrder =
                ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE + " ASC";

        //Lanzamos la consulta
        Cursor cursor = db.query(
                ProductoContract.ProductoInfo.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        //Leemos el cursor
        List<Producto> productos = new ArrayList<Producto>();
        while(cursor.moveToNext()) {
            //Buscamos todas por el _ID
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE));
            int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo.COLUMN_NAME_CANTIDAD));
            int preciounitario = cursor.getInt(cursor.getColumnIndexOrThrow(ProductoContract.ProductoInfo.COLUMN_NAME_PRECIO));
            productos.add(new Producto(itemId,nombre,cantidad,preciounitario));
        }
        cursor.close();

        return productos;
    }

    public int deleteById(long id){

        ProductoDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Definimos el Where de la consulta
        String selection = ProductoContract.ProductoInfo._ID + " = ?";
        // Vinculamos el valor al que corresponda, como la consulta solo tiene un ?, solo habrá un argunmento.
        String[] selectionArgs = { String.valueOf(id) };
        // ejecutamos la orden que nos devuelve las filas eliminadas
        return db.delete(ProductoContract.ProductoInfo.TABLE_NAME, selection, selectionArgs);

    }

    public int updateNameById(long id, String newName){

        ProductoDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Elegimos el nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(ProductoContract.ProductoInfo.COLUMN_NAME_NOMBRE, newName);

        // Qué fila actualizamos. La que coincida con el ID
        String selection = ProductoContract.ProductoInfo._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        return db.update(
                ProductoContract.ProductoInfo.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        //Devuelve la cantidad de filas afectadas, debería ser 0 o 1 si existe ya que filtramos por el ID.

    }

    public void cerrar(){

        ProductoDBHelper dbHelper = getInstance();
        dbHelper.close();

    }
}
