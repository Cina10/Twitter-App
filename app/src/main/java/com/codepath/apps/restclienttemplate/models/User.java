package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public User() { }

    public String screenName;
    public String profileImageUrl;
    public String handle;


    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.screenName = jsonObject.getString("name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.handle = "@" + jsonObject.getString("screen_name");
        return user;
    }
}
