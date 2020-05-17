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

import com.example.sean_alberto_futbollab_2.Conexion.ConexionPostgresSQL;
import com.example.sean_alberto_futbollab_2.Objetos.Cliente;
import com.example.sean_alberto_futbollab_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    ConexionPostgresSQL con = new ConexionPostgresSQL();
    static Cliente clienteLogin;
    private String TAG = Login.class.getSimpleName();

    Button btnLogin, btnRegistrar;
    EditText txtCorreo, txtPassword;

    static String URL_login = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin = findViewById(R.id.button_login);
        btnRegistrar = findViewById(R.id.button_registrarse);
        txtCorreo = findViewById(R.id.email_field);
        txtPassword = findViewById(R.id.password_field);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = txtCorreo.getText().toString();
                String pass = txtPassword.getText().toString();
                URL_login = getURL_login(mail, pass);
                new IniciarSesion().execute();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent ventanaRegistro = new Intent(getApplicationContext(), VentanaRegistro.class);
                startActivity(ventanaRegistro);
            }
        });

    }

    public static String getURL_login(String mail, String password) {
        String url = "https://futlab-credito-sintesis.herokuapp.com/login/" + mail + "/" + password;
        return url;
    }

    private class IniciarSesion extends AsyncTask<Void, Void, Void> {
        boolean loginExiste = false;
        @Override
        protected Void doInBackground(Void... Voids) {
            ConexionPostgresSQL conn = new ConexionPostgresSQL();
            // Making a request to url and getting response
            String jsonStr = conn.makeServiceCall(URL_login);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray clienteJS = jsonObj.getJSONArray("clientes");
                    clienteLogin = new Cliente();
                    // looping through All Courses
                    for (int i = 0; i < clienteJS.length(); i++) {
                        JSONObject c = clienteJS.getJSONObject(i);
                        int cliente_id = Integer.parseInt(c.getString("cliente_id"));
                        String nombre_text = c.getString("nombre");
                        String apellido_text = c.getString("apellidos");
                        String mail_text = c.getString("mail");

                        clienteLogin.setCliente_id(cliente_id);
                        clienteLogin.setNombre(nombre_text);
                        clienteLogin.setApellidos(apellido_text);
                        clienteLogin.setMail(mail_text);
                        loginExiste = true;
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error 1: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (loginExiste == true) {
                Intent intentCliente = new Intent(getApplicationContext(), VentanaCliente.class);
                startActivity(intentCliente);
                Toast.makeText(Login.this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "Correo o password invalidos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}