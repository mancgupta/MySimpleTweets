package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.clients.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvUsername;
    private TextView tvScreenName;
    private EditText etStatus;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.twitter);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        catch (NullPointerException e){
            Log.i("DEBUG", e.toString());
        }
        setUpViews();
        client = TwitterApplication.getRestClient();
        User user = (User) getIntent().getSerializableExtra("user");

        Picasso.with(ComposeActivity.this).load(user.getProfileImageUrl()).into(ivProfilePicture);
        tvUsername.setText(user.getName());
        tvScreenName.setText(user.getScreenName());


    }

    private void setUpViews() {
        ivProfilePicture = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        etStatus = (EditText) findViewById(R.id.etStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }

    public void onUpdateStatus(MenuItem item) {
        String status = etStatus.getText().toString();

        if (status.length() < 0 || status.length() > 140) {
            Toast.makeText(this, "Invalid Message length ( should be less than 140 characters)", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Updating status ...", Toast.LENGTH_LONG).show();
            client.updateStatus(status, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("DEBUG", "STATUS UPDATED");
                    Tweet tweet = Tweet.fromJSON(response);

                    Intent i = new Intent();
                    i.putExtra("tweet", tweet);
                    setResult(RESULT_OK, i);
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.i("DEBUG", "Failed to update status");
                }
            });
        }
    }
}
