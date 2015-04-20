package com.example.dgutierrez.warehouse;

/**
 * Created by dgutierrez on 11/04/2015.
 */
public class Pedido {

    private String tienda, estado;
    private int nro_ped;

    public Pedido(String tien, String est, int nro) {
        this.tienda= tien;
        this.estado= est;
        this.nro_ped=nro;

    }

    public String getTienda() {
        return tienda;
    }

    public String getEstado() {
        return estado;
    }

    public int getNro_ped() {
        return nro_ped;
    }

    public void setTienda(String tien) {
        this.tienda= tien;
    }

    public void setEstado(String est) {
        this.estado= est;
    }

    public void setNro_ped(int nro) {
        this.nro_ped= nro;
    }


}
