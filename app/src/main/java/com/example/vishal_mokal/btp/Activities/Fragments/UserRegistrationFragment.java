package com.example.vishal_mokal.btp.Activities.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishal_mokal.btp.Activities.Connections.Connection;
import com.example.vishal_mokal.btp.Activities.Connections.RequestDetails;
import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.FragmentCommunicator;
import com.example.vishal_mokal.btp.Activities.utils.Constants;
import com.example.vishal_mokal.btp.Activities.utils.GeneralUtilities;
import com.example.vishal_mokal.btp.R;

import org.ksoap2.serialization.SoapObject;

public class UserRegistrationFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
   
    Button registerUser;
    RadioButton rbMale, rbFemale;
    EditText txtName;
    EditText txtEmailAddress;
    EditText txtPhoneNumber;
    EditText txtAddress;
    EditText txtPassword;
   
    EditText txtReEnterPassword;
   TextView txtSelectDate;
    FragmentCommunicator communicator;
    String sex;
    

    RequestDetails requestDetails;
    
    public UserRegistrationFragment() {
        sex = "M";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_registration, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerUser = (Button) getActivity().findViewById(R.id.btn_register);
        txtSelectDate = (TextView) getActivity().findViewById(R.id.btn_selectDOB);
        txtName = (EditText) getActivity().findViewById(R.id.txtName);
        txtEmailAddress = (EditText) getActivity().findViewById(R.id.txtEmailAddress);
        txtPhoneNumber = (EditText) getActivity().findViewById(R.id.txtContactNumber);
        txtAddress = (EditText) getActivity().findViewById(R.id.txtAddress);
        txtPassword = (EditText) getActivity().findViewById(R.id.txtpassword);
        txtReEnterPassword = (EditText) getActivity().findViewById(R.id.txtreEnterpassword);
        rbMale = (RadioButton) getActivity().findViewById(R.id.rb_GenderMale);
        rbMale.setChecked(true);
        rbFemale = (RadioButton) getActivity().findViewById(R.id.rb_GenderFemale);
        rbFemale.setOnClickListener(this);
        rbMale.setOnClickListener(this);
        txtSelectDate.setOnClickListener(this);
       communicator = (FragmentCommunicator) getActivity();
        registerUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        
        switch (v.getId())
        {
           case R.id.btn_register:
               //check if all fields are entered properly
               if (!txtName.getText().toString().trim().equalsIgnoreCase("")&&
                       !txtEmailAddress.getText().toString().trim().equalsIgnoreCase("")&&
                       !txtAddress.getText().toString().trim().equalsIgnoreCase("")&&
                       !txtSelectDate.getText().toString().trim().equalsIgnoreCase("")&&
                       !txtPhoneNumber.getText().toString().trim().equalsIgnoreCase("")&&
                       !txtPassword.getText().toString().trim().equalsIgnoreCase("")) {

                   if(new GeneralUtilities().validateUserName(txtEmailAddress.getText().toString().trim())) {
                       
                       if(txtPassword.getText().toString().trim().equals(txtReEnterPassword.getText().toString().trim())) {
                            
                           RequestDetails requestDetails = new RequestDetails(Constants.USER_REGISTRATION_AUTHENTICATION_USERNAME , 
                                   Constants.USER_REGISTRATION_URL , 
                                   Constants.USER_REGISTRATION_NAMESPACE , 
                                   Constants.USER_REGISTRATION_SOAP_ACTION);
                           SoapObject registrationDetails = new SoapObject(Constants.USER_REGISTRATION_NAMESPACE , Constants.USER_REGISTRATION_METHOD_NAME);
                           registrationDetails.addProperty("name", txtName.getText().toString());
                           registrationDetails.addProperty("email", txtEmailAddress.getText().toString());
                           registrationDetails.addProperty("address", txtAddress.getText().toString());
                           registrationDetails.addProperty("sex", sex);
                           registrationDetails.addProperty("Dob", txtSelectDate.getText().toString());
                           registrationDetails.addProperty("password", txtPassword.getText().toString());
                          
                           requestDetails.setSoapObject(registrationDetails);

                           new RegistrationClass(requestDetails).execute();
                                   
            
                          
                       }
                       else
                       {
                           
                       }
                       
                   }
                   else
                       
                   {
                       Toast.makeText(getActivity(), "Please Enter Correct Email Address" , Toast.LENGTH_LONG).show();
                   }
                   
                   
               }
               else {

                   Toast.makeText(getActivity(), "All Fields Are Mandatory" , Toast.LENGTH_LONG).show();
               }
                 
               
            break;
            case R.id.rb_GenderMale:
                sex = "M";
                Log.d("sex" ,sex);
                break;
            case R.id.rb_GenderFemale:
                sex = "F";
                Log.d("sex" ,sex);
                break;
            case  R.id.btn_selectDOB:
                communicator.launchDatepickerFramgnet();
                break;
        }
        
        
    }
    
    
    /*background task*/

    class RegistrationClass extends AsyncTask<String , String , String>
    {
        RequestDetails requestDetails;
        boolean result;

        RegistrationClass(RequestDetails requestDetails) {
            this.requestDetails = requestDetails;
        }

        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            
         result =  new Connection().soapCALL(requestDetails , Constants.USER_REGISTRATION_AUTHENTICATION_USERNAME ,Constants.USER_REGISTRATION_AUTHENTICATION_password);
            
            
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            
          
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(result = false)
            {
                Toast.makeText(getActivity(), "Registration Failed" , Toast.LENGTH_LONG).show();
            }
            
            else    
            {
                Toast.makeText(getActivity(), "Registration Successful" , Toast.LENGTH_LONG).show();
            }
        }
    }
    
    
    
    public void setDate(String date)
    {
        txtSelectDate.setText(date);
    }
    
    
    
    
    
    
    

  


}
