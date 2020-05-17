package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sean_alberto_futbollab_2.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class VentanaCliente extends AppCompatActivity {

    Button btnDisponibles, btnComprados, btnCerrarSession;

    private CarouselView carouselView;

    private int[] Imagenes={R.drawable.masteren_preparacion_fisica1, R.drawable.grado_medio2, R.drawable.grado_superior3, R.drawable.pasantia_entrenador4, R.drawable.pasantia_jugador5, R.drawable.maste_en_entrenador_de_porteros_de_futbol6, R.drawable.master_en_profesional_del_futbol7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_cliente);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(Imagenes.length);

        ImageListener imageListener = new ImageListener(){
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(Imagenes[position]);
            }
        };
        carouselView.setImageListener(imageListener);

        btnDisponibles = findViewById(R.id.btnCursosDisponibles);
        btnComprados = findViewById(R.id.btnCursosComprados);
        btnCerrarSession = findViewById(R.id.btnCerrarSession);

        btnDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaDisponibles = new Intent(getApplicationContext(), CursosDisponibles.class);
                startActivity(ventanaDisponibles);
            }
        });

        btnComprados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaComprados = new Intent(getApplicationContext(), CursosComprados.class);
                startActivity(ventanaComprados);
            }
        });

        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cerrarSesion = new Intent(getApplicationContext(), Login.class);
                startActivity(cerrarSesion);
            }
        });
    }

}
