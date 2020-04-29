package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sean_alberto_futbollab_2.Conexion.ConexionPostgresSQL;
import com.example.sean_alberto_futbollab_2.Objetos.Cliente;
import com.example.sean_alberto_futbollab_2.R;

import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    ConexionPostgresSQL con = new ConexionPostgresSQL();
    static Cliente clienteLogin;

    Button btnLogin, btnRegistrar;
    EditText txtCorreo, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin = findViewById(R.id.button_login);
        btnRegistrar = findViewById(R.id.button_registrarse);
        txtCorreo = findViewById(R.id.email_field);
        txtPassword = findViewById(R.id.password_field);

        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = txtCorreo.getText().toString();
                String pass = txtPassword.getText().toString();
                IniciarSesion(mail,pass);
            }
        });*/
    }

    /*public void IniciarSesion(String mail, String password){
        ConexionPostgresSQL con = null;
        boolean encontroUser = false;
        try {
            String sql = "SELECT * FROM cliente";
            Statement stmt = con.conexionDB().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                if (rs.getString("mail").equals(mail) && rs.getString("password").equals(password)) {
                    if (rs.getString("mail").equals("admin")){
                        Intent ventanaAdmin = new Intent(this, VentanaAdmin.class);
                        startActivity(ventanaAdmin);
                        encontroUser = true;

                        //Guardamos los datos del cliente en un objeto global para poder acceder a los datos de este en cualquier ventana
                        clienteLogin.setCliente_id(rs.getInt("cliente_id"));
                        clienteLogin.setNombre(rs.getString("nombre"));
                        clienteLogin.setApellidos(rs.getString("apellidos"));
                        clienteLogin.setMail(rs.getString("mail"));

                        Toast.makeText(getApplicationContext(), "Entraste con admin", Toast.LENGTH_SHORT);
                        break;
                    } else {
                        Intent ventanaCliente = new Intent(this, VentanaCliente.class);
                        startActivity(ventanaCliente);
                        encontroUser = true;

                        //Guardamos los datos del cliente en un objeto global para poder acceder a los datos de este en cualquier ventana
                        clienteLogin.setCliente_id(rs.getInt("cliente_id"));
                        clienteLogin.setNombre(rs.getString("nombre"));
                        clienteLogin.setApellidos(rs.getString("apellidos"));
                        clienteLogin.setMail(rs.getString("mail"));

                        Toast.makeText(getApplicationContext(), "Bienvenido" + clienteLogin.getNombre(), Toast.LENGTH_SHORT);
                        break;
                    }
                }
            }
            if (encontroUser == false){
                Toast.makeText(getApplicationContext(), "Correo o password incorrecto", Toast.LENGTH_SHORT);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
