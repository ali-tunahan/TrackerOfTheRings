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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class companyStopsFragment extends Fragment {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int DEFAULT_UPDATE_INTERVAL = 5;
    public static final int FASTEST_UPDATE_INTERVAL = 1;
    private static boolean isEntered = false;
    public static ArrayList<Stop> stopsList = new ArrayList<Stop>(); //later change with actual stops list stop array list

    private GoogleMap mMap;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public Location currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

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
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                NavHostFragment.findNavController(companyStopsFragment.this)
                        .navigate(R.id.action_companyStopsFragment_to_companyMapsFragment);
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
                Location location = locationResult.getLastLocation();
            }
        };
 */
        showBottomSheetDialog();
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    public void showBottomSheetDialog(){
        final BottomSheetDialog bottomBar = new BottomSheetDialog(this.getContext());
        bottomBar.setContentView(R.layout.bottom_dialog_company_stops_routes_vehicles);
        TextView text = new TextView(this.getContext());
        text.append("STOPS LIST");
        LinearLayout linear1 = bottomBar.findViewById(R.id.list);
        text.setGravity(Gravity.CENTER);
        linear1.addView(text);
        //change with actual stops and proper locations
        if(!isEntered){
            stopsList.add(new Stop("Tunus", new Location("provider1"),"id1"));
            stopsList.add(new Stop("METU Subway", new Location("provider2"),"id1"));
            stopsList.add(new Stop("Bilkent Library", new Location("provider3"),"id1"));
            stopsList.add(new Stop("Bilkent Library1", new Location("provider4"),"id1"));
            stopsList.add(new Stop("Bilkent Library2", new Location("provider5"),"id1"));
            stopsList.add(new Stop("Bilkent Library3", new Location("provider6"),"id1"));
            stopsList.add(new Stop("Bilkent Library4", new Location("provider7"),"id1"));
            stopsList.add(new Stop("Bilkent Library5", new Location("provider8"),"id1"));
            stopsList.add(new Stop("Bilkent Library6", new Location("provider9"),"id1"));
            stopsList.add(new Stop("Bilkent Library7", new Location("provider10"),"id1"));
            stopsList.add(new Stop("Bilkent Library8", new Location("provider11"),"id1"));
            stopsList.add(new Stop("Bilkent Library9", new Location("provider12"),"id1"));
            stopsList.add(new Stop("Bilkent Library10", new Location("provider13"),"id1"));
        }
        for(int i = 0; i < stopsList.size(); i++){//delete after setting proper stops with actual route info
            if(!isEntered) {
                Route newRoute0 = new Route("Route" + i);
                Route newRoute1 = new Route("Route" + 2 * i);
                Route newRoute2 = new Route("Route" + 3 * i);
                newRoute0.addStop(stopsList.get(i));
                newRoute1.addStop(stopsList.get(i));
                newRoute2.addStop(stopsList.get(i));
            }
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
                    bottomBar.hide();
                    CompanyStopInfo.setStopToDisplay(stopsList.get(finalI));
                    NavHostFragment.findNavController(companyStopsFragment.this)
                            .navigate(R.id.action_companyStopsFragment_to_companyStopInfoFragment);
                }
            });
        }
        bottomBar.findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBar.hide();
                CompanyEditStop.setStatus(CompanyEditStop.NEW);
                NavHostFragment.findNavController(companyStopsFragment.this)
                        .navigate(R.id.action_companyStopsFragment_to_companyEditStop);
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