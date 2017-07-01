package difed.soccersat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import difed.entidades.Canales;
import difed.entidades.Frecuencias;
import difed.entidades.Partidos;
import difed.util.CustomLog;
import pl.polidea.view.ZoomView;

public class ListaCanales extends Activity {

	static final int TIME_DIALOG_ID = 0;
	private static final String TAG = "dialog";

	View canalesView = null;
	View frecView = null;
	Partidos partido = new Partidos();
	List<Canales> lcanales = new ArrayList<Canales>();
	List<Frecuencias> lfrecuencias;

    Context context;

    TextView txttitulocab;
    public String nombrePais;
    Typeface tf_regular;
    LinearLayout lytloading;
    LinearLayout lytcanaleshora;
    LinearLayout lytlistado;
    LinearLayout lytcanalesfrecuencia;
    public int colorFondo =0;
    RelativeLayout lytcabecera;
    ImageView imgatras;

    ImageView imgfiltro;

	boolean existeFiltro = true;

	View lytCanales;

	int mYear;
	int mMonth;
	int mDay;
	int hour;
	int minute;

	private static PrincipalTask task;

	public static final String PREFS_NAME = "MyPrefsFile1";
	public static final String PREFS_SAT = "myFilterSat";
	public CheckBox dontShowAgain;
	public CheckBox guardarSat;

	ArrayList<String> seletedItems = new ArrayList<String>();
	CharSequence[] items;
	boolean[] states;

	String sSatelites = "";

	List<String> listItems;

	SharedPreferences sPrefs;

    int primeravez =0;
    int primeravezfiltro =0;

    private ZoomView zoomView;
    LinearLayout main_container;

	public ListaCanales() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.container);

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.canales, null, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        zoomView = new ZoomView(this);
        zoomView.addView(v);

        main_container = (LinearLayout) findViewById(R.id.lytcontainer);
        main_container.addView(zoomView);

        context = this;

        txttitulocab = (TextView) findViewById(R.id.txttitulocab);
        tf_regular = Typeface.createFromAsset(getAssets(), "fonts/fuente.ttf");
        txttitulocab.setTypeface(tf_regular);

