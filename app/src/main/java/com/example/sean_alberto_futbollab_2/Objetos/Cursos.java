package com.example.sean_alberto_futbollab_2.Objetos;

public class Cursos {

    private String curso_id;
    private String nombre;
    private String plazas;
    private String grado_id;
    private String master_id;

    public Cursos(){

    }

    public Cursos(String curso_id, String nombre, String plazas, String grado_id, String master_id) {
        this.curso_id = curso_id;
        this.nombre = nombre;
        this.plazas = plazas;
        this.grado_id = grado_id;
        this.master_id = master_id;
    }

    public String getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(String curso_id) {
        this.curso_id = curso_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlazas() {
        return plazas;
    }

    public void setPlazas(String plazas) {
        this.plazas = plazas;
    }

    public String getGrado_id() {
        return grado_id;
    }

    public void setGrado_id(String grado_id) {
        this.grado_id = grado_id;
    }

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }
}
