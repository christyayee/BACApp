package com.example.bacapp;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Drinker
{
    private String myName;
    private double myWeight;
    private boolean mySex; //true is female
    private double myBAC;
    private int myDrinks;
    private Calendar myStart;

    private int numBeer;
    private int numWine;
    private int numLiquor;
    private int numOther;

    Drinker(String name, double weight, boolean sex)
    {
        myName = name;
        myWeight = weight;
        mySex = sex;
        myBAC = 0;
        myDrinks = 0;
        //Date date = Calendar.getInstance().getTime();
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //myStart = dateFormat.format(date);
        myStart = null;
        /*if (start == "now")
            myStart = Calendar.getInstance();
        else
        {
            try {
                myStart.setTime(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(start));
            }
            catch (ParseException e)
            {
                System.out.print(e.getMessage());
            }
        }*/

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
            myStart.setTime(new SimpleDateFormat("hh:mm").parse(start));
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

    public void setStartTime(String str)
    {
        if (str == "now")
            myStart = Calendar.getInstance();
        else
        {
            try {
                myStart.setTime(new SimpleDateFormat("hh:mm a").parse(str));
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
        long temp = date.getTime();
        Timestamp nowStamp = new Timestamp(date.getTime());
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(nowStamp.getTime());

        Timestamp startStamp = new Timestamp(myStart.getTime().getTime());

        //convert milliseconds to hours
        long milliseconds = nowStamp.getTime() - startStamp.getTime();
        int seconds = (int) milliseconds / 1000;
        double hours = seconds / 3600;

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


//TODO:set limit, otherAlc details

}
