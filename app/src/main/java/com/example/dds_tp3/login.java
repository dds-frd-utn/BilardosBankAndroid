package com.example.dds_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;


public  class login extends AppCompatActivity {
    public static final String EXTRA_DOCCLIENTE = "com.example.dds_tp.EXTRA_IDCLIENTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText documento = findViewById(R.id.textDocumento);
        final EditText password = findViewById(R.id.textPassword);
        final Button btnIngresar = findViewById(R.id.btnIngresar);

        documento.setTransformationMethod(null);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NuevoIngreso(documento.getText().toString(), view.getContext()).execute();
            }
        });
    }

    private class NuevoIngreso extends AsyncTask<String, String, String> {

        private String doc;
        private Context context;


        private NuevoIngreso(String doc, Context context) {
            this.doc = doc;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                result = RESTservice.makeGetRequest("http://lsi.no-ip.org:8282/BancoBilardos/rest/cliente/" + this.doc);
            } catch (Exception e) {
                Log.d("INFO", e.toString());
            }
            if (result != null)
                Log.d("INFO", result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("INFO", result);
            if (result != null) {
                if (result.length() > 0) {
                    try {
                        JSONObject cliente = new JSONObject(result);
                        Integer documento = cliente.getInt("id");

                        Intent intent = new Intent(login.this, Menu.class);
                        intent.putExtra("EXTRA_DOCCLIENTE", documento);

                        Toast notificacion = Toast.makeText(getApplicationContext(), "Bienvenido!  " + this.doc, Toast.LENGTH_SHORT);
                        notificacion.show();

                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    EditText documento = findViewById(R.id.textDocumento);
                    EditText password = findViewById(R.id.textPassword);
                    documento.setText("");
                    password.setText("");
                }
            }
        }
    }
}

