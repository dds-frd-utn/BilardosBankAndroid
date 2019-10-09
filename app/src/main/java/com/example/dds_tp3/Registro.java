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

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final EditText nombre = findViewById(R.id.nombre);
        final EditText apellido = findViewById(R.id.apellido);
        final EditText documento = findViewById(R.id.documento);
        final EditText fechaNac = findViewById(R.id.fechaNac);
        final EditText contrasenia = findViewById(R.id.password);
        final Button btnRegistrarse = findViewById(R.id.btnRegistrarse);


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new registrarse(nombre.getText().toString(), apellido.getText().toString(),contrasenia.getText().toString(), documento.getText().toString(), view.getContext()).execute();
            }
        });
    }

        private class registrarse extends AsyncTask<String, String, String>{
            private String nombre1;
            private String apellido1;
            private String contrasenia1;
            private Integer id1;
            private Context context1;

            private registrarse(String nombre, String apellido, String contrasenia, String documento, Context context){
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
                    j.put("fechaNacimiento","1996-01-24T00:00:00Z[UTC]");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.d("ERRORJSON", e.toString());
                }


                try {
                    result = RESTservice.callREST("http://lsi.no-ip.org:8282/BancoBilardos/registro" , "PUT", j);
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
                    Toast notificacion = Toast.makeText(getApplicationContext(), "Registro Exitoso "+result, Toast.LENGTH_SHORT);
                    notificacion.show();
                } else{
                    Toast notificacion = Toast.makeText(getApplicationContext(), "Fallo el Registro", Toast.LENGTH_SHORT);
                    notificacion.show();
                }
            }
        }

        public void eventoSalir(View view){
        Intent salir = new Intent(this, login.class);
        startActivity(salir);

        }
}
