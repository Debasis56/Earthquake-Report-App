package com.example.android.quakereport;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Activity context, List<Earthquake> words) {
                                        super(context, 0, words);

    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy",Locale.ENGLISH);
        return dateFormat.format(dateObject);
    }


    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a",Locale.ENGLISH);
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double mag)
    {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(mag);
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
                ;
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String primaryLocation;
        String locationOffset;
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        final Earthquake currentEarthquake = getItem(position);

        String magnitude = formatMagnitude(currentEarthquake.getmMagnitude());

                TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
                        magnitudeView.setText(magnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

                int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

                magnitudeCircle.setColor(magnitudeColor);



        String originalLocation  = currentEarthquake.getmLocation();

        if(originalLocation .contains(LOCATION_SEPARATOR))
        {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }
        else
        {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

                Date dateObject = new Date(currentEarthquake.getmTimeInMilliseconds());

                TextView dateView = (TextView) listItemView.findViewById(R.id.date);
                String formattedDate = formatDate(dateObject);
                dateView.setText(formattedDate);

                TextView timeView = (TextView) listItemView.findViewById(R.id.time);
                String formattedTime = formatTime(dateObject);
                timeView.setText(formattedTime);


                        return listItemView;
    }
}