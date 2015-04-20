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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;


public class MapaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Mapa");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
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

        private static final String LOG_TAG =  PlaceholderFragment.class.getSimpleName();
        private String numPedido;
        GridView gridView;
        static final Bloque[] bloques = new Bloque[]
                {new Bloque("","N",1,""),new Bloque("A","N",2,""),new Bloque("B","N",3,""),new Bloque("C","N",4,""),
                        new Bloque("W","N",5,""),new Bloque("","N",6,"1"),new Bloque("","N",7,"2"),new Bloque("","N",8,"3"),
                        new Bloque("X","N",9,""),new Bloque("","N",10,"4"),new Bloque("","N",11,"5"),new Bloque("","N",12,"6"),
                        new Bloque("Y","N",13,""),new Bloque("","N",14,"7"),new Bloque("","N",15,"8"),new Bloque("","N",16,"9"),
                        new Bloque("Z","N",17,""),new Bloque("","N",18,"10"),new Bloque("","N",19,"11"),new Bloque("","N",20,"12")};

        public PlaceholderFragment() {
        }

        public void updateResults() {
            String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            numPedido = result;
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
            View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);


            String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            TextView detailsText = (TextView)rootView.findViewById(R.id.t_nro_pedido);

            numPedido = result;

            detailsText.setText(result);

            gridView = (GridView) rootView.findViewById(R.id.gridview);

            //gridView.setAdapter(new MapAdapter(this.getActivity(), bloques));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    final Intent intent = new Intent(v.getContext(), ArticulosActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("NRO_PED",numPedido);
                    extras.putString("NRO_MOD",bloques[position].getNro());
                    intent.putExtras(extras);

                    startActivity(intent);

                }
            });

            return rootView;
        }

        class GetResultTask extends AsyncTask<Void, Void, String []> {

            @Override
            protected String[] doInBackground(Void... params) {
                //String resultString = Utility.getJsonStringFromNetwork();
                String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("STOCK","NRO_MODULOS",numPedido,"");
                Log.v(LOG_TAG, resultString);

                try {
                    return UtilityWarehouseApp.parseModulosJson(resultString);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[] {"No DATA"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {
                //Inicializamos los bloques
                bloquesClear();
                Log.v(LOG_TAG,"INICIA EL CARGADO DE BLOQUES");
                int orden = 0;
                for (String result : strings) {
                    if (result == "No DATA")
                    {
                        break;
                    }
                    String aux[] = result.trim().split("\\|");

                    orden++;
                    double pos = Double.parseDouble(aux[0]);
                    int posMapa = (int)(3.0 + Math.ceil(pos / 3.0) + pos);
                    bloques[posMapa] = new Bloque(Integer.toString(orden),aux[1],posMapa,aux[0]);

                    Log.v(LOG_TAG,"ESTO "+result);
                }
                Log.v(LOG_TAG,"ESTO TERMINO");
                gridView.setAdapter(new MapAdapter(getActivity(), bloques));

            }

            private void bloquesClear()
            {
                for (int i = 1; i < 13; i++) {
                    double pos = (double)(i);
                    int posMapa = (int)(3.0 + Math.ceil(pos / 3.0) + pos);
                    bloques[posMapa] = new Bloque("","N",i,Integer.toString(i));
                }
            }
        }
    }
}
