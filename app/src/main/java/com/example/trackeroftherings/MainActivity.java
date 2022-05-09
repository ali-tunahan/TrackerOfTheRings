package com.example.trackeroftherings;

import android.location.Location;
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
    public static LocationHandler userRoutesLocationHandler;
    public static  LocationHandler userStopsLocationHandler;
    public OnLocationUpdateListener onLocationUpdateListener;
    public OnLocationUpdateListener driverOnLocationUpdateListener;
    public OnLocationUpdateListener userRoutesOnLocationUpdateListener;
    public OnLocationUpdateListener userStopsOnLocationUpdateListener;
    public MainActivity mainActivity = this;

    //public static VehicleUpdater vehicleUpdater;
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
        //dummyvehiclelist.add(kendrick);
        //dummystops.add(dummyStop);
        //dummyRoute.addStop(dummyStop);
        //.addActiveVehicle(kendrick);
        //kendrick.setCurrentRoute(dummyRoute);
        //DatabaseUtility.add(kendrick);

        onLocationUpdateListener = MapsFragment.onLocationUpdateListener;
        driverOnLocationUpdateListener = DriverMapsFragment.onLocationUpdateListener;
        userRoutesOnLocationUpdateListener = UserRoutesFragment.onLocationUpdateListener;
        userStopsOnLocationUpdateListener = UserStopsFragment.onLocationUpdateListener;
        locationHandler = new LocationHandler(MainActivity.this, onLocationUpdateListener);
        driverLocationHandler = new LocationHandler(MainActivity.this, driverOnLocationUpdateListener);
        userRoutesLocationHandler = new LocationHandler(MainActivity.this, userRoutesOnLocationUpdateListener);
        userStopsLocationHandler = new LocationHandler(MainActivity.this, userStopsOnLocationUpdateListener);
        Vehicle vroom = new Vehicle("vroom", "123", "123");
        Location vroomLocation = new Location("");
        vroomLocation.setLatitude(41.003234);
        vroomLocation.setLongitude(29.071808);
        LocationPlus vroomLocationPlus = new LocationPlus(vroomLocation);
        vroom.setLocation(vroomLocationPlus);
        DatabaseUtility.add(vroom);

        Vehicle greer = new Vehicle("greer", "123", "123");
        Location greerLocation = new Location("");
        greerLocation.setLatitude(41.608048);
        greerLocation.setLongitude(33.357822);
        LocationPlus greerLocationPlus = new LocationPlus(greerLocation);
        greer.setLocation(greerLocationPlus);
        DatabaseUtility.add(greer);


        LoginUtility utilityObject = new LoginUtility();

        //used for uploading some objects to database do not delete
        /*
        Company c1 = new Company("Fellowship of the Ring ltd.şti","123456","123" );

        Stop s1 = new Stop("bilkent", new LocationPlus(), "123");
        Stop s2 = new Stop("dorm", new LocationPlus(), "123");
        Stop s3 = new Stop("tunus", new LocationPlus(), "123");

        DatabaseUtility.add(s1);
        DatabaseUtility.add(s2);
        DatabaseUtility.add(s3);

        Route r1 = new Route("Tunus Route" , "123");
        Route r2 = new Route("Nizamiye Route", "123");

        Vehicle v1 = new Vehicle("tunus driver","123456" ,"123");
        Vehicle v2 = new Vehicle("nizamiye driver","123456","123");


        r1.addStop(s1);
        r1.addStop(s2);
        r1.addStop(s3);

        r2.addStop(s1);
        r2.addStop(s2);

        c1.addStop(s1);
        c1.addStop(s2);
        c1.addStop(s3);

        c1.addRoute(r1);
        c1.addRoute(r2);

        v1.setCurrentRoute(r1);
        v2.setCurrentRoute(r2);

        v1.setActive(true);
        v2.setActive(true);

        c1.addVehicle(v1);
        c1.addVehicle(v2);

        DatabaseUtility.add(c1);
        DatabaseUtility.add(r1);
        DatabaseUtility.add(r2);
        DatabaseUtility.add(v1);
        DatabaseUtility.add(v2);*/

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