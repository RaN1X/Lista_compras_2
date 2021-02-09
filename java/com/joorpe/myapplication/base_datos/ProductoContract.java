package com.joorpe.myapplication.base_datos;

import android.provider.BaseColumns;

class ProductoContract {

    private  ProductoContract(){}


    public static class ProductoInfo implements BaseColumns {
        public static final String TABLE_NAME = "productos";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_CANTIDAD = "cantidad";
        public static final String COLUMN_NAME_PRECIO = "precio_unitario";

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductoInfo.TABLE_NAME + " (" +
                    ProductoInfo._ID + " INTEGER PRIMARY KEY," +
                    ProductoInfo.COLUMN_NAME_NOMBRE + " TEXT," +
                    ProductoInfo.COLUMN_NAME_CANTIDAD + " NUMBER," +
                    ProductoInfo.COLUMN_NAME_PRECIO + " REAL)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductoInfo.TABLE_NAME;
}