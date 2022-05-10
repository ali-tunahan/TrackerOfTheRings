package com.example.trackeroftherings;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class CompanyEditRoute extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    public static final int EMPTY = 0;
    public static final int EDIT = 1;
    public static final int NEW = 2;
    private static Route tempRoute = new Route();
    private static int status = EMPTY;
    private TextView selectedStopText = null;
    private int selectedStopIndex = 0;
    private static GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public LocationPlus currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public static void setStatus(int status) {
        CompanyEditRoute.status = status;
    }
    public static int getStatus() {
        return status;
    }
    public static Route getTempRoute() {
        return tempRoute;
    }

    public static OnLocationUpdateListener onLocationUpdateListener = new OnLocationUpdateListener() {
        @Override
        public void onLocationChange(LocationPlus location) {
            try {
                MainActivity.companyEditRouteLocationHandler.updateGPS();
                DriverCompanyLoginFragment.getController().updateVehicleLocations();

                mMap.clear();
                Route currentRoute = null;
                if(status == EDIT)
                    currentRoute = CompanyRouteInfoFragment.getRouteToDisplay();
                else if(status == NEW)
                    currentRoute = tempRoute;
                if(currentRoute != null) {
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
                }


                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLongitude()));
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
            Route currentRoute = null;
            if(status == EDIT)
                currentRoute = CompanyRouteInfoFragment.getRouteToDisplay();
            else if(status == NEW && tempRoute != null)
                currentRoute = tempRoute;
            MainActivity.companyEditRouteLocationHandler.startLocationUpdates();
            MainActivity.companyEditRouteLocationHandler.updateGPS();
            LatLng currentLatLong = new LatLng(MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(currentLatLong).title("Lat: " + MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLongitude()));

            if(currentRoute != null) {
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
            }


            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(new LatLng(MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLatitude(), MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLongitude())).title("Lat: " + MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLatitude() + " , Long: " + MainActivity.companyEditRouteLocationHandler.getmLastKnownLocation().getLongitude()));
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
                NavHostFragment.findNavController(CompanyEditRoute.this)
                        .navigate(R.id.action_companyEditRoute_to_companyMapsFragment);
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
        if(tempRoute == null) {
            if (status == EDIT)
                tempRoute = CompanyRouteInfoFragment.getRouteToDisplay();
            else if (status == NEW)
                tempRoute = new Route("", DriverCompanyLoginFragment.getCompanyID());
        }
        showBottomSheetDialog();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){

        BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_edit_route);

        EditText routeName = bottomBar.findViewById(R.id.editTextRouteName);
        if(status == EDIT) {
            routeName.setText(CompanyRouteInfoFragment.getRouteToDisplay().getName());
            tempRoute = new Route(CompanyRouteInfoFragment.getRouteToDisplay());
        }else if(status == NEW && tempRoute == null){
            tempRoute = new Route(routeName.getText().toString(),DriverCompanyLoginFragment.getCompanyID());
        }
        LinearLayout linear1 = bottomBar.findViewById(R.id.linear);
            for (int i = 0; i < tempRoute.getStopsList().size(); i++) {
                TextView stopText = new TextView(this.getContext());
                stopText.setText(tempRoute.getStopsList().get(i).getName());
                stopText.setId(i);
                stopText.setTextSize(20);
                stopText.setTextColor(Color.parseColor("#FFFFFFFF"));
                stopText.setBackgroundColor(R.color.teal_200);
                stopText.setGravity(Gravity.CENTER);
                stopText.setPadding(15, 10, 15, 10);
                stopText.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
                int finalI = stopText.getId();
                stopText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedStopText == null) {
                            stopText.setBackgroundColor(Color.parseColor("#DC952D"));
                            selectedStopText = stopText;
                            selectedStopIndex = stopText.getId();
                        } else {
                            int biggerIndex = Math.max(selectedStopIndex, stopText.getId());
                            int smallerIndex = Math.min(selectedStopIndex, stopText.getId());
                            if (biggerIndex == smallerIndex){
                                Toast.makeText(getContext(), "Please select a stop different from the first one!", Toast.LENGTH_SHORT).show();
                                stopText.setBackgroundColor(R.color.teal_200);
                            }else{
                                float tempY = stopText.getY();
                                stopText.setY(selectedStopText.getY());
                                selectedStopText.setY(tempY);
                                selectedStopText.setBackgroundColor(R.color.teal_200);
                                Stop tempStop = tempRoute.getStopsList().get(smallerIndex);
                                tempRoute.getStopsList().remove(smallerIndex);
                                tempRoute.getStopsList().add(smallerIndex, tempRoute.getStopsList().get(biggerIndex - 1));
                                tempRoute.getStopsList().remove(biggerIndex);
                                tempRoute.getStopsList().add(biggerIndex, tempStop);
                                int tempTextIndex = stopText.getId();
                                stopText.setId(selectedStopText.getId());
                                selectedStopText.setId(tempTextIndex);

                            }
                            selectedStopText = null;
                            selectedStopIndex = 0;

                        }
                    }
                });
                linear1.addView(stopText);

        }
        bottomBar.findViewById(R.id.floatingActionButtonEditConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();

                if(status == EDIT) {
                    //set the new location of the stop here
                    Route selectedRoute = null;
                    for(int i = 0; i < LocationController.getRoutes().size(); i++){
                        if(tempRoute.equals(LocationController.getRoutes().get(i))){
                            selectedRoute = LocationController.getRoutes().get(i);
                        }
                    }
                    selectedRoute.setName(routeName.getText().toString(),true);
                    tempRoute = null;

                    NavHostFragment.findNavController(CompanyEditRoute.this)
                            .navigate(R.id.action_companyEditRoute_to_companyRouteInfoFragment);
                }else if(status == NEW) {

                    tempRoute.setName(routeName.getText().toString(), false);
                    tempRoute.setCompanyID(DriverCompanyLoginFragment.getCompanyID());
                    DatabaseUtility.add(tempRoute);
                    LocationController.addRoute(tempRoute);
                    NavHostFragment.findNavController(CompanyEditRoute.this)
                            .navigate(R.id.action_companyEditRoute_to_companyRoutesFragment);
                }
            }
        });
        bottomBar.findViewById(R.id.floatingActionButtonAddStopToRoute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                NavHostFragment.findNavController(CompanyEditRoute.this)
                        .navigate(R.id.action_companyEditRoute_to_companyAddStopToRoute2);
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