//        dotsTextView = (DotsTextView) findViewById(R.id.dotsc);
//        lytloading = (LinearLayout) findViewById((R.id.lytloadingc));
        lytcabecera = (RelativeLayout) findViewById(R.id.lytcabcanales);



        imgatras = (ImageView) findViewById(R.id.imgatrasc);
        imgfiltro = (ImageView) findViewById(R.id.imgfiltro);
        imgfiltro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("perfil",
                        Context.MODE_PRIVATE);
                primeravezfiltro = settings.getInt("primeravezfiltro", 0);
                if (primeravezfiltro==0) {

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("primeravezfiltro", 1);
                    editor.commit();

                    MostrarComoHacerFiltro();
                }

                mostrarSatelites();
            }
        });


        // lanzar task
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            partido = extras.getParcelable("listacanales");
            nombrePais =  extras.getString("nombrepais");
            colorFondo =  extras.getInt("colorfondo");

        }




        txttitulocab.setText(nombrePais);


        //color
        //Color de fondo
        if (colorFondo>0){
            if (colorFondo==1){
                lytcabecera.setBackgroundColor(Color.parseColor("#4CAF50"));
            }
            if (colorFondo==2){
                lytcabecera.setBackgroundColor(Color.parseColor("#303F9F"));
            }
            if (colorFondo==3){
                lytcabecera.setBackgroundColor(Color.parseColor("#00BCD4"));
            }
            if (colorFondo==4){
                lytcabecera.setBackgroundColor(Color.parseColor("#F44336"));
            }
            if (colorFondo==5){
                lytcabecera.setBackgroundColor(Color.parseColor("#CDDC39"));
            }
            if (colorFondo==6){
                lytcabecera.setBackgroundColor(Color.parseColor("#673AB7"));
            }
            if (colorFondo==7){
                lytcabecera.setBackgroundColor(Color.parseColor("#00BCD4"));
            }
        }

        //---


        String imageUrl = partido.getImagem_png();
        ImageView dato_imagen = (ImageView) findViewById(R.id.canales_imagen);

        Picasso.with(this).load(imageUrl).into(dato_imagen);

        imgatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        // ---------------------------------------------------------
        // Si tenemos filtro vamos a pasar solamente la lista filtrada
        // TODO
        lcanales = partido.getLcanales();

        // Cogemos los sat�lites existentes.
        crearListaStelites();

        // COmprobamos si tenemos filtro creado
        sPrefs = getSharedPreferences(PREFS_SAT, 0);

        int size = sPrefs.getInt("size", 0);
        if (size > 0) { // Existe filtro guardado.
            recuperarFiltroSatelites();
            filtrarSatelites();
            existeFiltro = true;
        } else {
            existeFiltro = false;
            conectar();
        }

        lytcanaleshora = (LinearLayout) findViewById(R.id.lytcanaleshora);
        lytcanaleshora.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                recordarAlarma();
            }
        });

        SharedPreferences settings = getSharedPreferences("perfil",
                Context.MODE_PRIVATE);
        primeravez = settings.getInt("primeravez", 0);
        if ((primeravez==0) || (primeravez==1)) {

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("primeravez", 2);
            editor.commit();

            MostrarComoHacerNotificacion();
        }


	}

    private void MostrarComoHacerFiltro() {

        final Intent intent;
        intent =  new Intent(this, AyudaFiltroActivity.class);
        startActivity(intent);


    }

	private void MostrarComoHacerNotificacion() {


        final Intent intent;
        intent =  new Intent(this, AyudaActivity.class);
        startActivity(intent);
	}


    private void mostrarSatelites() {

		if (existeFiltro) {
			for (int i = 0; i < items.length; i++) {
				if (seletedItems.contains( items[i])) {
					states[i] = true;
				}
			}
		}

		seletedItems.clear();

		// TODO Si tnemos FIltro ANterior ponemos la variable states a 1 donde
		// esten los satelites del filtro.

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater chkInflater = LayoutInflater.from(this);
		View guardarchkLayout = chkInflater.inflate(R.layout.chkguardarsat,
				null);
		guardarSat = (CheckBox) guardarchkLayout.findViewById(R.id.guardarsat);

		builder.setTitle(getString(R.string.filtro));
		builder.setMultiChoiceItems(items, states,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int indexSelected, boolean isChecked) {

					}
				});
		builder.setPositiveButton(getString(R.string.filtrar),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						for (int i = 0; i < listItems.size(); i++) {
							if (states[i]) {
								seletedItems.add((String) items[i]);
							}
						}

						filtrarSatelites();

						if (guardarSat.isChecked()) {
							salvarFiltrosatelites();
						}

						dialog.dismiss();

					}
				});
		builder.setNegativeButton(getString(R.string.cancelar),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						if (guardarSat.isChecked()) {
							salvarFiltrosatelites();
						}

						dialog.dismiss();

					}
				});

        builder.setNeutralButton(getString(R.string.eliminarfiltro),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       seletedItems.clear();
                       salvarFiltrosatelites();
                       filtrarSatelites();

                        dialog.dismiss();

                    }
                });

		builder.setView(guardarchkLayout);

		builder.create().show();

	}

	private void salvarFiltrosatelites() {
//
		SharedPreferences.Editor edtSat = sPrefs.edit();

		edtSat.clear();

		// Estan en seletedItems
		if (!seletedItems.isEmpty()) {

			for (int i = 0; i < seletedItems.size(); i++) {
				edtSat.putString("val" + i, seletedItems.get(i));
			}
			edtSat.putInt("size", seletedItems.size());
		}
		edtSat.commit();
		
	}

	private void recuperarFiltroSatelites() {

		ArrayList<String> myAList = new ArrayList<String>();

	    int size = sPrefs.getInt("size", 0);

		for (int j = 0; j < size; j++) {
			myAList.add(sPrefs.getString("val" + j, ""));
		}
		seletedItems = myAList;
	}

	private void filtrarSatelites() {
		boolean canalConFrecuencia;
		List<Canales> lcanalesAux = new ArrayList<Canales>();
		List<Frecuencias> lfrecuenciasAux = new ArrayList<Frecuencias>();

		lcanales = partido.getLcanales();
		lcanalesAux.clear();

		if (!seletedItems.isEmpty()) {
			if (lcanales != null) {
				for (Canales c : lcanales) {
					lfrecuenciasAux.clear();
					canalConFrecuencia = false;
					lfrecuencias = new ArrayList<Frecuencias>();
					lfrecuencias = c.getlFrecuencias();
					for (Frecuencias f : lfrecuencias) {

						if (seletedItems.contains(f.getSatelite() + " (" + f.getPosicion() + ")")) {
							canalConFrecuencia = true;
							lfrecuenciasAux.add(f);
						}
					}

					if (canalConFrecuencia) {
						Canales canales;
						canales = new Canales();
						canales.setNombre(c.getNombre());
						canales.setlFrecuencias(new ArrayList<Frecuencias>(
								lfrecuenciasAux));
						lcanalesAux.add(canales);
					}
				}
			}

			if (!lcanalesAux.isEmpty()) {
				lcanales = lcanalesAux;

//                new SweetAlertDialog(this)
//                        .setTitleText(getString(R.string.filtroactivo))
//                        .show();

                Toast.makeText(this,
                        getString(R.string.filtroactivo),
                        Toast.LENGTH_LONG).show();
			} else {
				//No debemos aplicar filtro
                //lcanales = null;
//                new SweetAlertDialog(this)
 //                       .setTitleText(getString(R.string.filtronoaplicado))
 //                       .show();
                new SweetAlertDialog(this)
                        .setTitleText((getString(R.string.filtro)))
                        .setContentText(getString(R.string.filtronoaplicado))
                        .show();
//                Toast.makeText(this,
//                        getString(R.string.filtronoaplicado),
//                        Toast.LENGTH_LONG).show();
			}
		}

		conectar();

	}

