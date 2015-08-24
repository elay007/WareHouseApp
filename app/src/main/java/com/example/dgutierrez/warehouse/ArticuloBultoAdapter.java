package com.example.dgutierrez.warehouse;

//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
import android.content.Context;
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
import android.widget.TextView;

//import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dgutierrez on 12/04/2015.
 */
public class ArticuloBultoAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList <ArticuloBulto> articulosBulto;

    public ArticuloBultoAdapter(Context context, ArrayList<ArticuloBulto> articulosBulto) {
        this.context = context;
        this.articulosBulto = articulosBulto;
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

        tv_nro.setText("Art. "+articulosBulto.get(position).getArticulo());

        TextView tv_descr = (TextView) listView.findViewById(R.id.lv_tienda );

        tv_descr.setText("Bulto Nro. " + Integer.toString(articulosBulto.get(position).getNroBulto()));

        TextView tv_estado = (TextView) listView.findViewById(R.id.lv_estado );

        tv_estado.setText("Cant: "+Integer.toString(articulosBulto.get(position).getCantBulto()));

        listView.setBackgroundResource(R.drawable.custom_selector);

        return listView;
    }

    @Override
    public int getCount() {
        return articulosBulto.size();
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
        articulosBulto.clear();
        notifyDataSetChanged();
    }

}

