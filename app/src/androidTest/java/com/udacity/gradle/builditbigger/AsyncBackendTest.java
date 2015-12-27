package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.test.AndroidTestCase;

import java.util.concurrent.TimeUnit;

/**
 * @author Wander Salomao
 */
public class AsyncBackendTest extends AndroidTestCase {

    /**
     * Test method used to test if a joke is returned from the java library
     */
    public void testTellJoke() {

        // Create the Main Activity
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);

        // Run the AsyncTask and check the joke value
        try {
            EndpointsAsyncTask jokeTask = new EndpointsAsyncTask();
            jokeTask.execute(getContext());
            String joke = jokeTask.get(30, TimeUnit.SECONDS);
            //assertEquals("This is a funny library joke", joke);
            assertTrue(joke != null);
        } catch (Exception e){
            fail("Test failed: " + e.getMessage());
        }
    }
}
