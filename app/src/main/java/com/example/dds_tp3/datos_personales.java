package com.example.dds_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class datos_personales extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        final EditText nombre = findViewById(R.id.nombre);
        final EditText apellido = findViewById(R.id.apellido);
        final EditText contrasenia = findViewById(R.id.contrasenia);
        final Button btnActualizar = findViewById(R.id.actualizar);



        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer documento = recibirDato();
                new actualizacionDatos(nombre.getText().toString(), apellido.getText().toString(),contrasenia.getText().toString(), documento, view.getContext()).execute();
            }
        });


    }

    private Integer recibirDato() {
        Bundle documento = getIntent().getExtras();
        Integer id = documento.getInt("Documento");

        return id;
    }

    private class actualizacionDatos extends AsyncTask<String, String, String>{
        private String nombre1;
        private String apellido1;
        private String contrasenia1;
        private Integer id1;
        private Context context1;

        private actualizacionDatos(String nombre, String apellido, String contrasenia, Integer documento, Context context){
            this.nombre1 = nombre;
            this.apellido1 = apellido;
            this.contrasenia1 = contrasenia;
            this.context1 = context;
            this.id1 = documento;

        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject j = new JSONObject();
            String result = null;

            try {
                j.put("apellidos", apellido1);
                j.put("nombres", nombre1);
                j.put("documento", id1);
                j.put("id", id1);
                j.put("password", contrasenia1);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("ERRORJSON", e.toString());
            }


            try {
                result = RESTservice.callREST("http://lsi.no-ip.org:8282/BancoBilardos/rest/cliente/" + this.id1, "PUT", j);
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
                Toast notificacion = Toast.makeText(getApplicationContext(), "Actualizacion exitosa"+result, Toast.LENGTH_SHORT);
                notificacion.show();
            } else{
                Toast notificacion = Toast.makeText(getApplicationContext(), "Fallo la actualizacion", Toast.LENGTH_SHORT);
                notificacion.show();
            }
        }
    }


    public void irAlMenu(View view){
        Intent ingresar = new Intent(datos_personales.this, Menu.class);
        startActivity(ingresar);

    }
}
