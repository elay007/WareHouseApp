package com.example.dgutierrez.warehouse;

/**
 * Created by dmancilla on 14/08/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;


//@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning
public class ArticuloBulto implements Parcelable {
    private String pk_articulo;
    private int nro_mod,cant_bulto,nro_bulto;

//Add Parcelable
    public static final Parcelable.Creator<ArticuloBulto> CREATOR =
        new Parcelable.Creator<ArticuloBulto>()
    {
        @Override
        public ArticuloBulto createFromParcel(Parcel parcel)
        {
            return new ArticuloBulto(parcel);
        }

        @Override
        public ArticuloBulto[] newArray(int size)
        {
            return new ArticuloBulto[size];
        }
    };

    public ArticuloBulto(Parcel parcel)
    {
        //seguir el mismo orden que el usado en el método writeToParcel
        pk_articulo = parcel.readString();
        nro_mod = parcel.readInt();
        nro_bulto = parcel.readInt();
        cant_bulto = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(pk_articulo);
        parcel.writeInt(nro_mod);
        parcel.writeInt(nro_bulto);
        parcel.writeInt(cant_bulto);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
//Fin Add Parcelable
    public ArticuloBulto(){//(String pkArt, int nro_bulto, int nro_mod, int cant_bulto) {
        super();
        /*
        this.pk_articulo = pkArt;
        this.nro_bulto = nro_bulto;
        this.nro_mod = nro_mod;
        this.cant_bulto = cant_bulto;
        */
    }

    public ArticuloBulto(String pkArt, int nro_bulto, int nro_mod, int cant_bulto) {
        super();
        this.pk_articulo = pkArt;
        this.nro_bulto = nro_bulto;
        this.nro_mod = nro_mod;
        this.cant_bulto = cant_bulto;


    }

    public String getArticulo() {
        return pk_articulo;
    }

    public int getNroModulo() {
        return nro_mod;
    }

    public int getNroBulto() {
        return nro_bulto;
    }

    public int getCantBulto() {
        return cant_bulto;
    }

    public void setArticulo(String pk_art) { this.pk_articulo= pk_art; }

    public void setNroBulto(int nro) { this.nro_bulto = nro; }

    public void setNroModulo(int nro) {
        this.nro_mod= nro;
    }

    public void setCantBulto(int cantp) {
        this.cant_bulto= cantp;
    }
}
