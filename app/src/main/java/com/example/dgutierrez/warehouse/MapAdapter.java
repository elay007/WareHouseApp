package com.example.dgutierrez.warehouse;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by dgutierrez on 30/03/2015.
 */
public class MapAdapter extends BaseAdapter {
    private Context context;
    private final Bloque[] bloques;

    public MapAdapter(Context context, Bloque[] bloques) {
        this.context = context;
        this.bloques = bloques;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int auxWidth;
        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.bloque, null);

            TextView title_textView = (TextView) gridView.findViewById(R.id.title_pedido);


            TextView textView = (TextView) gridView.findViewById(R.id.label);

            textView.setText(bloques[position].toString());


            int height = gridView.getHeight();

            textView.setHeight(115);
            textView.setWidth(250);


            if (position==0 || position==1 || position==2 || position==3)
            {
                textView.setBackgroundColor(Color.TRANSPARENT);
                textView.setGravity(Gravity.BOTTOM|Gravity.CENTER);
                textView.setHeight(65);
            }
            else if (position==4 || position==8 || position==12 || position==16)
            {
                textView.setBackgroundColor(Color.TRANSPARENT);
                textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
            }
            else
            {

                if (bloques[position].getSeleccionado()=="S")
                    textView.setBackgroundColor(Color.argb(255,255,243,28));
                else
                    textView.setBackgroundColor(Color.argb(255,255,163,169));
            }
           // ImageView flag = (ImageView) gridView .findViewById(R.id.flag);

  //          String mobile = bloques[position];
/*
            if (mobile.equals("Greece")) {
                flag.setImageResource(R.drawable.greekflag);
            } else if (mobile.equals("Germany")) {
                flag.setImageResource(R.drawable.germanflag);
            } else if (mobile.equals("Italy")) {
                flag.setImageResource(R.drawable.italianflag);
            } else {
                flag.setImageResource(R.drawable.britishflag);
            }
*/
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return bloques.length;
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

