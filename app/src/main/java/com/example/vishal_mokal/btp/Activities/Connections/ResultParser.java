package com.example.vishal_mokal.btp.Activities.Connections;

import android.util.Log;

import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.Controller;
import com.example.vishal_mokal.btp.Activities.ObjectHolders.BottleNeck;
import com.example.vishal_mokal.btp.Activities.ObjectHolders.Voilation;
import com.example.vishal_mokal.btp.Activities.ObjectHolders.Voilations;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
                    voilationList.add(0 ,voilation);
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


    public boolean parseBottleneckData() {
        String status;
        try {
            BottleNeck bottleNeck;
            ArrayList<BottleNeck> bottleNeckArrayList = new ArrayList<BottleNeck>();
            Log.d("resylt", resultString);
            factory = DocumentBuilderFactory.newInstance();
            documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element rootElemtnt = document.getDocumentElement();
            NodeList resultItemList = rootElemtnt.getChildNodes();
            
            int size = resultItemList.getLength();
            
           
            
            
            JSONObject bottleneckobject = new JSONObject();
            JSONArray bottleneckjsonlist = new JSONArray();
            
            for(int i = 0 ; i<size ; i++)
            {
             Node resultItemElement = resultItemList.item(i);
                if(resultItemElement.getNodeName().equalsIgnoreCase("channel"))
                {
                    NodeList itemList = resultItemElement.getChildNodes();
                   
                    for(int j = 0 ;j < itemList.getLength() ; j++)
                    {
                        if(itemList.item(j).getNodeName().equalsIgnoreCase("item"))
                        {
                            NodeList bottlenecklist = itemList.item(j).getChildNodes();
                            bottleNeck = new BottleNeck();
                            JSONObject bottleneckjson = new JSONObject();
                            for(int k = 0 ; k < bottlenecklist.getLength() ; k++)
                            {
                               if( bottlenecklist.item(k).getNodeName().equalsIgnoreCase("description"))
                               {
                                  Log.d("BottleNeck" , bottlenecklist.item(k).getTextContent());
                                   bottleNeck.setDescription( bottlenecklist.item(k).getTextContent());
                                   bottleneckjson.put("Description" , bottlenecklist.item(k).getTextContent());
                                  
                               }
                                else  if( bottlenecklist.item(k).getNodeName().equalsIgnoreCase("pubDate"))
                               {
                                   Log.d("BottleNeck" , bottlenecklist.item(k).getTextContent());
                                   bottleNeck.setDate(bottlenecklist.item(k).getTextContent());
                                   bottleneckjson.put("Date" , bottlenecklist.item(k).getTextContent());
                                   
                               }
                            }
                            bottleNeckArrayList.add(bottleNeck);
                            bottleneckjsonlist.put(0 , bottleneckjson);
                        }
                        
                    }
                }
            }
            bottleneckobject.put("BottleNeck" , bottleneckjsonlist);
            controller.setBottleNeckList(bottleNeckArrayList);
            
            Log.d("Json" , bottleneckobject.toString());
            
            
            
            
            
            return true;
        } catch (Exception e) {
            Log.d("Error", "Parsing Error");
            return false;
        }

    }
    
    
    public boolean parseExsistingBottleNeckListforcurrentDate(String Data)
    {
      
        try {

            JSONObject jsonObject = new JSONObject(Data);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
        
    }



    public String[] parseResults() {
        String[] parseresults = new String[4];
       
        try {
           
            Log.d("resylt", resultString);
            factory = DocumentBuilderFactory.newInstance();
            documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element rootElemtnt = document.getDocumentElement();
            NodeList resultItemList = rootElemtnt.getChildNodes();
            int size = resultItemList.getLength();
            
            for(int i = 0 ; i < resultItemList.getLength() ; i++)
            {
               Node resultItem = resultItemList.item(i);
                if(resultItem.getNodeName().equalsIgnoreCase("USERNAME"))
                {
                   parseresults[0] = resultItem.getTextContent().toString();
                }
                else if(resultItem.getNodeName().equalsIgnoreCase("STATUS"))
                {
                    parseresults[1] = resultItem.getTextContent().toString();
                }
                else if(resultItem.getNodeName().equalsIgnoreCase("USERMESSAGE"))
                {
                    parseresults[2] = resultItem.getTextContent().toString();
                }else if(resultItem.getNodeName().equalsIgnoreCase("PASSWORD"))
                {
                    parseresults[3] = resultItem.getTextContent().toString();
                }
            }

            return parseresults;
        } catch (Exception e) {
            parseresults[1] = "0";
            parseresults[0] = "null";
            parseresults[2] = "Parsing Error";
            return parseresults;
        }

    }
    
    
    

}
