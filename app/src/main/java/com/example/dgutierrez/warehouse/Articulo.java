package com.example.dgutierrez.warehouse;

/**
 * Created by dgutierrez on 12/04/2015.
 */
public class Articulo {
    private String pk_articulo;
    private int nro_mod,stock,cant_ped,cant_desp;

    public Articulo(String pk_art, int nro_mod, int stk, int cantp, int cantd) {
        this.pk_articulo= pk_art;
        this.nro_mod= nro_mod;
        this.stock=stk;
        this.cant_ped=cantp;
        this.cant_desp=cantd;
    }

    public String getPk_articulo() {
        return pk_articulo;
    }

    public int getNro_mod() {
        return nro_mod;
    }

    public int getStock() {
        return stock;
    }

    public int getCant_ped() {
        return cant_ped;
    }

    public int getCant_desp() {
        return cant_desp;
    }

    public void setPk_articulo(String pk_art) {
        this.pk_articulo= pk_articulo;
    }

    public void setNro_mod(int nro) {
        this.nro_mod= nro;
    }

    public void setStock(int stk) {
        this.stock= stk;
    }

    public void setCant_ped(int cantp) {
        this.cant_ped= cantp;
    }

    public void setCant_desp(int cantd) {
        this.cant_desp= cantd;
    }

}

