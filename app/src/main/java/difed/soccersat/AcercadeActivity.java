package difed.soccersat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class AcercadeActivity extends Activity {

	Button btnEmail;
	Button btnPlay;
	Button btnCompartir;
	Button btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quitamos barra de titulo de la aplicacion

        super.onCreate(savedInstanceState);

        setContentView(R.layout.acercade);


        btnEmail = (Button) findViewById(R.id.about_like_mail);
        btnEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                botonPulsado("email");

            }
        });

//        btnPlay = (Button) findViewById(R.id.about_like_market);
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                botonPulsado("play");
//
//            }
//        });

        btnCompartir = (Button) findViewById(R.id.about_like_compartir);
        btnCompartir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                botonPulsado("compartir");

            }
        });

//        btnHelp = (Button)findViewById(R.id.about_help);
//        btnHelp.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                botonPulsado("help");
//
//            }
//        });

        TextView htmlTextView = (TextView) findViewById(R.id.html_text);
        htmlTextView.setMovementMethod(new ScrollingMovementMethod());
        htmlTextView.setText(Html.fromHtml(getString(R.string.frasenovedades)));
    }
	

	private void botonPulsado(String boton) {

		if (boton.equalsIgnoreCase("email")) {
			Intent intent = new Intent(Intent.ACTION_SEND);            
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.about_problem_mail)});     
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_problem_subject));     
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(intent);

		}
		if (boton.equalsIgnoreCase("play")) {
			
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + getPackageName()));
			startActivity(intent);

		}
		if (boton.equalsIgnoreCase("compartir")) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT,
					getString(R.string.about_twitter_msg));
			startActivity(intent);

		}
		if (boton.equalsIgnoreCase("help")) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setTitle(getString(R.string.about_help));
			adb.setMessage(Html.fromHtml(getString(R.string.help)));

			adb.setNeutralButton(getString(R.string.aceptar),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			adb.show();
		}
	}

}
