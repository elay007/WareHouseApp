package com.example.dgutierrez.warehouse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


public class BultosActivity extends ActionBarActivity {

    private final String RESULT_FRAGMENT_TAG = "RF_TAG_BULTO";
    private static final String LOG_TAG1 =  BultosActivity.class.getSimpleName();
    private String nroPed, nroBulto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bultos);
        String result = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        nroPed = result;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(nroPed))
                    .commit();
        }

        Log.v(LOG_TAG1, "ESTO CREATE BULTOS");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Bultos");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setGlobal();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bultos, menu);
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
        ListView listbultos;
        private ArrayList<Bulto> bultos;
        private BultoAdapter BultoAdapter;
        private String nroPed,nroBulto;
        private int currentListItemIndex;

        public PlaceholderFragment() {
        }

        @SuppressLint("ValidFragment")
        public PlaceholderFragment(String nroPed) {
            this.nroPed = nroPed;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.v(LOG_TAG, "ESTO CREATE FRAGMENT");
            updateResults();
        }

        public void updateResults() {
            String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            nroPed = result;
            GetResultTask task = new GetResultTask();

            task.execute();

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bultos, container, false);

            bultos = new ArrayList<Bulto>();

            Log.v(LOG_TAG,"llenado bultos");

            listbultos = (ListView) rootView.findViewById(R.id.result_bultos);

            listbultos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    final Intent intent = new Intent(getActivity(), ArticulosBultoActivity.class);

                    nroBulto = Integer.toString(bultos.get(position).getNroBulto());

                    intent.putExtra(Intent.EXTRA_TEXT, nroBulto+"|"+nroPed);

                    setGlobal();

                    startActivity(intent);

                }

            });

            Button btnAdd = (Button) rootView.findViewById(R.id.add_bulto);

            btnAdd.setOnClickListener(new AdapterView.OnClickListener() {
                public void onClick(View v) {
                    final Intent intent1 = new Intent(getActivity(), ArticulosBultoActivity.class);
                    nroBulto = "N";
                    intent1.putExtra(Intent.EXTRA_TEXT, nroBulto+"|"+nroPed);
                    setGlobal();
                    startActivity(intent1);
                }

            });

            Button btnCancel = (Button) rootView.findViewById(R.id.cancel_bulto);

            btnCancel.setOnClickListener(new AdapterView.OnClickListener() {
                public void onClick(View v) {
                    Intent intent2 = new Intent(getActivity(),PedidosActivityB.class);
                    setGlobal();
                    startActivity(intent2);
                }

            });


            registerForContextMenu(listbultos);

            return rootView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu_bulto, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String nPedido, nBulto;
            currentListItemIndex = info.position;
            nPedido = Integer.toString(bultos.get(currentListItemIndex).getNroPed());
            nBulto = Integer.toString(bultos.get(currentListItemIndex).getNroBulto());
            switch (item.getItemId()) {
                case R.id.edit:
                    final Intent intent = new Intent(getActivity(), ArticulosBultoActivity.class);
                    nroBulto = Integer.toString(bultos.get(currentListItemIndex).getNroBulto());
                    intent.putExtra(Intent.EXTRA_TEXT, nroBulto+"|"+nroPed);
                    setGlobal();
                    startActivity(intent);
                    //editNote(info.id);

                    return true;
                case R.id.delete:
                    new MyAsyncTask(getActivity())
                            .execute("POST","DELETE_BULTO",nBulto+"|"+nPedido,null,null,null,null);
                    Toast.makeText(getActivity(), "Bulto "+nBulto+" Eliminado! ", Toast.LENGTH_SHORT).show();
                    bultos.remove(currentListItemIndex);
                    listbultos.setAdapter(new BultoAdapter(getActivity(), bultos));

                    //deleteNote(info.id);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
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
                //String resultString = Utility.getJsonStringFromNetwork();
                Log.v(LOG_TAG, "INICIA BULTOS");
                Log.v(LOG_TAG, "PEDIDO EN BULTO: " + nroPed);
                String resultString="";
                resultString = UtilityWarehouseApp.getJsonStringFromNetwork("BULTOS_WHA", "BULTOS_PEDIDO", nroPed, "");

                Log.v(LOG_TAG, "TERMINA FILTRO");
                Log.v(LOG_TAG, resultString);

                try {
                    return UtilityWarehouseApp.parseBultoJson(resultString);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[] {"No DATA"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {

                for (String result : strings) {
                    if (result == "No DATA")
                    {
                       // bultos.add(new Bulto(0,0,0,0,"NO DATA"));
                        break;
                    }
                    String aux[] = result.trim().split("\\|");

                    bultos.add(new Bulto(Integer.parseInt(aux[0]),Integer.parseInt(aux[1]),Integer.parseInt(aux[2]),Integer.parseInt(aux[3]),aux[4]));

                    Log.v(LOG_TAG,"ESTO "+result);

                }
                Log.v(LOG_TAG,"ESTO TERMINO");

                listbultos.setAdapter(new BultoAdapter(getActivity(), bultos));

            }
        }
    }
}
