package com.example.vishal_mokal.btp.Activities.Connections;

import android.util.Log;

import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.Controller;
import com.example.vishal_mokal.btp.Activities.ObjectHolders.Voilation;
import com.example.vishal_mokal.btp.Activities.ObjectHolders.Voilations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by vishal_mokal on 4/3/15.
 */
public class ResultParser {

    String resultString;
    InputStream inputStream;
    DocumentBuilderFactory factory;
    DocumentBuilder documentBuilder;
    Controller controller;


    public ResultParser(String resultString) {
        this.resultString = resultString.trim().replace("&", "&amp;");
        inputStream = new ByteArrayInputStream(this.resultString.getBytes());
        controller = Controller.getInstance();
    }
    
    
    
    public boolean parseVoilationResult()
    {
        String status;
        try {
            Voilations voilations = new Voilations();
            ArrayList<Voilation> voilationList = new ArrayList<Voilation>();
            Log.d("resylt", resultString);
            factory = DocumentBuilderFactory.newInstance();
            documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element rootElemtnt = document.getDocumentElement();
            Log.d("Root Element" , rootElemtnt.getTextContent().toString());

            NodeList voilationData = rootElemtnt.getChildNodes();
            int size;
            size = voilationData.getLength();
            Log.d("Data Size" , ""+size);
            for(int i = 0 ; i<size ; i++)
            {
                if(voilationData.item(i).getNodeName().equalsIgnoreCase("STATUS"))
                {
                   voilations.setStatus(voilationData.item(i).getTextContent());
                }
                else if(voilationData.item(i).getNodeName().equalsIgnoreCase("ERRORMSG"))
                {
                   voilations.setErrorMessage(voilationData.item(i).getTextContent());
                }
                else if(voilationData.item(i).getNodeName().equalsIgnoreCase("REGISTRATION_NUMBER"))
                {
                   voilations.setRegistrationNumber(voilationData.item(i).getTextContent());
                }
                else if(voilationData.item(i).getNodeName().equalsIgnoreCase("VIOLATIONDATA"))
                {
                    NodeList voilation_details = voilationData.item(i).getChildNodes();
                    Voilation voilation = new Voilation();
                    for (int j= 0 ; j < voilation_details.getLength() ; j++ )
                    {
                        if(voilation_details.item(j).getNodeName().equalsIgnoreCase("VIOLATION_TIME"))
                        {
                            voilation.setVoilatioTime(voilation_details.item(j).getTextContent().toString());
                        }
                        else if(voilation_details.item(j).getNodeName().equalsIgnoreCase("VIOLATION_DATE"))
                        {
                            voilation.setVoilatioDate(voilation_details.item(j).getTextContent().toString());
                        }
                        else if(voilation_details.item(j).getNodeName().equalsIgnoreCase("OFFENCE_DESCRIPTION"))
                        {
                            voilation.setOffenceDescription(voilation_details.item(j).getTextContent().toString());
                        }
                        else if(voilation_details.item(j).getNodeName().equalsIgnoreCase("FINE_AMOUNT"))
                        {
                            voilation.setFineAmmount(voilation_details.item(j).getTextContent().toString());
                        }
                        else if(voilation_details.item(j).getNodeName().equalsIgnoreCase("NOTICE_NUMBER"))
                        {
                            voilation.setNoticeNumber(voilation_details.item(j).getTextContent().toString());
                        } else if(voilation_details.item(j).getNodeName().equalsIgnoreCase("LOCATION"))
                        {
                            voilation.setLocation(voilation_details.item(j).getTextContent().toString());
                        }
                       
                    }
                    voilationList.add(voilation);
                }
                voilations.setVoilationDetails(voilationList);
                controller.setVoilations(voilations);
            }
            
            return true;
                 
        }
        catch (Exception e)
        {
            Log.d("Error" , "Parsing Error");
            return false;
        }
    }
    
    
    
}
