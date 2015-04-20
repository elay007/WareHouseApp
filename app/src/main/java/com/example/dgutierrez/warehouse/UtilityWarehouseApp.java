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

}
