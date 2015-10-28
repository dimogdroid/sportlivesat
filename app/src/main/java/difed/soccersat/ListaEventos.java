package difed.soccersat;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import difed.entidades.Partidos;

import difed.api.Conexion;
import difed.api.ConnectionException;
import difed.entidades.Canales;
import difed.entidades.FechaPartidos;
import difed.entidades.Frecuencias;
import difed.entidades.PaisPartidos;
import difed.entidades.Partidos;
import difed.util.CustomLog;
import difed.util.HorariosAdapter;
import pl.polidea.view.ZoomView;

/**
 * Created by dgdavila on 24/09/2015.
 */
public class ListaEventos extends Activity {

    private final String[] array = {"Hello", "World", "Android", "is", "Awesome", "World", "Android", "is", "Awesome", "World", "Android", "is", "Awesome", "World", "Android", "is", "Awesome"};

    ExpandableListView expandableListView;
 //   DotsTextView dotsTextView;
 //   LinearLayout lytloading;
    LinearLayout lytcabecera;
    ImageView imgatras;
    TextView txttitulocab;
    public String pais;
    public String nombrePais;
    public int colorFondo =0;
    String sGmz = "0";
    Typeface tf_regular;

    Context context = this;

    private PaisPartidos paispartidos;
    private static PrincipalTask task;
    HorariosAdapter adapter;

    private ZoomView zoomView;
    LinearLayout main_container;

