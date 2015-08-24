package com.example.dgutierrez.warehouse;

/**
 * Created by dgutierrez on 12/08/2015.
 */
public class Talla {

    private String talla, orden;
    private int talla_ped,talla_desp;

    public Talla(String tall, String ord, int talla_p, int talla_d) {
        this.talla= tall;
        this.orden= ord;
        this.talla_ped=talla_p;
        this.talla_desp=talla_d;

    }

    public String getTalla() {
        return talla;
    }

    public String getOrden() {
        return orden;
    }

    public int getTalla_ped() {
        return talla_ped;
    }

    public int getTalla_desp() {
        return talla_desp;
    }

    public void setTalla(String tall) {
        this.talla= tall;
    }

    public void setOrden(String ord) {
        this.orden= ord;
    }

    public void setTalla_ped(int talla_p) {
        this.talla_ped= talla_p;
    }

    public void setTalla_desp(int talla_d) { this.talla_desp= talla_d; }
}
