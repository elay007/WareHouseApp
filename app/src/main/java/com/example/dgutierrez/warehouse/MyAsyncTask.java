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
<<<<<<< HEAD
import android.widget.TextView;
=======
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> Version-4

public class MyAsyncTask extends AsyncTask<String,Void,String>{

    private ProgressDialog progressDialog;
    private Context context;
<<<<<<< HEAD
    String nroPed;
=======
    String nroPed,tipo,nroBulto;
>>>>>>> Version-4

    /**Constructor de clase */
    public MyAsyncTask(Context context) {
        this.context = context;
    }
    /**
     * Realiza la tarea en segundo plano
<<<<<<< HEAD
     * @param params[0] Comando GET/POST
     * @param params[1] ID
     * @param params[2] nroped
     * @param params[3] nromod
     * @param params[4] articulo
     * @param params[5] cantdesp
=======
     * @param_params[0] Comando GET/POST
     * @param_params[1] ID
     * @param_params[2] nroped
     * @param_params[3] nromod
     * @param_params[4] articulo
     * @param_params[5] cantdesp
>>>>>>> Version-4
     * */
    @Override
    protected String doInBackground(String... params) {

<<<<<<< HEAD
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
=======
        tipo= params[1];
        if(tipo == "ARTICULO") {
            nroPed = params[2];
            if( params[1].length()==0 || params[2].length()==0 || params[3].length()==0 || params[4].length()==0 || params[5].length()==0)
            {
                return "Todos los campos son obligatorios";
            }
        }
        else if(tipo == "BULTO")
        {
            nroPed = params[2];
            if( params[1].length()==0 || params[2].length()==0)
            {
                return "Todos los campos son obligatorios";
            }

        }
        else if(tipo == "DELETE_BULTO")
        {
            String aux[] =  params[2].trim().split("\\|");
            nroBulto = aux[0];
            nroPed = aux[1];
            if( params[1].length()==0 || params[2].length()==0)
            {
                return "Todos los campos son obligatorios";
            }

        }
        else if(tipo == "ARTICULO_BULTO")
        {
            String aux[] =  params[2].trim().split("\\|");
            nroBulto = aux[0];
            nroPed = aux[1];
            if( params[1].length()==0 || params[2].length()==0 || params[3].length()==0 || params[4].length()==0 || params[5].length()==0 || params[6].length()==0)
            {
                return "Todos los campos son obligatorios";
            }

        }
        else if(tipo == "CONFIRMAR_PEDIDO")
        {
            nroPed = params[2];
            if( params[1].length()==0 || params[2].length()==0)
            {
                return "Todos los campos son obligatorios";
            }

        }

        MyRestFulGP myRestFulGP = new MyRestFulGP();
        try {
            if( params[0].equals("GET"))
            {
                return myRestFulGP.addEventGet(params[1],params[2],params[3],params[4],params[5]);
            }else if( params[0].equals("POST"))
            {
                Log.v("SAVE", "Manda por post");
                String res;
                res = myRestFulGP.addEventPost(params[1],params[2],params[3],params[4],params[5],params[6]);
                Log.v("SAVE", "Resultado Post "+res);
                return res;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
>>>>>>> Version-4
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
<<<<<<< HEAD
        final Intent intent = new Intent(context, MapaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Intent.EXTRA_TEXT, nroPed);
        context.startActivity(intent);

=======
        if(tipo == "ARTICULO") {
            final Intent intent = new Intent(context, MapaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Intent.EXTRA_TEXT, nroPed);
            context.startActivity(intent);
        }
        Log.v("CLAVE","RESULTADO FINAL WHEN ??? "+resul);
        if(tipo == "BULTO")
        {
            final Intent intent = new Intent(context, ArticulosBultoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            nroBulto = resul;
            intent.putExtra(Intent.EXTRA_TEXT, nroBulto+"|"+nroPed);
            context.startActivity(intent);
        }
        if(tipo == "ARTICULO_BULTO")
        {
            final Intent intent = new Intent(context, BultosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            nroBulto = resul;
            intent.putExtra(Intent.EXTRA_TEXT, nroPed);
            context.startActivity(intent);
        }
        if(tipo == "DELETE_BULTO")
        {
            final Intent intent = new Intent(context, BultosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Intent.EXTRA_TEXT, nroPed);
            context.startActivity(intent);
        }
        if(tipo == "CONFIRMAR_PEDIDO")
        {
            final Intent intent = new Intent(context, PedidosActivityB.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            if(resul == "1")
            {
                Toast.makeText(context, "Pedido Confirmado Exitosamente !!!", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(context, "El pedido no puede ser confirmado !!!", Toast.LENGTH_SHORT).show();
            }
            intent.putExtra(Intent.EXTRA_TEXT, nroBulto+"|"+nroPed);
            context.startActivity(intent);
        }


    }

    protected String getNroBulto() {
        return nroBulto;
>>>>>>> Version-4
    }
}