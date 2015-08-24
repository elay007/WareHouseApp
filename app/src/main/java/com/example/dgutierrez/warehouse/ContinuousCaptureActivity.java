package com.example.dgutierrez.warehouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ContinuousCaptureActivity extends Activity {
    private static final String TAG = ContinuousCaptureActivity.class.getSimpleName();
    private CompoundBarcodeView barcodeView;
    private MediaPlayer mPlayer = null;
    private ArrayList<ArticuloBulto> scanArticulosBulto;
    private static final String LOG_TAG =  ContinuousCaptureActivity.class.getSimpleName();
    private BeepManager beepManager;
    private DialogInterface.OnClickListener dialogCancelClick;
    private AlertDialog dialog;
    String nroPed, nroBulto;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String res = result.getText();
            Log.v(LOG_TAG, "ESCANEA");
            if (res != null && res.length() <= 9) {
                onPause();
                barcodeView.pause();//Pause preview
                beepManager.playBeepSoundAndVibrate();

                //Added preview of scanned barcode
                ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
                imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));

                Log.v(LOG_TAG, "ESCANEA 2");
                barcodeView.setStatusText(res);
                //Add dmancilla
                Log.v(LOG_TAG, "ESCANEA 3");
                /*if(!mPlayer.isPlaying())
                {
                    mPlayer.start();
                }*/
                Log.v(LOG_TAG, "ESCANEA 4 Art:"+res.substring(0,7)+" Mod:"+res.substring(7));
                //Lo adicionamos a la lista
                scanArticulosBulto.add(new ArticuloBulto(res.substring(0,7),Integer.parseInt(res.substring(7)),1,1));
                Log.v(LOG_TAG, "ESCANEA 5");

                //Fin Add dmancilla
                onResume();
                barcodeView.resume();//Resume scanning after pressing confirm button
            }



        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continuous_scan);

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(this);
        //-- begin
        scanArticulosBulto = new ArrayList<ArticuloBulto>();

        getGlobal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void done(View view) {
        Intent resultIntent = new Intent(this, ArticulosBultoActivity.class);
        resultIntent.putExtra("ArticulosExtra", scanArticulosBulto);
        //Prueba cambiar por parametros enviados por el intent
        resultIntent.putExtra(Intent.EXTRA_TEXT, nroBulto+"|"+nroPed);
        startActivity(resultIntent);
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
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
}

