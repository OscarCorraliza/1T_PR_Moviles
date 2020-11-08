package com.example.practicamoviles_1tr;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.practicamoviles_1tr.fragments.CurrentLocation;
import com.example.practicamoviles_1tr.fragments.HomeFragment;
import com.example.practicamoviles_1tr.services.RunGPS;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.practicamoviles_1tr.common.Constantes.INTENT_LOCALIZATION_ACTION;
import static com.example.practicamoviles_1tr.common.Constantes.LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.LONGITUDE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navView;
    Double latitude=0.0;
    Double longitude=0.0;
    private Fragment locationFragment;

    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String TITLE = "My location";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    public static final String DESCRIPTION = "This is my location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navView = findViewById(R.id.nav_view);

        //añadimos la toolbar
        setToolbar();
        //llamamos al metodo de mas abajo que inicia el servicio del gps(sin funcion todavia)
        getGPS();
        //hay que establecer el fragment de inicio
        setFragment(0);
        //activamos el broadcast receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(INTENT_LOCALIZATION_ACTION));

        //activamos el listener para el navView para que pueda cambiar cuando se pulsa una opcion del menu
        if (navView != null) {
            navView.setNavigationItemSelectedListener(this);
        }


    }


    //metodo para incluir la toolbar
    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    //metodo que controla la interaccion con el navigation view
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        if(item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.ubicacionactual:
                Log.d("","Value of latitude: ".concat(String.valueOf(latitude)));
                System.out.println("Ha pasado"+latitude);
                //intent para mapa posicion actual
                Intent locationIntent = new Intent(MainActivity.this, CurrentLocation.class);
                locationIntent.putExtra(TITLE_KEY,TITLE);
                locationIntent.putExtra(DESCRIPTION_KEY,DESCRIPTION);
                locationIntent.putExtra(LATITUDE, latitude);
                locationIntent.putExtra(LONGITUDE, longitude);

                //cambia el fragment
                setFragment(1);

        }
        return false;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = intent.getDoubleExtra(LATITUDE, 0);
            longitude = intent.getDoubleExtra(LONGITUDE, 0);
        }
    };

    public void startService() {
        Intent mServiceIntent = new Intent(getApplicationContext(), RunGPS.class);
        startService(mServiceIntent);
    }

    //metodo para controlar los permisos de gps y su servicio
    private void getGPS(){
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //este servicio inicia el gps
            startService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), R.string.gps_ok, Toast.LENGTH_SHORT).show();
                startService();
            } else {
                Toast.makeText(getApplicationContext(), R.string.gps_no_ok, Toast.LENGTH_SHORT).show();
            }
        }
    }



    //metodo para cambiar fragmentos
    public void setFragment(int fragment){
        FragmentManager manager = getSupportFragmentManager();

        switch(fragment){
            //0 sera el fragment que se muestre al iniciar la app
            case 0:
                locationFragment = new HomeFragment();
                manager.beginTransaction().add(R.id.fragmentContainer, locationFragment).commit();
                break;
            case 1:
                locationFragment = new CurrentLocation();
                manager.beginTransaction().replace(R.id.fragmentContainer, locationFragment).commit();
                break;
        }
    }

}