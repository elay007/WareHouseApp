package com.example.dgutierrez.warehouse;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by dgutierrez on 16/08/2015.
 */

public class UtilityApiRest  {

    private final String HTTP_EVENT="http://manaco.com.bo/ServerJSON/apiresttall.php";
    //private final String HTTP_EVENT="http://10.0.2.2/ServerJSON/apiresttall.php";
    private HttpClient httpclient;

    /**
     * Envia los datos por GET con otros parametros para la entrega
     * @throws IOException
     * @throws ClientProtocolException
     * @throws JSONException
     * */
    public String addEventGet(String nroped, String nromod, String articulo, String desp1,String desp2,String desp3,String desp4,String desp5,String desp6,String desp7,String desp8,String desp9) throws ClientProtocolException, IOException, JSONException
    {
        httpclient = new DefaultHttpClient();
        //los datos a enviar
        String uuid = UUID.randomUUID().toString();
        nroped = URLEncoder.encode(nroped,"UTF-8");
        nromod = URLEncoder.encode(nromod,"UTF-8");
        articulo = URLEncoder.encode(articulo,"UTF-8");
        desp1 = URLEncoder.encode(desp1,"UTF-8");
        desp2 = URLEncoder.encode(desp2,"UTF-8");
        desp3 = URLEncoder.encode(desp3,"UTF-8");
        desp4 = URLEncoder.encode(desp4,"UTF-8");
        desp5 = URLEncoder.encode(desp5,"UTF-8");
        desp6 = URLEncoder.encode(desp6,"UTF-8");
        desp7 = URLEncoder.encode(desp7,"UTF-8");
        desp8 = URLEncoder.encode(desp8,"UTF-8");
        desp9 = URLEncoder.encode(desp9,"UTF-8");
        //url, cabecera JSON y ejecuta
        HttpGet httpget = new HttpGet(HTTP_EVENT + "?nroped="+nroped+"&nromod="+nromod+"&articulo="+articulo+"&desp1="+desp1+"&desp2="+desp2+"&desp3="+desp3+"&desp4="+desp4+"&desp5="+desp5+"&desp6="+desp6+"&desp7="+desp7+"&desp8="+desp8+"&desp9="+desp9 );
        httpget.addHeader("Content-Type", "application/json");
        HttpResponse response = httpclient.execute(httpget);
        //obtiene la respuesta del servidor se transforma a objeto JSON
        String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
        JSONObject object = new JSONObject(jsonResult);
        Log.i("jsonResult",jsonResult);
        if( object.getString("Result").equals("200"))
        {
            return "Petición GET: Exito";
        }
        return "Petición GET: Fracaso";
    }

    /**
     * Envia los datos por POST para la entrega
     * @throws IOException
     * @throws ClientProtocolException
     * @throws JSONException
     * */
    public String addEventPost(String nroped, String nromod, String articulo, String desp1,String desp2,String desp3,String desp4,String desp5,String desp6,String desp7,String desp8,String desp9) throws ClientProtocolException, IOException, JSONException
    {
        httpclient = new DefaultHttpClient();
        String uuid = UUID.randomUUID().toString();
        //url y tipo de contenido
        HttpPost httppost = new HttpPost(HTTP_EVENT);
        httppost.addHeader("Content-Type", "application/json");
        //forma el JSON y tipo de contenido
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nroped", nroped );
        jsonObject.put("nromod",nromod );
        jsonObject.put("articulo",articulo );
        jsonObject.put("desp1", desp1 );
        jsonObject.put("desp2", desp2 );
        jsonObject.put("desp3", desp3 );
        jsonObject.put("desp4", desp4 );
        jsonObject.put("desp5", desp5 );
        jsonObject.put("desp6", desp6 );
        jsonObject.put("desp7", desp7 );
        jsonObject.put("desp8", desp8 );
        jsonObject.put("desp9", desp9 );

        StringEntity stringEntity = new StringEntity( jsonObject.toString());
        stringEntity.setContentType( (Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httppost.setEntity(stringEntity);
        //ejecuta
        HttpResponse response = httpclient.execute(httppost);
        //obtiene la respuesta y transorma a objeto JSON
        String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
        JSONObject object = new JSONObject(jsonResult);
        Log.i("jsonResult",jsonResult);
        if( object.getString("Result").equals("200"))
        {
            return "Petición POST: Exito";
        }
        return "Petición POST: Fracaso";
    }

    /**
     * Transforma el InputStream en un String
     * @return StringBuilder
     * */
    private StringBuilder inputStreamToString(InputStream is)
    {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader rd = new BufferedReader( new InputStreamReader(is) );
        try
        {
            while( (line = rd.readLine()) != null )
            {
                stringBuilder.append(line);
            }
        }
        catch( IOException e)
        {
            e.printStackTrace();
        }

        return stringBuilder;
    }
}
