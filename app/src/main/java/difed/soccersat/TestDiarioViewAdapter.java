package difed.soccersat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import difed.util.Publicidad;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestDiarioViewAdapter extends RecyclerView.Adapter<TestDiarioViewAdapter.AnimeViewHolder> {

    List<Object> contents;


    private static Context context = null;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    String titulo;
    String pais;

    Date date;

    public TestDiarioViewAdapter(List<Object> contents) {
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
            this.txtpais = (TextView) v.findViewById(R.id.pais);
            tf_regular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/fuente.ttf");
            this.txttitulo.setTypeface(tf_regular);
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
        view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_card_small, viewGroup, false);
                return new AnimeViewHolder(view) {
                };

    }


    @Override
    public void onBindViewHolder(final AnimeViewHolder  holder, int position) {


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent =  new Intent(context, ListaEventos.class);
                intent.putExtra("nombrepais", holder.txttitulo.getText().toString());
                intent.putExtra("pais", holder.txtpais.getText().toString());
                intent.putExtra("colorfondo",1);
                context.startActivity(intent);
            }

        });

        Calendar fecha = Calendar.getInstance();

        fecha.add(Calendar.DATE, position);

        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String sDia = null;
        if (dia <= 9) {
            sDia = "0" + dia;
        } else {
            sDia = String.valueOf(dia);
        }
        mes++;
        String sMes = null;
        if (mes <= 9) {
            sMes = "0" + mes;
        } else {
            sMes = String.valueOf(mes);
        }

        titulo = sDia + "/" + sMes + "/" + anio;
        pais = sDia + sMes + anio;
        holder.txttitulo.setText(titulo);
        holder.txtpais.setText(pais);

        SimpleDateFormat sdf = new SimpleDateFormat("ccc");
        date = fecha.getTime();
        String diaSemana=sdf.format(date);
       /* try {
            date= sdf.parse(sDia + "-" + sMes + "-" + anio);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        //  Date date = sdf.parse(dateInString);

        if (date!=null) {
            diaSemana = date.toString();
        }*/


        //---LETRAS

        Bitmap icon;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 3;
        options.inPurgeable = true;

        if (diaSemana.startsWith("L")) {
            holder.imgcard.setImageResource(R.drawable.lunes);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.lunes, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("M")) {
            holder.imgcard.setImageResource(R.drawable.martes);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.martes, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("J")) {
            holder.imgcard.setImageResource(R.drawable.jueves);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.jueves, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("V")) {
            holder.imgcard.setImageResource(R.drawable.viernes);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.viernes, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("S")) {
            holder.imgcard.setImageResource(R.drawable.sabado);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.sabado, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("F")) {
            holder.imgcard.setImageResource(R.drawable.friday);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.friday, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("D")) {
            holder.imgcard.setImageResource(R.drawable.domingo);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.domingo, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("T")) {
            holder.imgcard.setImageResource(R.drawable.tuesday);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.tuesday, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        if (diaSemana.startsWith("W")) {
            holder.imgcard.setImageResource(R.drawable.wednesday);
//            icon = BitmapFactory.decodeResource(context.getResources(),
//                    R.drawable.wednesday, options);
//            holder.imgcard.setImageBitmap(icon);
        }
        //----


//        if (fecha.get(Calendar.DAY_OF_WEEK)==1) { //Domingo
//            if ( (Locale.getDefault().getLanguage().equalsIgnoreCase("es")) ||
//                    (Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) )  {
//                holder.imgcard.setImageResource(R.drawable.domingo);
//            }else {
//                holder.imgcard.setImageResource(R.drawable.sabado);
//            }
//        }
//        if (fecha.get(Calendar.DAY_OF_WEEK)==2)  { //lunes
//            if ( (Locale.getDefault().getLanguage().equalsIgnoreCase("es")) ||
//                    (Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) )  {
//                holder.imgcard.setImageResource(R.drawable.lunes);
//            }else {
//                holder.imgcard.setImageResource(R.drawable.martes);
//            }
//        }
//        if (fecha.get(Calendar.DAY_OF_WEEK)==3)  { //
//            if ( (Locale.getDefault().getLanguage().equalsIgnoreCase("es")) ||
//                    (Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) )  {
//                holder.imgcard.setImageResource(R.drawable.martes);
//            }else {
//                holder.imgcard.setImageResource(R.drawable.tuesday);
//            }
//        }
//        if (fecha.get(Calendar.DAY_OF_WEEK)==4)  { //
//            if ( (Locale.getDefault().getLanguage().equalsIgnoreCase("es")) ||
//                    (Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) )  {
//                holder.imgcard.setImageResource(R.drawable.martes);
//            }else {
//                holder.imgcard.setImageResource(R.drawable.tuesday);
//            }
//            holder.imgcard.setImageResource(R.drawable.miercoles);
//        }
//        if (fecha.get(Calendar.DAY_OF_WEEK)==5)  { //
//            holder.imgcard.setImageResource(R.drawable.jueves);
//        }
//        if (fecha.get(Calendar.DAY_OF_WEEK)==6)  { //
//            holder.imgcard.setImageResource(R.drawable.viernes);
//        }
//        if (fecha.get(Calendar.DAY_OF_WEEK)==7)  { //
//            holder.imgcard.setImageResource(R.drawable.sabado);
//        }



        //holder.imgcard.setImageResource(R.drawable.icono_calendario);

    }


}