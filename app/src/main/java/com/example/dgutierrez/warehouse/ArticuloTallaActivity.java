package com.example.dgutierrez.warehouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


public class ArticuloTallaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_talla);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Detalle Articulo");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_articulo_talla, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        final GlobalClass globalVariable = (GlobalClass)  getApplicationContext();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                final Intent intent = new Intent(ArticuloTallaActivity.this, ArticulosActivity.class);

//                Bundle extras = new Bundle();
//                extras.putString("NRO_PED",globalVariable.getNroPed());
//                extras.putString("NRO_MOD",globalVariable.getNroMod());
//                intent.putExtras(extras);

                startActivity(intent);

                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        final GlobalClass globalVariable = (GlobalClass)  getApplicationContext();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home)
        {
            final Intent intent = new Intent(ArticuloTallaActivity.this, ArticulosActivity.class);

     //       Bundle extras = new Bundle();
     //       extras.putString("NRO_PED",globalVariable.getNroPed());
     //       extras.putString("NRO_MOD",globalVariable.getNroMod());
     //       intent.putExtras(extras);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String LOG_TAG =  PlaceholderFragment.class.getSimpleName();
        GridView listTallas;
        private ArrayList<Talla> tallas;
        private TallaAdapter tallaAdapter;

        String nro_ped,nro_mod,pk_articulo;
        View rootView;
        ImageButton boton_guardar;
        ImageButton boton_cancelar;

        public PlaceholderFragment() {
        }
/*
        private void rellenarTallas() {

            tallas.add(new Talla("37","S",5,3));
            tallas.add(new Talla("38","S",10,3));
            tallas.add(new Talla("39","S",15,3));
            tallas.add(new Talla("40","S",5,3));
            tallas.add(new Talla("41","S",3,4));
            tallas.add(new Talla("42","S",3,4));
            tallas.add(new Talla("43","S",3,4));
            tallas.add(new Talla("44","S",3,4));
            tallas.add(new Talla("45","S",3,4));
        }
*/
        public void updateResults() {

            Bundle extras = getActivity().getIntent().getExtras();

            nro_ped = extras.getString("NRO_PED");
            nro_mod = extras.getString("NRO_MOD");
            pk_articulo = extras.getString("PK_ARTICULO");

            GetResultTask task = new GetResultTask();

            task.execute();

        }

        public boolean validarDespacho()
        {
            for (int i = 0; i < tallas.size(); i++)
            {
                if (tallas.get(i).getTalla_desp()>tallas.get(i).getTalla_ped())
                {

                    Toast.makeText(getActivity(), "La cantidad despachada en  " + tallas.get(i).getTalla() + " es mayor a la cantidad pedida!", Toast.LENGTH_LONG).show();
                    return false;
                }

            }
            return true;
        }


        public boolean guardarDespacho()
        {
            String tam1="0",tam2="0",tam3="0",tam4="0",tam5="0",tam6="0",tam7="0",tam8="0",tam9="0";


            for (int i = 0; i < tallas.size(); i++)
            {
                /*
                if (tallas.get(i).getOrden()=="1")
                    tam1=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="2")
                    tam2=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="3")
                    tam3=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="4")
                    tam4=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="5")
                    tam5=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="6")
                    tam6=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="7")
                    tam7=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="8")
                    tam8=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden()=="9")
                    tam9=Integer.toString(tallas.get(i).getTalla_desp());
*/

                if (tallas.get(i).getOrden().equals("1"))
                    tam1=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("2"))
                    tam2=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("3"))
                    tam3=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("4"))
                    tam4=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("5"))
                    tam5=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("6"))
                    tam6=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("7"))
                    tam7=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("8"))
                    tam8=Integer.toString(tallas.get(i).getTalla_desp());
                else if (tallas.get(i).getOrden().equals("9"))
                    tam9=Integer.toString(tallas.get(i).getTalla_desp());

            }


            new UtilityUpdateWarehouse(getActivity())
                    .execute("POST", nro_ped, nro_mod,pk_articulo,tam1,tam2,tam3,tam4,tam5,tam6,tam7,tam8,tam9);

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
            View rootView = inflater.inflate(R.layout.fragment_articulo_talla, container, false);

            tallas =  new ArrayList<Talla>();

            Bundle extras = getActivity().getIntent().getExtras();

            nro_ped = extras.getString("NRO_PED");
            nro_mod = extras.getString("NRO_MOD");
            pk_articulo = extras.getString("PK_ARTICULO");

            TextView detailsText = (TextView)rootView.findViewById(R.id.t_pk_articulo);
            detailsText.setText(pk_articulo);

         //   rellenarTallas(); //despues comentar

            listTallas = (GridView) rootView.findViewById(R.id.result_tallas);

         //   listTallas.setAdapter(new TallaAdapter(getActivity(), tallas)); // despues comentar

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

            boton_cancelar = (ImageButton) rootView.findViewById(R.id.btn_cancelar);

            boton_cancelar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(v.getContext(), ArticulosActivity.class);

           //         Bundle extras = new Bundle();
           //         extras.putString("NRO_PED",nro_ped);
           //         extras.putString("NRO_MOD",nro_mod);
           //         intent.putExtras(extras);

                    startActivity(intent);


                }


            });


            return rootView;
        }

        class GetResultTask extends AsyncTask<Void, Void, String[]> {

            @Override
            protected String[] doInBackground(Void... params) {

                String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("TALLAS", "PED_ART_TALL", nro_ped, pk_articulo);
                Log.v(LOG_TAG, resultString);

                try {
                    return UtilityWarehouseApp.parseArticuloTallaJson(resultString);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[]{"No DATA"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {
                int pos,num_i,num_f;
                //Inicializamos los bloques
                Log.v(LOG_TAG, "INICIA EL CARGADO DE BLOQUES");

                for (String result : strings) {
                    if (result == "No DATA") {
                        break;
                    }
                    String aux[] = result.trim().split("\\|");

                    //ARMAR LISTA DE TALLAS
                    num_i=Integer.parseInt(aux[19]);
                    num_f=Integer.parseInt(aux[20]);
                    pos= Integer.parseInt(aux[18]);

                    for (int i = num_i; i <= num_f ; i++)
                    {
                        tallas.add(new Talla(Integer.toString(i),Integer.toString(pos),Integer.parseInt(aux[pos-1]),Integer.parseInt(aux[pos+8])));
                        pos++;
                    }

                }
                Log.v(LOG_TAG, "ESTO TERMINO");

                tallaAdapter = new TallaAdapter(getActivity(), tallas);
                listTallas.setAdapter(tallaAdapter);

            }
        }

    }
}
