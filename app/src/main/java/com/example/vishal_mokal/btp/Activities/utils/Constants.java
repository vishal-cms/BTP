package com.example.vishal_mokal.btp.Activities.utils;

import android.os.Environment;

/**
 * Created by vishal_mokal on 4/3/15.
 */
public class Constants {


   public final static String USER_REGISTRATION_URL = "http://182.74.160.157/MobileRegistration/RegistrationForm.asmx";
    public final static  String USER_REGISTRATION_NAMESPACE = "http://tempuri.org/";
    public final static String USER_REGISTRATION_METHOD_NAME = "UserRegistration";
    public final static String USER_REGISTRATION_SOAP_ACTION = "http://tempuri.org/UserRegistration";
    public final static String  USER_REGISTRATION_AUTHENTICATION_USERNAME = "COD"; 
    public final static String USER_REGISTRATION_AUTHENTICATION_PASSWORD = "CKOSXzEsSUUA9A";


    public final static String CHANGE_PASSWORD_URL = "http://182.74.160.157/MobileRegistration/RegistrationForm.asmx";
    public final static String CHANGE_PASSWORD_NAMESPACE = "http://tempuri.org/";
    public final static String CHANGE_PASSWORD_METHOD_NAME = "ChangePassword";
    public final static String CHANGE_PASSWORD_SOAP_ACTION = "http://tempuri.org/ChangePassword";
    public final static String CHANGE_PASSWORD_AUTHENTICATION_USERNAME = "COD";
    public final static String CHANGE_PASSWORD_AUTHENTICATION_PASSWORD = "CKOSXzEsSUUA9A";
    
    
   public final static String FORGOT_PASSWORD_URL = "http://182.74.160.157/MobileRegistration/RegistrationForm.asmx";
    public final static String FORGOT_PASSWORD_NAMESPACE = "http://tempuri.org/";
    public final static String FORGOT_PASSWORD_METHOD_NAME = "ForgotPassword";
    public final static String FORGOT_PASSWORD_SOAP_ACTION = "http://tempuri.org/ForgotPassword";
    public final static String  FORGOT_PASSWORD_AUTHENTICATION_USERNAME = "COD"; 
    public final static String FORGOT_PASSWORD_AUTHENTICATION_PASSWORD = "CKOSXzEsSUUA9A"; 
    
    
    public final static String USER_LOGIN_URL = "http://182.74.160.157/MobileRegistration/RegistrationForm.asmx";
    public final static String USER_LOGIN_NAMESPACE = "http://tempuri.org/";
    public final static String USER_LOGIN_METHOD_NAME = "UserVerification";
    public final static String USER_LOGIN_SOAP_ACTION = "http://tempuri.org/UserVerification";
    public final static String  USER_LOGIN_AUTHENTICATION_USERNAME = "COD"; 
    public final static String USER_LOGIN_AUTHENTICATION_PASSWORD = "CKOSXzEsSUUA9A";
    

    public final static String GET_VOILATION_URL = "http://125.17.140.50:8088/cidwebservice/CIDServices.asmx";
    public final static String GET_VOILATION_NAMESPACE = "http://tempuri.org/";
    public final static String GET_VOILATION_METHOD_NAME = "GetViolationDetails_MobileAPP";
    public final static String GET_VOILATION_SOAP_ACTION = "http://tempuri.org/GetViolationDetails_MobileAPP";
    public final static String  VOILATION_AUTHENTICATION_USERNAME = "CID";
    public final static String  VOILATION_AUTHENTICATION_password = "CKOSXzEsSUUA9A";
    
    
    
    public static final String APPLICATION_BASE_PATH = Environment.getExternalStorageDirectory() + "/.BTP/";  
    
    
    
    public static final String BOTTLENECK_INFORAMTION_LINK = "http://www.bangaloretrafficpolice.gov.in/Bottlenecks.xml";
    
}
