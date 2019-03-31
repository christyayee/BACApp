package com.example.bacapp;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.nfc.Tag;

public class Drinks extends AppCompatActivity {

    RadioGroup group;
    RadioButton rButton;
    EditText numText;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        group = (RadioGroup) findViewById(R.id.radioGroup);

        //TODO: add back images

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText timeText = (EditText) findViewById(R.id.timeText);
                if (checkBox.isChecked())
                    timeText.setEnabled(false);
                else
                    timeText.setEnabled(true);
            }
        });

        final LinearLayout timeLayout = (LinearLayout) findViewById(R.id.timeLayout);
        if (Profiles.current.getDrinks() == 0 && !Profiles.current.hasStarted())
            timeLayout.setVisibility(View.VISIBLE);
        else
            timeLayout.setVisibility(View.INVISIBLE);
        numText = (EditText) findViewById(R.id.numText);
        num = Integer.parseInt("" + numText.getText());

        Button upButton = (Button) findViewById(R.id.upButton);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt("" + numText.getText());
                num++;
                numText.setText("" + num);
            }

        });
        Button downButton = (Button) findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt("" + numText.getText());
                num--;
                numText.setText("" + num);
            }

        });


        Button continueButton = (Button) findViewById(R.id.continueButton);
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

                //update startTime
                EditText timeText = (EditText) findViewById(R.id.timeText);
                if (timeText.isShown()) {
                    String startTime = getStartTime();
                    Profiles.current.setStartTime(startTime);
                    Toast t = Toast.makeText(getApplicationContext(), "FUCK FUCK FUCK", Toast.LENGTH_LONG);
                    t.show();
                }


                //goes to Result Activity Screen
                Intent drinksIntent = new Intent(getApplicationContext(), Result.class);
                startActivity(drinksIntent);
            }
        });


    }

    public void onRButtonClicked(View view) {
        int radioID = group.getCheckedRadioButtonId();
        rButton = findViewById(radioID);
    }

    public String getStartTime() {
        String startTime = "now";
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        if (!checkBox.isChecked()) {
            EditText timeText = (EditText) findViewById(R.id.timeText);
            String temp = "" + timeText.getText();
            ToggleButton timeToggle = (ToggleButton) findViewById(R.id.timeToggle);
            int indexOf = temp.indexOf(":");
            String substr = temp.substring(0, indexOf);
            int hours = Integer.parseInt(substr);
            if (timeToggle.isChecked()) //PM
            {
                if (hours != 12)
                    hours += 12;
                startTime = "" + hours + temp.substring(indexOf);
            } else if (hours == 12)//midnight
            {
                hours = 0;
                startTime = "" + hours + temp.substring(indexOf);
            }

        }
        return startTime;
    }
}

//TODO:only proceed if fields are valid