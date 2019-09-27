package com.example.dds_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Movimientos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
    }
    public void eventoMenu(View view){
        Intent ingresar = new Intent(this, Menu.class);
        startActivity(ingresar);

    }
}
