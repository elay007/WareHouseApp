package com.example.dgutierrez.warehouse;

/**
 * Created by dgutierrez on 18/04/2015.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.UUID;

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
import android.util.Log;

public class MyRestFulGP  {

    private final String HTTP_EVENT="http://manaco.com.bo/ServerJSON/apirest.php";
    private HttpClient httpclient;

    /**
     * Envia los datos por GET
     * @throws IOException
     * @throws ClientProtocolException
     * @throws JSONException
     * */
    public String addEventGet(String name, String age) throws ClientProtocolException, IOException, JSONException
    {
        httpclient = new DefaultHttpClient();
        //los datos a enviar
        String uuid = UUID.randomUUID().toString();
        name = URLEncoder.encode(name,"UTF-8");
        age = URLEncoder.encode(age,"UTF-8");
        //url, cabecera JSON y ejecuta
        HttpGet httpget = new HttpGet(HTTP_EVENT + "?name="+name+"&age="+age+"&uuid="+uuid );
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
     * Envia los datos por GET con otros parametros para la entrega
     * @throws IOException
     * @throws ClientProtocolException
     * @throws JSONException
     * */
    public String addEventGet(String nroped, String nromod, String articulo, String cantdesp) throws ClientProtocolException, IOException, JSONException
    {
        httpclient = new DefaultHttpClient();
        //los datos a enviar
        String uuid = UUID.randomUUID().toString();
        nroped = URLEncoder.encode(nroped,"UTF-8");
        nromod = URLEncoder.encode(nromod,"UTF-8");
        articulo = URLEncoder.encode(articulo,"UTF-8");
        cantdesp = URLEncoder.encode(cantdesp,"UTF-8");
        //url, cabecera JSON y ejecuta
        HttpGet httpget = new HttpGet(HTTP_EVENT + "?nroped="+nroped+"&nromod="+nromod+"&articulo="+articulo+"&cantdesp="+cantdesp );
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
     * Envia los datos por POST
     * @throws IOException
     * @throws ClientProtocolException
     * @throws JSONException
     * */
    public String addEventPost(String name, String age) throws ClientProtocolException, IOException, JSONException
    {
        httpclient = new DefaultHttpClient();
        String uuid = UUID.randomUUID().toString();
        //url y tipo de contenido
        HttpPost httppost = new HttpPost(HTTP_EVENT);
        httppost.addHeader("Content-Type", "application/json");
        //forma el JSON y tipo de contenido
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", uuid );
        jsonObject.put("name", name );
        jsonObject.put("age", age );
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
     * Envia los datos por POST para la entrega
     * @throws IOException
     * @throws ClientProtocolException
     * @throws JSONException
     * */
    public String addEventPost(String nroped, String nromod, String articulo, String cantdesp) throws ClientProtocolException, IOException, JSONException
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
        jsonObject.put("cantdesp", cantdesp );

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
