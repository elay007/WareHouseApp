package com.example.dgutierrez.warehouse;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by dgutierrez on 15/04/2015.
 */
public class UtilityWarehouseApp {
    private static final String LOG_TAG = UtilityWarehouseApp.class.getSimpleName();

    public static String getJsonStringFromNetwork(String table, String name, String param1, String param2) {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String method = table+"."+name;


        try {

            final String FIXTURE_BASE_URL = "http://manaco.com.bo/ServerJSON";
<<<<<<< HEAD
=======
            //final String FIXTURE_BASE_URL = "http://10.0.8.24/ServerJSON";
>>>>>>> Version-4
            final String FIXTURE_PATH = "api.php";
            final String TIME_FRAME_PARAMETER = "method";
            final String PARAMETER_1 = "param1";
            final String PARAMETER_2 = "param2";

            Uri builtUri = Uri.parse(FIXTURE_BASE_URL).buildUpon()
                    .appendPath(FIXTURE_PATH)
                    .appendQueryParameter(TIME_FRAME_PARAMETER, method)
                    .appendQueryParameter(PARAMETER_1, param1)
                    .appendQueryParameter(PARAMETER_2, param2)
                    .build();
            URL url = new URL(builtUri.toString());
            Log.d(LOG_TAG, builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static String[] parseFixtureJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "fixtures";
        final String HOME_TEAM = "homeTeamName";
        final String AWAY_TEAM = "awayTeamName";
        final String RESULT = "result";
        final String HOME_SCORE = "goalsHomeTeam";
        final String AWAY_SCORE = "goalsAwayTeam";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String homeTeam;
            String awayTeam;
            int homeScore;
            int awayScore;
            JSONObject matchObject = fixturesArray.getJSONObject(i);
            JSONObject resultObject = matchObject.getJSONObject(RESULT);

            homeTeam = matchObject.getString(HOME_TEAM);
            awayTeam = matchObject.getString(AWAY_TEAM);
            homeScore = resultObject.getInt(HOME_SCORE);
            awayScore = resultObject.getInt(AWAY_SCORE);

            String resultString = new Formatter().format("%s: %d - %s: %d", homeTeam, homeScore, awayTeam, awayScore).toString();
            result.add(resultString);
        }
        return result.toArray(new String[result.size()]);
    }

    public static String [] parsePedidoJson(String fixtureJson) throws JSONException {
        //public static String[] parsePedidoJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String NRO_PED = "NRO_PED";
        final String OBSERVACION = "OBS";
        final String PK_SEMANA = "PK_SEMANA";
        final String FECHA_PED  = "FECHA_PED";
        final String TIENDA_DESTINO = "DESTINO";
        final String ESTADO_PEDIDO = "ESTADO";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);
        /*
        String[][] resultado = new String[3][];
        resultado[0] = new String[fixturesArray.length()+1];
        resultado[1] = new String[fixturesArray.length()+1];
        resultado[2] = new String[fixturesArray.length()+1];
        */




        for (int i = 0; i < fixturesArray.length(); i++) {
            String nroPed;
            String obs;
            String  pkSemana;
            String  fechaPed;
            String destino;
            String estado;
            JSONObject matchObject = fixturesArray.getJSONObject(i);
            //JSONObject resultObject = matchObject.getJSONObject(RESULT);

            nroPed = matchObject.getString(NRO_PED);
            obs = matchObject.getString(OBSERVACION);
            pkSemana = matchObject.getString(PK_SEMANA);
            fechaPed = matchObject.getString(FECHA_PED);
            destino = matchObject.getString(TIENDA_DESTINO);
            estado = matchObject.getString(ESTADO_PEDIDO);
/*
            alTienda.add(destino);
            alEstado.add(estado);
            alNroPed.add(nroPed);
*/
            // String resultString = new Formatter().format("Ped:%s Tda:%s Estado = %s", nroPed, destino, estado).toString();
            String resultString = new Formatter().format("%s|%s|%s", nroPed, destino, estado).toString();
            result.add(resultString);

            /*
            resultado[0][i] = nroPed;
            resultado[1][i] = destino;
            resultado[2][i] = estado;
*/
        }
        return result.toArray(new String[result.size()]);
        //return arrayPedidos.toArray(new String[arrayPedidos.size()]);
        //return resultado;
    }

