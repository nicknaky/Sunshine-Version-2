package com.mycompany.sunshine;
//import android.support.v7.app.ActionBar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

//import android.support.v4.app.Fragment;


public class DetailActivity extends Activity {

    private ShareActionProvider mShareActionProvider;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        String actionProvider = mShareActionProvider.toString();
        Log.v("Action Provider", actionProvider);

        Intent intent = this.getIntent();
        String shareDetail = intent.getStringExtra(Intent.EXTRA_TEXT) + " #SunshineApp";
        Log.v("shareDetail", shareDetail);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareDetail);
        shareIntent.setType("text/plain");
        mShareActionProvider.setShareIntent(shareIntent);


        //setShareMenu();

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

        return super.onOptionsItemSelected(item);
    }
  /*  private void setShareMenu(){

        Intent intent = this.getIntent();
        String shareDetail = intent.getStringExtra(Intent.EXTRA_TEXT) + " #SunshineApp";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareDetail);
        mShareActionProvider.setShareIntent(shareIntent);


    } */

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.textview_detail);


            Intent intent = getActivity().getIntent();
            String forecastDetail = intent.getStringExtra(Intent.EXTRA_TEXT);

            textView.setText(forecastDetail);


            return rootView;
        }
    }
}




