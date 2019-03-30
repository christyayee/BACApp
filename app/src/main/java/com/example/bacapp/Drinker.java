package com.example.bacapp;
import java.time.Duration;
import java.util.*;

public class Drinker
{
    private String myName;
    private double myWeight;
    private boolean mySex; //true is female
    private double myBAC;
    private int myDrinks;
    private Calendar myStart;

    Drinker(String name, double weight, boolean sex)
    {
        myName = name;
        myWeight = weight;
        mySex = sex;
        myBAC = 0;
        myDrinks = 0;
        myStart = Calendar.getInstance();
    }

    public double getBAC()
    {
        return myBAC;
    }

    public String getName()
    {
        return myName;
    }

    public int getDrinks()
    {
        return myDrinks;
    }

    public void incrementDrinks(int num)
    {
        myDrinks += num;
    }

    //returns hours
    public double getTimeElapsed()
    {
        Calendar now = Calendar.getInstance();
        Date firstDate  = now.getTime();
        Date secondDate = myStart.getTime();
        double elapsedTime = (double)firstDate.getTime() - secondDate.getTime();
        return (elapsedTime/1000)/3600;

    }

    public double calculateBAC()
    {
        double numAlcGrams = myDrinks*14;
        double numBGrams;
        if (mySex) //female
            numBGrams = 0.55*myWeight;
        else
            numBGrams = 0.68*myWeight;
        myBAC = ((numAlcGrams/numBGrams)*100)-(getTimeElapsed() * .015);
        return myBAC;
    }



}
