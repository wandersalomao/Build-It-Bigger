package com.udacity.gradle.builditbigger.free;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewSwitcher;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;


public class MainActivity extends ActionBarActivity {

    InterstitialAd mInterstitialAd;
    ViewSwitcher mViewSwitch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mViewSwitch = (ViewSwitcher) findViewById(R.id.viewSwitcher);

        final MainActivity mainActivity = this;
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mViewSwitch.showNext();
                requestNewInterstitial();
                new EndpointsAsyncTask().execute(mainActivity);
            }
        });

        requestNewInterstitial();
    }

    /**
     * Method called when the user clicks on the button
     *
     * @param view The view that triggered this action
     */
    public void tellJoke(View view){

        // because this is the free version we should first show the interstitial ad
        // and then when the ad is closed we call the java library to retrieve the joke
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    /**
     * Method used to load a new Interstitial Ad
     */
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
