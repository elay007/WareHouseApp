package com.example.dgutierrez.warehouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dgutierrez on 12/08/2015.
 */

public class TallaAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Talla> tallas;

    public TallaAdapter(Context context,ArrayList <Talla> tallas) {
        this.context = context;
        this.tallas = tallas;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int auxWidth;
        View listView;
        final int pos;
        final  TextView tv_talla_desp;

        if (convertView == null) {

            listView = new View(context);

            listView = inflater.inflate(R.layout.talla_listview, null);

        } else

        {
            listView = (View) convertView;
        }

        pos=position;

        TextView tv_talla = (TextView) listView.findViewById(R.id.lv_talla );

        tv_talla.setText(tallas.get(position).getTalla());

        TextView tv_talla_ped = (TextView) listView.findViewById(R.id.lv_talla_ped );

        tv_talla_ped.setText(Integer.toString(tallas.get(position).getTalla_ped()));

        tv_talla_desp = (TextView) listView.findViewById(R.id.lv_talla_desp );

        tv_talla_desp.setText(Integer.toString(tallas.get(position).getTalla_desp()));

        tv_talla_desp.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    if (!tv_talla_desp.getText().toString().equals(""))
                    {tallas.get(position).setTalla_desp(Integer.parseInt(tv_talla_desp.getText().toString()));}
                    else
                    {tallas.get(position).setTalla_desp(0);}
                }
            }
        });



        listView.setBackgroundResource(R.drawable.custom_selector);

        return listView;
    }

    @Override
    public int getCount() {
        return tallas.size();
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
        tallas.clear();
        notifyDataSetChanged();
    }
}
