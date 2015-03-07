package com.example.vishal_mokal.btp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.FragmentCommunicator;
import com.example.vishal_mokal.btp.Activities.Fragments.DatePickerFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.GetTrafficVoilationFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.LoginFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.UserRegistrationFragment;
import com.example.vishal_mokal.btp.R;


public class ContainerActivity extends ActionBarActivity implements FragmentCommunicator {
FragmentManager manager;
    UserRegistrationFragment userRegistrationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        
        manager = getSupportFragmentManager();
        launcfLoginFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_container, menu);
        return true;
    }

  
    
    public void launcfLoginFragment()
    {
        LoginFragment loginfragment = new LoginFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.containerlayout , loginfragment , "loginFragment");
       
        transaction.commit();
    }


    @Override
    public void launchUserRegistrationFragment() {
       userRegistrationFragment = new UserRegistrationFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.containerlayout , userRegistrationFragment , "UserRegistration");
        fragmentTransaction.commit();
        
    }

    @Override
    public void setDate(String date) {
        if(userRegistrationFragment != null )
        {
            userRegistrationFragment.setDate(date);
        }
    }

    @Override
    public void launchDatepickerFramgnet() {
        DatePickerFragment datePickerFragment = new DatePickerFragment("mdy");
        datePickerFragment.show(manager  , "dateFragment");
    }

    @Override
    public void launchGetVoilationFragment() {
        GetTrafficVoilationFragment getTrafficVoilationFragment = new GetTrafficVoilationFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerlayout , getTrafficVoilationFragment , "TrafficVoilationFragment");
        transaction.commit();
    }
    
}
