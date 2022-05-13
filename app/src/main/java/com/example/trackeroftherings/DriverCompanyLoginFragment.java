package com.example.trackeroftherings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trackeroftherings.databinding.FragmentDriverLoginBinding;

public class DriverCompanyLoginFragment extends Fragment {//change the name later, this is both for companies and drivers

    private FragmentDriverLoginBinding binding;
    private static int loginState;
    public static final int COMPANY = 0;
    public static final int DRIVER = 1;
    private static Vehicle selfVehicle = null;
    private static String companyID = ""; //public since it should be used outside of this class
    public static VehicleUpdater vehicleUpdater;
    private static LocationController controller;
    public static String getCompanyID() {
        return companyID;
    }//maybe static??

    public static void setLoginState(int aLoginState) {
        loginState = aLoginState;
    }
    public static Vehicle getSelfVehicle() {
        return selfVehicle;
    }

    public static LocationController getController() {
        return controller;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentDriverLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//check credentials
                //set the credentials given to text and pass it to LoginUtility
                String aUsername = binding.editTextDriverUsername.getText().toString();
                String aPassword = binding.editTextPassword.getText().toString();
                String aCompanyID = binding.editTextCompanyID.getText().toString();
                //login state retreived from FirstFragment enables using one fragment for two usages, according to the selection the corresponding LoginUtility method is called
                if(loginState == COMPANY ){
                    if(LoginUtility.companyLogin(aUsername, aPassword, aCompanyID) != null){
                        //navigate to company front page

                        companyID = aCompanyID;
                        controller = new LocationController(companyID);
                        NavHostFragment.findNavController(DriverCompanyLoginFragment.this)
                                .navigate(R.id.action_driverLoginFragment_to_companyMapsFragment);
                    }
                    else{
                        Toast.makeText(getActivity(), "Invalid credentials, try again!", Toast.LENGTH_SHORT).show();
                    }

                }else if(loginState == DRIVER ){
                    if(LoginUtility.vehicleLogin(aUsername, aPassword, aCompanyID) != null){
                        // navigate to driver front page
                        //set proper vehicle object according to credentials
                        selfVehicle = LoginUtility.vehicleLogin(aUsername, aPassword, aCompanyID);
                        vehicleUpdater = new VehicleUpdater(DriverCompanyLoginFragment.getSelfVehicle(), (MainActivity) getActivity());
                        companyID = aCompanyID;
                        controller = new LocationController(companyID);
                        NavHostFragment.findNavController(DriverCompanyLoginFragment.this)
                                .navigate(R.id.action_driverLoginFragment_to_driverMapsFragment);
                    }else{
                        Toast.makeText(getActivity(), "Invalid credentials, try again!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}