package com.example.vishal_mokal.btp.Activities.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.DatePicker;

import com.example.vishal_mokal.btp.Activities.FragmentCommunicator.FragmentCommunicator;

import java.util.Calendar;

/**
 * A simple {@link android.app.Fragment} subclass.
 */

public class DatePickerFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

    FragmentCommunicator communicator;
    
    String dateFormat;

    
    //if dateformat = dmy onDateSet method will return date in DD/MM/YYYY format
    //if dateformat = mdy onDateSet method will return date in MM/DD/YYYY format

    public DatePickerFragment() {
    }

    public DatePickerFragment(String dateFormat) {
       
        this.dateFormat = dateFormat;
        

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        communicator = (FragmentCommunicator) getActivity();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        String day = "" + dayOfMonth;
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = "" + (monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        }
        String date = null;
        
        if(dateFormat.equals("dmy")) {
            date = "" + day.trim() + "/" + month.trim() + "/" + year;
        }
        else if(dateFormat.equals("mdy")) {
            date = "" +month.trim() + "/" +  day.trim() + "/" + year;
        }
       communicator.setDate(date);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Detach", "Detached");
       

    }
}