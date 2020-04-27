package com.example.sean_alberto_futbollab_2.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgresSQL {

    public static Connection conexionDB(){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("postgres://tfcxapmrfbwkcq:43695daad5506f1514de9ec0ee5e37fed9514ae2507139aee85284584420c18a@ec2-54-217-213-79.eu-west-1.compute.amazonaws.com:5432/d4fv13je3mkhai");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }
    protected static void cerrar_conexion(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