    int primeravezeventos;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        outState.putParcelable("listapartidos", paispartidos);
        outState.putString("pais", pais);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.container);

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.expandable_listview, null, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        zoomView = new ZoomView(this);
        zoomView.addView(v);

        main_container = (LinearLayout) findViewById(R.id.lytcontainer);
        main_container.addView(zoomView);


     //   setContentView(R.layout.expandable_listview);

        expandableListView = (ExpandableListView) findViewById(R.id.listado);

      //  dotsTextView = (DotsTextView) findViewById(R.id.dots);
      //  lytloading = (LinearLayout) findViewById((R.id.lytloading));
        lytcabecera = (LinearLayout) findViewById(R.id.lytcabecera);
        imgatras = (ImageView) findViewById(R.id.imgatras);
        txttitulocab = (TextView) findViewById(R.id.txttitulocab);
        tf_regular = Typeface.createFromAsset(getAssets(), "fonts/fuente.ttf");
        txttitulocab.setTypeface(tf_regular);

        if ((paispartidos == null) || (pais == null)) {
            if (savedInstanceState != null) {
                pais = savedInstanceState.getString("pais");
                nombrePais = savedInstanceState.getString("nombrepais");
                paispartidos = savedInstanceState
                        .getParcelable("listapartidos");
                colorFondo = savedInstanceState.getInt("colorfondo");
            } else {

                // lanzar task
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    pais = extras.getString("pais");
                    nombrePais =  extras.getString("nombrepais");
                    colorFondo =  extras.getInt("colorfondo");
                }
                paispartidos = new PaisPartidos();
                paispartidos.setPais(pais);
                task = new PrincipalTask();
                task.setActivity(this);
                task.execute(paispartidos);
            }
        }

        try {


            txttitulocab.setText(nombrePais);

            //Color de fondo
            if (colorFondo>0){
                if (colorFondo==1){
                    lytcabecera.setBackgroundColor(Color.parseColor("#4CAF50"));
                    expandableListView.setBackgroundColor(Color.parseColor("#bfffc1"));
                }
                if (colorFondo==2){
                    lytcabecera.setBackgroundColor(Color.parseColor("#303F9F"));
                    expandableListView.setBackgroundColor(Color.parseColor("#d1d7ff"));
                }
                if (colorFondo==3){
                    lytcabecera.setBackgroundColor(Color.parseColor("#00BCD4"));
                    expandableListView.setBackgroundColor(Color.parseColor("#cefaff"));
                }
                if (colorFondo==4){
                    lytcabecera.setBackgroundColor(Color.parseColor("#F44336"));
                    expandableListView.setBackgroundColor(Color.parseColor("#fce0de"));
                }
                if (colorFondo==5){
                    lytcabecera.setBackgroundColor(Color.parseColor("#CDDC39"));
                    expandableListView.setBackgroundColor(Color.parseColor("#fbffd3"));
                }
                if (colorFondo==6){
                    lytcabecera.setBackgroundColor(Color.parseColor("#673AB7"));
                    expandableListView.setBackgroundColor(Color.parseColor("#eee5ff"));
                }
                if (colorFondo==7){
                    lytcabecera.setBackgroundColor(Color.parseColor("#00BCD4"));
                    expandableListView.setBackgroundColor(Color.parseColor("#cefaff"));
                }
            }else{
                //todo Error solucionado
                expandableListView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }


            adapter = new HorariosAdapter(this,
                    (ArrayList<FechaPartidos>) paispartidos.getLpartidos());




            ExpandableListView listview = expandableListView;





            listview.setAdapter(adapter);

            listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Partidos selected = (Partidos) adapter.getChild(groupPosition,
                            childPosition);

                    selectItem(selected);
                    return true;
                }
            });

            imgatras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });


            SharedPreferences settings = getSharedPreferences("perfil",
                    Context.MODE_PRIVATE);
            primeravezeventos = settings.getInt("primeravezeventos", 0);
            if (primeravezeventos==0) {

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("primeravezeventos", 1);
                editor.commit();

                MostrarComoHacerZoom();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void MostrarComoHacerZoom() {

        final Intent intent;
        intent =  new Intent(this, AyudaZoomActivity.class);
        startActivity(intent);
    }

    private void selectItem(Partidos partido) {

        final Intent intent;
        intent =  new Intent(this, ListaCanales.class);
        intent.putExtra("listacanales", partido);
        intent.putExtra("nombrepais",nombrePais);
        intent.putExtra("colorfondo",colorFondo);

        startActivity(intent);

    }


    class PrincipalTask extends AsyncTask<PaisPartidos, Integer, PaisPartidos> {

        private Context activity;
        private ConnectionException.ConnectionError error;

        private String gmz;
        SweetAlertDialog pDialog;

        public void setActivity(Context activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {

        //    lytloading.setVisibility(View.VISIBLE);
        //    dotsTextView.showAndPlay();

            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText(getString(R.string.Cargando));
            pDialog.setCancelable(false);
            pDialog.show();

//           setProgressBarIndeterminateVisibility(true);
//
//            Toast toast1 = Toast.makeText(
//                    getApplicationContext(),
//                    getString(R.string.extrayendo), Toast.LENGTH_LONG);
//
//            toast1.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // if (values[0] == 0) {
            // activity.mProgressDialog
            // .setMessage(getString(R.string.extrayendo));
            // }
        }

        @Override
        protected PaisPartidos doInBackground(PaisPartidos... params) {
            // PaisPartidos c = params[0];

            // pais = c.getUrl();
            // gmz = c.getGmz();

            gmz = "";

            Conexion conexion = new Conexion(pais, gmz);

            InputStream resultado = null;

            BufferedReader br = null;
            String line = null;

            try {
                resultado = conexion.conectar(activity);

                br = new BufferedReader(new InputStreamReader(resultado,
                        "ISO-8859-1"));

                cogerGmz();

                boolean Encontrado = false;
                try {
                    if (br.read() == -1) {
                        CustomLog.error("MainActivity", "vacio");
                        resultado = null;
                    }

                    while (((line = br.readLine()) != null) && !Encontrado) {
                        if (line.indexOf("sport_head") > 0) {
                            Encontrado = true;
                        }
                    }

                    if (Encontrado) {
                        // continua
                    } else {
                        resultado = null;
                    }

                } catch (IOException e) {
                    CustomLog.error("MainActivity", e.getMessage());
                    resultado = null;
                }

            } catch (ConnectionException e) {
                resultado = null;
                CustomLog.error("MainActivity", e.getMessage());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                resultado = null;
                CustomLog.error("MainActivity", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (resultado != null) {

                String frase = null;

                Partidos partidos = new Partidos();
                FechaPartidos fpartidos = new FechaPartidos();
                Canales canalesPartido = new Canales();

                List<Partidos> listaPartidos = new ArrayList<Partidos>();
                List<FechaPartidos> lfechaPartidos = new ArrayList<FechaPartidos>();
                List<Canales> listaCanales;

                // Buscar la cadena de inicio

                // Progreso
                publishProgress(0);
                boolean Encontrado = true;

                try {
                    while (line.indexOf("images/corner_lb4.gif") <= 0) {

                        listaCanales = new ArrayList<Canales>();

                        // cogemos la fecha
                        // ejemplo: <h2 class="time_head">Friday, 4th
                        // November</h2>
                        if (line.indexOf("blockfix") < 0) { // se da al buscar
                            // ms
                            // de un partido en una
                            // fecha
                            Encontrado = false;
                            while (!Encontrado) {
                                if (line.indexOf("time_head") > 0) { // si
                                    // encontrado
                                    fpartidos = new FechaPartidos();
                                    listaPartidos = new ArrayList<Partidos>();
                                    String fecha = line.substring(
                                            line.indexOf("time_head>") + 10,
                                            line.indexOf("</h2"));

                                    if (fecha.indexOf("Monday")>=0){
                                        fecha = fecha.replaceAll("Monday",getString(R.string.lunes));
                                    }
                                    if (fecha.indexOf("Tuesday")>=0){
                                        fecha = fecha.replaceAll("Tuesday",getString(R.string.martes));
                                    }
                                    if (fecha.indexOf("Wednesday")>=0){
                                        fecha = fecha.replaceAll("Wednesday",getString(R.string.miercoles));
                                    }
                                    if (fecha.indexOf("Thursday")>=0){
                                        fecha = fecha.replaceAll("Thursday",getString(R.string.jueves));
                                    }
                                    if (fecha.indexOf("Friday")>=0){
                                        fecha = fecha.replaceAll("Friday",getString(R.string.viernes));
                                    }
                                    if (fecha.indexOf("Saturday")>=0){
                                        fecha = fecha.replaceAll("Saturday",getString(R.string.sabado));
                                    }
                                    if (fecha.indexOf("Sunday")>=0){
                                        fecha = fecha.replaceAll("Sunday",getString(R.string.domingo));
                                    }

                                    //Limpieza de sufijos
                                    //st
//                                    fecha = fecha.replaceAll("st"," ");
//                                    fecha = fecha.replaceAll("rd"," ");
//                                    fecha = fecha.replaceAll("nd"," ");
//                                    fecha = fecha.replaceAll("th"," ");

                                    //Meses
                                    if (fecha.indexOf("January")>=0){
                                        fecha = fecha.replaceAll("January",getString(R.string.enero));
                                    }
                                    if (fecha.indexOf("February")>=0){
                                        fecha = fecha.replaceAll("February",getString(R.string.febrero));
                                    }
                                    if (fecha.indexOf("March")>=0){
                                        fecha = fecha.replaceAll("March",getString(R.string.marzo));
                                    }
                                    if (fecha.indexOf("April")>=0){
                                        fecha = fecha.replaceAll("April",getString(R.string.abril));
                                    }
                                    if (fecha.indexOf("May")>=0){
                                        fecha = fecha.replaceAll("May",getString(R.string.mayo));
                                    }
                                    if (fecha.indexOf("June")>=0){
                                        fecha = fecha.replaceAll("June",getString(R.string.junio));
                                    }
                                    if (fecha.indexOf("July")>=0){
                                        fecha = fecha.replaceAll("July",getString(R.string.julio));
                                    }
                                    if (fecha.indexOf("August")>=0){
                                        fecha = fecha.replaceAll("August",getString(R.string.agosto));
                                    }
                                    if (fecha.indexOf("September")>=0){
                                        fecha = fecha.replaceAll("September",getString(R.string.septiembre));
                                    }
                                    if (fecha.indexOf("October")>=0){
                                        fecha = fecha.replaceAll("October",getString(R.string.octubre));
                                    }
                                    if (fecha.indexOf("November")>=0){
                                        fecha = fecha.replaceAll("November",getString(R.string.noviembre));
                                    }
                                    if (fecha.indexOf("December")>=0){
                                        fecha = fecha.replaceAll("December",getString(R.string.diciembre));
                                    }




                                    fpartidos.setFecha(fecha);
                                    Encontrado = true;
                                } else {
                                    try {
                                        line = br.readLine();
                                    } catch (IOException e) {
                                        CustomLog.error("MainActivity",
                                                e.getMessage());
                                    }
                                }
                            }

                            // frase pequea
                            Encontrado = false;
                            while (!Encontrado) {
                                if (line.indexOf("comp_head") > 0) { // si
                                    // encontrado
                                    frase = line.substring(
                                            line.indexOf("comp_head>") + 10,
                                            line.indexOf("</span"));

                                    Encontrado = true;
                                } else {
                                    try {
                                        line = br.readLine();
                                    } catch (IOException e) {
                                        CustomLog.error("MainActivity",
                                                e.getMessage());
                                    }
                                }
                            }

                        }
                        Encontrado = false;
                        while (!Encontrado) {
                            if (line.indexOf("blockfix") > 0) { // si encontrado
                                partidos = new Partidos();

                                String parte1 = "";
                                String parte2 = "";
                                if (frase != null) {
                                    if (frase.length() > 40) {
                                        parte1 = frase.substring(0,
                                                frase.indexOf("-"));
                                        parte2 = frase.substring(frase
                                                .indexOf("-") + 1);
                                    } else {
                                        parte1 = frase;
                                        parte2 = "";
                                        // hijo.dato_frase2.setVisibility(View.INVISIBLE);
                                    }
                                }

                                partidos.setFrase(parte1);
                                partidos.setFrase2(parte2);
                                Encontrado = true;
                            } else {
                                try {
                                    line = br.readLine();
                                } catch (IOException e) {
                                    CustomLog.error("MainActivity",
                                            e.getMessage());
                                }
                            }
                        }

                        // si leemos blockfix, comienza el bloque
                        // leemos hasta encontrar la cadena /fix/fix_, que
                        // es la imagen png de los partidos
                        Encontrado = false;
                        while (!Encontrado) {
                            if (line.indexOf("/fix/fix_") > 0) { // si
                                // encontrado
                                String imagemPng = line.substring(
                                        line.indexOf("/fix/"),
                                        line.indexOf("></div>") - 1);
                                partidos.setImagem_png("http://liveonsat.com"
                                        + imagemPng);
                                Encontrado = true;
                            } else {
                                try {
                                    line = br.readLine();
                                } catch (IOException e) {
                                    CustomLog.error("MainActivity",
                                            e.getMessage());
                                }
                            }
                        }

                        // Leemos hasta buscar la hora
                        // fLeft_time_live, la siguiente lectura es la hora
                        Encontrado = false;
                        while (!Encontrado) {
                            if (line.indexOf("fLeft_time_live") > 0) { // si
                                // encontrado
                                try {
                                    line = br.readLine();
                                } catch (IOException e) {
                                    CustomLog.error("MainActivity",
                                            e.getMessage());
                                }
                                String hora = line.substring(1).trim();

                                // TODO Hay que sumar el GMZ
                                String[] temp;
                                temp = hora.split(":");
                                int horaGmz = Integer.valueOf(temp[1].trim());

                                horaGmz = horaGmz + Integer.valueOf(sGmz);
                                hora = horaGmz + ":" + temp[2];
                                partidos.setHora(hora);
                                Encontrado = true;
                            } else {
                                try {
                                    line = br.readLine();
                                } catch (IOException e) {
                                    CustomLog.error("MainActivity",
                                            e.getMessage());
                                }
                            }
                        }

                        // pasamos la tabla
                        Encontrado = false;
                        while (!Encontrado) {
                            if ((line.indexOf("chan_live_not_free")) > 0
                                    || (line.indexOf("chan_live_free")) > 0) {

                                canalesPartido = tratarTabla(line);
                                listaCanales.add(canalesPartido);
                                try {
                                    line = br.readLine();
                                } catch (IOException e) {
                                    CustomLog.error("MainActivity",
                                            e.getMessage());
                                }
                                // Encontrado = true;
                            } else {

                                if (line.indexOf("blockfix") >= 0) {
                                    // nuevo bloque de partidos

                                    partidos.setLcanales(listaCanales);
                                    // listaCanales.clear();
                                    listaPartidos.add(partidos);
                                    Encontrado = true; // de datos
                                } else {
                                    if (line.indexOf("comp_head") > 0) { // si
                                        // encontrado
                                        frase = line
                                                .substring(
                                                        line.indexOf("comp_head>") + 10,
                                                        line.indexOf("</span"));
                                    }
                                    if (line.indexOf("time_head") >= 0) {

                                        // puede que haya que actualizar la
                                        // frase

                                        // nuevo bloque desde fechas, guardar
                                        // todo
                                        // y poner a 0
                                        partidos.setLcanales(listaCanales);
                                        listaPartidos.add(partidos);
                                        fpartidos.setLpartidos(listaPartidos);
                                        lfechaPartidos.add(fpartidos);
                                        Encontrado = true; // de datos
                                    } else {
                                        if (line.indexOf("</html>") >= 0) { // Final
                                            // de
                                            // lectura
                                            partidos.setLcanales(listaCanales);
                                            listaPartidos.add(partidos);
                                            fpartidos
                                                    .setLpartidos(listaPartidos);
                                            lfechaPartidos.add(fpartidos);
                                            Encontrado = true;
                                        } else {
                                            try {
                                                line = br.readLine();
                                            } catch (IOException e) {
                                                CustomLog.error("MainActivity",
                                                        e.getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    CustomLog.error("MainActivity", e.getMessage());
                }

                try {
                    br.close();
                } catch (IOException e) {
                    CustomLog.error("MainActivity", e.getMessage());
                }

                paispartidos.setLpartidos(lfechaPartidos);
            } else {
                paispartidos.setLpartidos(null);
            }

            PaisPartidos result = paispartidos;

            // publishProgress(2);
            return result;
        }

        @Override
        protected void onPostExecute(PaisPartidos result) {

            if (result.getLpartidos() == null) {
                // Error de conexion
                // Si vale, hacer

                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText(getString(R.string.error_no_data))
                        .show();

//                Toast toast1 = Toast.makeText(getApplicationContext(),
//                            getString(R.string.error_no_data),
//                            Toast.LENGTH_LONG);
//
//                    toast1.show();
                if (error == null) {
                    error = ConnectionException.ConnectionError.UNKNOWN_ERROR;
                }

            } else {

                adapter.setFechaPartidos((ArrayList<FechaPartidos>) paispartidos
                        .getLpartidos());
                adapter.notifyDataSetChanged();
            }

            pDialog.cancel();
         //   dotsTextView.hideAndStop();
         //   lytloading.setVisibility(View.GONE);

        }

        private Canales tratarTabla(String line) {
            Canales canales = new Canales();
            Frecuencias frecuencias;
            List<Frecuencias> listaFrec = new ArrayList<Frecuencias>();

            String pos_col = null;
            String rest_col = null;
            String freq_col = null;
            String rest_col2 = null;
            String codificacion = null;
            String restoLinea = line;
            try {
                while (restoLinea.indexOf("&quot;pos_col&quot;>") > 0) {

                    pos_col = limpiarString(restoLinea
                            .substring(restoLinea
                                            .indexOf("&quot;pos_col&quot;>"),
                                    restoLinea.indexOf(("</td>"), restoLinea
                                            .indexOf("&quot;pos_col&quot;>"))));
                    restoLinea = restoLinea.substring(restoLinea
                            .indexOf("&quot;pos_col&quot;>"));
                    rest_col = limpiarString(restoLinea.substring(
                            restoLinea.indexOf("rest_col"),
                            restoLinea.indexOf(("</td>"),
                                    restoLinea.indexOf("rest_col"))));
                    restoLinea = restoLinea.substring(restoLinea
                            .indexOf("rest_col"));
                    freq_col = limpiarString(restoLinea.substring(
                            restoLinea.indexOf("freq_col"),
                            restoLinea.indexOf(("</td>"),
                                    restoLinea.indexOf("freq_col"))));
                    restoLinea = restoLinea.substring(restoLinea
                            .indexOf("freq_col"));
                    rest_col2 = limpiarString(restoLinea.substring(
                            restoLinea.indexOf("rest_col"),
                            restoLinea.indexOf(("</td>"),
                                    restoLinea.indexOf("rest_col"))));
                    restoLinea = restoLinea.substring(restoLinea
                            .indexOf("rest_col"));

                    String subcadena = restoLinea.substring(1, 100);
                    if (subcadena.indexOf("enc_not_live") >= 0) {
                        codificacion = limpiarString(restoLinea.substring(
                                restoLinea.indexOf("enc_not_live"),
                                restoLinea.indexOf(("</td>"),
                                        restoLinea.indexOf("enc_not_live"))));
                    } else {
                        if (subcadena.indexOf("enc_live") >= 0) {
                            codificacion = limpiarString(restoLinea.substring(
                                    restoLinea.indexOf("enc_live"),
                                    restoLinea.indexOf(("</td>"),
                                            restoLinea.indexOf("enc_live"))));
                        } else {
                            codificacion = "";
                        }
                    }

                    restoLinea = restoLinea.substring(restoLinea
                            .indexOf("rest_col"));

                    frecuencias = new Frecuencias();
                    frecuencias.setPosicion(pos_col);
                    if (rest_col.length()>=16){
                        rest_col = rest_col.substring(0, 13) + rest_col.substring(rest_col.length()-1);
                    }
                    frecuencias.setSatelite(rest_col);
                    frecuencias.setFrecuencia(freq_col);
                    frecuencias.setSymbol(rest_col2);
                    frecuencias.setEncriptacion(codificacion);

                    listaFrec.add(frecuencias);
                }

                String nombre = limpiarString(restoLinea.substring(
                        restoLinea.indexOf("CAPTION"),
                        restoLinea.indexOf(("&nbsp;"),
                                restoLinea.indexOf("CAPTION"))));

                canales.setNombre(nombre);
                canales.setlFrecuencias(listaFrec);
            } catch (Exception ex) {
                CustomLog.error("tratarTabla", ex.getMessage());
            }
            return canales;

        }

        private String limpiarString(String sucia) {

            String limpia = null;

            try {
                if (sucia.indexOf(">") >= 0) {
                    limpia = sucia.substring(sucia.indexOf(">"));
                } else {
                    limpia = sucia.substring(sucia.indexOf(","));
                }

                // String limpia=sucia;
                if (limpia.indexOf("&deg;") >= 0) { // grados del satelite
                    limpia = limpia.replaceAll("&deg;", "º");
                }
                if (limpia.indexOf("\"") >= 0) { // grados del satelite
                    limpia = limpia.replaceAll("\"", "");
                }
                if (limpia.indexOf(">") >= 0) { // grados del satelite
                    limpia = limpia.replaceAll(">", "");
                }
                if (limpia.indexOf("\'") >= 0) { // grados del satelite
                    limpia = limpia.replaceAll("\'", "");
                }
                if (limpia.indexOf(",") >= 0) { // grados del satelite
                    limpia = limpia.replaceAll(",", "");
                }
            } catch (Exception ex) {
                CustomLog.error("limpiarString", ex.getMessage());
            }

            return limpia;
        }

    }

    private String cogerGmz() {
        // Hacer funcional
        sGmz = "0";

        SharedPreferences settings = getSharedPreferences(
                "perfil", Context.MODE_PRIVATE);
        sGmz = settings.getString("gmz", "0");

        if (sGmz.equalsIgnoreCase("GMZ 0")) {
            sGmz = "0";
        } else {
            if (sGmz.indexOf("GMZ") >= 0) {
                sGmz = sGmz.substring(4);
            } else {
                sGmz = "0";
            }
        }
        // quitar el mï¿½s
        if (sGmz.indexOf("+") >= 0) {
            sGmz = sGmz.substring(1);
        }
        return sGmz;

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//     //   getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.      int id = item.getItemId();
////        if (id == R.id.action_settings) {
////            return true;
////        }
////        return super.onOptionsItemSelected(item);
////
//    }


}
