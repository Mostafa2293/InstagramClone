package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("kjipVKraRjahxAHr7Wr4OdmiY38238tixoxx7MTy")
                // if defined
                .clientKey("2ZcfGo6uFyYtW7vmV4fZYnji2MjG7MbVKGGjUXT9")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
