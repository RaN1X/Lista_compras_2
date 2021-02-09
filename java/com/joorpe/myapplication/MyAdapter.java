package com.joorpe.myapplication;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Producto> productos;
    TextView tvTotal;
    CallBack myCallBack;
    gestionar gesti;
    gestionar_SQL gestisql;
    String almacenamiento;

    public MyAdapter(Context context, ArrayList<Producto> productos, TextView tvTotal, gestionar gest,String almacenamiento,gestionar_SQL gestisql, CallBack myCallBack) {
        this.gesti=gest;
        this.context = context;
        this.productos = productos;
        this.tvTotal = tvTotal;
        this.myCallBack = myCallBack;
        this.almacenamiento = almacenamiento;
        this.gestisql=gestisql;
    }

    public void setData(Producto pro){

        productos.add(0, pro);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder holder, final int i) {

        holder.nombre.setText(productos.get(i).getNombre());
        holder.precio.setText(String.valueOf(productos.get(i).getPrecio()));
        holder.cantidad.setText(String.valueOf(productos.get(i).getCantidad()));
        holder.precio_por_cantidad.setText(String.valueOf(productos.get(i).getCantidad()*productos.get(i).getPrecio()));
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(productos.get(i).getCantidad()<=1){
                    productos.remove(i);
                    notifyDataSetChanged();
                    Double total=0.0;
                    for(int i = 0; i<productos.size();i++){
                        total =  total +((int)productos.get(i).precio * productos.get(i).cantidad);
                    }
                    myCallBack.actualizarTotal(total);
                    if (almacenamiento.equals("Fichero")) {
                        gesti.deleteProducto(productos, context);
                    }else if(almacenamiento.equals("SQL")) {
                        try {
                            gestisql.deleteProducto(productos,context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    productos.get(i).setCantidad(productos.get(i).getCantidad()-1);
                    holder.cantidad.setText(String.valueOf(productos.get(i).getCantidad()));
                    holder.precio_por_cantidad.setText(String.valueOf(productos.get(i).getCantidad()*productos.get(i).getPrecio()));
                    notifyDataSetChanged();
                    Double total=0.0;
                    for(int i = 0; i<productos.size();i++){
                        total =  total +((int)productos.get(i).precio * productos.get(i).cantidad);
                    }
                    myCallBack.actualizarTotal(total);
                    if (almacenamiento.equals("Fichero")) {
                        gesti.updateProducto(productos, context);
                    }else if(almacenamiento.equals("SQL")) {
                        try {
                            gestisql.updateProducto(productos,context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return this.productos.size();
    }

    public interface CallBack {
        void actualizarTotal(double total);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nombre, precio, cantidad, precio_por_cantidad;
        public ImageButton ibDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ibDelete = (ImageButton) itemView.findViewById(R.id.boton_borrar);
            nombre = (TextView) itemView.findViewById(R.id.nombre_producto);
            precio = (TextView) itemView.findViewById(R.id.precio_unidad);
            cantidad = (TextView) itemView.findViewById(R.id.cantidad_producto);
            precio_por_cantidad = (TextView) itemView.findViewById(R.id.precio_total);
        }
    }

}
