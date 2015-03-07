package com.example.vishal_mokal.btp.Activities.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.Controller;
import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.FragmentCommunicator;
import com.example.vishal_mokal.btp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    TextView btnNewUserRegistration;
    FragmentCommunicator communicator;
    Button btnLogin;
    Controller controller;
    EditText txtUserName , txtPassword ; 

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = (FragmentCommunicator) getActivity();
        btnNewUserRegistration = (TextView) getActivity().findViewById(R.id.btn_newUser);
        btnNewUserRegistration.setOnClickListener(this);
        btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        txtUserName = (EditText)getActivity().findViewById(R.id.txtLoginUserName);
        txtUserName = (EditText)getActivity().findViewById(R.id.txtLoginPassword);
        controller = Controller.getInstance();
    }

    @Override
    public void onClick(View v) {
        
        switch (v.getId())
        {
            case R.id.btnLogin:
                
         
                    communicator.launchGetVoilationFragment();
                
    
                break;
            case R.id.btn_newUser:
                communicator.launchUserRegistrationFragment();
                break;


        }
        
    }
    
    
    
    
    
    

    
    
}
