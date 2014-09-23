package com.aerilys.steamexplorer.tools;

import android.content.Context;

import com.aerilys.steamexplorer.models.Category;
import com.aerilys.steamexplorer.models.GameApp;
import com.aerilys.steamexplorer.models.User;
import com.aerilys.steamexplorer.network.NetworkHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class DataContainer {

    private static final String PREFS_USER_KEY = "PREFS_USER_KEY";

    @RootContext
    Context mContext;

    /**
     * Gson serializer
     */
    private Gson mGson = new Gson();

    /**
     * List of all game categories
     */
    private List<Category> mListCategories;

    /**
     * Network helper instance
     */
    private NetworkHelper mNetworkHelper = new NetworkHelper();

    /**
     * Unique User object
     */
    private User mUser = new User();

    public User loadUser()
    {
        String userAsJson = PrefsManager.getString(PREFS_USER_KEY, null);

        if(userAsJson != null) {
            mUser = mGson.fromJson(userAsJson, User.class);
        }

        return mUser;
    }

    @Background
    public void saveUser()
    {
        String userAsJson = mGson.toJson(mUser);
        PrefsManager.edit(PREFS_USER_KEY, userAsJson);
    }

    public void loadCategories() throws IOException {
        String result = getStringFromAssets(mContext, "data/categories.json");
        Type listType = new TypeToken<List<Category>>() {
        }.getType();
        mListCategories = mGson.fromJson(result, listType);
    }

    public List<GameApp> loadCategoryApps(Category category) throws  IOException{
        String result = getStringFromAssets(mContext, "data/"+category.name+".json");

        Type listType = new TypeToken<List<GameApp>>() {
        }.getType();
        return mGson.fromJson(result, listType);
    }

    public static String getStringFromAssets(Context context, String path) throws IOException {
        InputStream is = context.getAssets().open(path);

        int ch;
        StringBuilder sb = new StringBuilder();

        while ((ch = is.read()) != -1) {
            sb.append((char) ch);
        }

        String result = sb.toString();
        is.close();
        return result;
    }


    public List<Category> getlistCategories() {
        return mListCategories;
    }


    public NetworkHelper getNetworkHelper() {
        return mNetworkHelper;
    }

    public User getUser() {
        return mUser;
    }
}
