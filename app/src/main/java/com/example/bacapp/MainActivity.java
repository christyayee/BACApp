package com.example.bacapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populate sexSpinner
        Spinner sexSpinner = (Spinner)findViewById(R.id.sexSpinner);
        String[]sexes = {"female", "male"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        Button endButton = (Button)findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
                Intent drinksIntent = new Intent(getApplicationContext(),Profiles.class);
                startActivity(drinksIntent);
            }
        });

        Button continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
                //goes to Drinks Activity Screen
                Intent drinksIntent = new Intent(getApplicationContext(),Drinks.class);
                startActivity(drinksIntent);
            }
        });

    }
    //TODO: return bool if all fields filled and profile is created
    private void createProfile()
    {
        //get all values
        EditText nameText = (EditText)findViewById(R.id.nameText);
        EditText weightText = (EditText)findViewById(R.id.weightText);  //assume kgs
        EditText feetText = (EditText)findViewById(R.id.feetText);
        EditText inText = (EditText)findViewById(R.id.inText);
        Spinner sexSpinner = (Spinner)findViewById(R.id.sexSpinner);

        String name = "" + nameText.getText();
        double weight = Double.parseDouble(""+ weightText.getText());
        int feet = Integer.parseInt(""+ feetText.getText());
        int inches = Integer.parseInt(""+ inText.getText());
        String sex = "" + sexSpinner.getSelectedItem();
        boolean sexBool = false;
        if (sex == "female")
            sexBool =  true;

        //convert lbs to kgs if lbs
        ToggleButton weightToggle = (ToggleButton)findViewById(R.id.weightToggle);
        if (weightToggle.isChecked()) //lbs
            weight /= 2.20462;

        weight *= 1000; //convert kgs to g

        //create profile
        Drinker drunk = new Drinker(name, weight, sexBool);

        //add to ArrayList
        if (!Profiles.drunks.contains(drunk))
            Profiles.drunks.add(drunk);
        Profiles.current = drunk;
    }

}

//TODO:optional personal limit (with suggested limits)