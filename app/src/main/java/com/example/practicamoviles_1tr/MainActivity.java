package com.example.practicamoviles_1tr;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.practicamoviles_1tr.common.SaveLocation;
import com.example.practicamoviles_1tr.fragments.CurrentLocation;
import com.example.practicamoviles_1tr.fragments.FavouritesFragment;
import com.example.practicamoviles_1tr.fragments.GymFragment;
import com.example.practicamoviles_1tr.fragments.HomeFragment;
import com.example.practicamoviles_1tr.fragments.PoolsFragment;
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

import static com.example.practicamoviles_1tr.common.Constantes.CURRENT_LOCATION_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.CURRENT_LOCATION_LONGITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.INTENT_LOCALIZATION_ACTION;
import static com.example.practicamoviles_1tr.common.Constantes.LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.LONGITUDE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationView navView;
    Double latitude=0.0;
    Double longitude=0.0;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);

        setToolbar();
        //llamamos al metodo de mas abajo que inicia el servicio del gps
        getGPS();
        //hay que establecer el fragment de inicio
        setFragment(0);
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
                //System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+latitude+"lon: "+longitude);
                setFragment(1);
                break;

            case R.id.guardarubicacion:
                setFragment(5);
                break;
            case R.id.instalacionesdeportivas:
                setFragment(2);
                break;
            case R.id.piscinas:
                setFragment(3);
                break;
            case R.id.favoritos:
                setFragment(4);
                break;
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(INTENT_LOCALIZATION_ACTION));
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
    public void setFragment(int fragmentNum){
        FragmentManager manager = getSupportFragmentManager();

        //este bundle se encarga de pasar datos a los fragments como si fuese un intent
        Bundle bundle = new Bundle();

        switch(fragmentNum){
            //0 sera el fragment que se muestre al iniciar la app
            case 0:
                fragment = new HomeFragment();
                manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
                break;
            //1 para la opcion de mostrar la ubicacion actual
            case 1:
                bundle.putDouble(CURRENT_LOCATION_LATITUDE, latitude);
                bundle.putDouble(CURRENT_LOCATION_LONGITUDE, longitude);
                fragment = new CurrentLocation();
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                break;
            //2 para el fragment de instalaciones deportivas(mapa)
            case 2:
                bundle.putDouble(CURRENT_LOCATION_LATITUDE, latitude);
                bundle.putDouble(CURRENT_LOCATION_LONGITUDE, longitude);
                fragment = new GymFragment(longitude, latitude);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                break;
                //3 para mostrar las piscinas
            case 3:
                bundle.putDouble(CURRENT_LOCATION_LATITUDE, latitude);
                bundle.putDouble(CURRENT_LOCATION_LONGITUDE, longitude);
                fragment = new PoolsFragment(longitude, latitude);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                break;
            //4 para mostrar los favoritos
            case 4:
                fragment = new FavouritesFragment();
                manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                break;
            case 5:
                new SaveLocation(getApplicationContext(), latitude, longitude).saveLocation();
                break;
        }
    }

}