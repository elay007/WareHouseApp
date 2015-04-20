package com.example.dgutierrez.warehouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;


public class ArticulosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Articulos");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_articulos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        ListView listArticulos;
        private ArrayList<Articulo> articulos;
        private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

        public PlaceholderFragment() {
        }

        public void updateResults() {
            //String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            //numPedido = result;
            GetResultTask task = new GetResultTask();

            task.execute();

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.v(LOG_TAG, "ESTO CREATE FRAGMENT");
            updateResults();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_articulos, container, false);

            articulos = new ArrayList<Articulo>();

            //rellenarArticulos();


            listArticulos = (ListView) rootView.findViewById(R.id.result_articulos);


/*
            listArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {


                    final Intent intent = new Intent(getActivity(), MapaActivity.class);
                    //intent.putExtra(TestConstants.SELCTED_SCENE_KEY, position);

                    intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(pedidos.get(position).getNro_ped()));

                    startActivity(intent);

                }
            });

*/

            return rootView;
        }

        class GetResultTask extends AsyncTask<Void, Void, String[]> {

            @Override
            protected String[] doInBackground(Void... params) {

                String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("DETALLES", "PED_ARTICULOS", "1", "12");
                Log.v(LOG_TAG, resultString);

                try {
                    return UtilityWarehouseApp.parseArticulosJson(resultString);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[]{"No DATA"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {
                //Inicializamos los bloques
                Log.v(LOG_TAG, "INICIA EL CARGADO DE BLOQUES");

                for (String result : strings) {
                    if (result == "No DATA") {
                        break;
                    }
                    String aux[] = result.trim().split("\\|");
                    articulos.add(new Articulo(aux[0],Integer.parseInt(aux[1]),Integer.parseInt(aux[2]),Integer.parseInt(aux[3]),0));

                    Log.v(LOG_TAG, "ESTO " + result);
                }
                Log.v(LOG_TAG, "ESTO TERMINO");
                listArticulos.setAdapter(new ArticuloAdapter(getActivity(), articulos));

            }
        }
    }
}
