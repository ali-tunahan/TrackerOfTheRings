package com.example.trackeroftherings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trackeroftherings.databinding.FragmentDriverLoginBinding;

public class DriverLoginFragment extends Fragment {//change the name later, this is both for companies and drivers

    private FragmentDriverLoginBinding binding;
    private static int loginState;
    public static final int COMPANY = 0;
    public static final int DRIVER = 1;
    private static String driversCompanyID = ""; //get this in static context for later

    public String getDriversCompanyID() {
        return driversCompanyID;
    }//maybe static??

    public static void setLoginState(int aLoginState) {
        loginState = aLoginState;
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
                String username = binding.editTextDriverUsername.toString();
                String password = binding.editTextPassword.toString();
                String companyID = binding.editTextCompanyID.toString();
                if(loginState == COMPANY ){
                    if(LoginUtility.companyLogin(username, password, companyID) != null){
                        //navigate to company front page

                    }
                    NavHostFragment.findNavController(DriverLoginFragment.this)//put this into the if statement later, this is here for testing
                            .navigate(R.id.action_driverLoginFragment_to_companyMapsFragment);
                }else if(loginState == DRIVER ){
                    if(LoginUtility.vehicleLogin(username, password, companyID) != null){
                        // navigate to driver front page
                    }
                    NavHostFragment.findNavController(DriverLoginFragment.this)//put this into the if statement later, this is here for testing
                            .navigate(R.id.action_driverLoginFragment_to_driverMapsFragment);
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