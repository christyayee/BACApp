package com.example.bacapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.nfc.Tag;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

//implements TimePickerDialog.OnTimeSetListener
public class Drinks extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AbandonDialog.NoticeDialogListener{

    RadioGroup group;
    RadioButton rButton;
    EditText numText;
    int num;
    static TextView timeText;
    CheckBox checkBox;
    static TextView timeToggle;
    LinearLayout timeLayout;

    String nextScreen = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        group = (RadioGroup) findViewById(R.id.radioGroup);
        numText = (EditText) findViewById(R.id.numText);
        timeText = (TextView) findViewById(R.id.timeText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        timeToggle = (TextView) findViewById(R.id.timeToggle);
        timeLayout = (LinearLayout) findViewById(R.id.timeLayout);
        updateNum();

        if (Profiles.current.getDrinks() == 0 && !Profiles.current.hasStarted())
            timeLayout.setVisibility(View.VISIBLE);
        else
            timeLayout.setVisibility(View.INVISIBLE);

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeLayout.isShown()) {
                    DialogFragment timePicker = new TimeDialog();
                    timePicker.show(getSupportFragmentManager(), "timePicker");
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    timeText.setEnabled(false);
                    timeToggle.setText("");
                }
                else {
                    timeText.setEnabled(true);
                    timeText.setText("hh:mm");
                }
            }
        });

        Button upButton = (Button) findViewById(R.id.upButton);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strN = "" + numText.getText();
                if (strN == "" || strN == null) {
                    numText.setText("1");
                    num = 1;
                } else {
                    num = Integer.parseInt("" + numText.getText());
                    num++;
                    numText.setText("" + num);
                }
            }
        });

        Button downButton = (Button) findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strN = "" + numText.getText();
                if (strN == "") {
                    numText.setText("0");
                    num = 0;
                }
                else {
                    updateNum();
                    if (num <= 0)
                    {
                        numText.setText("0");
                        num = 0;
                    }
                    else {
                        num--;
                        numText.setText("" + num);
                    }
                }
            }
        });

        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (checkData()) {
                    //goes to Result Activity Screen
                    Intent intent = new Intent(getApplicationContext(), Result.class);
                    startActivity(intent);
                }
            }
        });

        Button menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen = "profiles";
                DialogFragment dialog = new AbandonDialog();
                dialog.show(getSupportFragmentManager(), "AbandonDialog");
            }
        });

        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen = "main";
                DialogFragment dialog = new AbandonDialog();
                dialog.show(getSupportFragmentManager(), "AbandonDialog");
            }
        });
    }

    public void onRButtonClicked(View view) {
        int radioID = group.getCheckedRadioButtonId();
        rButton = findViewById(radioID);
    }

    //account for INVALID # OF DIGITS, NO :, CORRECT FORMAT BUT NONSENSICAL TIME (13:62)
    //return null if invalid
    private String getStartTime() {
        return "" + timeText.getText();
    }


    static public String isValidTime()
    {
        String startTime = "now";

            String temp = "" + timeText.getText();
            if (temp.contains("hh:mm"))
                return null;
            if (temp == "" || (temp.length() != 5 && temp.length() != 4))
                return null;

            int indexOf = temp.indexOf(":");
            if (indexOf == -1)
                return null;

            String strH = temp.substring(0, indexOf);
            if (strH.length() != 2 && strH.length() != 1)
                return null;
            int hours = Integer.parseInt(strH);
            if (hours < 1 || hours > 12)
                return null;

            String strM = temp.substring(indexOf+1);
            if (strM.length() != 2)
                return null;
            int min = Integer.parseInt(strM);
            if (min < 0 || min > 59)
                return null;

            //convert AM/PM to military
            if (timeToggle.getText() == "PM")
            {
                if (hours != 12)
                    hours += 12;
            }
            else if (hours == 12)//midnight (12AM)
                hours = 0;

            startTime = hours + ":" + strM;

        return startTime;
    }

    private String throwMessage(char key) {
        Toast t = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
        switch (key) {
            case 'd':
                t.setText("Type of drink cannot be left empty.");
                break;
            case '#':
                t.setText("Number of drinks cannot be left empty.");
                break;
            case 't':
                t.setText("Invalid time. \nRemember to follow the format hh:mm");
                break;
        }
        t.show();
        return null;
    }

    private int updateNum()
    {
        String str = "" + numText.getText();
        num = Integer.parseInt("" + numText.getText());
        return num;
    }

    private boolean checkData()
    {
        //update startTime
        String startTime = getStartTime();
        if (startTime == null || startTime == "")
            return false;
        else if (startTime != null && startTime != "now")
            Profiles.current.setStartTime(startTime);
        //otherwise, assume Drinker's start Time already established or start now

        //update Drinks
        int radioID = group.getCheckedRadioButtonId();
        if (radioID == -1) {
            throwMessage('d');
            return false;
        }
        else {
            rButton = findViewById(radioID);
            String strN = "" + numText.getText();
            if (strN == "") {
                throwMessage('#');
                return false;
            }
            else {
                updateNum();
                switch (radioID) {
                    case R.id.beerButton:
                        Profiles.current.incrementBeer(num);
                        break;
                    case R.id.wineButton:
                        Profiles.current.incrementWine(num);
                        break;
                    case R.id.liquorButton:
                        Profiles.current.incrementLiquor(num);
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeToggle.setText("AM");
        int hours = hourOfDay;
        if (hourOfDay >= 12) {
            timeToggle.setText("PM");
            if (hourOfDay != 12)
                hours -= 12;
        }
        else if (hourOfDay == 0)
            hours = 12;

       String strM = "" + minute;
       if (minute < 10)
           strM = 0 + strM;
       timeText.setText(hours + ":" + strM);

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //User touched the dialog's positive button

        Intent intent = new Intent(getApplicationContext(), Drinks.class);
        if (nextScreen == "profiles")
            intent = new Intent(getApplicationContext(), Profiles.class);
        else if (nextScreen == "main")
            intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        Toast t = Toast.makeText(getApplicationContext(), "Drinks not updated.", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        //cancel, do nothing
    }

}