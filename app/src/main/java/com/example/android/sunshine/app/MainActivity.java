package com.mycompany.sunshine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//import android.support.v7.app.ActionBarActivity;


public class MainActivity extends Activity {

    String LOG_LIFE = this.getClass().getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LOG_LIFE, "LOG LIFE: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOG_LIFE, "LOG LIFE: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOG_LIFE, "LOG LIFE: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LOG_LIFE, "LOG LIFE: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOG_LIFE, "LOG LIFE: onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
        Log.v(LOG_LIFE, "LOG LIFE: onCreate");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_view_map) {
            openPreferredLocationInMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void openPreferredLocationInMap(){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPref.getString(getString(R.string.location_key),getString(R.string.location_key));

        Uri uri = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Log.d(this.getClass().getSimpleName(), "Couldn't call " + location);
        }

    }

}
