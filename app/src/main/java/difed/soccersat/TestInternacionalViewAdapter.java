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
public class TestInternacionalViewAdapter extends RecyclerView.Adapter<TestInternacionalViewAdapter.AnimeViewHolder> {

    List<Object> contents;


    private static Context context = null;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestInternacionalViewAdapter(List<Object> contents) {
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
                intent.putExtra("colorfondo",2);
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
        switch (position) {
            case 0:
                holder.txttitulo.setTextSize(25);
                holder.txttitulo.setText(context.getString(R.string.amistosos));
                holder.txtpais.setText("amistosos");
                //holder.imgcard.setImageResource(R.drawable.amistoso);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.amistoso, options);
                holder.imgcard.setImageBitmap(icon);
                break;
            case 1:
                holder.txttitulo.setTextSize(20);
                holder.txttitulo.setText(context.getString(R.string.todosmen));
                holder.txtpais.setText("todosint");
                //holder.imgcard.setImageResource(R.drawable.amistosomen);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.amistosomen, options);
                holder.imgcard.setImageBitmap(icon);
                break;
            case 2:
                holder.txttitulo.setTextSize(20);
                holder.txttitulo.setText(context.getString(R.string.todoswomen));
                holder.txtpais.setText("todoswomen");
                //holder.imgcard.setImageResource(R.drawable.amistosowomen);
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.amistosowomen, options);
                holder.imgcard.setImageBitmap(icon);
                break;

        }
    }


}