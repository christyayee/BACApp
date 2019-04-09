package com.example.bacapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class TimeDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String time = Drinks.isValidTime();
        int hour, minute;
        if (time != null)
        {
            int indexOf = time.indexOf(":");
            String strH = time.substring(0, indexOf);
            hour = Integer.parseInt(strH);
            String strM = time.substring(indexOf+1);
            minute = Integer.parseInt(strM);
        }
        else {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog, (TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}