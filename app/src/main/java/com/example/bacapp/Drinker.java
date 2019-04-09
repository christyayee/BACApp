package com.example.bacapp;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.content.ContentValues.TAG;

public class Drinker
{
    private String myName;
    private double myWeight;
    private boolean mySex; //true is female
    private double myBAC;
    private int myDrinks;
    private Calendar myStart;
    private int myFeet;
    private int myInches;

    private int numBeer;
    private int numWine;
    private int numLiquor;
    private int numOther;

    Drinker(String name, double weight, boolean sex, int feet, int inches)
    {
        myName = name;
        myWeight = weight;
        mySex = sex;
        myBAC = 0;
        myDrinks = 0;
        myStart = null;
        myFeet = feet;
        myInches = inches;

        numBeer = 0;
        numWine = 0;
        numLiquor = 0;

    }

    Drinker(String name, double weight, boolean sex, int nBeer, int nWine, int nLiquor, String start)
    {
        myName = name;
        myWeight = weight;
        mySex = sex;
        myBAC = 0;
        try {
            myStart.setTime(new SimpleDateFormat("HH:mm").parse(start));
        }
        catch (ParseException e)
        {
            System.out.print(e.getMessage());
        }

        numBeer = nBeer;
        numWine = nWine;
        numLiquor = nLiquor;
        myDrinks = numBeer + numWine + numLiquor;

    }

    public double getBAC()
    {
        return myBAC;
    }

    public String getName()
    {
        return myName;
    }

    public double getWeight()
    {
        return myWeight;
    }

    public int getFeet()
    {
        return myFeet;
    }

    public int getInches()
    {
        return myInches;
    }

    public boolean getSex()
    {
        return mySex;

    }

    public int getDrinks()
    {
        return myDrinks;
    }

    public void incrementBeer(int num)
    {
        if (myStart == null && myDrinks == 0)
            myStart = Calendar.getInstance();
        numBeer += num;
        myDrinks += num;
    }

    public void incrementWine(int num)
    {
        if (myStart == null && myDrinks == 0)
            myStart = Calendar.getInstance();
        numWine += num;
        myDrinks += num;
    }

    public void incrementLiquor(int num)
    {
        if (myStart == null && myDrinks == 0)
            myStart = Calendar.getInstance();
        numLiquor += num;
        myDrinks += num;
    }

    public void reset()
    {
        myDrinks = 0;
        myBAC = 0;
        numBeer = 0;
        numWine = 0;
        numLiquor = 0;
        myStart = null;
    }

    //returns true if myStart is not null (time began)
    public boolean hasStarted()
    {
        return myStart != null;
    }

    public void setName(String name)
    {
        myName = name;
    }

    public void setWeight(double weight)
    {
        myWeight = weight;
    }

    public void setFeet(int feet)
    {
        myFeet = feet;
    }

    public void setInches(int inches)
    {
        myInches = inches;
    }

    public void setSex(boolean sex)
    {
        mySex = sex;
    }

    public void setStartTime(String str)
    {
        if (str == "now")
            myStart = Calendar.getInstance();
        else
        {
            try {
                myStart.setTime(new SimpleDateFormat("HH:mm").parse(str));
                myStart.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DATE));
                myStart.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                myStart.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

//                Log.v(TAG,"FUCK! FUCK! FUCK!" + str);

            }
            catch (ParseException e)
            {
                System.out.print(e.getMessage());
            }
        }
    }

    //returns time elapsed in hours
    public double getTimeElapsed()
    {
        Date date = new Date();
        Timestamp nowStamp = new Timestamp(date.getTime());

        //now Time hour/sec is before startTime's
        if (date.before(myStart.getTime()))
            myStart.set(Calendar.DAY_OF_MONTH, -1 + Calendar.getInstance().get(Calendar.DATE));

        Timestamp startStamp = new Timestamp(myStart.getTime().getTime());

        //convert milliseconds to hours
        long milliseconds = nowStamp.getTime() - startStamp.getTime();
        long seconds = milliseconds / 1000;
        double hours = (double)seconds / 3600;

        return hours;
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


//TODO:personal stats

}