//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//
//
//	}

	private void crearListaStelites() {
		List<Canales> lcanalesB = new ArrayList<Canales>();
		listItems = new ArrayList<String>();

		lcanalesB = partido.getLcanales();

		if (lcanalesB != null) {
			for (Canales c : lcanalesB) {
				lfrecuencias = new ArrayList<Frecuencias>();
				lfrecuencias = c.getlFrecuencias();
				for (Frecuencias f : lfrecuencias) {

					if (!listItems.contains(f.getSatelite() + " (" + f.getPosicion() + ")")) {
						listItems.add(f.getSatelite() + " (" + f.getPosicion() + ")");
					}
				}
			}

			Collections.sort(listItems);
			items = listItems.toArray(new CharSequence[listItems.size()]);
			states = new boolean[items.length];
		}

	}

	public void recordarAlarma() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		final Dialog myDialog = new Dialog(this);
		myDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		myDialog.setContentView(R.layout.recordatorio);
		myDialog.setTitle(getString(R.string.notifica));

		myDialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_notifica);

		myDialog.setCancelable(false);

		final TextView txtFecha = (TextView) myDialog
				.findViewById(R.id.txtfecha);
		final TextView txtHora = (TextView) myDialog.findViewById(R.id.txthora);

		final Button btnFecha = (Button) myDialog.findViewById(R.id.btn_fecha);
		txtFecha.setText(mDay + " / " + (mMonth + 1) + " / " + mYear);
		btnFecha.setText(getString(R.string.cambiarfecha));

		btnFecha.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DatePickerDialog dpd = new DatePickerDialog(context,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {

								txtFecha.setText(dayOfMonth + " / "
										+ (monthOfYear + 1) + " / " + year);
								mYear = year;
								mMonth = monthOfYear;
								mDay = dayOfMonth;

							}
						}, mYear, mMonth, mDay);
				dpd.show();

			}
		});

		final Button btnHora = (Button) myDialog.findViewById(R.id.btn_hora);
		txtHora.setText(new StringBuilder().append(pad(hour)).append(":")
				.append(pad(minute))
				+ " H");
		btnHora.setText(getString(R.string.cambiarhora));
		btnHora.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				TimePickerDialog tpd = new TimePickerDialog(context,
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int selectedHour, int selectedMinute) {
								txtHora.setText(new StringBuilder()
										.append(pad(selectedHour)).append(":")
										.append(pad(selectedMinute))
										+ " H");
								hour = selectedHour;
								minute = selectedMinute;

							}
						}, hour, minute, true);
				tpd.show();

			}
		});

		Button btn_cancelar = (Button) myDialog.findViewById(R.id.btn_cancelar);
		btn_cancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				myDialog.dismiss();
			}
		});

		Button btn_aceptar = (Button) myDialog.findViewById(R.id.btn_aceptar);
		btn_aceptar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ACTIVAR ALARMA
				Calendar current = Calendar.getInstance();
				Calendar cal = Calendar.getInstance();
				cal.set(mYear, mMonth, mDay, hour, minute, 00);

				if (cal.compareTo(current) <= 0) {
					// The set Date/Time already passed
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(getString(R.string.dateinvalid))
                            .show();
//					Toast.makeText(getApplicationContext(),
//							getString(R.string.dateinvalid), Toast.LENGTH_LONG)
//							.show();
				} else {
					setAlarm(cal);
				}

				myDialog.dismiss();
			}
		});

		myDialog.show();

	}

	private void setAlarm(Calendar targetCal) {

		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm '  ' dd/MM/yyyy");
		String formatted = format1.format(targetCal.getTime());

        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.notifica))
                .setContentText(getString(R.string.notificacreada) + " " + formatted)
                .show();


