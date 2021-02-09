package com.joorpe.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class gestionar implements  Persistencia {
    Context con;
    gestionar(Context c){
        this.con=c;
    }


    @Override
    public void addProducto(ArrayList<Producto> p, Context c) {
        File f = c.getFilesDir();
        try {
            FileOutputStream fos;
            ObjectOutputStream oos;
            fos = new FileOutputStream(new File(f,"productos.txt"));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Producto> getProductos(Context c) {
        File f = c.getFilesDir();
        ArrayList<Producto> arrayprods = new  ArrayList<Producto>();
        ObjectInputStream ois=null;
        try {
            FileInputStream fis = new FileInputStream(new File(f,"productos.txt"));
            ois = new ObjectInputStream(fis);
            arrayprods= (ArrayList<Producto>) ois.readObject(); //el array de objetos lo relleno con el fichero
            ois.close();

        } catch (IOException ex) {

        }
        catch (ClassNotFoundException ex) {

        }finally{
            return arrayprods;
        }
    }

    @Override
    public Producto getProductoById(int id) {
        return null;
    }

    @Override
    public void updateProducto(ArrayList<Producto> p, Context c) {
        File f = c.getFilesDir();
        try {
            FileOutputStream fos;
            ObjectOutputStream oos;
            fos = new FileOutputStream(new File(f,"productos.txt"));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteProducto(ArrayList<Producto> p, Context c) {
        File f = c.getFilesDir();
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(new File(f,"productos.txt"));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
