package difed.soccersat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
public class TestAsiaViewAdapter extends RecyclerView.Adapter<TestAsiaViewAdapter.AnimeViewHolder> {

    List<Object> contents;


    private static Context context = null;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestAsiaViewAdapter(List<Object> contents) {
        this.contents = contents;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView txttitulo;
        public TextView txtpais;
        Typeface tf_regular;
        public ImageView imgcard;



        public AnimeViewHolder(View v) {
            super(v);
            this.cardView = (CardView) v.findViewById(R.id.card_view_small);
            this.txttitulo = (TextView) v.findViewById(R.id.txttitulo);
            tf_regular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/fuente.ttf");
            this.txttitulo.setTypeface(tf_regular);

            this.imgcard = (ImageView) v.findViewById(R.id.imgcard);

            this.txtpais = (TextView) v.findViewById(R.id.pais);

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

        Bitmap icon;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent =  new Intent(context, ListaEventos.class);
                intent.putExtra("pais", holder.txtpais.getText().toString());
                intent.putExtra("nombrepais", holder.txttitulo.getText().toString());
                intent.putExtra("colorfondo",5);
                context.startActivity(intent);
            }

        });


        //holder.txttitulo.setTextSize(25);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 3;
        options.inPurgeable = true;

        switch ((position)) {
            case 0:
                holder.txttitulo.setText(context.getString(R.string.todos));
                holder.txtpais.setText("asiafrica");
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.asiafrica, options);
                holder.imgcard.setImageBitmap(icon);
               // holder.imgcard.setImageResource(R.drawable.asiafrica);
                break;
            case 1:
                holder.txttitulo.setText(context.getString(R.string.afc));
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.afc, options);
                holder.imgcard.setImageBitmap(icon);
              //  holder.imgcard.setImageResource(R.drawable.afc);
                holder.txtpais.setText("afc");
                break;
            case 2:
                holder.txttitulo.setText(context.getString(R.string.caf));
              //  holder.imgcard.setImageResource(R.drawable.caf);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.caf, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("caf");
                break;
            case 3:
                holder.txttitulo.setText(context.getString(R.string.australia));
               // holder.imgcard.setImageResource(R.drawable.australia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.australia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("australia");
                break;
            case 4:
                holder.txttitulo.setText(context.getString(R.string.china));
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.china, options);
                holder.imgcard.setImageBitmap(icon);
                // holder.imgcard.setImageResource(R.drawable.china);
                holder.txtpais.setText("china");
                break;
            case 5:
                holder.txttitulo.setText(context.getString(R.string.japon));
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.japon, options);
                holder.imgcard.setImageBitmap(icon);
                //holder.imgcard.setImageResource(R.drawable.japon);
                holder.txtpais.setText("japon");
                break;
            case 6:
                holder.txttitulo.setText(context.getString(R.string.marruecos));
                //holder.imgcard.setImageResource(R.drawable.marruecos);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.marruecos, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("marruecos");
                break;
            case 7:
                holder.txttitulo.setText(context.getString(R.string.arabia));
                holder.txttitulo.setTextSize(22);
                //holder.imgcard.setImageResource(R.drawable.arabia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.arabia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("arabia");
                break;
            case 8:
                holder.txttitulo.setText(context.getString(R.string.africasur));
                //holder.imgcard.setImageResource(R.drawable.sudafrica);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.sudafrica, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("africasur");
                break;
            case 9:
                holder.txttitulo.setText(context.getString(R.string.coreasur));
                holder.txttitulo.setTextSize(22);
                //holder.imgcard.setImageResource(R.drawable.corea);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.corea, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("coreasur");
                break;


        }
    }



}