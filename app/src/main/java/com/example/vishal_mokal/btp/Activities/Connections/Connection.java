package com.example.vishal_mokal.btp.Activities.Connections;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

/**
 * Created by vishal_mokal on 3/3/15.
 */
public class Connection {


    public boolean soapCALL(RequestDetails requestDetails, String wsdlUserName, String wsdlPassword) {
        String url = requestDetails.getUrl().toString();
        String nameSpace = requestDetails.getNameSpace().toString();
        String methodName = requestDetails.getMethodName().toString();
        String soapAction = requestDetails.getSoapAction().toString();
        Element[] header = new Element[1];
        header[0] = new Element().createElement(nameSpace, "AuthenticationHeader");

        try {
            Element UserName = new Element().createElement(nameSpace, "UserName");
            UserName.addChild(Node.TEXT, wsdlUserName);
            header[0].addChild(Node.ELEMENT, UserName);
            Element Password = new Element().createElement(nameSpace, "Password");
            Password.addChild(Node.TEXT, wsdlPassword);
            header[0].addChild(Node.ELEMENT, Password);
            SoapObject request = requestDetails.getSoapObject();

            SoapSerializationEnvelope sopaSerilizationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sopaSerilizationEnvelope.dotNet = true;
            sopaSerilizationEnvelope.headerOut = header;
            sopaSerilizationEnvelope.setOutputSoapObject(request);

            HttpTransportSE httptransport = new HttpTransportSE(url);
            httptransport.call(soapAction, sopaSerilizationEnvelope);
            SoapPrimitive response = (SoapPrimitive) sopaSerilizationEnvelope.getResponse();
            Log.d("Respons", response.toString());
            return true;


        } catch (Exception e) {
            Log.d("Respons", e.getMessage().toString() + "   " + e.getCause().toString());
            return false;

        }


       
    }


}
