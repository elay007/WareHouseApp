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
import android.widget.Button;
import android.widget.ListView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ArticulosBultoActivity extends ActionBarActivity {

    private final String RESULT_FRAGMENT_TAG = "RF_TAG_ART_BULTO";
    private static final String LOG_TAG1 =  ActionBarActivity.class.getSimpleName();
    private String nroPed, nroBulto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos_bulto);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Log.v(LOG_TAG1, "ESTO CREATE ARTICULOS EN EL BULTO");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Articulos Bulto");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Llamamos a la función getGlobal();
        getGlobal();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_articulos_bulto, menu);
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

    public void scanContinuous(View view) throws InterruptedException {
        //Intent intent = new Intent(this, ContinuousCaptureActivity.class);
        //startActivity(intent);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(ContinuousCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        //integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    // Método para enviar los datos de los editText a la variable Global
    public void setGlobal() {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        globalVariable.setNroBulto(nroBulto);
        globalVariable.setNroPed(nroPed);
    }

    // Método para obtener los datos desde la clase global y asignarlos al editText
    public void getGlobal() {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        // Obtenemos valores desde la clase global
        nroPed = globalVariable.getNroPed();
        nroBulto = globalVariable.getNroBulto();

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String LOG_TAG =  PlaceholderFragment.class.getSimpleName();
        ListView listArtBultos;
        private ArrayList<ArticuloBulto> articulosBulto;
        private ArticuloBultoAdapter ArticuloBultoAdapter;
        private String nroBulto, nroPed;
        private MyAsyncTask tarea;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.v(LOG_TAG, "ESTO CREATE FRAGMENT");
            updateResults();
        }

        public void updateResults() {

            //Recuperar el listado
            /*
            Log.v(LOG_TAG, "OBJECT 1");
            Intent i = getActivity().getIntent();
            articulosBulto = (ArrayList<ArticuloBulto>)i.getSerializableExtra("sampleObject");
            ArticuloBulto artBulto = new ArticuloBulto();
            artBulto = getActivity().getIntent().getParcelableExtra("ArticuloBulto");
            Log.v(LOG_TAG, "OBJECT 2");
            if(artBulto != null)
            {
                Log.i("RM", String.valueOf(artBulto));
                Log.v(LOG_TAG, "ARTICULO: "+artBulto.getArticulo());
            }
            Log.v(LOG_TAG, "OBJECT 3");
            */
            articulosBulto = new ArrayList<ArticuloBulto>();
            ArrayList<ArticuloBulto> mQuestionsList = new ArrayList<ArticuloBulto>();
            Intent intent = getActivity().getIntent();
            if(intent != null && intent.hasExtra("ArticulosExtra")) {
                mQuestionsList = intent.getParcelableArrayListExtra("ArticulosExtra");
                articulosBulto = mQuestionsList;
            }
/*
            if(mQuestionsList != null && mQuestionsList.size() > 0) {
                ArticuloBulto artBulto = new ArticuloBulto();
                artBulto = articulosBulto.get(0);
                if (artBulto != null) {
                    Log.v(LOG_TAG, "ARTICULO: " + artBulto.getArticulo());
                    Log.v(LOG_TAG, "BULTO: " + Integer.toString(artBulto.getNroBulto()));
                }
            }*/
            //Fin recuperacion
            /*String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            if(result != null && result != "") {
                String aux[] = result.trim().split("\\|");
                nroBulto = aux[0];
                nroPed = aux[1];
            }*/

            getGlobal();

            GetResultTask task = new GetResultTask();

            task.execute();

            //Pruebita
            if(nroBulto.equals("N"))
            {
                Log.v("NEO BULTO", "CREACION NUEVO BULTO");
                tarea = new MyAsyncTask(getActivity());
                tarea.execute("POST","BULTO", nroPed, null,null,null,null);
                Log.v("NEO BULTO", "FIN CREACION NUEVO BULTO");
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_articulos_bulto, container, false);

            Log.v(LOG_TAG,"llenado articulos bultos");


            listArtBultos = (ListView) rootView.findViewById(R.id.result_articulos_bulto);
/*
            listArtBultos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    final Intent intent = new Intent(getActivity(), ArticulosBultoActivity.class);

                    intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(articulosBulto.get(position).getNroBulto()));

                    startActivity(intent);

                }

            });
*/
            Button btnSave = (Button) rootView.findViewById(R.id.save_articulos_bulto);

            btnSave.setOnClickListener(new AdapterView.OnClickListener() {
                public void onClick(View v) {
                    if(nroBulto.equals(null) || nroBulto.equals("") || nroBulto.equals("N")) {
                        nroBulto = tarea.getNroBulto();
                    }
                    //REvisdamos si efectivamente recupera el numero de bulto
                    Log.v(LOG_TAG,"BULTO "+ nroBulto);
                    for (int i = 0; i < articulosBulto.size(); i++)
                    {//String tipo, String nroped, String nromod, String articulo, String cantdesp, String secuencia
                        new MyAsyncTask(getActivity())
                                .execute("POST","ARTICULO_BULTO",
                                        nroBulto+"|"+nroPed,
                                        Integer.toString(articulosBulto.get(i).getNroModulo()),
                                        articulosBulto.get(i).getArticulo(),
                                        Integer.toString(articulosBulto.get(i).getCantBulto()),
                                        Integer.toString(i+1));

                    }

                    setGlobal();
                    //Despues agregamos su demas contenido
                    /*
                    final Intent intent = new Intent(getActivity(), ArticulosBultoActivity.class);

                    intent.putExtra(Intent.EXTRA_TEXT, nroPed);

                    startActivity(intent);
                    */
                }

            });

            Button btnCancel = (Button) rootView.findViewById(R.id.cancel_articulos_bulto);

            btnCancel.setOnClickListener(new AdapterView.OnClickListener() {
                public void onClick(View v) {
                    final Intent intent = new Intent(getActivity(),BultosActivity.class);
                    setGlobal();
                    intent.putExtra(Intent.EXTRA_TEXT, nroPed);
                    startActivity(intent);
                }

            });



            return rootView;
        }

        // Método para enviar los datos de los editText a la variable Global
        public void setGlobal() {
            final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();

            globalVariable.setNroBulto(nroBulto);
            globalVariable.setNroPed(nroPed);
        }

        // Método para obtener los datos desde la clase global y asignarlos al editText
        public void getGlobal() {
            final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();

            // Obtenemos valores desde la clase global
            nroPed = globalVariable.getNroPed();
            nroBulto = globalVariable.getNroBulto();

        }

        class GetResultTask extends AsyncTask<Void, Void, String []> {

            @Override
            protected String[] doInBackground(Void... params) {
                if(articulosBulto == null || articulosBulto.isEmpty() || articulosBulto.size() <= 0) {
                    //String resultString = Utility.getJsonStringFromNetwork();
                    Log.v(LOG_TAG, "INICIA BULTOS");
                    Log.v(LOG_TAG, "PEDIDO EN BULTO: " + nroPed);
                    String resultString = "";
                    resultString = UtilityWarehouseApp.getJsonStringFromNetwork("BULTOS_WHA", "DETALLES_BULTO_WHA", nroBulto.toString(), "");

                    Log.v(LOG_TAG, "TERMINA FILTRO");
                    Log.v(LOG_TAG, resultString);
                    Log.v(LOG_TAG, "POS 1");

                    try {
                        return UtilityWarehouseApp.parseArticuloBultoJson(resultString);
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                        e.printStackTrace();
                        Log.v(LOG_TAG, "POS 2");
                        return new String[]{"No DATA"};
                    }
                }
                else
                {
                    return new String[] {"SCAN"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {

                for (String result : strings) {
                    if (result == "No DATA")
                    {
                        //articulosBulto.add(new ArticuloBulto("NO DATA",0,0,0));
                        break;
                    }
                    if (result == "SCAN")
                    {
                       break;
                    }
                    String aux[] = result.trim().split("\\|");

                    ///String pk_art, int nro_mod, int nro_bulto, int cant
                    articulosBulto.add(new ArticuloBulto(aux[0],Integer.parseInt(aux[1]),Integer.parseInt(aux[2]),Integer.parseInt(aux[3])));

                    Log.v(LOG_TAG,"ESTO "+result);

                }
                Log.v(LOG_TAG,"ESTO TERMINO");

                listArtBultos.setAdapter(new ArticuloBultoAdapter(getActivity(), articulosBulto));

            }
        }
    }
}
