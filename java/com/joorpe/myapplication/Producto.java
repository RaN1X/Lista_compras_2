package com.joorpe.myapplication;

import java.io.Serializable;

public class Producto implements Serializable {

    String nombre;
    double precio;
    int cantidad;
    int id=0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto(int id, String nombre, int cantidad, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto() {
        this.nombre = "";
        this.precio = 0.0;
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String toString() {
        return id+":"+nombre+":"+cantidad+":"+precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
