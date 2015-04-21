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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        String nro_ped,nro_bloque;
        View rootView;
        ArticuloAdapter articulo;
        ImageButton boton_guardar;

        public PlaceholderFragment() {
        }
        /*
        private void rellenarArticulos() {

            articulos.add(new Articulo("8534987",5,10,3,3));
            articulos.add(new Articulo("8266945",5,15,3,3));
            articulos.add(new Articulo("8539984",5,5,3,3));
            articulos.add(new Articulo("7105970",5,3,4,3));
            articulos.add(new Articulo("7105971",5,3,4,3));
        }
        */
        public void updateResults() {

            Bundle extras = getActivity().getIntent().getExtras();

            nro_ped = extras.getString("NRO_PED");
            nro_bloque = extras.getString("NRO_MOD");

            GetResultTask task = new GetResultTask();

            task.execute();

        }


        public boolean validarDespacho()
        {
            for (int i = 0; i < articulos.size(); i++)
            {
                if (articulos.get(i).getCant_desp()>articulos.get(i).getCant_ped())
                {

                    Toast.makeText(getActivity(),"La cantidad despachada en  " + articulos.get(i).getPk_articulo() + " es mayor a la cantidad pedida!", Toast.LENGTH_LONG).show();
                    return false;
                }

            }
            return true;
        }


        public boolean guardarDespacho()
        {
            for (int i = 0; i < articulos.size(); i++)
            {
//                Toast.makeText(getActivity(),articulos.get(i).getPk_articulo(), Toast.LENGTH_SHORT).show();

                new MyAsyncTask(getActivity())
                        .execute("POST", nro_ped, nro_bloque,articulos.get(i).getPk_articulo(),Integer.toString(articulos.get(i).getCant_desp()));

            }
            return true;
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
            rootView = inflater.inflate(R.layout.fragment_articulos, container, false);

            articulos = new ArrayList<Articulo>();

          //  rellenarArticulos();


            listArticulos = (ListView) rootView.findViewById(R.id.result_articulos);

         //   listArticulos.setAdapter(new ArticuloAdapter(getActivity(), articulos, rootView));

            boton_guardar = (ImageButton) rootView.findViewById(R.id.btn_guardar);

            boton_guardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                  if (validarDespacho()) {
                      if (guardarDespacho()) {
                    //      Toast.makeText(getActivity(), "Existio un error al realizar la operacion!", Toast.LENGTH_SHORT).show();
                    //  } else
                     // {

                 //         Toast.makeText(getActivity(), "La operacion se realizo con exito!", Toast.LENGTH_LONG).show();

                       //   final Intent intent = new Intent(getActivity(), MapaActivity.class);
                       //   intent.putExtra(Intent.EXTRA_TEXT, nro_ped);
                       //   startActivity(intent);
                      }
                  }

                }


            });



            return rootView;
        }

        class GetResultTask extends AsyncTask<Void, Void, String[]> {

            @Override
            protected String[] doInBackground(Void... params) {

                //String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("DETALLES", "PED_ARTICULOS", "1", "12");
                String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("DETALLES", "PED_ARTICULOS", nro_ped, nro_bloque);
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
                    articulos.add(new Articulo(aux[0],Integer.parseInt(aux[1]),Integer.parseInt(aux[2]),Integer.parseInt(aux[3]),Integer.parseInt(aux[4])));

                    Log.v(LOG_TAG, "ESTO " + result);
                }
                Log.v(LOG_TAG, "ESTO TERMINO");

                articulo = new ArticuloAdapter(getActivity(), articulos,rootView);

                //listArticulos.setAdapter(new ArticuloAdapter(getActivity(), articulos));

                listArticulos.setAdapter(articulo);

            }
        }
    }
}
