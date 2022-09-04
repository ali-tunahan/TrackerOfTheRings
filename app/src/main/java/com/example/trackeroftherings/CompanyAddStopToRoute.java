package com.example.trackeroftherings;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
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

import com.example.trackeroftherings.databinding.FragmentMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CompanyAddStopToRoute extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static boolean isEntered = false;

    private static List<Stop> stopsListToAdd = new ArrayList<Stop>(); //later change with actual stops list stop array list
    public static List<Stop> stopsList = new ArrayList<Stop>(); //later change with actual stops list stop array list

    private static GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;


    public static List<Stop> getStopsListToAdd() {
        return stopsListToAdd;
    }

    public static void addStopsListToAdd(Stop s) {
        CompanyAddStopToRoute.stopsListToAdd.add(s);
    }

    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            try {
                MainActivity.companyAddStopToRouteLocationHandler.updateGPS();
                DriverCompanyLoginFragment.getController().updateVehicleLocations();

                mMap.clear();

                Route currentRoute = CompanyRouteInfoFragment.getRouteToDisplay();

                for (int k = 0; k < currentRoute.getActiveVehicles().size(); k++) {
                    Vehicle currentVehicle = currentRoute.getActiveVehicles().get(k);
                    if (currentVehicle.getUsername() != "kendrick" && currentVehicle.getLocation() != null && currentVehicle.isActive()) {
                        System.out.println("this is i value: " + k + " this is vehicle: " + currentVehicle.getUsername());
                        mMap.addMarker(new MarkerOptions().position(new LatLng(currentVehicle.getLocation().getLatitude(), currentVehicle.getLocation().getLongitude())).title("Lat: " + currentVehicle.getLocation().getLatitude() + " , Long: " + currentVehicle.getLocation().getLongitude()));
                    }
                }

                for (int j = 0; j < currentRoute.getStopsList().size(); j++) {
                    if (currentRoute.getStopsList().get(j).getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID())) {
                        LatLng stopLatLong = new LatLng(currentRoute.getStopsList().get(j).getLocation().getLatitude(), currentRoute.getStopsList().get(j).getLocation().getLongitude());
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(stopLatLong).title("Lat: " + stopLatLong.latitude + " , Long: " + stopLatLong.longitude));
                    }
                }


                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLongitude()));
            }catch (NullPointerException e){

            }
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
            mMap = googleMap;
            Route currentRoute = CompanyRouteInfoFragment.getRouteToDisplay();
            MainActivity.companyAddStopToRouteLocationHandler.startLocationUpdates();
            MainActivity.companyAddStopToRouteLocationHandler.updateGPS();
            LatLng currentLatLong = new LatLng(MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("Lat: " + MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLongitude()));

            if (CompanyEditRoute.getStatus() == CompanyEditRoute.EDIT){
                for(int k = 0; k < currentRoute.getActiveVehicles().size(); k++) {
                    Vehicle currentVehicle = currentRoute.getActiveVehicles().get(k);
                    if(currentVehicle.getUsername() != "kendrick" && currentVehicle.getLocation() != null && currentVehicle.isActive()) {
                        System.out.println("this is i value: " + k + " this is vehicle: " + currentVehicle.getUsername());
                        mMap.addMarker(new MarkerOptions().position(new LatLng(currentVehicle.getLocation().getLatitude(), currentVehicle.getLocation().getLongitude())).title("Lat: " + currentVehicle.getLocation().getLatitude() + " , Long: " + currentVehicle.getLocation().getLongitude()));
                    }
                }

                for(int j = 0; j < currentRoute.getStopsList().size(); j++){
                    if(currentRoute.getStopsList().get(j).getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID())) {
                        LatLng stopLatLong = new LatLng(currentRoute.getStopsList().get(j).getLocation().getLatitude(), currentRoute.getStopsList().get(j).getLocation().getLongitude());
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(stopLatLong).title("Lat: " + stopLatLong.latitude + " , Long: " + stopLatLong.longitude));
                    }
                }


            }

            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyAddStopToRouteLocationHandler.getmLastKnownLocation().getLongitude()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,18.0f));//moves camera (change to current location)
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        binding.homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CompanyAddStopToRoute.this)
                        .navigate(R.id.action_companyAddStopToRoute2_to_companyMapsFragment);
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
        showBottomSheetDialog();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){
        stopsListToAdd.clear();
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_company_addstop_toroute);
        TextView text = new TextView(this.getContext());
        text.append("STOPS LIST");
        LinearLayout linear1 = bottomBar.findViewById(R.id.list);
        text.setGravity(Gravity.CENTER);
        linear1.addView(text);
        stopsList = LocationController.getStops();
        //change with actual stops and proper locations



        for(int i = 0; i < stopsList.size(); i++){
            TextView b = new TextView(this.getContext());
            b.setText(stopsList.get(i).getName());
            b.setId(i);
            b.setTextSize(20);
            b.setTextColor(Color.parseColor("#FFFFFFFF"));
            b.setBackgroundColor(R.color.teal_200);
            for(int j = 0; j < CompanyEditRoute.getTempRoute().getStopsList().size(); j++){
                if(CompanyEditRoute.getTempRoute().getStopsList().get(j).equals(stopsList.get(i))) {
                    stopsListToAdd.add(stopsList.get(i));
                    b.setBackgroundColor(Color.parseColor("#DC952D"));
                }
            }
            b.setGravity(Gravity.CENTER);
            b.setPadding(15, 10, 15, 10);
            b.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
            linear1.addView(b);
            int finalI = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!stopsListToAdd.contains(stopsList.get(finalI))) {
                        b.setBackgroundColor(Color.parseColor("#DC952D"));
                        stopsListToAdd.add(stopsList.get(finalI));
                    }else{
                        b.setBackgroundColor(R.color.teal_200);
                        stopsListToAdd.remove(stopsList.get(finalI));
                    }
                }
            });
        }
        bottomBar.findViewById(R.id.floatingActionButtonAddStopToRouteConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CompanyEditRoute.getStatus() == CompanyEditRoute.EDIT) {
                    Route selectedRoute = null;
                    for (int i = 0; i < LocationController.getRoutes().size(); i++) {
                        if (CompanyEditRoute.getTempRoute().equals(LocationController.getRoutes().get(i))) {
                            selectedRoute = LocationController.getRoutes().get(i);
                        }
                    }
                    selectedRoute.setStopsList(new ArrayList<Stop>());

                    for (Stop s : stopsListToAdd) {

                        selectedRoute.addStop(s);
                    }
                }else if(CompanyEditRoute.getStatus() == CompanyEditRoute.NEW){
                    CompanyEditRoute.getTempRoute().setStopsList(new ArrayList<Stop>());
                    for (Stop s : stopsListToAdd) {
                        CompanyEditRoute.getTempRoute().addStop(s);
                    }
                }
                stopsListToAdd.clear();
                bottomBar.hide();
                NavHostFragment.findNavController(CompanyAddStopToRoute.this)
                        .navigate(R.id.action_companyAddStopToRoute2_to_companyEditRoute);
            }
        });
        isEntered = true;
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
