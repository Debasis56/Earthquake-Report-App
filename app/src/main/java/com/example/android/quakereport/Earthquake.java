package com.example.android.quakereport;

public class Earthquake {

    private double mMagnitude;

    private String mLocation;


    private long mTimeInMilliseconds;

    private String mUrl;

    public Earthquake(double fMagnitude, String fLocation, long fTimeInMilliseconds, String fUrl)
    {
        mMagnitude = fMagnitude;
        mLocation = fLocation;
        mTimeInMilliseconds = fTimeInMilliseconds;;
        mUrl = fUrl;
    }

    public double getmMagnitude()
    {
        return mMagnitude;
    }

    public String getmLocation()
    {
        return mLocation;
    }

    public long getmTimeInMilliseconds()
    {
        return mTimeInMilliseconds;
    }

    public String getmUrl()
    {
        return mUrl;
    }


}