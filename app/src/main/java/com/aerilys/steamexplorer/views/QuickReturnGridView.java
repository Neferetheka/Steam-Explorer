package com.aerilys.steamexplorer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Very basic Quick return GridView
 */
public class QuickReturnGridView extends GridView {

    private OnScrollListener mScrollListener;

    public QuickReturnGridView(Context context) {
        super(context);
    }

    public QuickReturnGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuickReturnGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setScrollListener(OnScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }

    public interface OnScrollListener {
        public abstract void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
