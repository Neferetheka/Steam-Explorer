package com.aerilys.steamexplorer.network;

import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.aerilys.steamexplorer.models.GameApp;

import retrofit.RestAdapter;

/**
 * Network layer to call Steam API
 */
public class NetworkHelper {

    private static final int CACHE_SIZE = 25;

    /**
     * Steam API URL
     */
    public static final String STEAM_HOST = "http://store.steampowered.com/api";

    /**
     * Steam Service.
     */
    private final SteamService mSteamService;

    private final GameConverter mGameConverter = new GameConverter();

    private LruCache<Integer, GameApp> mGamesCache = new LruCache<Integer, GameApp>(CACHE_SIZE);


    public NetworkHelper() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(STEAM_HOST)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setConverter(mGameConverter)
                .build();

        mSteamService = restAdapter.create(SteamService.class);
    }

    /**
     * Call the Steam API to get the details about a game.
     *
     * @param app : game app
     * @return : found app or null
     */
    public GameApp getGameDetails(GameApp app) {
        int appId = app.appid;

        GameApp gameApp = mGamesCache.get(appId);

        if (gameApp == null) {
            if(!TextUtils.isEmpty(app.detailedDescription))
            {
                mGamesCache.put(app.appid, app);
                return app;
            }

            mGameConverter.setAppId(Integer.toString(appId));
            gameApp = mSteamService.getAppDetails(appId);

            mGamesCache.put(app.appid, gameApp);
        }

        return gameApp;
    }
}
