package com.example.dgutierrez.warehouse;

/**
 * Created by dmancilla on 14/08/2015.
 */
public class Bulto {
    private String estado;
    private int nro_bulto,  nro_ped, cant_pares, cant_acces;

    public Bulto(int nroPed, int nroBulto, int cantPares, int cantAcces, String estado) {
        this.estado = estado;
        this.nro_bulto = nroBulto;
        this.nro_ped = nroPed;
        this.cant_pares = cantPares;
        this.cant_acces = cantAcces;
    }

    public String getEstado() {
        return estado;
    }

    public int getNroPed() {
        return nro_ped;
    }

    public int getCantPares() {
        return cant_pares;
    }

    public int getCantAcces() {
        return cant_acces;
    }

    public int getNroBulto() {
        return nro_bulto;
    }

    public void setEstado(String est) {
        this.estado= est;
    }

    public void setNroBulto(int nro) {
        this.nro_bulto= nro;
    }

    public void setNroPed(int nro) {
        this.nro_ped= nro;
    }

    public void setCantPares(int nro) {
        this.cant_pares= nro;
    }

    public void seCantAcces(int nro) {
        this.cant_acces= nro;
    }
}
