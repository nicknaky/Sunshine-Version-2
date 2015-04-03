package com.mycompany.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//import android.support.v4.app.Fragment;

/**
 * Created by NLam on 3/22/2015.
 */
public class ForecastFragment extends Fragment{


    public ArrayAdapter<String> forecastadapter;

    public String postcode = "94043";

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh){
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    private void updateWeather(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = sharedPref.getString(getString(R.string.location_key),getString(R.string.location_default));

        String geoValue = "geo:0,0?q=" + location;
        sharedPref.edit().putString(getString(R.string.geo_key), geoValue).apply();

        String geoValueDebug = sharedPref.getString(getString(R.string.geo_key),"Default Value");
        Log.v("Geo Key Debug", geoValueDebug);




        Log.v("Shared Preferences Str", location);
        new FetchWeatherTask(getActivity(),forecastadapter).execute(location);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        /* Fake data for testing
        ArrayList<String> fakedata = new ArrayList<String>();
        fakedata.add("Today - Sunny - 88/63");
        fakedata.add("Tomorrow - Foggy - 70/46");
        fakedata.add("Weds - Cloudy - 72/63");
        fakedata.add("Thurs - Rainy - 64/51");
        fakedata.add("Fri - Foggy - 70/46");
        fakedata.add("Sat - Sunny - 76/68");
        fakedata.add("Sun - Rainy - 88/63");
        fakedata.add("Mon - Sunny - 88/63");
        fakedata.add("Tue - Sunny - 88/63");
        */

        forecastadapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, new ArrayList<String>());


        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast toast = Toast.makeText(getActivity(), forecastadapter.getItem(position), Toast.LENGTH_SHORT);
                toast.show();


                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, forecastadapter.getItem(position));
                startActivity(intent);

            }
        });

        return rootView;
    }


}

