package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
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

public class VentanaRegistro extends AppCompatActivity {

    Button btnRegistrar, btnVolver;
    EditText nombreRegistro, apellidoRegistro, mailRegistro, passwordRegistro;

    String urlStr;
    String nombre, apellidos, mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_registro);

        btnRegistrar = findViewById(R.id.boton_confirmar_registro);
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
                nombre = nombreRegistro.getText().toString();
                apellidos = apellidoRegistro.getText().toString();
                mail =  mailRegistro.getText().toString();
                pass = passwordRegistro.getText().toString();

                new insertarRegistro().execute();
                //sendDatosClientes();
            }
        });
    }

    private class insertarRegistro extends AsyncTask<Void, Void, Void> {
        boolean insertado = false;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/registrar");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("nombre", nombre);
                jsonParam.put("apellidos", apellidos);
                jsonParam.put("mail", mail);
                jsonParam.put("password", pass);

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS prueba", String.valueOf(conn.getResponseCode()));
                Log.i("MSG supremo", conn.getResponseMessage());

                insertado = true;

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(VentanaRegistro.this, "El cliente no se has podido registrar", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(insertado == true){
                Toast.makeText(VentanaRegistro.this, "Cliente registrado", Toast.LENGTH_SHORT).show();
                Intent intentLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(intentLogin);
            } else {
                Toast.makeText(VentanaRegistro.this, "El cliente no se has podido registrar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