//        Toast.makeText(this,
//				getString(R.string.notificacreada) + " " + formatted,
//				Toast.LENGTH_LONG).show();


		Intent intent = new Intent(getBaseContext(),
				AlarmReceiver.class);

		intent.putExtra("recordar", partido);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
				(int) System.currentTimeMillis(), intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
				pendingIntent);

	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}


	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

		}
	};

	protected void conectar() {

		task = new PrincipalTask();
		task.setActivity(this);
		task.execute(partido);

	}

	class PrincipalTask extends AsyncTask<Partidos, Integer, LinearLayout> {

		private Context activity;
        SweetAlertDialog pDialog;

		protected void onPreExecute() {

          //  lytloading.setVisibility(View.VISIBLE);
          //  dotsTextView.showAndPlay();

            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText(getString(R.string.Cargando));
            pDialog.setCancelable(false);
            pDialog.show();
		}

		@Override
		protected LinearLayout doInBackground(Partidos... params) {

			LinearLayout datosList = new LinearLayout(activity);
			datosList.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			datosList.setOrientation(LinearLayout.VERTICAL);
			datosList.setPadding(2, 2, 2, 2);

			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			TextView textView;

			// lcanales = partido.getLcanales();
			try {
				for (Canales c : lcanales) {
					canalesView = inflater
							.inflate(R.layout.canales_block, null);
					textView = (TextView) canalesView
							.findViewById(R.id.canal_nombre);
					textView.setText(c.getNombre());

//                    lytlistado = (LinearLayout) canalesView.findViewById(R.id.lytlistado);
//                    lytlistado.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View view) {
//                            mostrarSatelites();
//                            return true;
//                        }
//                    });


					if ((c.getNombre().indexOf("~") >= 0)
							|| (c.getNombre().indexOf("3D") >= 0)) {
						LinearLayout rl = (LinearLayout) canalesView
								.findViewById(R.id.datos_desplegable);
						rl.setBackgroundColor((getResources()
								.getColor(R.color.verde)));
						// textView.setTextColor((getResources().getColor(R.color.verde)));
					}

					lfrecuencias = new ArrayList<Frecuencias>();
					lfrecuencias = c.getlFrecuencias();

					ViewGroup datosListFrec = (ViewGroup) canalesView
							.findViewById(R.id.canal_layout_frecuencias);

					for (Frecuencias f : lfrecuencias) {
						frecView = inflater.inflate(
								R.layout.canales_block_frecuencias, null);

						textView = (TextView) frecView
								.findViewById(R.id.canales_freq_encriptacion);
						String encriptacion = f.getEncriptacion().toUpperCase();

//                        lytcanalesfrecuencia = (LinearLayout) frecView.findViewById(R.id.lytlistado);
//                        lytcanalesfrecuencia.setOnLongClickListener(new View.OnLongClickListener() {
//                            @Override
//                            public boolean onLongClick(View view) {
//                                mostrarSatelites();
//                                return true;
//                            }
//                        });


						boolean colorRojo = false;
						if ((encriptacion.indexOf("FREE TO AIR") >= 0)
								|| (encriptacion.indexOf("FTA") >= 0)) {
							textView.setTextColor((getResources()
									.getColor(R.color.rojo)));
							colorRojo = true;
						}
						textView.setText(f.getEncriptacion());

						textView = (TextView) frecView
								.findViewById(R.id.canales_freq_satelite);
						textView.setText(f.getSatelite());
						if (colorRojo) {
							textView.setTextColor((getResources()
									.getColor(R.color.rojo)));
						}

						textView = (TextView) frecView
								.findViewById(R.id.canales_freq_posicion);
						textView.setText(f.getPosicion());
						if (colorRojo) {
							textView.setTextColor((getResources()
									.getColor(R.color.rojo)));
						}

						textView = (TextView) frecView
								.findViewById(R.id.canales_freq_frecuencia);
						textView.setText(f.getFrecuencia());
						if (colorRojo) {
							textView.setTextColor((getResources()
									.getColor(R.color.rojo)));
						}

						textView = (TextView) frecView
								.findViewById(R.id.canales_freq_symbol);
						textView.setText(f.getSymbol());
						if (colorRojo) {
							textView.setTextColor((getResources()
									.getColor(R.color.rojo)));
						}

						datosListFrec.addView(frecView);
					}

					datosList.addView(canalesView);

				}
			} catch (Exception e) {
				((Activity) activity).finish();
			}

			return datosList;
		}

		protected void onPostExecute(LinearLayout result) {

			try {
				ViewGroup viewGroup = ((ViewGroup) ((Activity) activity)
						.findViewById(R.id.datos_canales_scroll));
				viewGroup.removeAllViews();
				viewGroup.addView(result);

				TextView textView;

				textView = (TextView) ((Activity) activity)
						.findViewById(R.id.canales_hora);
                tf_regular = Typeface.createFromAsset(getAssets(), "fonts/reloj.ttf");
                textView.setTypeface(tf_regular);

				String hora = partido.getHora();
				textView.setText(hora);

				textView = (TextView) ((Activity) activity)
						.findViewById(R.id.canales_frase);
				String frase = partido.getFrase();
				textView.setText(frase);

				textView = (TextView) ((Activity) activity)
						.findViewById(R.id.canales_frase2);
				String frase2 = partido.getFrase2();
				textView.setText(frase2);

			} catch (Exception e) {
				CustomLog.info("e", e.toString());
			}

          //  dotsTextView.hideAndStop();
          //  lytloading.setVisibility(View.GONE);
            pDialog.cancel();
		}

		public void setActivity(Context activity) {
			this.activity = activity;
		}

	}

}
