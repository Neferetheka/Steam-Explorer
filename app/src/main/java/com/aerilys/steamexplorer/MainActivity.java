package com.aerilys.steamexplorer;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.aerilys.steamexplorer.activities.CategoriesActivity_;
import com.aerilys.steamexplorer.activities.GameActivity;
import com.aerilys.steamexplorer.activities.GameActivity_;
import com.aerilys.steamexplorer.adapters.GameAdapter;
import com.aerilys.steamexplorer.models.GameApp;
import com.aerilys.steamexplorer.views.QuickReturnGridView;
import com.aerilys.steamexplorer.views.RoundButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends SteamActivity implements AbsListView.OnScrollListener {

    @ViewById
    QuickReturnGridView mAppsGridview;

    @ViewById
    View mEmptyView;

    @ViewById
    RoundButton mAddButton;

    /**
     * Favorite games adapter.
     */
    private GameAdapter gameAdapter;

    /**
     * Used for scroll detection
     */
    private int mLastFirstVisibleItem;

    @AfterViews
    void afterViews() {
        mAppsGridview.setEmptyView(mEmptyView);

        gameAdapter = new GameAdapter(this, dataContainer.getUser().getListGameApps());
        mAppsGridview.setAdapter(gameAdapter);
        mAppsGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectGameApp(position, view);
            }
        });

        mAppsGridview.setOnScrollListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gameAdapter != null) {
            gameAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*if (item.getItemId() == R.id.action_add) {
            addGame();
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void selectGameApp(int position, View view) {
        GameApp gameApp = dataContainer.getUser().getListGameApps().get(position);

        Intent intent = GameActivity_.intent(this).get();
        intent.putExtra(GameActivity.INTENT_SELECTED_GAME, gameApp);

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, view.findViewById(R.id.categoryImage), "gameappImage");

        startActivity(intent, options.toBundle());
    }

    @Click(R.id.mAddButton)
    protected void addGame() {
        CategoriesActivity_.intent(this).start();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //Nothing to do here
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int i3) {
        if (mLastFirstVisibleItem < firstVisibleItem && !mAddButton.isHidden()) {
            mAddButton.hide();
        }
        if (mLastFirstVisibleItem > firstVisibleItem && mAddButton.isHidden()) {
            mAddButton.show();
        }
        mLastFirstVisibleItem = firstVisibleItem;
    }
}
