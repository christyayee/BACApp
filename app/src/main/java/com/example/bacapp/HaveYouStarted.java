package com.example.bacapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HaveYouStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_you_started);

        Button yesButton = (Button)findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goes to second Activity Screen
                Intent startIntent = new Intent(getApplicationContext(),MainActivity.class);
                //startIntent.putExtra("key", "HELLO WORLD");
                startActivity(startIntent);
            }
        });



    }
}
