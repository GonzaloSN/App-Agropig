package cl.gonzalo.agropig;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VentActivity extends AppCompatActivity {
    ListView listView;
    private RequestQueue rqt;
    private String url = "http://172.20.10.5:8000/api/crear/venta";
    private Context ctx;
    private StringRequest strq;
    private String var;
    private int pos;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    TextView cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lista de Animales a Venta");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_registrar);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setImageResource(R.drawable.ic_registrar);
        cantidad = (TextView) findViewById(R.id.tcantidad);

        ctx = VentActivity.this;
        rqt = Volley.newRequestQueue(ctx);

        listView = (ListView)findViewById(R.id.lista);

        final String valor = getIntent().getStringExtra("parametro");

        arrayList.add(valor);

        arrayAdapter = new ArrayAdapter(VentActivity.this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);


        int elementos = arrayAdapter.getCount();
        cantidad.setText(String.valueOf(elementos));
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    builder = new AlertDialog.Builder(VentActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(VentActivity.this);
                }
                builder.setTitle("Eliminar codigo")
                        .setMessage("¿Seguro de eliminar Codigo "+arrayList.get(position).toString()+ " ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(VentActivity.this, "Se eliminó el codigo "+arrayList.get(position).toString(),Toast.LENGTH_SHORT).show();
                                arrayList.remove(position);
                                int elementos = arrayAdapter.getCount();
                                cantidad.setText(String.valueOf(elementos));
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //no borrar
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                //
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(VentActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Escanear código QR");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.size() == 0){
                    Toast.makeText(VentActivity.this, "Debe registrar al menos un Animal",Toast.LENGTH_SHORT).show();
                } else {
                    int elementos = arrayAdapter.getCount();
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(VentActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(VentActivity.this);
                    }
                    builder.setTitle("Registrar")
                            .setMessage("¿Registrar " + elementos + " animales para venta?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int i;
                                    for (i = 0; i < arrayList.size(); i++){
                                        final String cod=arrayList.get(i);
                                        //inicio
                                        strq = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        Log.d("rta_servidor", response);
                                                        Toast.makeText(ctx, "Venta Registrada", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                //Log.d("error_servidor", error.toString());
                                                if(error instanceof ClientError){
                                                    Toast.makeText(VentActivity.this, "Codigo existente", Toast.LENGTH_SHORT).show();
                                                } else if(error instanceof TimeoutError){
                                                    Toast.makeText(VentActivity.this, "Error de conexion con servidor, reintente", Toast.LENGTH_SHORT).show();
                                                } else if(error instanceof ServerError){
                                                    Toast.makeText(VentActivity.this, "Error de conexion con servidor, reintente", Toast.LENGTH_SHORT).show();
                                                } else if(error instanceof NoConnectionError){
                                                    Toast.makeText(VentActivity.this, "Error de conexion con servidor", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }) {

                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> parametros = new HashMap<>();


                                                Log.d("arreglox", cod);
                                                parametros.put("codigo_venta", cod);
                                                return parametros;
                                            }
                                        };
                                        rqt.add(strq);
                                        //fin
                                    }
                                    startActivity(new Intent(VentActivity.this, Principal.class));
                                    finish();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //no salir
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }//else

            }//onClick
        });


    }//onCreate

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)  {
            if(result.getContents() == null) {
                Log.d("Principal", "Cancelled scan");
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_LONG).show();
            } else {
                Log.d("Principal", "Scanned");
                final String resultado = result.getContents();
                //Toast.makeText(this, "Codigo: " + resultado, Toast.LENGTH_LONG).show();

                if (resultado.length() != 10){
                    Toast.makeText(VentActivity.this, "Error, Tamaño de caracteres incorrecto", Toast.LENGTH_LONG).show();
                } else {
                    if (resultado.substring(0, 2).equals("ch")) {
                        if (!arrayList.contains(resultado)){
                            arrayList.add(resultado);
                            int elementos = arrayAdapter.getCount();
                            arrayAdapter.notifyDataSetChanged();
                            cantidad.setText(String.valueOf(elementos));
                        } else {
                            Toast.makeText(VentActivity.this, "Error, codigo repetido", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(VentActivity.this, "Error, codigo invalido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

    }//onActivityResult



    /*@Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        arrayList.addAll(guardarEstado.getStringArrayList("key"));
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        recEstado.putStringArrayList("key",arrayList);
    }*/


    /*protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("myArrayList", arrayList);
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        arrayList = savedInstanceState.getStringArrayList("myArrayList");
    }*/


}



