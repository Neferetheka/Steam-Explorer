package com.aerilys.steamexplorer;

import android.app.Application;

import com.aerilys.steamexplorer.tools.DataContainer;
import com.aerilys.steamexplorer.tools.PrefsManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import java.io.IOException;

@EApplication
public class SteamApplication extends Application {

    @Bean
    DataContainer mDataContainer;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            PrefsManager.init(this);
            mDataContainer.loadUser();
            mDataContainer.loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
