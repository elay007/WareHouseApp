package com.example.dgutierrez.warehouse;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        Button boton_login;
        EditText edt_usuario,edt_password;
        TextView txv_mensaje;
        String idUsuario, nombre, rol, login, pass, cad="DES";
        int err;



        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);

            boton_login = (Button) rootView.findViewById(R.id.btn_login);
            edt_password = (EditText) rootView.findViewById(R.id.edt_password);
            edt_usuario = (EditText) rootView.findViewById(R.id.edt_usuario);
            txv_mensaje = (TextView) rootView.findViewById(R.id.txv_mensaje);

            boton_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    login = edt_usuario.getText().toString();
                    pass = edt_password.getText().toString();

                    new GetResultTask().execute();

                }


            });

            return rootView;
        }

        class GetResultTask extends AsyncTask<Void, Void, String []>
        {
            private ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Intentando loguear...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String[] doInBackground(Void... params)
            {
                String resultString = UtilityWarehouseApp.getJsonStringFromNetwork("USUARIOS", "LOGIN", login, pass);
                Log.v(LOG_TAG, resultString);

                try
                {
                    //return UtilityWarehouseApp.parseLoginJson(resultString);
                    for (String result : UtilityWarehouseApp.parseLoginJson(resultString)) {
                        if (result == "No DATA") {
                            break;
                        }
                        String aux[] = result.trim().split("\\|");

                        idUsuario = aux[0];
                        nombre = aux[1];
                        rol = aux[2];

                    }

                    if(rol != null && !rol.isEmpty())
                    {
                        if (rol.equals("DES")) {
                            final Intent intent = new Intent(getActivity(), PedidosActivity.class);
                           // finish();
                            startActivity(intent);
                        }
                        if (rol.equals("EMB")) {
                            final Intent intent = new Intent(getActivity(), PedidosActivityB.class);
                            intent.putExtra(Intent.EXTRA_TEXT, "EMBALADOR");
                            // finish();
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        err=0;
                    }
                    return null;

                } catch (JSONException e)
                {
                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[] {"No DATA"};
                }
            }

            @Override
            protected void onPostExecute(String[] strings) {
                pDialog.dismiss();
/*
                if (err==0)
                {
                    txv_mensaje.setText("La datos no son validos.");
                }
*/
            }

        }
    }



}
