package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by magupta on 11/8/15.
 */
public class User implements Serializable{
    private String name;
    private String profileImageUrl;
    private String uid;
    private String location;
    private String screenName;

    public String getScreenName() {
        return "@" + screenName;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getLocation() {
        return location;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.location = jsonObject.getString("location");
            user.uid = jsonObject.getString("id_str");
            user.screenName = jsonObject.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
/*
      "name": "OAuth Dancer",
              "profile_sidebar_fill_color": "DDEEF6",
              "profile_background_tile": true,
              "profile_sidebar_border_color": "C0DEED",
              "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
              "createdAt": "Wed Mar 03 19:37:35 +0000 2010",
              "location": "San Francisco, CA",
              "follow_request_sent": false,
              "id_str": "119476949",
              "is_translator": false,
              "profile_link_color": "0084B4",
              "entities": {
                "url": {
                  "urls": [
                    {
                      "expanded_url": null,
                      "url": "http://bit.ly/oauth-dancer",
                      "indices": [
                        0,
                        26
                      ],
                      "display_url": null
                    }
                  ]
                },
                "description": null
*/