package difed.soccersat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import cn.pedant.SweetAlert.SweetAlertDialog;
import difed.entidades.Partidos;


public class MainActivity extends AppCompatActivity {

    private static final String AD_UNIT_ID = "ca-app-pub-7866637665636353/8579016620";

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    int gmzpuesto = 0;
    int googleplay = 0;
    String sGmz = "";

    Context context = this;

    Partidos partido = new Partidos();

    private InterstitialAd interstitial;

    @Override
    protected void onDestroy() {

        super.onDestroy();

        displayInterstitial();
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quitamos barra de titulo de la aplicacion

        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainactivity);


        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);//unico
            actionBar.setHomeButtonEnabled(false);
        }
        //    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        //    mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 7) {
                    case 0:
                        return DiarioViewFragment.newInstance();
                    //    return RecyclerViewFragment.newInstance();
                    case 1:
                        return InternacionalViewFragment.newInstance();
                    case 2:
                        return EuropaViewFragment.newInstance();
                    case 3:
                        return AmericaViewFragment.newInstance();
                    case 4:
                        return AsiaViewFragment.newInstance();
                    case 5:
                        return OtrosViewFragment.newInstance();
                    case 6:
                        return ConfigViewFragment.newInstance();
                    default:
                        return DiarioViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 7;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 7) {
                    case 0:
                        return getString(R.string.diario);
                    case 1:
                        return getString(R.string.internacional);
                    case 2:
                        return getString(R.string.europa);
                    case 3:
                        return getString(R.string.america);
                    case 4:
                        return getString(R.string.asia);
                    case 5:
                        return getString(R.string.otros);
                    case 6:
                        return getString(R.string.config);
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/diario1.jpg?attachauth=ANoY7coR8mVXtnYQ0D9QskqAfv35XTPro1rx6249ljCXxmDqqWQnecZDdJ-M9OPO-dtm-bsFrH-Qumw95Z-gRrujvw8ccJlmakXeGmD1aaOXXYijPx50eh3MuQOtOSDnBmxzN6ZiPzzlFVr0lHvMArrH5amNfScRO6e5xgnGV1DvTvI0zyYmmF1ltzNr563jYH7ZPdMXcaATU94RVwyrQAxXpK4YKCjU9Q%3D%3D&attredirects=0");
                               // "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/diario.jpg?attachauth=ANoY7crqKTYGVhnxwg4G2JhqoXEM9m2bZHP9fw5leQZJcA2r8X5NjTpeSQENohmkUJDzjNDfXpfjuhZkVvVQOcp5wyf0YwRVNpriCBGMt9B0d4p5Qf45PoxqhJgXyx_wy-M6bDGoP4B-1MkujM7E6lpHwoKFxzKp3T6VV8LETyqncJ-4_fFUSJYn9x42Nv4CBeldxzkBW_AU6rtHx8TPqRqD3iVd8IyiJg%3D%3D&attredirects=0");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/internacional.jpg?attachauth=ANoY7cqx5cdgUsi2I6lG4mogL23pGG5goD6lKW0_-7DmevOvRoL8XVJ_0JcEOVgqgucaGw3NXjDgs9NBu2NZRnKq7frCEuhnycsu4pvchxGpZzVDC5IzF2ju8p0LvRK84hWFZQRXlGHxPTkMRDJNDc4J--KVJqe13d0rXD-4qCYwfAWVsZwdR_JwEVy19wHz_QQGZTmdrQwVhT1XPtaIBiXIqTbxhSycp9ScrenrR4GdIafFtwHldEo%3D&attredirects=0");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/europa.jpg?attachauth=ANoY7cpYOUbo2AvQVptp3hcCp_wS3wiaYoLB3DarqBblWGm-MwJRvoB5hQ7PcfdEu6BhGe_nvaEp13J562DQVOIPlC6FgctmBI1bAsJqE4duVv5TByAMK6QS7v_K2vR_ykpUmNsq942zjQas00mrxXjbgyf81JMGEspPxbN4b2WeGuZ30E_kITnmLMbvOvrbGi2uuZTUU5tgsTmsN6DitANgKcO-Dqnwbw%3D%3D&attredirects=0");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/america.jpg?attachauth=ANoY7crl5jPo4LoeiPMWWUAH98SBSgQzAfPv-KjyD6h4E0JDD4f06dGyqmZhIet-EErYxERESTIFvkDQtnSQ6Uj_1SiUlbn1Hh9c69qzf9pyGQZBh5WdS5WbGQhxrplHaCksF2T_EWc8i0NpkqOfycInzsZIEZp9x-tdFDI-t_Nd2C-omlDF-eDGGRnRh9loWUvZx7_OsQL5fhXvphp2kroI7VjgH-x84Q%3D%3D&attredirects=0");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.lime,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/africa.jpg?attachauth=ANoY7cpcRFoQB_-coObm6xldaH_lu2gJXoFPX2QATEtlYlnOnRhev9ESj4mkdvXjZI72y1I8vTd3mkNdHlG2KXctl0JDNuCA0wflY4O25dyClyPKO8r6RUeOB4k55iJEEP031fnaDcWtL4aClXzC-wnsJOMiKrUOBpYIzddcYBhHRrwRnXfyxqCDy3S2G_pYZ2nKokINpgXg8C-I5i1S6fBMB9yfGtp6vg%3D%3D&attredirects=0");
                    case 5:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.purple,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/otros.jpg?attachauth=ANoY7crQKcxedzA0fBvOWUBbmmZru4MY5DMCGkfuRU4DzmZDtROMobRewtMgwDnmCKYnHyQxrKX8pdFuPW2sstDFVfVOnC_gd_WOoSxhbvBeg4CK77jrDB-_JQBDSDzaoK7r6vXr59Ya7i1F3r7zE4tMuB-P3boYCBZolxjgfQMnystfQrl3PrCJzHCwjxOMy4t5sZGwyASNkgPA6IU9VlWH7ijsbcLwuQ%3D%3D&attredirects=0");
                    case 6:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://815cb45b-a-62cb3a1a-s-sites.googlegroups.com/site/dimogiconos/iconos/blanco.jpg?attachauth=ANoY7crrQ84_tEz7WoeJStv7tT6mCFoJbl_3347eumXDf-gnpWFg7jVcAirdJcY3trV4MK10ZgW0H3_xxsENiJ8X6xUTmKjW8oOq2iYCs67ENxfvDLr9iPx59saUHEkINfn9nGz3wpkl0idnO5ldJumFhjRlcqZLIOBIYPvvuR2cEq7c3CvvRuxbRhJdxWAdzNeUkEdf7aci7dwL0CtfsMsfpv6HJkohIw%3D%3D&attredirects=0");


                    //"http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  mViewPager.notifyHeaderChanged();
                    // Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });


        // recupramos por si venimos de una Notificacion
        Bundle b = getIntent().getExtras();

        if (b != null) {
            partido = b.getParcelable("recordar");

            // partido = getIntent().getParcelableExtra("recordar");
            if (partido != null) {
                // Llamamos a la pestaña de Canales
                selectCanales(partido);
            }
        }

        // PUBLICIDAD
        interstitial = new InterstitialAd(this);
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


        //Comprobamos si tenemos gmz para lanzar el config
        SharedPreferences settings = getSharedPreferences("perfil",
                Context.MODE_PRIVATE);
        gmzpuesto = settings.getInt("gmzpuesto", 0);
        sGmz = settings.getString("gmz", "");

        //googlePlay
        googleplay = settings.getInt("googleplay", 0);

        if ((gmzpuesto == 0) && (sGmz.equalsIgnoreCase(""))) {  //Lanzar config
            // final Intent intent;
            // intent =  new Intent(this, AyudaZonaHorariaActivity.class);
            // startActivity(intent);
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.app_name))
                    .setContentText(getString(R.string.ayudazonah))
                    .setConfirmText(getString(R.string.aceptar))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            final Intent intent;
                            intent = new Intent(context, GmzActivity.class);
                            startActivity(intent);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }


        if (googleplay==3){//lanzar
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.app_name))
                    .setContentText(getString(R.string.googleplay))
                    .setCancelText(getString(R.string.nogoogle))
                    .setConfirmText(getString(R.string.sigoogle))
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + getPackageName()));
                            startActivity(intent);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
        }
        if (googleplay<=3){ //sumar1

            googleplay = googleplay + 1;
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("googleplay",googleplay);
            editor.commit();

        }




    }

    private void selectCanales(Partidos partido) {

        final Intent intent;
        intent = new Intent(this, ListaCanales.class);
        intent.putExtra("listacanales", partido);

        startActivity(intent);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return mDrawerToggle.onOptionsItemSelected(item) ||
//                super.onOptionsItemSelected(item);
//    }
}
