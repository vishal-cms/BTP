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
import com.example.vishal_mokal.btp.Activities.Connections.ResultParser;
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

                   if(GeneralUtilities.validateUserName(txtName.getText().toString().trim())) {

                       if (GeneralUtilities.validateEmailAddress(txtEmailAddress.getText().toString().trim())) {


                           if (GeneralUtilities.validatePhoneNumber(txtPhoneNumber.getText().toString()))

                           {

                               if (txtPassword.getText().toString().trim().equals(txtReEnterPassword.getText().toString().trim())) {
                            /*String nameSpace, String url, String methodName, String soapAction*/
                                   RequestDetails requestDetails = new RequestDetails(Constants.USER_REGISTRATION_NAMESPACE,
                                           Constants.USER_REGISTRATION_URL,
                                           Constants.USER_REGISTRATION_METHOD_NAME,
                                           Constants.USER_REGISTRATION_SOAP_ACTION
                                   );
                                   SoapObject registrationDetails = new SoapObject(Constants.USER_REGISTRATION_NAMESPACE, Constants.USER_REGISTRATION_METHOD_NAME);
                                   registrationDetails.addProperty("name", txtName.getText().toString());
                                   registrationDetails.addProperty("email", txtEmailAddress.getText().toString());
                                   registrationDetails.addProperty("mobile", txtPhoneNumber.getText().toString());
                                   registrationDetails.addProperty("address", txtAddress.getText().toString());
                                   registrationDetails.addProperty("gender", sex);
                                   registrationDetails.addProperty("dob", txtSelectDate.getText().toString());
                                   registrationDetails.addProperty("password", txtPassword.getText().toString());

                                   requestDetails.setSoapObject(registrationDetails);

                                   new RegistrationClass(requestDetails).execute();


                               } else {
                                   txtPassword.setError("Password And Re-Entered Password Dose Not Match.");
                                   txtPassword.setText("");
                                   txtReEnterPassword.setText("");
                                   txtPassword.requestFocus();
                               }
                           } else {
                               txtPhoneNumber.setError("Please Enter Correct Phone Number");
                               txtPhoneNumber.requestFocus();
                           }

                       } else

                       {
                           txtEmailAddress.setText("");
                           txtEmailAddress.setError("Please Enter Correct Email Address.");
                           txtEmailAddress.requestFocus();
                       }
                   }
                   else
                   {
                       txtName.setError("Please Enter Correct Name");
                       txtName.requestFocus();
                               
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
        String[] result;
        String[] parseResult;

        RegistrationClass(RequestDetails requestDetails) {
            this.requestDetails = requestDetails;
        }

        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            
         result =  new Connection().soapCALL(requestDetails , Constants.USER_REGISTRATION_AUTHENTICATION_USERNAME ,Constants.USER_REGISTRATION_AUTHENTICATION_PASSWORD, "AuthenticationHeaders");
            
            if (result[0].equals("1"))
            {
                
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
            if (progressDialog != null){
            progressDialog.dismiss();}
            
            if (result[0].equals("1"))
            {
                parseResult = new ResultParser(result[1].toString()).parseResults();
                
                if(parseResult[1].equals("1"))
                {
                    new GeneralUtilities(getActivity()).writeDataToSharedPreferences(parseResult[0].toString());
                    Toast.makeText(getActivity() , "Thank You ! Registration Successful." , Toast.LENGTH_LONG).show();
                    communicator.launchGetBottleNeckFragment();
                }
                else if (parseResult[1].equals("0"))
                {
                    Toast.makeText(getActivity() , "Sorry! " +parseResult[2].toString() , Toast.LENGTH_LONG).show();
                }
                
            }
            else if(result[0].equals("0"))
            {
                Toast.makeText(getActivity() , result[1] , Toast.LENGTH_LONG).show();
            }
           
        }
    }
    
    
    
    public void setDate(String date)
    {
        if(date.equalsIgnoreCase("false"))
        {
         Toast.makeText(getActivity() , "Please Enter Correct Date" , Toast.LENGTH_LONG).show();   
        }
        else {
            txtSelectDate.setText(date);
        }
        }



    @Override
    public void onStart() {
        super.onStart();
        communicator.changeActionBarTitle("User Registration.");
        communicator.showActionBar();
    }
    
    
    

  


}
