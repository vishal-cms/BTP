package com.example.vishal_mokal.btp.Activities.Fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishal_mokal.btp.Activities.Connections.Connection;
import com.example.vishal_mokal.btp.Activities.Connections.RequestDetails;
import com.example.vishal_mokal.btp.Activities.Connections.ResultParser;
import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.Controller;
import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.FragmentCommunicator;
import com.example.vishal_mokal.btp.Activities.utils.Constants;
import com.example.vishal_mokal.btp.Activities.utils.GeneralUtilities;
import com.example.vishal_mokal.btp.R;

import org.ksoap2.serialization.SoapObject;

import static com.example.vishal_mokal.btp.R.id.btn_ForgotPassword;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button btnNewUserRegistration;
    FragmentCommunicator communicator;
    Button btnLogin;
    Controller controller;
    EditText txtUserName , txtPassword ; 
    TextView txtErrorMessage;
    TextView txtChangePassword;
    TextView txtForgotPassword;

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
        String email = new GeneralUtilities(getActivity()).readDataFromSharedPreferences();
        communicator = (FragmentCommunicator) getActivity();
        btnNewUserRegistration = (Button) getActivity().findViewById(R.id.btn_newUser);
        txtErrorMessage = (TextView)getActivity().findViewById(R.id.txtErrorMessage);
        btnNewUserRegistration.setOnClickListener(this);
        btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        txtUserName = (EditText)getActivity().findViewById(R.id.txtLoginUserName);
        txtPassword = (EditText)getActivity().findViewById(R.id.txtLoginPassword);
        txtForgotPassword = (TextView)getActivity().findViewById(R.id.btn_ForgotPassword);
        txtForgotPassword.setOnClickListener(this);
        txtChangePassword = (TextView)getActivity().findViewById(R.id.btn_ChangePassword);
        txtChangePassword.setOnClickListener(this);
        controller = Controller.getInstance();
        if(email !=  null)
        {
            txtUserName.setText(email);
            txtPassword.requestFocus();
        }
    }

    @Override
    public void onClick(View v) {
        
        switch (v.getId())
        {
            case R.id.btnLogin:
                
         
                
                if(!txtUserName.getText().toString().trim().equals("") && !txtPassword.getText().toString().trim().equals(""))
                {
                    if(GeneralUtilities.validateEmailAddress(txtUserName.getText().toString().trim()))
                    {
                        RequestDetails requestDetails = new RequestDetails(Constants.USER_LOGIN_NAMESPACE,
                                Constants.USER_LOGIN_URL,
                                Constants.USER_LOGIN_METHOD_NAME,
                                Constants.USER_LOGIN_SOAP_ACTION
                        );
                        SoapObject registrationDetails = new SoapObject(Constants.USER_LOGIN_NAMESPACE, Constants.USER_LOGIN_METHOD_NAME);
                        registrationDetails.addProperty("email", txtUserName.getText().toString());
                        registrationDetails.addProperty("password", txtPassword.getText().toString());
                        
                        requestDetails.setSoapObject(registrationDetails);
                        new LoginTask(requestDetails).execute();
                    }
                    else
                    {
                        txtUserName.setError("Please Enter Valid Email Address");
                        txtUserName.requestFocus();
                    }
                }
                else
                {

                    txtErrorMessage.setText("All Fields Are Mandatory");
                }
                
                
                  //  communicator.launchGetBottleNeckFragment();
                
    
                break;
            case R.id.btn_newUser:
                communicator.launchUserRegistrationFragment();
                break;
            case R.id.btn_ChangePassword:
                communicator.launchChangePasswordFragment(txtUserName.getText().toString().trim());
                break;
            case btn_ForgotPassword:
                
                if(!txtUserName.getText().toString().trim().equals(""))
                {
                    if(GeneralUtilities.validateEmailAddress(txtUserName.getText().toString().trim()))
                    {
                        RequestDetails requestDetails = new RequestDetails(Constants.FORGOT_PASSWORD_NAMESPACE,
                                Constants.FORGOT_PASSWORD_URL,
                                Constants.FORGOT_PASSWORD_METHOD_NAME,
                                Constants.FORGOT_PASSWORD_SOAP_ACTION);
                        SoapObject forgotPasswordDetails = new SoapObject(Constants.FORGOT_PASSWORD_NAMESPACE ,Constants.FORGOT_PASSWORD_METHOD_NAME);
                        forgotPasswordDetails.addProperty("email", txtUserName.getText().toString().trim());
                        requestDetails.setSoapObject(forgotPasswordDetails);
                        new ForgotPasswordTask(requestDetails).execute(); 
                    }
                    else
                    {
                        txtUserName.setError("Please Enter Valid Email Address");
                        txtUserName.requestFocus();
                    }
                }
                else
                {
                    txtErrorMessage.setText("Please Enter Email Address");
                }
                
                
                break;


        }
        
    }

    @Override
    public void onStart() {
        super.onStart();
        communicator.hideActionBar();
    }

    //////Login Task///////////////////////////////////////////\
      
    /*background task*/

    class LoginTask extends AsyncTask<String , String , String> {
        RequestDetails requestDetails;
        String[] result;
        String[] parseResult;

        LoginTask(RequestDetails requestDetails) {
            this.requestDetails = requestDetails;
        }

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            result = new Connection().soapCALL(requestDetails, Constants.USER_LOGIN_AUTHENTICATION_USERNAME, Constants.USER_LOGIN_AUTHENTICATION_PASSWORD, "AuthenticationHeaders");

            if (result[0].equals("1")) {

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            if (result[0].equals("1")) {
                parseResult = new ResultParser(result[1].toString()).parseResults();

                if (parseResult[1].equals("1")) {
                    new GeneralUtilities(getActivity()).writeDataToSharedPreferences(parseResult[0].toString());
                    communicator.launchGetBottleNeckFragment();
                } else if (parseResult[1].equals("0")) {
                    txtErrorMessage.setText(parseResult[2].toString().toUpperCase());
                }

            } else if (result[0].equals("0")) {
                Toast.makeText(getActivity(), result[1].toUpperCase(), Toast.LENGTH_LONG).show();
            }

        }
    }

        //////////////ForgotPassword Task//////////////////////////////////////

        class ForgotPasswordTask extends AsyncTask<String, String, String> {
            RequestDetails requestDetails;
            String[] result;
            String[] parseResult;

            ForgotPasswordTask(RequestDetails requestDetails) {
                this.requestDetails = requestDetails;
            }

            ProgressDialog progressDialog;

            @Override
            protected String doInBackground(String... params) {

                result = new Connection().soapCALL(requestDetails, Constants.FORGOT_PASSWORD_AUTHENTICATION_USERNAME, Constants.FORGOT_PASSWORD_AUTHENTICATION_PASSWORD, "AuthenticationHeaders");

                if (result[0].equals("1")) {

                }

                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (result[0].equals("1")) {
                    parseResult = new ResultParser(result[1].toString()).parseResults();

                    if (parseResult[1].equals("1")) {
                        txtErrorMessage.setText("Password Is Sent To Your Email Address");
                    } else if (parseResult[1].equals("0")) {
                        txtErrorMessage.setText(parseResult[2].toString().toUpperCase());
                    }

                } else if (result[0].equals("0")) {
                    Toast.makeText(getActivity(), result[1].toUpperCase(), Toast.LENGTH_LONG).show();
                }

            }
        }


    }




