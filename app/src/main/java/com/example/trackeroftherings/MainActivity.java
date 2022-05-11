package com.example.trackeroftherings;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.trackeroftherings.databinding.ActivityMainBinding;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
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
    public static LocationHandler companyMapsLocationHandler;
    public static LocationHandler companyStopsLocationHandler;
    public static LocationHandler companyEditStopLocationHandler;
    public static LocationHandler companyStopInfoLocationHandler;
    public static LocationHandler userRoutesLocationHandler;
    public static LocationHandler routeInfoLocationHandler;
    public static LocationHandler userStopInfoLocationHandler;
    public static  LocationHandler userStopsLocationHandler;
    public static  LocationHandler companyRoutesLocationHandler;
    public static  LocationHandler companyRouteInfoLocationHandler;
    public static  LocationHandler companyEditRouteLocationHandler;
    public static  LocationHandler companyAddStopToRouteLocationHandler;
    public OnLocationUpdateListener onLocationUpdateListener;
    public OnLocationUpdateListener driverOnLocationUpdateListener;
    public OnLocationUpdateListener companyMapsOnLocationUpdateListener;
    public OnLocationUpdateListener companyStopsOnLocationUpdateListener;
    public OnLocationUpdateListener companyEditStopOnLocationUpdateListener;
    public OnLocationUpdateListener companyStopInfoOnLocationUpdateListener;
    public OnLocationUpdateListener routeInfoOnLocationUpdateListener;
    public OnLocationUpdateListener userRoutesOnLocationUpdateListener;
    public OnLocationUpdateListener userStopsOnLocationUpdateListener;
    public OnLocationUpdateListener userStopInfoOnLocationUpdateListener;
    public OnLocationUpdateListener companyRoutesOnLocationUpdateListener;
    public OnLocationUpdateListener companyRouteInfoOnLocationUpdateListener;
    public OnLocationUpdateListener companyEditRouteOnLocationUpdateListener;
    public OnLocationUpdateListener companyAddStopToRouteOnLocationUpdateListener;
    public static LocationController controller;
    public MainActivity mainActivity = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://stackoverflow.com/questions/63007726/navigation-component-how-to-navigate-from-activity-to-a-fragment
                NavController navController = NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main));
                navController.navigateUp();
                navController.navigate(R.id.FirstFragment);
            }
        });
        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);//https://stackoverflow.com/questions/40058799/remove-toolbar-title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        onLocationUpdateListener = MapsFragment.onLocationUpdateListener;
        driverOnLocationUpdateListener = DriverMapsFragment.onLocationUpdateListener;
        companyMapsOnLocationUpdateListener = CompanyMapsFragment.onLocationUpdateListener;
        companyStopsOnLocationUpdateListener = companyStopsFragment.onLocationUpdateListener;
        companyRoutesOnLocationUpdateListener = companyRoutesFragment.onLocationUpdateListener;
        companyRouteInfoOnLocationUpdateListener = CompanyRouteInfoFragment.onLocationUpdateListener;
        companyEditRouteOnLocationUpdateListener = CompanyEditRoute.onLocationUpdateListener;
        companyAddStopToRouteOnLocationUpdateListener = CompanyAddStopToRoute.onLocationUpdateListener;
        companyEditStopOnLocationUpdateListener = CompanyEditStop.onLocationUpdateListener;
        companyStopInfoOnLocationUpdateListener = CompanyStopInfo.onLocationUpdateListener;
        userRoutesOnLocationUpdateListener = UserRoutesFragment.onLocationUpdateListener;
        routeInfoOnLocationUpdateListener = RouteInfoFragment.onLocationUpdateListener;
        userStopInfoOnLocationUpdateListener = StopInfoFragment.onLocationUpdateListener;
        userStopsOnLocationUpdateListener = UserStopsFragment.onLocationUpdateListener;
        locationHandler = new LocationHandler(MainActivity.this, onLocationUpdateListener);
        driverLocationHandler = new LocationHandler(MainActivity.this, driverOnLocationUpdateListener);
        userRoutesLocationHandler = new LocationHandler(MainActivity.this, userRoutesOnLocationUpdateListener);
        companyMapsLocationHandler = new LocationHandler(MainActivity.this, companyMapsOnLocationUpdateListener);
        companyStopsLocationHandler = new LocationHandler(MainActivity.this, companyStopsOnLocationUpdateListener);
        companyRoutesLocationHandler = new LocationHandler(MainActivity.this, companyRoutesOnLocationUpdateListener);
        companyRouteInfoLocationHandler = new LocationHandler(MainActivity.this, companyRouteInfoOnLocationUpdateListener);
        companyEditRouteLocationHandler = new LocationHandler(MainActivity.this, companyEditRouteOnLocationUpdateListener);
        companyAddStopToRouteLocationHandler = new LocationHandler(MainActivity.this, companyAddStopToRouteOnLocationUpdateListener);
        companyEditStopLocationHandler = new LocationHandler(MainActivity.this, companyEditStopOnLocationUpdateListener);
        companyStopInfoLocationHandler = new LocationHandler(MainActivity.this, companyStopInfoOnLocationUpdateListener);
        routeInfoLocationHandler = new LocationHandler(MainActivity.this, routeInfoOnLocationUpdateListener);
        userStopsLocationHandler = new LocationHandler(MainActivity.this, userStopsOnLocationUpdateListener);
        userStopInfoLocationHandler = new LocationHandler(MainActivity.this, userStopInfoOnLocationUpdateListener);


        LoginUtility utilityObject = new LoginUtility();


        /*Vehicle vroom = new Vehicle("vroom", "123", "123");
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

       Stop test = new Stop("stopppTest", new LocationPlus(), "123");
        Location testLocation = new Location("");
        testLocation.setLatitude(40.703234);
        testLocation.setLongitude(32.771808);
        LocationPlus testLocationPlus = new LocationPlus(testLocation);
        test.setLocation(testLocationPlus);
        DatabaseUtility.add(test);*/




        //used for uploading some objects to database do not delete
        /*
        Company c1 = new Company("dogo","123456","123" );
        DatabaseUtility.add(c1);
        /*
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

        v1.setCurrentRoute(r1,true);
        v2.setCurrentRoute(r2,true);

        v1.setActive(true,false);
        v2.setActive(true,false);

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


        return super.onOptionsItemSelected(item);
    }

    @Override     public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



}