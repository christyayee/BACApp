package com.example.bacapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView calcText = (TextView)findViewById(R.id.calculationLbl);

        //update value
        double bac = Profiles.current.calculateBAC();
        DecimalFormat df = new DecimalFormat("#.####%");
        String formattedPercent = df.format(bac/100);

        TextView finalbac = (TextView) findViewById(R.id.calculationLbl);
        finalbac.setText(formattedPercent);

        Button anotherButton = (Button) findViewById(R.id.anotherButton);
        anotherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goes to Drinks Activity Screen
                Intent startIntent = new Intent(getApplicationContext(), Drinks.class);
                startActivity(startIntent);
            }
        });

        Button endButton = (Button) findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profiles.current.reset();

                //goes to Profiles Activity Screen
                Intent startIntent = new Intent(getApplicationContext(), Profiles.class);
                startActivity(startIntent);
            }
        });

        Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goes to Profiles Activity Screen
                Intent startIntent = new Intent(getApplicationContext(), Profiles.class);
                startActivity(startIntent);
            }
        });


    }
}

//TODO:consequences and colored