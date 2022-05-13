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

public class companyRoutesFragment extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static boolean isEntered = false;
    private static List<Route> routesList = new ArrayList<Route>(); //later change with actual routes list from LocationController

    private static GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public Location currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;


    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            //clear fragment, add all stops assigned to a route and all active vehicles
            try {
                MainActivity.companyRoutesLocationHandler.updateGPS();
                DriverCompanyLoginFragment.getController().updateVehicleLocations();

                mMap.clear();

                for (int i = 0; i < LocationController.getRoutes().size(); i++) {
                    Route currentRoute = LocationController.getRoutes().get(i);

                    for (int k = 0; k < LocationController.getVehicles().size(); k++) {
                        if (LocationController.getVehicles().get(k).getUsername() != "kendrick" && LocationController.getVehicles().get(k).getLocation() != null && LocationController.getVehicles().get(k).isActive()) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(LocationController.getVehicles().get(k).getLocation().getLatitude(), LocationController.getVehicles().get(k).getLocation().getLongitude())).title(LocationController.getVehicles().get(k).getUsername()));
                        }
                    }

                    for (int j = 0; j < currentRoute.getStopsList().size(); j++) {
                        if (currentRoute.getStopsList().get(j).getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID())) {
                            LatLng stopLatLong = new LatLng(currentRoute.getStopsList().get(j).getLocation().getLatitude(), currentRoute.getStopsList().get(j).getLocation().getLongitude());
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(stopLatLong).title(currentRoute.getStopsList().get(j).getName()));
                        }
                    }

                }
                }catch (NullPointerException e){

            }
        }

        @Override
        public void onError(String error) {
            //Toast.makeText(MainActivity, "error", Toast.LENGTH_SHORT).show();

        }
    };

    public static List<Route> getRoutesList() {
        return routesList;
    }

    public static void setRoutesList(List<Route> routesList) {
        companyRoutesFragment.routesList = routesList;
    }

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
            //clear fragment, add all stops assigned to a route and all active vehicles
            mMap = googleMap;
            googleMap.clear();
            LatLng currentLatLong = new LatLng(MainActivity.companyRoutesLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyRoutesLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("Lat: " + MainActivity.companyRoutesLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyRoutesLocationHandler.getmLastKnownLocation().getLongitude()));

            MainActivity.companyRoutesLocationHandler.startLocationUpdates();
            MainActivity.companyRoutesLocationHandler.updateGPS();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,18.0f));//moves camera (change to current location)
            for (int i = 0; i < LocationController.getRoutes().size(); i++) {
                Route currentRoute = LocationController.getRoutes().get(i);

                for (int k = 0; k < currentRoute.getActiveVehicles().size(); k++) {
                    Vehicle currentVehicle = currentRoute.getActiveVehicles().get(k);
                    if (currentVehicle.getUsername() != "kendrick" && currentVehicle.getLocation() != null && currentVehicle.isActive()) {
                        System.out.println("this is i value: " + i + " this is vehicle: " + currentVehicle.getUsername());
                        mMap.addMarker(new MarkerOptions().position(new LatLng(currentVehicle.getLocation().getLatitude(), currentVehicle.getLocation().getLongitude())).title("Lat: " + currentVehicle.getLocation().getLatitude() + " , Long: " + currentVehicle.getLocation().getLongitude()));
                    }
                }

                for (int j = 0; j < currentRoute.getStopsList().size(); j++) {
                    if (currentRoute.getStopsList().get(j) == null){
                        return;
                    }
                    if (currentRoute.getStopsList().get(j).getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID())) {
                        LatLng stopLatLong = new LatLng(currentRoute.getStopsList().get(j).getLocation().getLatitude(), currentRoute.getStopsList().get(j).getLocation().getLongitude());
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(stopLatLong).title("Lat: " + stopLatLong.latitude + " , Long: " + stopLatLong.longitude));
                    }
                }

            }
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("Lat: " + MainActivity.companyRoutesLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyRoutesLocationHandler.getmLastKnownLocation().getLongitude()));

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
                NavHostFragment.findNavController(companyRoutesFragment.this)
                        .navigate(R.id.action_companyRoutesFragment_to_companyMapsFragment);
            }
        });

        showBottomSheetDialog();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){
        //add generic number of buttons according to route number retreived from LocationController
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_company_stops_routes_vehicles);
        TextView text = new TextView(this.getContext());
        text.append("ROUTES LIST");
        LinearLayout linear1 = bottomBar.findViewById(R.id.list);
        text.setGravity(Gravity.CENTER);
        linear1.addView(text);
        routesList = LocationController.getRoutes();//change with actual routes list

        for(int i = 0; i < routesList.size(); i++){

            Button b = new Button(this.getContext());
            b.setText(routesList.get(i).getName());
            b.setId(i);
            b.setTextSize(20);
            b.setTextColor(Color.parseColor("#FFFFFFFF"));
            b.setBackgroundColor(R.color.teal_200);
            b.setGravity(Gravity.CENTER);
            b.setPadding(15, 10, 15, 10);
            b.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
            linear1.addView(b);
            int finalI = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomBar.hide();
                    //pass selected route info to CompanyRouteInfoFragment
                    CompanyRouteInfoFragment.setRouteToDisplay(routesList.get(finalI));
                    NavHostFragment.findNavController(companyRoutesFragment.this)
                            .navigate(R.id.action_companyRoutesFragment_to_companyRouteInfoFragment);
                }
            });
        }
        bottomBar.findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            //add new route
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                //pass the information that it is a new route not editing of an existing one
                CompanyEditRoute.setStatus(CompanyEditRoute.NEW);
                NavHostFragment.findNavController(companyRoutesFragment.this)
                        .navigate(R.id.action_companyRoutesFragment_to_companyEditRoute);
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