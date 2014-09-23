package com.aerilys.steamexplorer.network;

import com.aerilys.steamexplorer.models.GameApp;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Interface for Steam services calls using Retrofit
 */
public interface SteamService {


    @GET("/appdetails")
    GameApp getAppDetails(@Query("appids") int appId);
}
