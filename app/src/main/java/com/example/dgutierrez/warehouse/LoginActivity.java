package com.example.dgutierrez.warehouse;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
        Button boton_login;
        EditText edt_usuario,edt_password;
        TextView txv_mensaje;

        public PlaceholderFragment() {
        }

        public boolean validarUsuario(String usu, String pass)
        {
            if (usu.equals("admin") && pass.equals("123"))
            {
               return  true;
            }
            return false;
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

                    if (validarUsuario(edt_usuario.getText().toString(),edt_password.getText().toString()))
                    {
                        txv_mensaje.setText("");
                        final Intent intent = new Intent(getActivity(), PedidosActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        txv_mensaje.setText("La datos no son validos.");
                    }

                }


            });

            return rootView;
        }
    }
}
