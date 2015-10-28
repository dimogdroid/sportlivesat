package difed.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import difed.entidades.Partidos;
import difed.entidades.FechaPartidos;
import difed.soccersat.R;

public class HorariosAdapter extends BaseExpandableListAdapter {

    Context context;
    LayoutInflater inflater;
    Typeface tf_regular;

    ArrayList<FechaPartidos> fechaPartidos;

    public ArrayList<FechaPartidos> getFechaPartidos() {
        return fechaPartidos;
    }

    public void setFechaPartidos(ArrayList<FechaPartidos> fechaPartidos) {
        this.fechaPartidos = fechaPartidos;
    }

    public HorariosAdapter(Context context,
                           ArrayList<FechaPartidos> fechaPartidos) {
        this.context = context;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fechaPartidos = fechaPartidos;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return fechaPartidos.get(groupPosition).getLpartidos().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View row = convertView;
        Hijo hijo = null;

        Partidos p = (Partidos) getChild(groupPosition, childPosition);


        if (row == null) {
            hijo = new Hijo();

            row = inflater.inflate(R.layout.listadodias_item, parent, false);


            hijo.dato_hora = (TextView) row.findViewById(R.id.dato_hora);
            hijo.dato_imagen = (ImageView) row.findViewById(R.id.dato_imagen);
            hijo.dato_frase = (TextView) row.findViewById(R.id.dato_frase);
            hijo.dato_frase2 = (TextView) row.findViewById(R.id.dato_frase2);
            row.setTag(hijo);
        } else {
            hijo = (Hijo) row.getTag();
        }

        tf_regular = Typeface.createFromAsset(context.getAssets(), "fonts/reloj.ttf");
        hijo.dato_hora.setTypeface(tf_regular);
        hijo.dato_hora.setText(p.getHora());

        Picasso.with(context).load(p.getImagem_png()).into(hijo.dato_imagen);

        hijo.dato_frase.setText(p.getFrase());

        if (p.getFrase2().equalsIgnoreCase("") || (p.getFrase2().isEmpty())){
            hijo.dato_frase2.setVisibility(View.GONE);
        }else{
            hijo.dato_frase2.setVisibility(View.VISIBLE);
            hijo.dato_frase2.setText(p.getFrase2());
        }


        return row;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return fechaPartidos.get(groupPosition).getLpartidos().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return fechaPartidos.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        if (fechaPartidos == null) {
            return 0;
        } else {
            return fechaPartidos.size();
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        // TODO Auto-generated method stub


        super.onGroupExpanded(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parentView) {

        FechaPartidos partido = (FechaPartidos) getGroup(groupPosition);

        View view = convertView;
        Padre padre = null;

//			Animation animation = null;
//			animation = AnimationUtils.loadAnimation(context, R.anim.needed);


        if (view == null) {
            // new Padre
            padre = new Padre();
            // inflate
            view = inflater.inflate(R.layout.listadodias_parent, parentView, false);
            //view.startAnimation(animation);
            //animation = null;

            // findViewById en padre
            padre.rtlpadre = (RelativeLayout) view.findViewById(R.id.rtlpadre);
            padre.imgletra = (ImageView) view.findViewById(R.id.imgletra);
            padre.fecha = (TextView) view.findViewById(R.id.fecha);
            padre.items = (TextView) view.findViewById(R.id.items);

            // view.setTag
            view.setTag(padre);

        } else {
            padre = (Padre) view.getTag();
        }

        padre.fecha.setText(partido.getFecha());

        //---LETRAS
        if (partido.getFecha().startsWith("L")) {
            padre.imgletra.setImageResource(R.drawable.lunes);
        }
        if (partido.getFecha().startsWith("M")) {
            padre.imgletra.setImageResource(R.drawable.martes);
        }
        if (partido.getFecha().startsWith("J")) {
            padre.imgletra.setImageResource(R.drawable.jueves);
        }
        if (partido.getFecha().startsWith("V")) {
            padre.imgletra.setImageResource(R.drawable.viernes);
        }
        if (partido.getFecha().startsWith("S")) {
            padre.imgletra.setImageResource(R.drawable.sabado);
        }
        if (partido.getFecha().startsWith("F")) {
            padre.imgletra.setImageResource(R.drawable.friday);
        }
        if (partido.getFecha().startsWith("D")) {
            padre.imgletra.setImageResource(R.drawable.domingo);
        }
        if (partido.getFecha().startsWith("T")) {
            padre.imgletra.setImageResource(R.drawable.tuesday);
        }
        if (partido.getFecha().startsWith("W")) {
            padre.imgletra.setImageResource(R.drawable.wednesday);
        }
        //----

        if (isExpanded) {
            padre.rtlpadre.setBackgroundColor(Color.parseColor("#fff4c9"));
            padre.imgletra.setBackgroundColor(Color.parseColor("#fff4c9"));
        } else {
            padre.rtlpadre.setBackgroundColor(Color.parseColor("#FFFFFF"));
            padre.imgletra.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        padre.items.setText(getChildrenCount(groupPosition) + " items");

        return view;


    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }


    static class Hijo {
        TextView dato_hora;
        ImageView dato_imagen;
        TextView img_png;
        TextView dato_frase;
        TextView dato_frase2;
    }

    static class Padre {
        RelativeLayout rtlpadre;
        ImageView imgletra;
        TextView fecha;
        TextView items;
    }
}
