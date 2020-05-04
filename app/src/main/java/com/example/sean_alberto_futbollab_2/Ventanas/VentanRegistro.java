package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sean_alberto_futbollab_2.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VentanRegistro extends AppCompatActivity {

    Button btnRegistrar, btnVolver;
    EditText nombreRegistro, apellidoRegistro, mailRegistro, passwordRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventan_registro);

        btnRegistrar = findViewById(R.id.button_registrarse);
        btnVolver = findViewById(R.id.boton_volver_atras);

        nombreRegistro = findViewById(R.id.nombre_registro);
        apellidoRegistro = findViewById(R.id.apellidos_registro);
        mailRegistro = findViewById(R.id.mail_registro);
        passwordRegistro = findViewById(R.id.password_registro);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(volverLogin);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDatosClientes();
            }
        });
    }

    public void sendDatosClientes() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/registrar");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("nombre", nombreRegistro.getText().toString());
                    jsonParam.put("apellidos", apellidoRegistro.getText().toString());
                    jsonParam.put("mail", mailRegistro.getText().toString());
                    jsonParam.put("password", passwordRegistro.getText().toString());

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());

                    Intent intentLogin = new Intent(getApplicationContext(), Login.class);
                    startActivity(intentLogin);

                    Toast.makeText(VentanRegistro.this, "Cliente registrado", Toast.LENGTH_SHORT).show();

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(VentanRegistro.this, "El cliente no se has podido registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        thread.start();
    }
}
