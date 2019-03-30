package com.example.bacapp;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Drinks extends AppCompatActivity {

    RadioGroup group;
    RadioButton rButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        group = (RadioGroup)findViewById(R.id.radioGroup);

        //TODO: create otherButton and mystery Activity Screen

    }

    public void onRButtonClicked(View view)
    {
        int radioID = group.getCheckedRadioButtonId();
        rButton = findViewById(radioID);
    }

    /*public void onRButtonClicked(View view)
    {
        final RadioButton beerButton, wineButton, liquorButton, otherButton;

        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup);
        beerButton = (RadioButton)findViewById(R.id.beerButton);
        wineButton = (RadioButton)findViewById(R.id.wineButton);
        liquorButton = (RadioButton)findViewById(R.id.liquorButton);
        otherButton = (RadioButton)findViewById(R.id.otherButton);
        group.addView(beerButton);
        group.addView(wineButton);
        group.addView(liquorButton);
        group.addView(otherButton);

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId())
        {
            case R.id.beerButton:
                if (checked)
                {
                    group.clearCheck();
                    group.check(R.id.beerButton);
                }
            case R.id.wineButton:
                if (checked)
                {
                    group.clearCheck();
                    group.check(R.id.wineButton);
                }case R.id.liquorButton:
            if (checked)
            {
                group.clearCheck();
                group.check(R.id.liquorButton);
            }
            case R.id.otherButton:
            if (checked)
            {
                group.clearCheck();
                group.check(R.id.otherButton);
            }
        }
    }*/
}

