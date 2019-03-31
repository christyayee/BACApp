package com.example.bacapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Profiles extends AppCompatActivity {

    public static ArrayList<Drinker> drunks = new ArrayList<Drinker>();
    public static Drinker current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        int buttonNum = Profiles.drunks.size();
        LinearLayout col0 = (LinearLayout) findViewById(R.id.col0);
        if (buttonNum == 0) {
            Toast t = Toast.makeText(getApplicationContext(), "No profiles created yet.", Toast.LENGTH_LONG);
            t.show();
        }
        else {
            for (int i = 0; i < buttonNum; i++) {
                try {
                    Button newButton = new Button(this);
                    newButton.setId(R.id.class.getField("b" + i).getInt(null));
                    newButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
                    newButton.setText(Profiles.drunks.get(i).getName());
                    col0.addView(newButton);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }

            TextView v0 = (TextView) findViewById(R.id.v0);
            v0.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, buttonNum - 1));
        }

        for (int i = 0; i < buttonNum; i++)
        {
            final int pos = i;
            try {
                String idname = "b" + i;
                Button currButton = (Button)findViewById(R.id.class.getField("b" + i).getInt(null));
                currButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //goes to Drinks Screen
                        Profiles.current = Profiles.drunks.get(pos);
                        Intent startIntent = new Intent(getApplicationContext(), Drinks.class);
                        startActivity(startIntent);
                    }
                });
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        Button createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goes to Main Activity Screen
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });



    }
}

//TODO: edit profile