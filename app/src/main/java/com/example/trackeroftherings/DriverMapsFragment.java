package com.example.trackeroftherings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trackeroftherings.databinding.FragmentDriverMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DriverMapsFragment extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static boolean isEntered = false;
    private static boolean confirmed = false;
    private Button selectedButton = null;
    private static GoogleMap drivermMap;
    private FragmentDriverMapsBinding binding;
    public static List<Route> routesList = new ArrayList<Route>(); //later change with actual routes list route array list
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;


    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {

            MainActivity.driverLocationHandler.updateGPS();
            DriverCompanyLoginFragment.vehicleUpdater.updateVehicle();
            drivermMap.clear();
            drivermMap.addMarker(new MarkerOptions().position(new LatLng(MainActivity.driverLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.driverLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.driverLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.driverLocationHandler.getmLastKnownLocation().getLongitude()));


        }

        @Override
        public void onError(String error) {
            //Toast.makeText(MainActivity, "error", Toast.LENGTH_SHORT).show();

        }
    };

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));//moves camera (change to current location)
            drivermMap = googleMap;
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));//moves camera (change to current location)
            googleMap.clear();
            MainActivity.driverLocationHandler.startLocationUpdates();
            MainActivity.driverLocationHandler.updateGPS();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(MainActivity.driverLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.driverLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.driverLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.driverLocationHandler.getmLastKnownLocation().getLongitude()));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDriverMapsBinding.inflate(inflater, container, false);
        binding.startLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });


/*
        setContentView(binding.getRoot());
        Button button = (Button) binding.button2;
        TextView text = (TextView) binding.textView;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationRequest = com.google.android.gms.location.LocationRequest.create();

        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL * 1000); // default check speed

        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL * 1000); //when set to most frequent update speed

        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY); // mode of location updating, currently set to best accuracy

        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // save the location
                LocationPlus location = locationResult.getLastLocation();
            }
        };
 */

        return binding.getRoot();
    }
    public void onMapReady(GoogleMap googleMap) {
        drivermMap = googleMap;


        drivermMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                drivermMap.clear();
                drivermMap.addMarker(new MarkerOptions().position(latLng).title("" + latLng.latitude + " , " + latLng.latitude));
            }
        });
        drivermMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        ArrayList<Button> buttonsList = new ArrayList<Button>();
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.driver_route_selection);
        TextView text = new TextView(this.getContext());
        text.append("ROUTES LIST");
        LinearLayout linear1 = bottomBar.findViewById(R.id.list);
        text.setGravity(Gravity.CENTER);
        linear1.addView(text);
        if(!isEntered){
            routesList = LocationController.getRoutes();
        }
        for(int i = 0; i < routesList.size(); i++){
            Button b = new Button(this.getContext());
            b.setText(routesList.get(i).getName());
            b.setId(i);
            b.setTextSize(20);
            b.setTextColor(Color.parseColor("#FFFFFFFF"));
            b.setBackgroundColor(R.color.teal_200);
            if(selectedButton != null && b.getId() == selectedButton.getId()) {
                b.setBackgroundColor(Color.parseColor("#DC952D"));
            }
            b.setGravity(Gravity.CENTER);
            b.setPadding(15, 10, 15, 10);
            b.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
            buttonsList.add(b);

            linear1.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0; j < buttonsList.size(); j++){
                        buttonsList.get(j).setBackgroundColor(R.color.teal_200);
                    }
                    selectedButton = b;
                    selectedButton.setBackgroundColor(Color.parseColor("#DC952D"));
                }
            });
        }
        isEntered = true;
        bottomBar.findViewById(R.id.floatingActionButton6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedButton != null){
                    confirmed = true;//check confirmed later to see if the driver has hit the button
                    Route selectedRoute = null;
                    for(int i = 0; i < LocationController.getRoutes().size(); i++){
                        if(routesList.get(selectedButton.getId()).equals(LocationController.getRoutes().get(i))){
                            selectedRoute = LocationController.getRoutes().get(i);
                        }
                    }
                    if(DriverCompanyLoginFragment.getSelfVehicle() != null){
                        selectedRoute.addActiveVehicle(DriverCompanyLoginFragment.getSelfVehicle(),true);
                        DriverCompanyLoginFragment.getSelfVehicle().setActive(true,true);
                        DriverCompanyLoginFragment.getSelfVehicle().setCurrentRoute(selectedRoute,true);
                    }

                    binding.cancel.setVisibility(View.VISIBLE);
                    binding.cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //https://stackoverflow.com/questions/36747369/how-to-show-a-pop-up-in-android-studio-to-confirm-an-order
                            builder.setMessage("Do you really want to cancel ongoing route?")
                                    .setTitle("Cancel Ongoing Route?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            confirmed = false;
                                            selectedButton = null;
                                            binding.cancel.setVisibility(View.INVISIBLE);
                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // CANCEL
                                        }
                                    });
                            builder.show();
                        }
                    });
                    bottomBar.hide();
                    //other driver start location actions
                }
            }
        });

        bottomBar.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}