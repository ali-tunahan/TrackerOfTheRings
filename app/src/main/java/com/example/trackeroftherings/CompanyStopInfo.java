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
import com.example.trackeroftherings.databinding.FragmentCompanyStopInfoBinding;
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

public class CompanyStopInfo extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static Stop stopToDisplay = null;
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
                MainActivity.companyStopInfoLocationHandler.updateGPS();
                DriverCompanyLoginFragment.getController().updateVehicleLocations();

                mMap.clear();
                LatLng stopLatLong = new LatLng(stopToDisplay.getLocation().getLatitude(), stopToDisplay.getLocation().getLongitude());
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(stopToDisplay.getName()));

                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLongitude()));
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
            MainActivity.companyStopInfoLocationHandler.startLocationUpdates();
            MainActivity.companyStopInfoLocationHandler.updateGPS();
            LatLng currentLatLong = new LatLng(MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("Lat: " + MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyStopInfoLocationHandler.getmLastKnownLocation().getLongitude()));
            LatLng stopLatLong = new LatLng(stopToDisplay.getLocation().getLatitude(), stopToDisplay.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(stopToDisplay.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stopLatLong,18.0f));//moves camera (change to current location)

        }
    };

    public static Stop getStopToDisplay() {
        return stopToDisplay;
    }

    public static void setStopToDisplay(Stop stopToDisplay) {
        CompanyStopInfo.stopToDisplay = stopToDisplay;
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
                NavHostFragment.findNavController(CompanyStopInfo.this)
                        .navigate(R.id.action_companyStopInfoFragment_to_companyMapsFragment);
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
        BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_stop_route_vehicle_edit_info);
        TextView text = bottomBar.findViewById(R.id.info);
        LinearLayout linear1 = bottomBar.findViewById(R.id.linear);
        text.setText(stopToDisplay.getName());
        if(stopToDisplay.getRoutesList() != null) {//nothing to change here, it seems
            for(int i = 0; i < LocationController.getRoutes().size(); i++) {
                boolean check = false;
                for(int j = 0; j < LocationController.getRoutes().get(i).getStopsList().size(); j++){
                    if (LocationController.getRoutes().get(i).getStopsList().get(j) == null){
                        check = true;
                        continue;
                    }
                    if (LocationController.getRoutes().get(i).getStopsList().get(j).equals(stopToDisplay))
                        check = true;
                }
                if (check) {
                    Button b = new Button(this.getContext());
                    b.setText(LocationController.getRoutes().get(i).getName());
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
                            for (int i = 0; i < LocationController.getRoutes().size(); i++) {
                                if (LocationController.getRoutes().get(i).equals(LocationController.getRoutes().get(finalI))) {
                                    CompanyRouteInfoFragment.setRouteToDisplay(LocationController.getRoutes().get(i));
                                }
                            }
                            NavHostFragment.findNavController(CompanyStopInfo.this)
                                    .navigate(R.id.action_companyStopInfoFragment_to_companyRouteInfoFragment);
                        }
                    });
                    linear1.addView(b);
                }
            }
        }
        bottomBar.findViewById(R.id.floatingActionButtonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                CompanyEditStop.setStatus(CompanyEditStop.EDIT);
                NavHostFragment.findNavController(CompanyStopInfo.this)
                        .navigate(R.id.action_companyStopInfoFragment_to_companyEditStop);
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