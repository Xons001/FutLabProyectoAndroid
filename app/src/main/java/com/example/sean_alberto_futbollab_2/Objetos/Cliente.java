package com.example.sean_alberto_futbollab_2.Objetos;

public class Cliente {

    private int cliente_id;
    private String nombre;
    private String apellidos;
    private String mail;
    private String password;

    public Cliente(int cliente_id, String nombre, String apellidos, String mail) {
        this.cliente_id = cliente_id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

}
