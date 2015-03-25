package com.example.vishal_mokal.btp.Activities.Connections;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by vishal_mokal on 3/3/15.
 */
public class Connection {


    
    /*
    * Vishal Mokal 05-MAR-2015
    * soapCALL() function returns String Array.
    * Make A sope call and returns response. 
    * if responce is success full then user will get responce array 
    * first element status = 1 if syccessfull 0 = if any exception of faliur 
    * second element = response srtring if successful or error message.
    * */
    public String[] soapCALL(RequestDetails requestDetails, String wsdlUserName, String wsdlPassword , String headerName) {
        
        String url = requestDetails.getUrl().toString();
        String nameSpace = requestDetails.getNameSpace().toString();
        String methodName = requestDetails.getMethodName().toString();
        String soapAction = requestDetails.getSoapAction().toString();
        
        String[] responses = new String[2];
        
        Element[] header = new Element[1];
        
       // header[0] = new Element().createElement(nameSpace, "AuthenticationHeader");
        header[0] = new Element().createElement(nameSpace, headerName);

        try {
            Element UserName = new Element().createElement(nameSpace, "UserName");
            UserName.addChild(Node.TEXT, wsdlUserName);
            header[0].addChild(Node.ELEMENT, UserName);
            Element Password = new Element().createElement(nameSpace, "Password");
            Password.addChild(Node.TEXT, wsdlPassword);
            header[0].addChild(Node.ELEMENT, Password);
            SoapObject request = requestDetails.getSoapObject();

            SoapSerializationEnvelope soapSerilizationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapSerilizationEnvelope.dotNet = true;
            soapSerilizationEnvelope.headerOut = header;
            soapSerilizationEnvelope.setOutputSoapObject(request);
            Object env = soapSerilizationEnvelope.bodyOut;
            HttpTransportSE httptransport = new HttpTransportSE(url);
            httptransport.call(soapAction, soapSerilizationEnvelope);
            SoapPrimitive response = (SoapPrimitive) soapSerilizationEnvelope.getResponse();
            
            responses[0] = "1";
            responses[1] = response.toString();


            Log.d("Respons", response.toString());
            return responses;


        } 
        
        catch (SocketTimeoutException e)
        {
            responses[0] = "0";
            responses[1] = "Sorry!Unable To Connect Server Please Check Your Internet Connection Or Try After Some Time.";
            return responses;

        }
        catch (SocketException e)
        {
            responses[0] = "0";
            responses[1] = "Sorry!Unable To Connect Server Please Try After Some Time.";
            return responses;

        }
        
        
        catch (Exception e) {
            responses[0] = "0";
            responses[1] = "Sorry!Unable To Connect Server Please Try After Some Time.";
            return responses;


        }


       
    }



    /*
    * Vishal Mokal 07-MAR-2015 
    * Download bottleneck information 
    * */

    public String[] getResult(String requestdUrl) {
        InputStream inputStream = null;
        String result[] = new String[2];
        try {

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams , 10000);
            HttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpResponse httpResponse = httpClient.execute(new HttpGet(requestdUrl));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null){
                result[0] = "1";
                result[1] = convertInputStreamToString(inputStream);}
            else{
            result[0] = "0";
            result[1] = "No Data Found";}
            return result;
        } catch (Exception e) {
            result[0] = "0";
            result[1] = "Sorry!Problem Connecting Server Please Try After Some Time.";
            return result;
        }


    }


    
    /*
    * vishal Mokal 07-MAR-2015
    * it will convert input stream to string 
    * called when we are getting response and wants to convert it in to string.
    * */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
