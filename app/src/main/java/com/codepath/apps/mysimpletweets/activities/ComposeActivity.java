package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

public class ComposeActivity extends AppCompatActivity {

    private User user;
    private ImageView ivProfilePicture;
    private TextView tvUsername;
    private TextView tvScreenName;

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

        user = (User)getIntent().getSerializableExtra("user");

        Picasso.with(ComposeActivity.this).load(user.getProfileImageUrl()).into(ivProfilePicture);
        tvUsername.setText(user.getName());
        tvScreenName.setText(user.getScreenName());


    }

    private void setUpViews() {
        ivProfilePicture = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }
}
