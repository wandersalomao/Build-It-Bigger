package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.wandersalomao.builditbigger.backend.myApi.MyApi;
import com.example.wandersalomao.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * @author Wander Salomao
 */
public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... context) {

        if(myApiService == null) {  // Only do this once

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        this.context = context[0];

        try {

            // use the service api to call the java library to retrieve a joke
            return myApiService.tellJoke().execute().getData();

        } catch (IOException e) {
            return "Problem trying to connect to your service: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        // Get a joke and display it in a new activity.
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, result);
        context.startActivity(intent);
    }
}
