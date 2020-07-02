package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.github.scribejava.apis.TwitterApi;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String TAG = "ComposeActivity";

    EditText etCompose;
    Button btnTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        // Twitter client
        client = TwitterApp.getRestClient(this);

        // connect fields to items in xml
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        // set click listener on the button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make API call to twitter to publish tweet
                final String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,
                            "Sorry, Your tweet cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    // Snackbar is typically better for error handling
                    return;
                } else if (tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this,
                            "Sorry, Your tweet is too long",
                            Toast.LENGTH_SHORT).show();
                } else {
                    client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "onSuccess publishing tweet");
                            try {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                Log.i(TAG, "Published tweet! It says: " + tweet.body);
                                Intent i = new Intent();
                                i.putExtra("tweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, i);
                                finish(); // Closes activity and sends result to parent
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "onFailure publishing Tweet", throwable);
                        }
                    });
                }
            }
        });
    }
}