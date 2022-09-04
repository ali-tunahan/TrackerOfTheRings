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
import android.widget.EditText;
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

public class CompanyEditVehicle extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    public static final int EMPTY = 0;
    public static final int EDIT = 1;
    public static final int NEW = 2;
    private static int status = EMPTY;
    private static GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public static void setStatus(int status) {
        CompanyEditVehicle.status = status;
    }

    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            try {
                MainActivity.companyEditVehicleLocationHandler.updateGPS();
                DriverCompanyLoginFragment.getController().updateVehicleLocations();

                mMap.clear();
                LatLng stopLatLong = new LatLng(CompanyVehicleInfo.getvehicleToDisplay().getLocation().getLatitude(), CompanyVehicleInfo.getvehicleToDisplay().getLocation().getLongitude());
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(CompanyVehicleInfo.getvehicleToDisplay().getUsername() + " status: " + CompanyVehicleInfo.getvehicleToDisplay().getIsActive()));

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
            try {
                mMap = googleMap;
                MainActivity.companyEditVehicleLocationHandler.updateGPS();
                DriverCompanyLoginFragment.getController().updateVehicleLocations();

                mMap.clear();
                LatLng stopLatLong = new LatLng(CompanyVehicleInfo.getvehicleToDisplay().getLocation().getLatitude(), CompanyVehicleInfo.getvehicleToDisplay().getLocation().getLongitude());
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(CompanyVehicleInfo.getvehicleToDisplay().getUsername() + " status: " + CompanyVehicleInfo.getvehicleToDisplay().getIsActive()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stopLatLong,18.0f));//moves camera (change to current location)
            }
            catch (NullPointerException e){

            }


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
                NavHostFragment.findNavController(CompanyEditVehicle.this)
                        .navigate(R.id.action_companyEditVehicle_to_companyMapsFragment);
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
        bottomBar.setContentView(R.layout.bottom_dialog_edit_vehicle);
        EditText userName = bottomBar.findViewById(R.id.editTextUsername);
        EditText password = bottomBar.findViewById(R.id.editTextVehiclePassword);
        if(status == EDIT) {
            userName.setText(CompanyVehicleInfo.getvehicleToDisplay().getUsername());
            password.setText(CompanyVehicleInfo.getvehicleToDisplay().getPassword());
        }
        LinearLayout linear1 = bottomBar.findViewById(R.id.linear);
        //add putting markers on map click
        bottomBar.findViewById(R.id.floatingActionButtonEditConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();

                if(status == EDIT) {
                    Vehicle selectedVehicle = null;
                    for(int i = 0; i < LocationController.getVehicles().size(); i++){
                        if(CompanyVehicleInfo.getvehicleToDisplay().equals(LocationController.getVehicles().get(i))){
                            selectedVehicle = LocationController.getVehicles().get(i);
                        }
                    }

                    selectedVehicle.changeInfo(userName.getText().toString(), password.getText().toString());

                    NavHostFragment.findNavController(CompanyEditVehicle.this)
                            .navigate(R.id.action_companyEditVehicle_to_companyVehicleInfo);
                }else if(status == NEW) {
                    Vehicle vehicle = new Vehicle(userName.getText().toString(),password.getText().toString(),DriverCompanyLoginFragment.getCompanyID());
                    DatabaseUtility.add(vehicle);
                    LocationController.addVehicle(vehicle);
                    NavHostFragment.findNavController(CompanyEditVehicle.this)
                            .navigate(R.id.action_companyEditVehicle_to_companyVehicleFragment);
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