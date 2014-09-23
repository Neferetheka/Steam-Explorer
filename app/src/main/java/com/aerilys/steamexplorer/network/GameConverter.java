package com.aerilys.steamexplorer.network;

import android.util.Log;

import com.aerilys.steamexplorer.models.GameApp;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Custom converter for Retrofit.
 */
public class GameConverter implements Converter {

    private String mAppId;

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {

        try {
            String asString = getString(body.in());

            try {
                JSONObject jsonObject = new JSONObject(asString);

                jsonObject = jsonObject.getJSONObject(mAppId).getJSONObject("data");
                String detailsAsJson = jsonObject.toString();
                Log.d("Steam", detailsAsJson);
                Gson gson = new Gson();

                return gson.fromJson(detailsAsJson, GameApp.class);

            } catch (JSONException e) {
                throw new ConversionException(e);
            }

        } catch (IOException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }

    public static String getString(InputStream is) throws IOException {
        int ch;
        StringBuilder sb = new StringBuilder();
        while ((ch = is.read()) != -1) {
            sb.append((char) ch);
        }
        return sb.toString();
    }


    public void setAppId(String mAppId) {
        this.mAppId = mAppId;
    }
}