    public static String [] parseModulosJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String NRO_MOD = "NRO_MOD";
        final String ESTADO = "ESTADO";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);
        for (int i = 0; i < fixturesArray.length(); i++) {
            String nroMod, estado;

            JSONObject matchObject = fixturesArray.getJSONObject(i);

            nroMod = matchObject.getString(NRO_MOD);
            estado = matchObject.getString(ESTADO);

            String resultString = new Formatter().format("%s|%s", nroMod, estado).toString();
            result.add(resultString);

        }
        return result.toArray(new String[result.size()]);
    }

    public static String [] parseArticulosJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String PK_ARTICULO = "PK_ARTICULO";
        final String NRO_MOD = "NRO_MOD";
        final String CANT_STOCK = "CANT_STOCK";
        final String CANT_PED  = "CANT_PED";
        final String CANT_DESP  = "CANT_DESP";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String pkArticulo;
            String  nroMod;
            String  cantStock;
            String cantPed;
            String cantDesp;

            JSONObject matchObject = fixturesArray.getJSONObject(i);

            pkArticulo = matchObject.getString(PK_ARTICULO);
            nroMod = matchObject.getString(NRO_MOD);
            cantStock = matchObject.getString(CANT_STOCK);
            cantPed = matchObject.getString(CANT_PED);
            cantDesp = matchObject.getString(CANT_DESP);

            String resultString = new Formatter().format("%s|%s|%s|%s|%s", pkArticulo, nroMod, cantStock,cantPed,cantDesp).toString();
            result.add(resultString);

        }
        return result.toArray(new String[result.size()]);
    }

