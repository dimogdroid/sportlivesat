package difed.soccersat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestEuropaViewAdapter extends RecyclerView.Adapter<TestEuropaViewAdapter.AnimeViewHolder> {

    List<Object> contents;


    private static Context context = null;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestEuropaViewAdapter(List<Object> contents) {
        this.contents = contents;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView txttitulo;
        public TextView txtpais;
        public ImageView imgcard;
        Typeface tf_regular;



        public AnimeViewHolder(View v) {
            super(v);
            this.cardView = (CardView) v.findViewById(R.id.card_view_small);
            this.txttitulo = (TextView) v.findViewById(R.id.txttitulo);
            tf_regular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/fuente.ttf");
            this.txttitulo.setTypeface(tf_regular);

            this.txtpais = (TextView) v.findViewById(R.id.pais);

            this.imgcard = (ImageView) v.findViewById(R.id.imgcard);

            context = itemView.getContext();



        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = null;

    /*    switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_card_big, viewGroup, false);
                return new AnimeViewHolder(view) {
                };
            }
            case TYPE_CELL: {/**/
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_card_small, viewGroup, false);
                return new AnimeViewHolder(view) {
                };
           // }
       // }
       // return null;
    }


    @Override
    public void onBindViewHolder(final AnimeViewHolder  holder, int position) {



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent =  new Intent(context, ListaEventos.class);
                intent.putExtra("pais", holder.txtpais.getText().toString());
                intent.putExtra("nombrepais", holder.txttitulo.getText().toString());
                intent.putExtra("colorfondo",3);

               // Bundle extras = intent.getExtras();
              //  extras.putString("pais", holder.txtpais.getText().toString());

                context.startActivity(intent);
            }

        });


        holder.txttitulo.setTextSize(30);
        Bitmap icon;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 3;
        options.inPurgeable = true;
        switch ((position)) {
           // case 0:
               // holder.txttitulo.setText(context.getString(R.string.eurocopa));
               // holder.txtpais.setText("eurocopa");
                //holder.imgcard.setImageResource(R.drawable.eurocopa);
              //  icon = BitmapFactory.decodeResource(context.getResources(),
              //          R.drawable.eurocopa, options);
               // holder.imgcard.setImageBitmap(icon);
            //    break;
            case 0:
                holder.txttitulo.setTextSize(20);
                holder.txttitulo.setText(context.getString(R.string.champion));
                holder.txtpais.setText("champion");
               // holder.imgcard.setImageResource(R.drawable.champion);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.champion, options);
                holder.imgcard.setImageBitmap(icon);
                break;
            case 1:
                holder.txttitulo.setTextSize(22);
                holder.txttitulo.setText(context.getString(R.string.europaleague));
                holder.txtpais.setText("europaleague");
               // holder.imgcard.setImageResource(R.drawable.uefa);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.uefa, options);
                holder.imgcard.setImageBitmap(icon);
                break;
            case 2:
                holder.txttitulo.setText(context.getString(R.string.espana));
                holder.txtpais.setText("esp");
                //holder.imgcard.setImageResource(R.drawable.esp);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.esp, options);
                holder.imgcard.setImageBitmap(icon);
                break;
            case 3:
                holder.txttitulo.setText(context.getString(R.string.uk));
                holder.txtpais.setText("uk");
                //holder.imgcard.setImageResource(R.drawable.ukireland);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ukireland, options);
                holder.imgcard.setImageBitmap(icon);
                break;
            case 4:
                holder.txttitulo.setText(context.getString(R.string.portugal));
                //holder.imgcard.setImageResource(R.drawable.portugal);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.portugal, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("por");
                break;
            case 5:
                holder.txttitulo.setTextSize(23);
                holder.txttitulo.setText(context.getString(R.string.alemania));
               // holder.imgcard.setImageResource(R.drawable.alemania);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.alemania, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("ger");
                break;
            case 6:
                holder.txttitulo.setText(context.getString(R.string.francia));
               // holder.imgcard.setImageResource(R.drawable.francia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.francia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("fra");
                break;
            case 7:
                holder.txttitulo.setText(context.getString(R.string.italia));
               // holder.imgcard.setImageResource(R.drawable.italia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.italia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("ita");
                break;
            case 8:
                holder.txttitulo.setText(context.getString(R.string.holanda));
               // holder.imgcard.setImageResource(R.drawable.holanda);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.holanda, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("ned");
                break;
            case 9:
                holder.txttitulo.setText(context.getString(R.string.polonia));
                //holder.imgcard.setImageResource(R.drawable.polonia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.polonia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("pol");
                break;
            case 10:
                holder.txttitulo.setText(context.getString(R.string.grecia));
                //holder.imgcard.setImageResource(R.drawable.grecia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.grecia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("gre");
                break;
            case 11:
                holder.txttitulo.setText(context.getString(R.string.rusia));
              //  holder.imgcard.setImageResource(R.drawable.rusia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.rusia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("urusk");
                break;
            case 12:
                holder.txttitulo.setText(context.getString(R.string.turquia));
                //holder.imgcard.setImageResource(R.drawable.turquia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.turquia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("tur");
                break;
            case 13:
                holder.txttitulo.setText(context.getString(R.string.austria));
               // holder.imgcard.setImageResource(R.drawable.austria);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.austria, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("aut");
                break;

        }
    }


}