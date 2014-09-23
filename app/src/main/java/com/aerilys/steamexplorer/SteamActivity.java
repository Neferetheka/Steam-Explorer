package com.aerilys.steamexplorer;

import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.aerilys.steamexplorer.tools.DataContainer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class SteamActivity extends FragmentActivity {

    @Bean
    protected DataContainer dataContainer;

    @AfterViews
    protected void addBackActionBar()
    {
        if (!(this instanceof MainActivity))
        {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!(this instanceof MainActivity) && item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