<<<<<<< HEAD
=======
    public static String [] parseBultoJson(String fixtureJson) throws JSONException {

        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String NRO_PED = "NRO_PED";
        final String NRO_BULTO = "NRO_BULTO";
        final String CANT_PARES = "CANT_PARES";
        final String CANT_ACCES = "CANT_ACCES";
        final String ESTADO_BULTO = "ESTADO";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String nroPed;
            String nroBulto;
            String  cantPares;
            String  cantAcces;
            String estado;
            JSONObject matchObject = fixturesArray.getJSONObject(i);

            nroPed = matchObject.getString(NRO_PED);
            nroBulto = matchObject.getString(NRO_BULTO);
            cantPares = matchObject.getString(CANT_PARES);
            cantAcces = matchObject.getString(CANT_ACCES);
            estado = matchObject.getString(ESTADO_BULTO);

            String resultString = new Formatter().format("%s|%s|%s|%s|%s", nroPed, nroBulto, cantPares, cantAcces, estado).toString();
            result.add(resultString);

        }
        return result.toArray(new String[result.size()]);
    }

    //"NRO_BULTO" : "1","PK_SECUENCIA" : "1","PK_ARTICULO" : "7105970","NRO_MOD" : "12","CANT_BULTO" : "0"
    public static String [] parseArticuloBultoJson(String fixtureJson) throws JSONException {

        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String NRO_BULTO = "NRO_BULTO";
        final String NRO_MOD = "NRO_MOD";
        final String PK_SECUENCIA = "PK_SECUENCIA";
        final String PK_ARTICULO = "PK_ARTICULO";
        final String CANT_BULTO = "CANT_BULTO";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String nroMod;
            String nroBulto;
            String cantBulto;
            String pkSecuencia;
            String pkArticulo;

            JSONObject matchObject = fixturesArray.getJSONObject(i);

            nroMod = matchObject.getString(NRO_MOD);
            nroBulto = matchObject.getString(NRO_BULTO);
            cantBulto = matchObject.getString(CANT_BULTO);
            pkSecuencia = matchObject.getString(PK_SECUENCIA);
            pkArticulo = matchObject.getString(PK_ARTICULO);

            String resultString = new Formatter().format("%s|%s|%s|%s|%s", pkArticulo, nroMod, nroBulto, cantBulto, pkSecuencia).toString();
            result.add(resultString);

        }
        return result.toArray(new String[result.size()]);
    }

    //ULTIMO DGUTIERREZ
    public static String [] parseArticuloTallaJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String PED1 = "PED1";
        final String PED2 = "PED2";
        final String PED3 = "PED3";
        final String PED4 = "PED4";
        final String PED5 = "PED5";
        final String PED6 = "PED6";
        final String PED7 = "PED7";
        final String PED8 = "PED8";
        final String PED9 = "PED9";
        final String DESP1 = "DESP1";
        final String DESP2 = "DESP2";
        final String DESP3 = "DESP3";
        final String DESP4 = "DESP4";
        final String DESP5 = "DESP5";
        final String DESP6 = "DESP6";
        final String DESP7 = "DESP7";
        final String DESP8 = "DESP8";
        final String DESP9 = "DESP9";
        final String TAM_INI = "TAM_INI";
        final String NUM_INI = "NUM_INI";
        final String NUM_FIN = "NUM_FIN";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String ped1,ped2,ped3,ped4,ped5,ped6,ped7,ped8,ped9;
            String desp1,desp2,desp3,desp4,desp5,desp6,desp7,desp8,desp9;
            String tam_ini,num_ini,num_fin;

            JSONObject matchObject = fixturesArray.getJSONObject(i);

            ped1 = matchObject.getString(PED1);
            ped2 = matchObject.getString(PED2);
            ped3 = matchObject.getString(PED3);
            ped4 = matchObject.getString(PED4);
            ped5 = matchObject.getString(PED5);
            ped6 = matchObject.getString(PED6);
            ped7 = matchObject.getString(PED7);
            ped8 = matchObject.getString(PED8);
            ped9 = matchObject.getString(PED9);

            desp1 = matchObject.getString(DESP1);
            desp2 = matchObject.getString(DESP2);
            desp3 = matchObject.getString(DESP3);
            desp4 = matchObject.getString(DESP4);
            desp5 = matchObject.getString(DESP5);
            desp6 = matchObject.getString(DESP6);
            desp7 = matchObject.getString(DESP7);
            desp8 = matchObject.getString(DESP8);
            desp9 = matchObject.getString(DESP9);

            tam_ini = matchObject.getString(TAM_INI);
            num_ini = matchObject.getString(NUM_INI);
            num_fin = matchObject.getString(NUM_FIN);

            String resultString = new Formatter().format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", ped1, ped2, ped3, ped4, ped5, ped6, ped7, ped8, ped9, desp1, desp2, desp3, desp4, desp5, desp6, desp7, desp8, desp9, tam_ini, num_ini, num_fin).toString();
            result.add(resultString);

        }
        return result.toArray(new String[result.size()]);
    }

    public static String [] parseLoginJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "items";
        final String ID_USUARIO = "ID_USUARIO";
        final String NOMBRE = "NOMBRE";
        final String ROL = "ROL";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String id_usuario,nombre,rol;

            JSONObject matchObject = fixturesArray.getJSONObject(i);

            id_usuario = matchObject.getString(ID_USUARIO);
            nombre = matchObject.getString(NOMBRE);
            rol = matchObject.getString(ROL);

            String resultString = new Formatter().format("%s|%s|%s", id_usuario, nombre, rol).toString();
            result.add(resultString);

        }
        return result.toArray(new String[result.size()]);
    }

>>>>>>> Version-4
}
