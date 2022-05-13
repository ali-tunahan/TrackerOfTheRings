package com.example.trackeroftherings;

import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trackeroftherings.databinding.FragmentCompanyMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CompanyMapsFragment extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static GoogleMap mMap;
    private FragmentCompanyMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            //clear map, add markers on all locatable objects
            MainActivity.companyMapsLocationHandler.updateGPS();
            DriverCompanyLoginFragment.getController().updateVehicleLocations();

            mMap.clear();
            for(int i = 0; i < LocationController.getVehicles().size(); i++) {
                Vehicle currentVehicle = LocationController.getVehicles().get(i);
                if(currentVehicle.getUsername() != "kendrick" && currentVehicle.getLocation() != null && currentVehicle.getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID()) && currentVehicle.isActive()) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(currentVehicle.getLocation().getLatitude(), currentVehicle.getLocation().getLongitude())).title(currentVehicle.getUsername()));
                }
            }
            for(int i = 0; i < LocationController.getStops().size(); i++){
                if(LocationController.getStops().get(i).getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID())) {
                    LatLng stopLatLong = new LatLng(LocationController.getStops().get(i).getLocation().getLatitude(), LocationController.getStops().get(i).getLocation().getLongitude());
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(stopLatLong).title(LocationController.getStops().get(i).getName()));
                }
            }
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.companyMapsLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyMapsLocationHandler.getmLastKnownLocation().getLongitude())).title("You are here!"));

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
            //clear map, add markers on all locatable objects
            mMap = googleMap;
            googleMap.clear();
            MainActivity.companyMapsLocationHandler.startLocationUpdates();
            MainActivity.companyMapsLocationHandler.updateGPS();
            LatLng currentLatLong = new LatLng(MainActivity.companyMapsLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyMapsLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("You are here!"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,18.0f));
            for(int i = 0; i < LocationController.getStops().size(); i++){
                if(LocationController.getStops().get(i).getCompanyID().equals(DriverCompanyLoginFragment.getCompanyID())) {
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

        binding = FragmentCompanyMapsBinding.inflate(inflater, container, false);
        binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });


        return binding.getRoot();
    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("" + latLng.latitude + " , " + latLng.latitude));

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });


    }


    public void showBottomSheetDialog(){
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_company);
        //onClicks similar to the user's one difference being the vehicle button
        bottomBar.findViewById(R.id.button_stops).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                NavHostFragment.findNavController(CompanyMapsFragment.this)
                        .navigate(R.id.action_companyMapsFragment_to_companyStopsFragment);
            }
        });
        bottomBar.findViewById(R.id.button_routes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                NavHostFragment.findNavController(CompanyMapsFragment.this)
                        .navigate(R.id.action_companyMapsFragment_to_companyRoutesFragment);
            }
        });
        bottomBar.findViewById(R.id.button_vehicles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                NavHostFragment.findNavController(CompanyMapsFragment.this)
                        .navigate(R.id.action_companyMapsFragment_to_companyVehicleFragment);
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