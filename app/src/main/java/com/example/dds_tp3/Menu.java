package com.example.dds_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void menuPagar(View view){
        Intent menuPagar = new Intent(this, Pagar.class);
        startActivity(menuPagar);

    }

    public void menuMovimientos(View view){
        Intent mov = new Intent(this, Movimientos.class);
        startActivity(mov);

    }
    public void menuDatosPersonales(View view){
        Intent datos = new Intent(this, datos_personales.class);
        startActivity(datos);

    }
    public void eventoSalir(View view){
        Intent ingresar = new Intent(this, login.class);
        startActivity(ingresar);

    }
}
