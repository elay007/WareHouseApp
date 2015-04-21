package com.example.dgutierrez.warehouse;

/**
 * Created by dgutierrez on 18/04/2015.
 */
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<String,Void,String>{

    private ProgressDialog progressDialog;
    private Context context;
    String nroPed;

    /**Constructor de clase */
    public MyAsyncTask(Context context) {
        this.context = context;
    }
    /**
     * Realiza la tarea en segundo plano
     * @param params[0] Comando GET/POST
     * @param params[1] ID
     * @param params[2] nroped
     * @param params[3] nromod
     * @param params[4] articulo
     * @param params[5] cantdesp
     * */
    @Override
    protected String doInBackground(String... params) {

        nroPed=params[1];
        if( params[1].length()==0 || params[2].length()==0 || params[3].length()==0 || params[4].length()==0)
        {
            return "Todos los campos son obligatorios";
        }
        else
        {
            MyRestFulGP myRestFulGP = new MyRestFulGP();
            try {
                if( params[0].equals("GET"))
                {
                    return myRestFulGP.addEventGet(params[1],params[2],params[3],params[4]);
                }else if( params[0].equals("POST"))
                {
                    return myRestFulGP.addEventPost(params[1],params[2],params[3],params[4]);
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
        final Intent intent = new Intent(context, MapaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Intent.EXTRA_TEXT, nroPed);
        context.startActivity(intent);

    }
}