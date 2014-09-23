package com.aerilys.steamexplorer.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aerilys.steamexplorer.R;
import com.aerilys.steamexplorer.SteamActivity;
import com.aerilys.steamexplorer.adapters.GameAdapter;
import com.aerilys.steamexplorer.models.Category;
import com.aerilys.steamexplorer.models.GameApp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;

@EActivity(R.layout.activity_category)
public class CategoryActivity extends SteamActivity {

    public static final String INTENT_CATEGORY_INDEX = "INTENT_CATEGORY_INDEX";

    @ViewById
    GridView mGridView;

    private Category mCategory;
    private List<GameApp> mListGameApps;

    @AfterViews
    void afterViews() {
        if (getIntent().getExtras() == null || !getIntent().hasExtra(INTENT_CATEGORY_INDEX)) {
            finish();
            return;
        }

        int categoryIndex = getIntent().getExtras().getInt(INTENT_CATEGORY_INDEX);
        mCategory = dataContainer.getlistCategories().get(categoryIndex);
        getActionBar().setTitle(mCategory.name);
        loadCategory();
    }

    @Background
    protected void loadCategory() {
        try {
            mListGameApps = dataContainer.loadCategoryApps(mCategory);
        } catch (IOException e) {
            e.printStackTrace();
            finish();
            return;
        }

        updateCategoryUi();
    }

    @UiThread
    protected void updateCategoryUi() {
        GameAdapter adapter = new GameAdapter(this, mListGameApps);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectGame(position, view);
            }
        });
    }

    /**
     * Callback used when a game has been selected.
     * @param position : game position in adapter
     */
    private void selectGame(int position, View view) {
        GameApp gameApp = mListGameApps.get(position);

        Intent intent = GameActivity_.intent(this).get();
        intent.putExtra(GameActivity.INTENT_SELECTED_GAME, gameApp);

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, view.findViewById(R.id.categoryImage), "gameappImage");

        startActivity(intent, options.toBundle());
    }
}
