package com.example.bacapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import org.w3c.dom.Text;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements SaveDialog.NoticeDialogListener{

    EditText nameText;
    EditText weightText;  //assume kgs
    EditText feetText;
    EditText inText;
    Spinner sexSpinner;
    ToggleButton weightToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get all values
        nameText = (EditText)findViewById(R.id.nameText);
        weightText = (EditText)findViewById(R.id.weightText);  //assume kgs
        feetText = (EditText)findViewById(R.id.feetText);
        inText = (EditText)findViewById(R.id.inText);
        sexSpinner = (Spinner)findViewById(R.id.sexSpinner);
        weightToggle = (ToggleButton)findViewById(R.id.weightToggle);

        //populate sexSpinner
        String[]sexes = {"female", "male"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        populateData();

        Button menuButton = (Button)findViewById(R.id.saveButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveData())
                {
                    DialogFragment dialog = new SaveDialog();
                    dialog.show(getSupportFragmentManager(), "SaveDialog");
                }
                else
                {
                    //goes to Profiles Activity Screen
                    Intent drinksIntent = new Intent(getApplicationContext(),Profiles.class);
                    startActivity(drinksIntent);
                }
            }
        });
        Button continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveData()) //no data issues
                {
                    //goes to Drinks Activity Screen
                    Intent drinksIntent = new Intent(getApplicationContext(),Drinks.class);
                    startActivity(drinksIntent);
                }
            }
        });
    }

    //returns created Drinker if all valid values
    //returns null and displays Toast of first invalid
    private Drinker createProfile()
    {
        String name = "" + nameText.getText();
        if (name == "")
            return throwMessage('n');

        String strW = "" + weightText.getText();
        if (strW == "")
            return throwMessage('w');
        double weight = Double.parseDouble(strW);
        if (weight <= 0)
            return throwMessage('w');

        String strF = "" + feetText.getText();
        String strI = "" + inText.getText();
        if (strF == "" || strI == "")
            return throwMessage('w');

        int feet;
        int inches;
        try {
            feet = Integer.parseInt(strF);
            inches = Integer.parseInt(strI);
        }
        catch (NumberFormatException e){
            return null;
        }

        if (feet < 0 || inches < 0 || feet + inches <= 0)
            return throwMessage('h');
        if (inches >= 12)
            return throwMessage('i');

        boolean sexBool = false;
        if (sexSpinner.getSelectedItem() != null)
        {
            String sex = "" + sexSpinner.getSelectedItem();
            if (sex == "female")
                sexBool = true;
        }
        else
            return throwMessage('s');

        //convert lbs to kgs if lbs
        if (weightToggle.isChecked()) //lbs
            weight /= 2.20462;

        weight *= 1000; //convert kgs to g

        //create profile
        Drinker drunk = new Drinker(name, weight, sexBool, feet, inches);
        return drunk;
    }

    //displays Toast of error message determined by key, returns null
    private Drinker throwMessage(char key)
    {
        Toast t = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
        switch (key)
        {
            case 'n':
                t.setText("Please input a name. Try again.");
                break;
            case 'w':
                t.setText("Invalid weight. Weight must be positive, nonzero number. Try again.");
                break;
            case 'h':
                t.setText("Invalid height. Height must be positive, nonzero number. Try again.");
                break;
            case 'i':
                t.setText("Invalid height. \nKeep inches value between 0-11");
                break;
            case 's':
                t.setText("Please choose a sex. Try again.");
                break;
        }
        t.show();
        return null;
    }

    private void populateData()
    {
        if (Profiles.current != null)
        {
            double weight = Profiles.current.getWeight(); //assume g
            weight /= 1000; //convert g to kg
            weight *= 2.20462; //convert kg to lb
            String formatted = "" + weight;
            if (weight % 1 != 0) {
                DecimalFormat df = new DecimalFormat("###.##");
                formatted = df.format(weight);
            }
            weightText.setText(formatted);

            nameText.setText(Profiles.current.getName());
            feetText.setText("" + Profiles.current.getFeet());
            inText.setText("" + Profiles.current.getInches());
            sexSpinner.setSelection(1);
        }
    }

    private boolean saveData()
    {
        Drinker drunk = createProfile();
        if (drunk != null) //no data issues
        {
            //add to ArrayList
            if ( Profiles.current == null && !Profiles.drunks.contains(drunk)) {
                Profiles.drunks.add(drunk);
                Profiles.current = drunk;
            }
            else //editting
            {
                //could edit Name,weight, feet, inches, sex
                Profiles.current.setName(drunk.getName());
                Profiles.current.setWeight(drunk.getWeight());
                Profiles.current.setFeet(drunk.getFeet());
                Profiles.current.setInches(drunk.getInches());
                Profiles.current.setSex(drunk.getSex());
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //User touched the dialog's positive button
        //continue to main

        //goes to Profiles Activity Screen
        Intent drinksIntent = new Intent(getApplicationContext(),Profiles.class);
        startActivity(drinksIntent);
        Toast t = Toast.makeText(getApplicationContext(), "Profile not created/updated.", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        //cancel, do nothing
    }
}



//TODO:optional personal limit (with suggested limits)
//TODO:save/write to files