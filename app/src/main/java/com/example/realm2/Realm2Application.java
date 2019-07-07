package com.example.realm2;

import android.app.Application;

import io.realm.Realm;

public class Realm2Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);


    }
}
