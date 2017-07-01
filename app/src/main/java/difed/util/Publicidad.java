package difed.util;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/** Publicidad Insterticial
 * Created by Diego on 24/04/2016.
 */
public class Publicidad {


    //Bueno. Banner

    private static final String AD_UNIT_ID = "ca-app-pub-7866637665636353/8579016620";

    private InterstitialAd interstitial;

    private static Publicidad mipublicidad;

    private Context context;



    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    public  static Publicidad getPublicidad(Context context) {

        if (mipublicidad==null) {

            mipublicidad=new Publicidad(context);
        }
        return mipublicidad;
    }


    private Publicidad(Context context) {


        this.context = context;

        // PUBLICIDAD
        interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId(AD_UNIT_ID);

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Check the logcat output for your hashed device ID to get test ads on
        // a physical device.
        //AdRequest adRequest = new AdRequest.Builder()
        //	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        //		.addTestDevice("A31D280E26B4043E35F3B89D12C66B27").build();

        // Load the interstitial ad.
        interstitial.loadAd(adRequest);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
