package com.example.dds_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class Pagar extends AppCompatActivity {

    String opcion_activa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);

        final Spinner opciones = findViewById(R.id.idSpinnerTipoPagos);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipoPago, R.layout.spinner_personalizado);

        opciones.setAdapter(adapter);
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String temporal = opciones.getSelectedItem().toString();
                switch (temporal) {
                    case "Transferencia Inmediata":
                        opcion_activa = "1";
                        break;
                    case "Transferencia Interbancaria":
                        opcion_activa = "2";
                        break;
                    case "Compraventa":
                        opcion_activa = "3";
                        break;
                    default:
                        opcion_activa = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                opcion_activa = null;
            }
        });


        final EditText nroCuenta = findViewById(R.id.nroCuenta);
        final EditText monto = findViewById(R.id.monto);
        final Button btnRealiza = findViewById(R.id.btnRealizar);

        btnRealiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NuevaTransferencia(nroCuenta.getText().toString(), monto.getText().toString(), opcion_activa, view.getContext()).execute();
            }
        });
    }
        private class NuevaTransferencia extends AsyncTask<String, String, String> {

            private String nroCuenta;
            private String monto;
            private String opcion2;
            private Context context;


            private NuevaTransferencia(String cuenta, String monto, String opcion, Context context) {
                this.nroCuenta = cuenta;
                this.monto = monto;
                this.opcion2 = opcion;
                this.context = context;
            }

            @Override
            protected String doInBackground(String... strings) {
                JSONObject j = new JSONObject();
                try {
                    j.put("cuentaDestino", nroCuenta);
                    j.put("fechaInicio", new Date());
                    j.put("fechaFin", new Date());
                    j.put("estado", "1");
                    j.put("monto", monto);
                    j.put("tipo", opcion2);
                    System.out.println(j);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.d("ERRORJSON", e.toString());
                }

                String result = null;
                try {  //
                    result = RESTservice.callREST("http://lsi.no-ip.org:8282/BancoBilardos/rest/transferencias", "POST", j);
                } catch (Exception e) {
                    Log.d("INFO", e.toString());
                }
                if (result != null)
                    Log.d("INFO", result.toString());
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    if (result.length() > 0) {
                        Toast notificacion = Toast.makeText(getApplicationContext(), "Transferencia Exitosa"+ result , Toast.LENGTH_SHORT);
                            notificacion.show();
                    } else {
                        Toast notificacion = Toast.makeText(getApplicationContext(), "Transferencia fallo. result:" + result, Toast.LENGTH_SHORT);
                        notificacion.show();
                        Log.d("INFO", result);
                    }
                } else {
                    Toast notificacion = Toast.makeText(getApplicationContext(), "Transferencia fallo: result null" , Toast.LENGTH_SHORT);
                    notificacion.show();
                    Log.d("INFO", result);
                }
            }
        }


        public void eventoMenu (View view){
            Intent ingresar = new Intent(this, Menu.class);
            startActivity(ingresar);

        }


    }

