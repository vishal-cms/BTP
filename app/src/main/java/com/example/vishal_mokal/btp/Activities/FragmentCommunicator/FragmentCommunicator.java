package com.example.vishal_mokal.btp.Activities.FragmentCommunicator;

/**
 * Created by vishal_mokal on 2/3/15.
 */
public interface FragmentCommunicator {

    public void launchUserRegistrationFragment();
    public void setDate(String date);
    public void launchDatepickerFramgnet();
    public void launchGetVoilationFragment();
    public void launchGetBottleNeckFragment();
    public void showActionBar();
    public void hideActionBar();
    public void launcfLoginFragment();
    public void launchChangePasswordFragment(String email);
    public void changeActionBarTitle(String Title);
    
    

}
