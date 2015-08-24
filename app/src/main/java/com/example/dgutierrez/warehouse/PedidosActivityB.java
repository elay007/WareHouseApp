package com.example.dgutierrez.warehouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


public class PedidosActivityB extends ActionBarActivity {
    private final String RESULT_FRAGMENT_TAG = "RF_TAG";
    private static final String LOG_TAG1 =  PedidosActivityB.class.getSimpleName();

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

    }


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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String LOG_TAG =  PlaceholderFragment.class.getSimpleName();
        ListView listPedidos;
        private ArrayList<Pedido> pedidos;
        private PedidoAdapterB pedidoAdapter;
        private String rolUsuario;
        private MyAsyncTask tarea;
        // Tracks current menu item
        private int currentListItemIndex;
        // Tracks current contextual action mode
        private ActionMode currentActionMode;
/*
        private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

            // Called when the action mode is created; startActionMode() was called
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Actions");
                mode.getMenuInflater().inflate(R.menu.actions_textview, menu);
                return true;
            }

            // Called each time the action mode is shown.
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // Return false if nothing is done
            }

            // Called when the user selects a contextual menu item
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        pedidos.remove(currentListItemIndex); // Remove current item
                        pedidoAdapter.notifyDataSetChanged(); // Refresh adapter
                        mode.finish(); // Action picked, so close the contextual menu
                        return true;
                    default:
                        return false;
                }
            }

            // Called when the user selects a contextual menu item
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                String nroPedido;
                nroPedido = Integer.toString(pedidos.get(currentListItemIndex).getNro_ped());
                switch (item.getItemId()) {
                    case R.id.menu_send:
                        Toast.makeText(getActivity(), "Sending! "+currentListItemIndex, Toast.LENGTH_SHORT).show();
                        Log.v("NEO BULTO", "CREACION NUEVO BULTO");
                        tarea = new MyAsyncTask(getActivity());
                        tarea.execute("POST","CONFIRMAR_PEDIDO", nroPedido, null,null,null,null);
                        Log.v("NEO BULTO", "FIN CREACION NUEVO BULTO");
                        GetResultTask task = new GetResultTask();
                        task.execute();
                        mode.finish(); // Action picked, so close the contextual menu
                        return true;
                    case R.id.menu_edit:
                        final Intent intent = new Intent(getActivity(), BultosActivity.class);
                            intent.putExtra(Intent.EXTRA_TEXT, nroPedido);
                            startActivity(intent);
                        mode.finish(); // Action picked, so close the contextual menu
                        return true;
                    case R.id.menu_delete:
                        // Trigger the deletion here
                        Toast.makeText(getActivity(), "Deleting! "+currentListItemIndex, Toast.LENGTH_SHORT).show();
                        mode.finish(); // Action picked, so close the contextual menu
                        return true;
                    default:
                        return false;
                }
            }

            // Called when the user exits the action mode
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                currentActionMode = null; // Clear current action mode
            }
        };

*/

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.v(LOG_TAG, "ESTO CREATE FRAGMENT");
            updateResults();

        }

        public void updateResults() {
            String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            rolUsuario = result;
            rolUsuario = "EMBALADOR"; //PRUEBA
            GetResultTask task = new GetResultTask();

            task.execute();

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pedidos, container, false);

            pedidos = new ArrayList<Pedido>();


            Log.v(LOG_TAG,"llenado pedidos");

            listPedidos = (ListView) rootView.findViewById(R.id.result_pedidos);

            listPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                if( rolUsuario.equals("DESPACHADOR")) {
                    final Intent intent = new Intent(getActivity(), MapaActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(pedidos.get(position).getNro_ped()));
                    startActivity(intent);
                }
                if( rolUsuario.equals("EMBALADOR")) {
                    final Intent intent = new Intent(getActivity(), BultosActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(pedidos.get(position).getNro_ped()));
                    startActivity(intent);
                }
                }

            });
/*
            listPedidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                    return onLongListItemClick(v,pos,id);
                }
            });

            // Setup contextual action mode when item is clicked
            listPedidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (currentActionMode != null) { return false; }
                    currentListItemIndex = position;
                    listPedidos.setItemChecked(currentListItemIndex, true);
                    currentActionMode = getActivity().startActionMode(modeCallBack);
                    view.setSelected(true);
                    return true;
                }
            });
            */

            registerForContextMenu(listPedidos);

            return rootView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String nroPedido;
            currentListItemIndex = info.position;
            nroPedido = Integer.toString(pedidos.get(currentListItemIndex).getNro_ped());
            switch (item.getItemId()) {
                case R.id.save:
                    Toast.makeText(getActivity(), "Sending! "+currentListItemIndex, Toast.LENGTH_SHORT).show();
                    Log.v("NEO BULTO", "CREACION NUEVO BULTO");
                    tarea = new MyAsyncTask(getActivity());
                    tarea.execute("POST","CONFIRMAR_PEDIDO", nroPedido, null,null,null,null);
                    Log.v("NEO BULTO", "FIN CREACION NUEVO BULTO");
                    GetResultTask task = new GetResultTask();
                    task.execute();
                case R.id.edit:
                    final Intent intent = new Intent(getActivity(), BultosActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, nroPedido);
                    startActivity(intent);
                    //editNote(info.id);

                    return true;
                case R.id.delete:
                    Toast.makeText(getActivity(), "Sending Delete! "+info.id, Toast.LENGTH_SHORT).show();
                    //deleteNote(info.id);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        /*

        protected boolean onLongListItemClick(View v, int pos, long id) {
            Log.i(LOG_TAG, "onLongListItemClick id=" + id);
            return true;
        }
*/
        class GetResultTask extends AsyncTask<Void, Void, String []> {

            @Override
            protected String[] doInBackground(Void... params) {
                //String resultString = Utility.getJsonStringFromNetwork();
                Log.v(LOG_TAG, "INICIA PEDIDOS");
                Log.v(LOG_TAG, "ROL: "+ rolUsuario);
                String resultString="";
                //resultString = UtilityWarehouseApp.getJsonStringFromNetwork("PEDIDOS", "PED_PENDIENTES", "", "");

                if( rolUsuario.equals("DESPACHADOR")) {
                    resultString = UtilityWarehouseApp.getJsonStringFromNetwork("PEDIDOS", "PED_PENDIENTES", "", "");
                }

                if( rolUsuario.equals("EMBALADOR")) {
                    resultString = UtilityWarehouseApp.getJsonStringFromNetwork("PEDIDOS", "PED_EMBALAR", "", "");
                }

                Log.v(LOG_TAG, "TERMINA FILTRO");
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
                pedidos.clear();
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
                listPedidos.setAdapter(new PedidoAdapterB(getActivity(), pedidos));
         //       pedidoAdapter = new PedidoAdapterB(getActivity(),pedidos);

            }
        }
    }
}
