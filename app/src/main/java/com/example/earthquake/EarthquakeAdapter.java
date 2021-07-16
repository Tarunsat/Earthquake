package com.example.earthquake;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mohamed on 8/27/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    List<Earthquake> mEarthquakes;

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);

        mEarthquakes = earthquakes;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Earthquake current = mEarthquakes.get(position);


        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.recycle_item, parent, false);
        }


        TextView tvMag = (TextView) view.findViewById(R.id.mag_text_view);
        tvMag.setText(formatMagnitude(current.getMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) tvMag.getBackground();


        String []fullLocation = split(current.getLocation());

        TextView tvPlace = (TextView) view.findViewById(R.id.place_text_view);
        tvPlace.setText(fullLocation[1]);

        TextView tvOffset = (TextView) view.findViewById(R.id.offset_text_view);
        tvOffset.setText(fullLocation[0]+" of");

        Date date = new Date(current.getTimeInMilliSeconds());

        TextView tvDate = (TextView) view.findViewById(R.id.date_text_view);
        tvDate.setText(formatDate(date));

        TextView tvTime = (TextView) view.findViewById(R.id.time_text_view);
        tvTime.setText(formatTime(date));


        return view;
    }

    public String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h: mm a");
        return  dateFormat.format(date);
    }

    private String formatDate(Date date ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM DD, yyyy");
        return dateFormat.format(date);
    }

    private String [] split(String string) {
        String [] array = {};
        if(string.contains("of")) {
            array = string.split("of");
            return array;
        } else {
            array = new String[]{"Near", string};
            return array;
        }
    }



    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}



