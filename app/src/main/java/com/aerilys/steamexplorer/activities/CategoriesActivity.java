package com.aerilys.steamexplorer.activities;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aerilys.steamexplorer.R;
import com.aerilys.steamexplorer.SteamActivity;
import com.aerilys.steamexplorer.adapters.CategoryAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * A page which displays all possible categories
 */
@EActivity(R.layout.activity_categories)
public class CategoriesActivity extends SteamActivity {

    @ViewById
    GridView mGridView;

    @AfterViews
    void afterViews() {
        CategoryAdapter adapter = new CategoryAdapter(this, dataContainer.getlistCategories());

        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectCategory(position);
            }
        });
    }

    private void selectCategory(int position) {
        Intent intent = CategoryActivity_.intent(this).get();
        intent.putExtra(CategoryActivity.INTENT_CATEGORY_INDEX, position);
        startActivity(intent);
    }
}
