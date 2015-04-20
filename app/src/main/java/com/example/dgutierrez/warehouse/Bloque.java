package com.example.dgutierrez.warehouse;

/**
 * Created by dgutierrez on 31/03/2015.
 */
public class Bloque
{
    private String orden, seleccionado;
    private int id;

    public Bloque(String ord, String seleccionado, int id) {
        this.orden= ord;
        this.seleccionado= seleccionado;
        this.id=id;

    }

    public String toString() {
        return orden;
    }

    public String getSeleccionado() {
        return seleccionado;
    }

    public int getId() {
        return id;
    }

    public void setSeleccionado(String sel) {
        this.seleccionado= sel;
    }

    public void setOrden(String ord) {
        this.orden= ord;
    }


}
