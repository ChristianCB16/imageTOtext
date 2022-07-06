package com.example.textdemo.Model;

public class translateData {

    private String Texto;
    private String Traduccion;
    //private String email;

    public translateData(){

    }
    public translateData(String Texto, String Traduccion) {
        this.Texto = Texto;
        this.Traduccion = Traduccion;
        //this.email = email;
    }

    public String getTexto() {
        return Texto;
    }

    public String getTraduccion() {
        return Traduccion;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }

    public void setTraduccion(String traduccion) {
        Traduccion = traduccion;
    }
}
