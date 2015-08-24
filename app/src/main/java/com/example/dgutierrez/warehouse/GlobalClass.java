package com.example.dgutierrez.warehouse;

import android.app.Application;

/**
 * Created by dgutierrez on 21/08/2015.
 */
public class GlobalClass extends Application {
    private String nroPed;
    private String nroMod;
    private String pkArticulo;
   private String nroBulto = "N";



    public String getNroPed() {

        return nroPed;
    }

    public void setNroPed(String aNroPed) {

        nroPed = aNroPed;

    }

    public String getNroMod() {

        return nroMod;
    }

    public void setNroMod(String aNroMod) {

        nroMod = aNroMod;
    }

    public String getPkArticulo() {

        return pkArticulo;
    }

    public void setPkArticulo(String aPkArticulo) {

        pkArticulo = aPkArticulo;
    }

    // Getter Numero Bulto
    public String getNroBulto(){
        return nroBulto;
    }

    // Setter Numero Pedido
    public void setNroBulto(String nroBulto){
        this.nroBulto = nroBulto;
    }

}
