package com.joorpe.myapplication;

import android.content.Context;
import android.util.Log;

import com.joorpe.myapplication.base_datos.SQLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class gestionar_SQL implements Persistencia {

    ArrayList<Producto> productos = new ArrayList<Producto>();

    @Override
    public void addProducto(ArrayList<Producto> productos, Context context) {

        SQLManager sqm = new SQLManager(context);
        for (int i=0;i<productos.size();i++){
            if(i == productos.size()-1){
                sqm.insert(productos.get(i));
            }

        }
    }

    @Override
    public ArrayList<Producto> getProductos(Context context) throws ClassNotFoundException {
        SQLManager sqm = new SQLManager(context);
        List<Producto> productoslista = sqm.selectAll();
        productos = (ArrayList<Producto>) productoslista;
        for (int i = 0; i < productos.size();i++){

        }
        return productos;
    }

    @Override
    public Producto getProductoById(int id) {
        return null;
    }



    @Override
    public void updateProducto(ArrayList<Producto> productos, Context context) throws IOException, ClassNotFoundException {
        SQLManager sqm = new SQLManager(context);
        ArrayList<Producto> productos2 = new ArrayList<Producto>();
        productos2=getProductos(context);
        for (int i = 0; i < productos2.size();i++){
            sqm.deleteById(productos2.get(i).id);
        }

        for (int i=0; i<productos.size();i++){
            sqm.insert(productos.get(i));
        }

    }

    @Override
    public void deleteProducto(ArrayList<Producto> productos, Context context) throws IOException, ClassNotFoundException {
        SQLManager sqm = new SQLManager(context);
        ArrayList<Producto> productos2 = new ArrayList<Producto>();
        productos2=getProductos(context);
        for (int i = 0; i < productos2.size();i++){
            sqm.deleteById(productos2.get(i).id);
        }

        for (int i=0; i<productos.size();i++){
            sqm.insert(productos.get(i));
        }
    }
}