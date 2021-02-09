package com.joorpe.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    MyAdapter myAdapter;
    RecyclerView myList;
    EditText  precio_metido, nombre_metido, cantidad_metido;
    ImageButton boton_agregar;
    TextView total_precio;
    ArrayList<Producto>  productos;
    Integer idMetido;
    String NomMetido;
    Integer CanMetida;
    Double precio;
    Producto productoAgregado;
    gestionar gestionador;
    gestionar_SQL gestionadorsql;
    String tipo_moneda,almacenamiento;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestionador = new gestionar(this);
        gestionadorsql = new gestionar_SQL();
        productos = new ArrayList<Producto>();
        //datos producto
        myList  = (RecyclerView) findViewById(R.id.contenedor_lista);
        nombre_metido = (EditText) findViewById(R.id.producto_nombre);
        precio_metido = (EditText) findViewById(R.id.prducto_precio);
        cantidad_metido = (EditText) findViewById(R.id.producto_cantidad);
        precio_metido = (EditText) findViewById(R.id.prducto_precio);
        precio_metido.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        //total precio
        total_precio = (TextView) findViewById(R.id.total_compra_info);
        //Boton agregar producto
        boton_agregar  = (ImageButton) findViewById(R.id.imageButton);
        boton_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar_producto();
            }
        });
        cantidad_metido.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    agregar_producto();
                    return true;
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        tipo_moneda = sharedPreferences.getString("list_preference_1","");

        almacenamiento = sharedPreferences.getString("list_preference_2", "");
        Log.i("ALMACENAMIENTO", almacenamiento);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                Map<String,?> preferencias = prefs.getAll();
                String s = preferencias.get(key).toString();
                if(s.length()>1){
                    almacenamiento=s;
                    Log.i("ALMACENAMIENTO2"+s, almacenamiento);
                }else{
                    tipo_moneda=s;
                    Log.i("MONEDA2"+s, tipo_moneda);
                }
                int total=0;
                for(int i =0 ; i<productos.size(); i++){

                    total =  total +((int)productos.get(i).precio * productos.get(i).cantidad);

                }


                total_precio.setText(Integer.toString(total) + " " + tipo_moneda);

                cargar();



            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);



        if (almacenamiento.equals("Fichero")) {
                productos = gestionador.getProductos(this);
        }else if(almacenamiento.equals("SQL")){
            try {
                productos = gestionadorsql.getProductos(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        int total1=0;
        for(int i =0 ; i<productos.size(); i++){

            total1 =  total1 +((int)productos.get(i).precio * productos.get(i).cantidad);

        }
        total_precio.setText(Integer.toString(total1) +" " + tipo_moneda);

        myAdapter = new MyAdapter(getApplicationContext(), productos, total_precio, gestionador,almacenamiento,gestionadorsql, new MyAdapter.CallBack() {
            @Override
            public void actualizarTotal(double total) {
                total_precio.setText(String.valueOf(total) + " " + tipo_moneda);
            }
        });

        myList.setLayoutManager(new LinearLayoutManager(this));
        myList.setAdapter(myAdapter);

    }

    public void agregar_producto() {

         NomMetido = nombre_metido.getText().toString();
         CanMetida =  Integer.parseInt(cantidad_metido.getText().toString());
         precio = Double.parseDouble(precio_metido.getText().toString());
         productoAgregado = new Producto((int) (Math.random()*800),NomMetido,CanMetida,precio);
         productos.add(0,productoAgregado);
         MyAdapter nuevo;
         nuevo = new MyAdapter(getApplicationContext(), productos, total_precio,gestionador,almacenamiento,gestionadorsql, new MyAdapter.CallBack() {
            @Override
            public void actualizarTotal(double total) {
                total_precio.setText(String.valueOf(total)+ " " + tipo_moneda);
            }
        });
        Toast.makeText(getApplicationContext(),"hola " + productos.get(0).nombre,Toast.LENGTH_LONG).show();
        myList.setLayoutManager(new LinearLayoutManager(this));
        myList.setAdapter(nuevo);
        int total=0;
        for(int i =0 ; i<productos.size(); i++){

            total =  total +((int)productos.get(i).precio * productos.get(i).cantidad);

        }
        total_precio.setText(Integer.toString(total) + " "  + tipo_moneda);

        if(almacenamiento.equals("Fichero")) {
            gestionador.addProducto(productos, this);
        }

        if ( almacenamiento.equals("SQL")){
            gestionadorsql.addProducto(productos, getApplicationContext());
        }




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, configuracion.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cargar(){
        if (almacenamiento.equals("Fichero")) {
            productos = gestionador.getProductos(this);
        }else if(almacenamiento.equals("SQL")){
            try {
                productos = gestionadorsql.getProductos(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        int total1=0;
        for(int i =0 ; i<productos.size(); i++){

            total1 =  total1 +((int)productos.get(i).precio * productos.get(i).cantidad);

        }
        total_precio.setText(Integer.toString(total1) +" " + tipo_moneda);

        myAdapter = new MyAdapter(getApplicationContext(), productos, total_precio, gestionador,almacenamiento,gestionadorsql, new MyAdapter.CallBack() {
            @Override
            public void actualizarTotal(double total) {
                total_precio.setText(String.valueOf(total) + " " + tipo_moneda);
            }
        });

        myList.setLayoutManager(new LinearLayoutManager(this));
        myList.setAdapter(myAdapter);

    }

class DecimalDigitsInputFilter implements InputFilter {

    private Pattern mPattern;
    DecimalDigitsInputFilter(int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }


   }
}


