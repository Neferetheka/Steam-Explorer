package com.aerilys.steamexplorer.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO to represent the user data
 */
public class User {
    private List<GameApp> mListGameApps;

    public User() {
        mListGameApps = new ArrayList<GameApp>();
    }

    public List<GameApp> getListGameApps() {
        return mListGameApps;
    }

    public boolean containsGame(int appId) {
        for (GameApp app : mListGameApps) {
            if (app.appid == appId) {
                return true;
            }
        }
        return false;
    }

    public void removeGame(GameApp gameApp) {
        for(GameApp app : mListGameApps)
        {
            if(app.appid == gameApp.appid)
            {
                mListGameApps.remove(app);
                return;
            }
        }
    }

    public void addGame(GameApp gameApp) {
        mListGameApps.add(gameApp);
    }
}
