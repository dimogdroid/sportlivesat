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
public class AyudaZoomActivity extends Activity {

    Button btnAyuda;
    TextView txtayudalista;

    Typeface tf_regular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quitamos barra de titulo de la aplicacion

        super.onCreate(savedInstanceState);

        setContentView(R.layout.zoom);

        txtayudalista = (TextView) findViewById(R.id.txtayudalista);
        tf_regular = Typeface.createFromAsset(getAssets(), "fonts/tiza.ttf");
        txtayudalista.setTypeface(tf_regular);

        btnAyuda = (Button) findViewById(R.id.buttonayuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
