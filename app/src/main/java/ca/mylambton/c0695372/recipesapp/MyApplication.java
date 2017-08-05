package ca.mylambton.c0695372.recipesapp;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by rodrigocoutinho on 2017-08-03.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

}
