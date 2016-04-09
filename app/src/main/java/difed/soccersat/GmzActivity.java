package difed.soccersat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class GmzActivity extends Activity implements OnClickListener {

    private String[] gmz = {"GMZ +6","GMZ +4.30", "GMZ +4", "GMZ +3.30", "GMZ +3",
            "GMZ +2", "GMZ +1", "GMZ 0", "GMZ -1", "GMZ -3", "GMZ -3.30",
            "GMZ -4", "GMZ -5", "GMZ -6", "GMZ -8"};

    private String[] gmz_text = {
            "",
            "Kabul, Afganistan ",
            "Abu Dhabi (Emiratos Arabes Unidos), Moscatel, Tblisi, Volgogrado",
            "Teheran (Iran)",
            "Kuwait, Nairobi (Kenya), Riad (Arabia Saudita), Moscu (Rusia)",
            "Atenas (Grecia), Helsinki (Finlandia), Estambul (Turquia), Jerusalen (Israel),Harare (Zimbabwe)",
            "Paris (Francia),Berlin (Alemania),Amsterdam (Paises Bajos), Bruselas (Belgica), Viena (Austria),"
                    + "Madrid (España),Roma (Italia), Berna (Suiza), Estocolmo (Suecia), Oslo (Noruega) ",
            "Londres (Inglaterra), Dublin (Irlanda), Edimburgo (Escocia),Lisboa (Portugal), Reykjavik (Islandia),"
                    + "Casablanca (Marruecos)",
            "Azores, las islas de Cabo Verde",
            "Brasilia (Brasil), Buenos Aires (Argentina), Georgetown, Guyana",
            "Terranova", "Caracas, La Paz",
            "Bogota, Lima (Peru), Nueva York, NY, EE.UU.",
            "Ciudad de Mexico, Mexico Saskatchewan, Canada",
            "Los Angeles, California, EE.UU. "};

    View gmzView = null;

    private Context activity;

    String sGmz = null;

    public GmzActivity() {
        // Empty constructor required for fragment subclasses
    }


    //	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.gmz_parent, container, false);
//	}
//
//	 @Override
//	    public void onActivityCreated(Bundle savedInstanceState) {
//	    	super.onActivityCreated(savedInstanceState);
//
//
//
//	    	buildView();
//		}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gmz_parent);

        buildView();
    }

    private void buildView() {

        try {
            ViewGroup datosList = (ViewGroup) findViewById(R.id.gmz_list);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            TextView textView;

            textView = (TextView) findViewById(R.id.gmz_actual);
            textView
                    .setText(getString(R.string.actual) + " GMZ: " + cogerGmz());

            int size = gmz.length;
            for (int i = 0; i < size; i++) {

                gmzView = inflater.inflate(R.layout.gmz_item, null);
                gmzView.findViewById(R.id.gmz_layout).setOnClickListener(this);

                textView = (TextView) gmzView.findViewById(R.id.gmz_textogra);
                textView.setText(gmz[i]);

                textView = (TextView) gmzView.findViewById(R.id.gmz_textopeq);
                textView.setText(gmz_text[i]);

                datosList.addView(gmzView);

            }
        } catch (Exception e) {

        }

    }


    private String cogerGmz() {
        SharedPreferences settings = getSharedPreferences("perfil",
                Context.MODE_PRIVATE);
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
        // quitar el m�s

        if (sGmz.indexOf("+") >= 0) {
            sGmz = sGmz.substring(1);
        }
        return sGmz;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.gmz_layout) {

            try {

                TextView vGmz = (TextView) v.findViewById(R.id.gmz_textogra);
                String sGmz = vGmz.getText().toString();

                SharedPreferences settings = getSharedPreferences("perfil",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("gmz", sGmz);
                editor.putInt("gmzpuesto",1);
                editor.commit();

                // startActivity(new Intent(this, MainActivity.class));
                //Lo cerramos
                finish();


            }catch (Exception e){

            }

        }

    }

}
