package com.example.vishal_mokal.btp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.FragmentCommunicator;
import com.example.vishal_mokal.btp.Activities.Fragments.ChangePasswordFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.DatePickerFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.GetBottleNackFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.GetTrafficVoilationFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.LoginFragment;
import com.example.vishal_mokal.btp.Activities.Fragments.UserRegistrationFragment;
import com.example.vishal_mokal.btp.Activities.utils.Constants;
import com.example.vishal_mokal.btp.R;

import java.io.File;


public class ContainerActivity extends ActionBarActivity implements FragmentCommunicator , FragmentManager.OnBackStackChangedListener {
FragmentManager manager;
    UserRegistrationFragment userRegistrationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        createApplicationDir();
        manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(this);
        launcfLoginFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_container, menu);
        return true;
    }

  
    @Override
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
        fragmentTransaction.addToBackStack("UserRegistration");
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
       DatePickerFragment datePickerFragment = new DatePickerFragment("dmy");
        datePickerFragment.show(manager  , "dateFragment");
    }

    @Override
    public void launchGetVoilationFragment() {
        GetTrafficVoilationFragment getTrafficVoilationFragment = new GetTrafficVoilationFragment();
     
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerlayout , getTrafficVoilationFragment , "TrafficVoilationFragment");
        transaction.addToBackStack("TrafficVoilationFragment");
        transaction.commit();
    }

    @Override
    public void launchGetBottleNeckFragment() {
        GetBottleNackFragment  getBottleNackFragment = new GetBottleNackFragment();
       
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerlayout , getBottleNackFragment , "getBottleneckfragment");
       transaction.commit();
        
    }

    private void createApplicationDir() {
        File f = new File(Constants.APPLICATION_BASE_PATH);
        if (!f.exists()) {
            f.mkdir();
        }
    }


    @Override
    public void showActionBar() {
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.show();
    }

    @Override
    public void hideActionBar() {
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void changeActionBarTitle(String Title) {
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(Title);
    }

    @Override
    public void launchChangePasswordFragment(String email) {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        Bundle argiments = new Bundle();
        argiments.putString("email" , email);
         changePasswordFragment.setArguments(argiments);       
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerlayout, changePasswordFragment, "ChangePasswordFragment");
        transaction.addToBackStack("ChangePasswordFragment");
        transaction.commit();
    }

    @Override
    public void onBackStackChanged() {
        showUpNavigation();
    }

    public void showUpNavigation() {
        if (manager.getBackStackEntryCount() > 0)
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        else if (manager.getBackStackEntryCount() == 0)
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
    }
    @Override
    public boolean onSupportNavigateUp() {
        manager.popBackStack();
        return true;
    }

}
