package com.example.dgutierrez.warehouse;

<<<<<<< HEAD
=======
import android.app.AlertDialog;
import android.content.DialogInterface;
>>>>>>> Version-4
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
=======
import android.view.KeyEvent;
>>>>>>> Version-4
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> Version-4

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class PedidosActivity extends ActionBarActivity {
    private final String RESULT_FRAGMENT_TAG = "RF_TAG";
    private static final String LOG_TAG1 =  PedidosActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Log.v(LOG_TAG1, "ESTO CREATE");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Pedidos");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
<<<<<<< HEAD

    }

=======
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void showDialogClose() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PedidosActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirma Salir...");

        // Setting Dialog Message
        alertDialog.setMessage("Esta seguro de salir de la aplicacion?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_action_warning);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
               // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:


                    showDialogClose();

                return true;
        }
        return false;
    }
>>>>>>> Version-4

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedidos, menu);
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
<<<<<<< HEAD
=======
        else if (id == android.R.id.home)
        {
            showDialogClose();
        }
>>>>>>> Version-4

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();

        PlaceholderFragment fragment = (PlaceholderFragment)getSupportFragmentManager().findFragmentByTag(RESULT_FRAGMENT_TAG);

        if (fragment != null)
            fragment.updateResults();


    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String LOG_TAG =  PlaceholderFragment.class.getSimpleName();
        ListView listPedidos;
        private ArrayList<Pedido> pedidos;
        private PedidoAdapter pedidoAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.v(LOG_TAG, "ESTO CREATE FRAGMENT");
            updateResults();
        }

        private void rellenarPedidos() {

            pedidos.add(new Pedido("Tienda 22220","P",2345));
            pedidos.add(new Pedido("Tienda 22220","P",2341));
            pedidos.add(new Pedido("Tienda 22220","P",3344));
            pedidos.add(new Pedido("Tienda 22220","E",4567));
        }

        public void updateResults() {
            GetResultTask task = new GetResultTask();

            task.execute();

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pedidos, container, false);

            pedidos = new ArrayList<Pedido>();

<<<<<<< HEAD
            //rellenarPedidos();

            Log.v(LOG_TAG,"llenado pedidos");
=======
            final GlobalClass globalVariable = (GlobalClass)  getActivity().getApplicationContext();

            //rellenarPedidos();

>>>>>>> Version-4

            listPedidos = (ListView) rootView.findViewById(R.id.result_pedidos);

            //pedidoAdapter=new PedidoAdapter(this.getActivity(), pedidos);

            //listPedidos.setAdapter(new PedidoAdapter(this.getActivity(), pedidos));



            listPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {


<<<<<<< HEAD

=======
                    globalVariable.setNroPed(Integer.toString(pedidos.get(position).getNro_ped()));
>>>>>>> Version-4

                    final Intent intent = new Intent(getActivity(), MapaActivity.class);
                    //intent.putExtra(TestConstants.SELCTED_SCENE_KEY, position);


                    intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(pedidos.get(position).getNro_ped()));

<<<<<<< HEAD
=======


>>>>>>> Version-4
                    startActivity(intent);

                }
            });


            return rootView;
        }

        class GetResultTask extends AsyncTask<Void, Void, String []> {

            @Override
            protected String[] doInBackground(Void... params) {
                //String resultString = Utility.getJsonStringFromNetwork();
                String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("PEDIDOS", "PED_PENDIENTES", "", "");
                Log.v(LOG_TAG, resultString);

                try {
                    return UtilityWarehouseApp.parsePedidoJson(resultString);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[] {"No DATA"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {


       //         pedidoAdapter.clear();
               // pedidos.clear();
                for (String result : strings) {
                    if (result == "No DATA")
                    {
                        pedidos.add(new Pedido("NO DATA","",-1));
                        break;
                    }
                    String aux[] = result.trim().split("\\|");

                    pedidos.add(new Pedido("Tienda "+aux[1],aux[2],Integer.parseInt(aux[0])));

                    Log.v(LOG_TAG,"ESTO "+result);
                   // String diaArray[] = result.trim().split("\\|");
                   // Log.v(LOG_TAG,"SPLIT "+diaArray[1]);
                   // arrayAdapter.add(diaArray[1]);
                }
                Log.v(LOG_TAG,"ESTO TERMINO");
/*
                for (int i = 0; i < pedidos.size(); i++) {
                    Log.v(LOG_TAG,"LEIDO DE PEDIDOS "+ Integer.toString(pedidos.get(i).getNro_ped())+" "+pedidos.get(i).getTienda()+" "+pedidos.get(i).getEstado());
                }
*/
                listPedidos.setAdapter(new PedidoAdapter(getActivity(), pedidos));
         //       pedidoAdapter = new PedidoAdapter(getActivity(),pedidos);

            }
        }
    }
}
