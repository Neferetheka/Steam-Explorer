package com.aerilys.steamexplorer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerilys.steamexplorer.R;
import com.aerilys.steamexplorer.models.Category;

import java.util.List;
import java.util.Locale;

/**
 * Adapter for all game categories
 */
public class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<Category> mListCategories;

    public CategoryAdapter(Context context, List<Category> listCategories) {
        mContext = context;
        mListCategories = listCategories;
    }

    @Override
    public int getCount() {
        return mListCategories.size();
    }

    @Override
    public Category getItem(int i) {
        return mListCategories.get(i);
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

        Category category = getItem(position);

        holder.categoryTitle.setText(category.name);

        int resourceID =
                mContext.getResources().getIdentifier(category.name.toLowerCase(Locale.US).replace(" ", "_"), "drawable", mContext.getPackageName());
        holder.categoryImage.setImageResource(resourceID);

        return convertView;
    }

    private static class ViewHolder {
        ImageView categoryImage;
        TextView categoryTitle;
    }
}
