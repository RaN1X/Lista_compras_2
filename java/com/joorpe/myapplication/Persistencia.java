package com.joorpe.myapplication;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

public interface Persistencia {

    void addProducto(ArrayList<Producto> p, Context c);
    ArrayList<Producto> getProductos( Context c) throws ClassNotFoundException;
    Producto getProductoById(int id);
    void updateProducto(ArrayList<Producto> p, Context c) throws IOException, ClassNotFoundException;
    void deleteProducto(ArrayList<Producto> p, Context c) throws IOException, ClassNotFoundException;

}
