package difed.soccersat;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dgdavila on 01/10/2015.
 */
public class AyudaActivity extends Activity {

    Button btnAyuda;
    TextView txtayhora;

    Typeface tf_regular;
    TextView txtaylista;
    TextView txtayudazoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quitamos barra de titulo de la aplicacion

        super.onCreate(savedInstanceState);

        setContentView(R.layout.ayuda);

        txtayhora = (TextView) findViewById(R.id.txtayudahora);
        tf_regular = Typeface.createFromAsset(getAssets(), "fonts/tiza.ttf");
        txtayhora.setTypeface(tf_regular);
        txtaylista = (TextView) findViewById(R.id.txtayudalista);
        txtaylista.setTypeface(tf_regular);
        txtayudazoom = (TextView) findViewById(R.id.txtayudazoom);
        txtayudazoom.setTypeface(tf_regular);

        btnAyuda = (Button) findViewById(R.id.buttonayuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
