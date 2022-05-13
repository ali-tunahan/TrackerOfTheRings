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

public class RouteInfoFragment extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static Route routeToDisplay = null;
    private static GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            try {
                //clear map, display all stops in selected route and all active vehicles on selected route
                MainActivity.routeInfoLocationHandler.updateGPS();
                SecondFragment.getController().updateVehicleLocations();

                mMap.clear();

                Route currentRoute = routeToDisplay;

                for (int k = 0; k < LocationController.getVehicles().size(); k++) {
                    if (LocationController.getVehicles().get(k).getUsername() != "kendrick" && LocationController.getVehicles().get(k).getLocation() != null && LocationController.getVehicles().get(k).isActive() && LocationController.getVehicles().get(k).getCurrentRoute().getName().equals(currentRoute.getName())) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(LocationController.getVehicles().get(k).getLocation().getLatitude(), LocationController.getVehicles().get(k).getLocation().getLongitude())).title(LocationController.getVehicles().get(k).getUsername()));
                    }
                }

                for (int j = 0; j < currentRoute.getStopsList().size(); j++) {
                    if (currentRoute.getStopsList().get(j).getCompanyID().equals(SecondFragment.getUsersCompanyID())) {
                        LatLng stopLatLong = new LatLng(currentRoute.getStopsList().get(j).getLocation().getLatitude(), currentRoute.getStopsList().get(j).getLocation().getLongitude());
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(stopLatLong).title(currentRoute.getStopsList().get(j).getName()));
                    }
                }


                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.routeInfoLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.routeInfoLocationHandler.getmLastKnownLocation().getLongitude())).title("You are here!"));
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
            //clear map, display all stops in selected route and all active vehicles on selected route
            mMap = googleMap;
            Route currentRoute = routeToDisplay;
            MainActivity.routeInfoLocationHandler.startLocationUpdates();
            MainActivity.routeInfoLocationHandler.updateGPS();
            LatLng currentLatLong = new LatLng(MainActivity.routeInfoLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.routeInfoLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("You are here!"));

            for(int k = 0; k < currentRoute.getActiveVehicles().size(); k++) {
                Vehicle currentVehicle = currentRoute.getActiveVehicles().get(k);
                if(currentVehicle.getUsername() != "kendrick" && currentVehicle.getLocation() != null && currentVehicle.isActive()) {
                    System.out.println("this is i value: " + k + " this is vehicle: " + currentVehicle.getUsername());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(currentVehicle.getLocation().getLatitude(), currentVehicle.getLocation().getLongitude())).title(currentVehicle.getUsername()));
                }
            }

            for(int j = 0; j < currentRoute.getStopsList().size(); j++){
                if(currentRoute.getStopsList().get(j).getCompanyID().equals(SecondFragment.getUsersCompanyID())) {
                    LatLng stopLatLong = new LatLng(currentRoute.getStopsList().get(j).getLocation().getLatitude(), currentRoute.getStopsList().get(j).getLocation().getLongitude());
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(stopLatLong).title(currentRoute.getStopsList().get(j).getName()));
                }
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,18.0f));//moves camera (change to current location)
        }
    };

    public static Route getRouteToDisplay() {
        return routeToDisplay;
    }

    public static void setRouteToDisplay(Route routeToDisplay) {
        RouteInfoFragment.routeToDisplay = routeToDisplay;
    }

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
                NavHostFragment.findNavController(RouteInfoFragment.this)
                        .navigate(R.id.action_routeInfoFragment_to_mapsFragment);
            }
        });

        showBottomSheetDialog();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){
        //generic number of buttons retreived from LocatonController according to number of stops of the route
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_stop_route_info);
        LinearLayout linear1 = bottomBar.findViewById(R.id.linear);
        TextView text = bottomBar.findViewById(R.id.info);
        text.setText(routeToDisplay.getName());
        text.append("\n---STOPS---\n" );
        if(routeToDisplay.getStopsList() != null) {
            for(int i = 0; i < routeToDisplay.getStopsList().size(); i++) {
                //each button is for a stop on the route
                Button b = new Button(this.getContext());
                b.setText(routeToDisplay.getStopsList().get(i).getName());
                b.setId(i);
                b.setTextSize(20);
                b.setTextColor(Color.parseColor("#FFFFFFFF"));
                b.setBackgroundColor(R.color.teal_200);
                b.setGravity(Gravity.CENTER);
                b.setPadding(15, 10, 15, 10);
                b.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
                int finalI = i;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomBar.hide();
                        for(int i = 0; i < LocationController.getStops().size(); i++){
                            if(LocationController.getStops().get(i).equals(routeToDisplay.getStopsList().get(finalI))){
                                //pass clicked stop info to setStopToDisplay
                                StopInfoFragment.setStopToDisplay(LocationController.getStops().get(i));
                            }
                        }
                        NavHostFragment.findNavController(RouteInfoFragment.this)
                                .navigate(R.id.action_routeInfoFragment_to_stopInfoFragment);
                    }
                });
                linear1.addView(b);
            }
        }
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