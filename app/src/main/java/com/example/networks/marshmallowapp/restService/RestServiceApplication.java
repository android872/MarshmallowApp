package com.example.networks.marshmallowapp.restService;

import android.app.Application;

import com.example.networks.marshmallowapp.restService.data.User;

/**
 * Created by Networks on 5/3/2017.
 */

public class RestServiceApplication extends Application {
    private static RestServiceApplication instance;
    private User user;
    private String accessToken;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        user = new User();
    }
    public static RestServiceApplication getInstance(){
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
