package difed.soccersat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
public class TestAmericaViewAdapter extends RecyclerView.Adapter<TestAmericaViewAdapter.AnimeViewHolder> {

    List<Object> contents;


    private static Context context = null;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestAmericaViewAdapter(List<Object> contents) {
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



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent =  new Intent(context, ListaEventos.class);
                intent.putExtra("pais", holder.txtpais.getText().toString());
                intent.putExtra("nombrepais", holder.txttitulo.getText().toString());
                intent.putExtra("colorfondo",4);
                context.startActivity(intent);
            }

        });


        //holder.txttitulo.setTextSize(25);

        Bitmap icon;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 3;
        options.inPurgeable = true;
        switch ((position)) {
            case 0:
                holder.txttitulo.setText(context.getString(R.string.todos));
                //holder.imgcard.setImageResource(R.drawable.todoamerica);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.todoamerica, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("allamericas");
                break;
            case 1:
                holder.txttitulo.setTextSize(20);
                holder.txttitulo.setText(context.getString(R.string.copaamerica));
                // holder.imgcard.setImageResource(R.drawable.concacaf);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.copaamerica, options);
                holder.imgcard.setImageBitmap(icon);

                holder.txtpais.setText("copaamerica");
                break;
            case 2:
                holder.txttitulo.setText(context.getString(R.string.concacaf));
               // holder.imgcard.setImageResource(R.drawable.concacaf);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.concacaf, options);
                holder.imgcard.setImageBitmap(icon);

                holder.txtpais.setText("concacaf");
                break;
            case 3:
                holder.txttitulo.setText(context.getString(R.string.conmebol));
               // holder.imgcard.setImageResource(R.drawable.conmebol);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.conmebol, options);
                holder.imgcard.setImageBitmap(icon);

                holder.txtpais.setText("conmebol");
                break;
            case 4:
                holder.txttitulo.setText(context.getString(R.string.mls));
               // holder.imgcard.setImageResource(R.drawable.mls);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.mls, options);
                holder.imgcard.setImageBitmap(icon);

                holder.txtpais.setText("mls");
                break;
            case 5:
                holder.txttitulo.setText(context.getString(R.string.argentina));
               // holder.imgcard.setImageResource(R.drawable.argentina);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.argentina, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("arg");
                break;
            case 6:
                holder.txttitulo.setText(context.getString(R.string.brasil));
               // holder.imgcard.setImageResource(R.drawable.brasil);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.brasil, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("bra");
                break;
            case 7:
                holder.txttitulo.setText(context.getString(R.string.colombia));
               // holder.imgcard.setImageResource(R.drawable.colombia);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.colombia, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("col");
                break;
            case 8:
                holder.txttitulo.setText(context.getString(R.string.mexico));
               // holder.imgcard.setImageResource(R.drawable.mexico);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.mexico, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("mex");
                break;
            case 9:
                holder.txttitulo.setText(context.getString(R.string.nothamerica));
                holder.txttitulo.setTextSize(22);
                // holder.imgcard.setImageResource(R.drawable.mexico);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.nothamerica, options);
                holder.imgcard.setImageBitmap(icon);
                holder.txtpais.setText("nothamerica");
                break;


        }
    }


}