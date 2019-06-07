package cl.gonzalo.agropig;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Principal extends AppCompatActivity{


    ImageButton btn_registrar;
    ImageButton btn_venta;

    int boton=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        View.OnClickListener abrirCamara = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boton(view);
                IntentIntegrator integrator = new IntentIntegrator(Principal.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Escanear código QR");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        };

        btn_registrar = findViewById(R.id.b_registrar);
        btn_registrar.setOnClickListener(abrirCamara);
        btn_venta = findViewById(R.id.b_venta);
        btn_venta.setOnClickListener(abrirCamara);

    }//onCreate

    public void boton(View view){
        switch(view.getId()){
            case R.id.b_registrar:
                boton=1;
                break;
            case R.id.b_venta:
                boton=2;
                break;
        }
    }

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
                            Toast.makeText(Principal.this, "Error, Tamaño de caracteres incorrecto", Toast.LENGTH_LONG).show();
                    } else {
                        if (resultado.substring(0, 2).equals("ch")) {

                            if(boton==1) {
                                Intent intent = new Intent(Principal.this, SecondActivity.class);
                                intent.putExtra("parametro", resultado);
                                startActivity(intent);
                            }
                            if(boton==2) {
                                Intent intent = new Intent(Principal.this, VentActivity.class);
                                intent.putExtra("parametro", resultado);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(Principal.this, "Error, codigo invalido", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salir) {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(Principal.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(Principal.this);
            }
            builder.setTitle("Salir")
                    .setMessage("¿Seguro que desea salir de la aplicación?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
        }
        return super.onOptionsItemSelected(item);
    }

} //class Principal



