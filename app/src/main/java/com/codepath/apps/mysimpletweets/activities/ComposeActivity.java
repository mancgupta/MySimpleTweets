package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
    private MenuItem miCharacterCount;
    private MenuItem miUpdateStatus;

    private static final int MAX_TWEET_CHARACTERS = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if ( toolbar != null ) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.twitter);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        setUpViews();

        client = TwitterApplication.getRestClient();
        User user = (User) getIntent().getSerializableExtra("user");

        Picasso.with(ComposeActivity.this).load(user.getProfileImageUrl()).into(ivProfilePicture);
        tvUsername.setText(user.getName());
        tvScreenName.setText(user.getScreenName());


    }

    private void setListeners() {
        etStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int characterLeft = MAX_TWEET_CHARACTERS - s.length();
                if (characterLeft < 0) {
                    miUpdateStatus.setEnabled(false);
                    miCharacterCount.setTitle("0");
                }
                else{
                    miUpdateStatus.setEnabled(true);
                    miCharacterCount.setTitle(String.valueOf(characterLeft));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        miCharacterCount = (MenuItem) menu.findItem(R.id.miCharacterCount);
        miUpdateStatus = (MenuItem) menu.findItem(R.id.miUpdateStatus);

        setListeners();
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
