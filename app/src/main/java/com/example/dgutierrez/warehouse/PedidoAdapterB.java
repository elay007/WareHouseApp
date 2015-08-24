package com.example.dgutierrez.warehouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dgutierrez on 11/04/2015.
 */
public class PedidoAdapterB extends BaseAdapter {
    private Context context;
    private final ArrayList <Pedido> pedidos;

    public PedidoAdapterB(Context context, ArrayList<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
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


        TextView tv_nro_ped = (TextView) listView.findViewById(R.id.lv_nro_ped );

        tv_nro_ped.setText(Integer.toString(pedidos.get(position).getNro_ped()));

        TextView tv_tienda = (TextView) listView.findViewById(R.id.lv_tienda );

        tv_tienda.setText(pedidos.get(position).getTienda());

        TextView tv_estado = (TextView) listView.findViewById(R.id.lv_estado );

        tv_estado.setText(pedidos.get(position).getEstado());


        listView.setBackgroundResource(R.drawable.custom_selector);

        return listView;
    }

    @Override
    public int getCount() {
        return pedidos.size();
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
        pedidos.clear();
        notifyDataSetChanged();
    }
}
