package com.example.dgutierrez.warehouse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dgutierrez on 12/04/2015.
 */
public class ArticuloAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Articulo> articulos;

    public ArticuloAdapter(Context context,ArrayList <Articulo> articulos) {
        this.context = context;
        this.articulos = articulos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int auxWidth;
        View listView;

        if (convertView == null) {

            listView = new View(context);

            listView = inflater.inflate(R.layout.articulo_listview, null);

            ImageView IMG = (ImageView)listView.findViewById(R.id.lv_imagen_articulo);



            try {

                    Drawable d = Drawable.createFromStream(this.context.getAssets().open(articulos.get(position).getPk_articulo() + ".png"), null);
                    IMG.setImageDrawable(d);

            } catch (IOException e)
            {
                try {
                    Drawable d = Drawable.createFromStream(this.context.getAssets().open("nd.png"), null);
                    IMG.setImageDrawable(d);
                }
                catch (IOException e1)
                {}
            }


            TextView tv_pk_articulo = (TextView) listView.findViewById(R.id.lv_pk_articulo );

            tv_pk_articulo.setText(articulos.get(position).getPk_articulo());

            TextView tv_stock = (TextView) listView.findViewById(R.id.lv_stock );

            tv_stock.setText(Integer.toString(articulos.get(position).getStock()));

            TextView tv_cant_ped = (TextView) listView.findViewById(R.id.lv_cant_ped );

            tv_cant_ped.setText(Integer.toString(articulos.get(position).getCant_ped()));

            TextView tv_cant_desp = (TextView) listView.findViewById(R.id.lv_cant_desp);

            tv_cant_desp.setText(Integer.toString(articulos.get(position).getCant_desp()));


            listView.setBackgroundResource(R.drawable.custom_selector);

        } else {
            listView = (View) convertView;
        }

        return listView;
    }

    @Override
    public int getCount() {
        return articulos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}

