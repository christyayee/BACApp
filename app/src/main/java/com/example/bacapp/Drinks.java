package com.example.bacapp;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Drinks extends AppCompatActivity {

    RadioGroup group;
    RadioButton rButton;
    EditText numText;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        group = (RadioGroup)findViewById(R.id.radioGroup);

        //TODO: create mystery Activity Screen
        //TODO: add back images
        //TODO: error message for invalid info

        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText timeText = (EditText)findViewById(R.id.timeText);
                if (checkBox.isChecked())
                    timeText.setEnabled(false);
                else
                    timeText.setEnabled(true);
            }
        });

        final LinearLayout timeLayout = (LinearLayout)findViewById(R.id.timeLayout);
        if (Profiles.current.getDrinks() == 0 && !Profiles.current.hasStarted())
            timeLayout.setVisibility(View.VISIBLE);
        numText = (EditText)findViewById(R.id.numText);
        num = Integer.parseInt("" + numText.getText());

        Button upButton = (Button)findViewById(R.id.upButton);
        upButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt("" + numText.getText());
                num++;
                numText.setText("" + num);
            }

        });
        Button downButton = (Button)findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt("" + numText.getText());
                num--;
                numText.setText("" + num);
            }

        });


        Button continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update Drinks
                int radioID = group.getCheckedRadioButtonId();
                rButton = findViewById(radioID);
                switch (radioID) {
                    case R.id.beerButton:
                        num = Integer.parseInt("" + numText.getText());
                        Profiles.current.incrementBeer(num);
                        break;
                    case R.id.wineButton:
                        num = Integer.parseInt("" + numText.getText());
                        Profiles.current.incrementWine(num);
                        break;
                    case R.id.liquorButton:
                        num = Integer.parseInt("" + numText.getText());
                        Profiles.current.incrementLiquor(num);
                        break;
                }

                //update Time
                if (timeLayout.getVisibility() == View.VISIBLE)
                {
                    String startTime = getStartTime();
                    Profiles.current.setStartTime(startTime);
                }

                //goes to Result Activity Screen
                Intent drinksIntent = new Intent(getApplicationContext(),Result.class);
                startActivity(drinksIntent);
            }
        });



    }

    public void onRButtonClicked(View view)
    {
        int radioID = group.getCheckedRadioButtonId();
        rButton = findViewById(radioID);
    }

    public String getStartTime()
    {
        String startTime;
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        if (checkBox.isChecked())
            startTime = "now";
        else
        {
            EditText timeText = (EditText)findViewById(R.id.timeText);
            startTime = "" + timeText.getText();
            ToggleButton timeToggle = (ToggleButton)findViewById(R.id.timeToggle);
            /*int indexOf = ("" + timeText.getText()).indexOf(":");
            String substr = ("" + timeText.getText()).substring(0, indexOf);
            int hours = Integer.parseInt(substr);
            if (timeToggle.isChecked()) //PM
            {
                if (hours != 12)
                    hours += 12;
                startTime = "" + hours + ("" + timeText.getText()).substring(indexOf);
            }
            else if (hours == 12)//midnight
            {
                hours = 0;
                startTime = "" + hours + ("" + timeText.getText()).substring(indexOf);
            }*/

            if (timeToggle.isChecked()) //PM
                startTime += "PM";
            else
                startTime += "AM";
        }
        return startTime;
    }
}

//TODO:only proceed if fields are valid
//TODO:other
