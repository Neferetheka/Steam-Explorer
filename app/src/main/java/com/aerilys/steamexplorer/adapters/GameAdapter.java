package com.aerilys.steamexplorer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerilys.steamexplorer.R;
import com.aerilys.steamexplorer.models.Category;
import com.aerilys.steamexplorer.models.GameApp;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Game adapter for the games list.
 */
public class GameAdapter extends BaseAdapter{
    private Context mContext;
    private List<GameApp> mListGameApps;

    public GameAdapter(Context context, List<GameApp> listGames) {
        mContext = context;
        mListGameApps = listGames;
    }

    @Override
    public int getCount() {
        return mListGameApps.size();
    }

    @Override
    public GameApp getItem(int i) {
        return mListGameApps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.gridview_category, null);

            holder.categoryImage = (ImageView) convertView.findViewById(R.id.categoryImage);
            holder.categoryTitle = (TextView) convertView.findViewById(R.id.categoryTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GameApp game = getItem(position);

        holder.categoryTitle.setText(game.name);

        Picasso.with(mContext).load(game.header).into(holder.categoryImage);

        return convertView;
    }

    private static class ViewHolder {
        ImageView categoryImage;
        TextView categoryTitle;
    }
}
