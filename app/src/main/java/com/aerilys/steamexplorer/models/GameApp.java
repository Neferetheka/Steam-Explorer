package com.aerilys.steamexplorer.models;

import android.content.Context;

import com.aerilys.steamexplorer.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Game app POJO
 */
public class GameApp implements Serializable {
    public int appid;
    public String name;
    public String header;

    @SerializedName("detailed_description")
    public String detailedDescription;

    @SerializedName("about_the_game")
    public String about;

    public String reviews;
    public String[] developers;
    public String[] publishers;

    public String getDevelopers(Context context) {

        StringBuilder sb = new StringBuilder("");
        if(developers != null && developers.length > 0)
        {
            sb.append(context.getString(R.string.developed_by, developers[0]));
            sb.append("\n");
        }
        if(publishers != null && publishers.length > 0)
        {
            sb.append(context.getString(R.string.published_by, publishers[0]));
        }
        return sb.toString();
    }
}
