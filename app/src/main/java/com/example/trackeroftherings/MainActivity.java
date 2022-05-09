package com.example.trackeroftherings;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.trackeroftherings.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public static LocationHandler locationHandler;
    public static LocationHandler driverLocationHandler;
    public OnLocationUpdateListener onLocationUpdateListener;
    public OnLocationUpdateListener driverOnLocationUpdateListener;
    public static Vehicle kendrick = new Vehicle("kendrick", "123456", "123");
    public static VehicleUpdater vehicleUpdater;
    public static LocationPlus dummyLoc = new LocationPlus();

    public static Stop dummyStop = new Stop("name", dummyLoc, "123");
    public static List<Stop> dummystops = new ArrayList<Stop>();
    public static  Route dummyRoute = new Route("dummies", "123");
    public static List<Vehicle> dummyvehiclelist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        dummyvehiclelist.add(kendrick);
        dummystops.add(dummyStop);
        dummyRoute.addStop(dummyStop);
        dummyRoute.addActiveVehicle(kendrick);
        kendrick.setCurrentRoute(dummyRoute);
        DatabaseUtility.add(kendrick);

        onLocationUpdateListener = MapsFragment.onLocationUpdateListener;
        driverOnLocationUpdateListener = DriverMapsFragment.onLocationUpdateListener;
        locationHandler = new LocationHandler(MainActivity.this, onLocationUpdateListener);
      
        driverLocationHandler = new LocationHandler(MainActivity.this, driverOnLocationUpdateListener);
        vehicleUpdater = new VehicleUpdater(kendrick, MainActivity.this);




    }

    public MainActivity getMain() {
        return  this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}