package com.example.dgutierrez.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by dgutierrez on 16/08/2015.
 */

public class UtilityUpdateWarehouse extends AsyncTask<String,Void,String> {

    private ProgressDialog progressDialog;
    private Context context;
    String nroPed,nroMod;

    /**Constructor de clase */
    public UtilityUpdateWarehouse(Context context) {
        this.context = context;
    }
    /**
     * Realiza la tarea en segundo plano
     * @param params[0] Comando GET/POST
     * @param params[1] ID
     * @param params[2] nroped
     * @param params[3] nromod
     * @param params[4] articulo
     * @param params[5] desp1
     * @param params[6] desp2
     * @param params[7] desp3
     * @param params[8] desp4
     * @param params[9] desp5
     * @param params[10] desp6
     * @param params[11] desp7
     * @param params[12] desp8
     * @param params[13] desp9
     * */
    @Override
    protected String doInBackground(String... params) {

        nroPed=params[1];
        nroMod=params[2];
        if( params[1].length()==0 || params[2].length()==0 || params[3].length()==0 || params[4].length()==0|| params[5].length()==0|| params[6].length()==0|| params[7].length()==0|| params[8].length()==0|| params[9].length()==0|| params[10].length()==0|| params[11].length()==0|| params[12].length()==0)
        {
            return "Todos los campos son obligatorios";
        }
        else
        {
            UtilityApiRest myApiRest = new UtilityApiRest();
            try {
                if( params[0].equals("GET"))
                {
                    return myApiRest.addEventGet(params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11],params[12]);
                }else if( params[0].equals("POST"))
                {
                    return myApiRest.addEventPost(params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11],params[12]);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Antes de comenzar la tarea muestra el progressDialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(
                context, "Por favor espere", "Procesando...");
    }

    /**
     * Cuando se termina de ejecutar, cierra el progressDialog y retorna el resultado a la interfaz
     * **/

     @Override
    protected void onPostExecute(String resul) {
        progressDialog.dismiss();
        final Intent intent = new Intent(context, ArticulosActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Bundle extras = new Bundle();
        extras.putString("NRO_PED",nroPed);
        extras.putString("NRO_MOD",nroMod);
        intent.putExtras(extras);

        context.startActivity(intent);

    }

}