package com.aerilys.steamexplorer.activities;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aerilys.steamexplorer.R;
import com.aerilys.steamexplorer.SteamActivity;
import com.aerilys.steamexplorer.models.GameApp;
import com.aerilys.steamexplorer.views.ListenableScrollView;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_game)
@OptionsMenu(R.menu.game)
public class GameActivity extends SteamActivity implements ListenableScrollView.OnScrollChangedListener {

    public static final String INTENT_SELECTED_GAME = "INTENT_SELECTED_GAME";

    @ViewById
    ListenableScrollView mListenableScrollView;

    @ViewById
    ImageView mGameHeader;

    @ViewById
    TextView mGameDescription, mGameDevelopers, mGameReviews, mGameReviewsTitle;

    @ViewById
    View mCardReviews;

    @ViewById
    ViewGroup mContentLayout;

    @ViewById
    ProgressBar mProgressBar;

    private GameApp mGameApp;

    /**
     * Actionbar background drawable.
     */
    protected Drawable mActionBarBackgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState, persistentState);
    }

    @AfterViews
    void afterViews() {
        if (getIntent().getExtras() == null || !getIntent().hasExtra(INTENT_SELECTED_GAME)) {
            finish();
            return;
        }

        mContentLayout.setVisibility(View.GONE);
        mGameApp = (GameApp) getIntent().getExtras().getSerializable(INTENT_SELECTED_GAME);
        Picasso.with(this).load(mGameApp.header).into(mGameHeader);

        getActionBar().setTitle(mGameApp.name);
        mListenableScrollView.setOnScrollChangedListener(this);
        initTransparentActionbar();

        //displayUsingRevealEffect(mGameHeader);

        mProgressBar.setVisibility(View.VISIBLE);
        loadGameDetails();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (dataContainer.getUser().containsGame(mGameApp.appid)) {
            menu.removeItem(R.id.action_add_favorite);
        } else {
            menu.removeItem(R.id.action_remove_favorite);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add_favorite) {
            dataContainer.getUser().addGame(mGameApp);
            dataContainer.saveUser();
        } else if (item.getItemId() == R.id.action_remove_favorite) {
            dataContainer.getUser().removeGame(mGameApp);
            dataContainer.saveUser();
        }

        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    @Background
    protected void loadGameDetails() {
        GameApp app = dataContainer.getNetworkHelper().getGameDetails(mGameApp);
        updateGameUi(app);
    }

    @UiThread
    protected void updateGameUi(GameApp app) {
        mProgressBar.setVisibility(View.GONE);

        mGameDescription.setText(Html.fromHtml(app.detailedDescription));

        if (app.reviews != null) {
            mGameReviews.setText(Html.fromHtml(app.reviews));
        } else {
            mCardReviews.setVisibility(View.GONE);
        }
        mGameDevelopers.setText(app.getDevelopers(this));
        mContentLayout.setVisibility(View.VISIBLE);

        invalidateOptionsMenu();
    }

    private void displayUsingRevealEffect(View viewToAnimate)
    {
        int cx = (viewToAnimate.getLeft() + viewToAnimate.getRight()) / 2;
        int cy = (viewToAnimate.getTop() + viewToAnimate.getBottom()) / 2;

        int finalRadius = viewToAnimate.getWidth();

        ValueAnimator anim =
                ViewAnimationUtils.createCircularReveal(viewToAnimate, cx, cy, 0, finalRadius);
        anim.start();
    }

    /**
     * Create a transparent actionbar which stays transparent.
     */
    protected void initTransparentActionbar()
    {
        mActionBarBackgroundDrawable = new ColorDrawable(getResources().getColor(R.color.accent_color));
        mActionBarBackgroundDrawable.setAlpha(100);
        getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

        Drawable.Callback drawableCallback = new Drawable.Callback()
        {
            @Override
            public void invalidateDrawable(Drawable who)
            {
                getActionBar().setBackgroundDrawable(who);
            }

            @Override
            public void scheduleDrawable(Drawable who, Runnable what, long when)
            {
            }

            @Override
            public void unscheduleDrawable(Drawable who, Runnable what)
            {
            }
        };

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            mActionBarBackgroundDrawable.setCallback(drawableCallback);
        }
    }

    @Override
    public void onScrollChanged(ScrollView source, int l, int t, int oldl, int oldt) {
        int opacity = (int) (t / 1.5);
        opacity += 100;
        if (opacity > 255)
        {
            opacity = 255;
        }
        else if (opacity < 0)
        {
            opacity = 0;
        }

        mActionBarBackgroundDrawable.setAlpha(opacity);
    }
}
