package com.example.earthquake;



import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;


public class modalClass extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public modalClass(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        return query.fetchEarthquakeData(mUrl);
    }

}