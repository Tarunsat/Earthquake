package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import android.content.Loader;
import android.net.Uri;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>>{

    private static final int EARTHQUAKE_LOADER_ID = 1;
    ArrayList<Earthquake> earthquakes = new ArrayList<>();
    EarthquakeAdapter mAdapter;
    ListView earthquakeListView;
    TextView mEmptyState;
    ProgressBar mProgressBar;

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=2&limit=20";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DownloadData downloadData = new DownloadData();
//        downloadData.execute(USGS_REQUEST_URL);

        mEmptyState = (TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Checking the internet connection before initializing the loader
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager  = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        } else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No internet found", Toast.LENGTH_SHORT).show();
        }

        earthquakeListView = (ListView) findViewById(R.id.list);

        earthquakeListView.setEmptyView(mEmptyState);

        mAdapter = new EarthquakeAdapter(this, earthquakes);
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = earthquakes.get(i).getUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        // Create a new {@link ArrayAdapter} of earthquakes

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new modalClass(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        mAdapter.clear();
        if(earthquakes == null) {
            return;
        }
        mAdapter.addAll(earthquakes);

        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "No earthquake found", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        mAdapter.clear();
    }

//    private class DownloadData extends AsyncTask<String, Void, List<Earthquake> > {
//
//        @Override
//        protected List<Earthquake> doInBackground(String... urls) {
//
//            List<Earthquake> earthquakes = new ArrayList<>();
//            if(urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);
//            return earthquakes;
//        }
//
//        @Override
//        protected void onPostExecute(List<Earthquake> earthquakesList) {
//

//            mAdapter.clear();
//            if(earthquakesList != null && !earthquakesList.isEmpty() ) {
//                mAdapter.addAll(earthquakesList);
//            }
//        }
//    }

}