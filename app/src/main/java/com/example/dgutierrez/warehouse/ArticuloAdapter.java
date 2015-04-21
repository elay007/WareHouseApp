package com.example.dgutierrez.warehouse;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dgutierrez on 12/04/2015.
 */
public class ArticuloAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Articulo> articulos;
    private View view_activity;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    public ArticuloAdapter(Context context,ArrayList <Articulo> articulos, View v) {
        this.context = context;
        this.articulos = articulos;
        this.view_activity=v;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int auxWidth;
        View listView;
        final int pos;
        final  TextView tv_cant_desp;

        if (convertView == null) {

            listView = new View(context);

            listView = inflater.inflate(R.layout.articulo_listview, null);

        } else
        {
            listView = (View) convertView;
        }

        ImageView IMG = (ImageView)listView.findViewById(R.id.lv_imagen_articulo);
        pos=position;

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

        tv_cant_desp = (TextView) listView.findViewById(R.id.lv_cant_desp);

        tv_cant_desp.setText(Integer.toString(articulos.get(position).getCant_desp()));

        tv_cant_desp.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    if (!tv_cant_desp.getText().toString().equals(""))
                    {articulos.get(position).setCant_desp(Integer.parseInt(tv_cant_desp.getText().toString()));}
                    else
                    {articulos.get(position).setCant_desp(0);}
                }
            }
        });


        IMG.setOnClickListener(new View.OnClickListener() {
            String s = articulos.get(pos).getPk_articulo();
            @Override
            public void onClick(View v) {

               // Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

                zoomImageList(v,s);

            }
        });

        listView.setBackgroundResource(R.drawable.custom_selector);

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

    private void zoomImageList(final View thumbView, String image) {

        mShortAnimationDuration = view_activity.getResources().getInteger(android.R.integer.config_shortAnimTime);

        Drawable d;

        d=null;

        try {

            d = Drawable.createFromStream(thumbView.getContext().getAssets().open(image + ".png"), null);

        } catch (IOException e)
        {
            try {
                d = Drawable.createFromStream(thumbView.getContext().getAssets().open("nd.png"), null);
            }
            catch (IOException e1)
            {}
        }

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final ImageView expandedImageView = (ImageView) view_activity.findViewById(R.id.expanded_image);
        expandedImageView.setImageDrawable(d);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);

        final FrameLayout layout_art= (FrameLayout) view_activity.findViewById(R.id.layout_articulos);

        layout_art.getGlobalVisibleRect(finalBounds,globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y,
                        startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(
                        ObjectAnimator.ofFloat(expandedImageView, View.X,
                                startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                                startBounds.top))
                        .with(ObjectAnimator.ofFloat(expandedImageView,
                                View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(expandedImageView,
                                View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

}

