package com.example.dgutierrez.warehouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dmancilla on 14/08/2015.
 */
public class BultoAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList <Bulto> bultos;

    public BultoAdapter(Context context, ArrayList<Bulto> bultos) {
        this.context = context;
        this.bultos = bultos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int auxWidth;
        View listView;

        if (convertView == null) {

            listView = new View(context);

            listView = inflater.inflate(R.layout.pedido_listview, null);

        } else

        {
            listView = (View) convertView;
        }


        TextView tv_nro = (TextView) listView.findViewById(R.id.lv_nro_ped );

        tv_nro.setText("Bulto Nro. "+Integer.toString(bultos.get(position).getNroBulto()));

        TextView tv_descr = (TextView) listView.findViewById(R.id.lv_tienda );

        tv_descr.setText("");

        TextView tv_estado = (TextView) listView.findViewById(R.id.lv_estado );

        tv_estado.setText(bultos.get(position).getEstado());


        listView.setBackgroundResource(R.drawable.custom_selector);

        return listView;
    }

    @Override
    public int getCount() {
        return bultos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void clear()
    {
        bultos.clear();
        notifyDataSetChanged();
    }
}
