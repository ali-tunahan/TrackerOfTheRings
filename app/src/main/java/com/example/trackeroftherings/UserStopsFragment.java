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
import android.widget.Toast;

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

public class UserStopsFragment extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static boolean isEntered = false;
    public static List<Stop> stopsList = new ArrayList<Stop>();

    private static GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public Company company;

    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            //clear the map and display all stops and vehicle from LocatonController data
            try {
                MainActivity.userStopsLocationHandler.updateGPS();
                SecondFragment.getController().updateVehicleLocations();

                mMap.clear();
                for (int i = 0; i < LocationController.getVehicles().size(); i++) {
                    Vehicle currentVehicle = LocationController.getVehicles().get(i);
                    if (currentVehicle.getUsername() != "kendrick" && currentVehicle.getLocation() != null && currentVehicle.getCompanyID().equals(SecondFragment.getUsersCompanyID()) && currentVehicle.isActive()) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(currentVehicle.getLocation().getLatitude(), currentVehicle.getLocation().getLongitude())).title(currentVehicle.getUsername()));
                    }
                }
                for (int i = 0; i < LocationController.getStops().size(); i++) {
                    if (LocationController.getStops().get(i).getCompanyID().equals(SecondFragment.getUsersCompanyID())) {
                        LatLng stopLatLong = new LatLng(LocationController.getStops().get(i).getLocation().getLatitude(), LocationController.getStops().get(i).getLocation().getLongitude());
                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(LocationController.getStops().get(i).getName()));
                    }
                }
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.userStopsLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.userStopsLocationHandler.getmLastKnownLocation().getLongitude())).title("You are here!"));
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
            //clear the map and display all stops and vehicle from LocatonController data

            mMap = googleMap;
            MainActivity.userStopsLocationHandler.startLocationUpdates();
            MainActivity.userStopsLocationHandler.updateGPS();
            for(Stop stop : LocationController.getStops()) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(stop.getLocation().getLatitude(), stop.getLocation().getLongitude())).title(stop.getName()));
            }

            googleMap.clear();
            LatLng currentLatLong = new LatLng(MainActivity.userStopsLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.userStopsLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("You are here!"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,18.0f));//moves camera (change to current location)
            for(int i = 0; i < LocationController.getStops().size(); i++){
                if(LocationController.getStops().get(i).getCompanyID().equals(SecondFragment.getUsersCompanyID())) {
                    LatLng stopLatLong = new LatLng(LocationController.getStops().get(i).getLocation().getLatitude(), LocationController.getStops().get(i).getLocation().getLongitude());
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(LocationController.getStops().get(i).getName()));
                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //set up onclicks

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
                NavHostFragment.findNavController(UserStopsFragment.this)
                        .navigate(R.id.action_userStopsFragment_to_mapsFragment);
            }
        });


        showBottomSheetDialog();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){
        //find all stops and display generic number of buttons that navigate to specific stop info fragments
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_stops_routes_info);
        TextView text = new TextView(this.getContext());
        text.append("STOPS LIST");
        LinearLayout linear1 = bottomBar.findViewById(R.id.list);
        text.setGravity(Gravity.CENTER);
        linear1.addView(text);
        if(!isEntered){
            stopsList = LocationController.getStops();
        }

        for(int i = 0; i < stopsList.size(); i++){
            Button b = new Button(this.getContext());
            b.setText(stopsList.get(i).getName());
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
                    //pass the stop that is clicked to stopInfoFragment
                    bottomBar.hide();
                    StopInfoFragment.setStopToDisplay(stopsList.get(finalI));
                    NavHostFragment.findNavController(UserStopsFragment.this)
                            .navigate(R.id.action_userStopsFragment_to_stopInfoFragment);
                }
            });
        }
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