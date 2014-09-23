package com.aerilys.steamexplorer.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.aerilys.steamexplorer.R;

public class RoundButton extends FrameLayout
{
	/**
	 * Duration in ms for transitions and show/hide animations.
	 */
    private static final int ANIMATION_DURATION = 200;
    
	private Paint mButtonPaint;
	
	private boolean mIsHidden = false;

    public RoundButton(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        init(context.getResources().getColor(R.color.accent_color));
    }
    
	public RoundButton(Context context)
    {
        super(context);
        init(context.getResources().getColor(R.color.accent_color));
    }
    
    public void setFabColor(int fabColor)
    {
    	init(fabColor);
    }

	public void init(int fabColor)
    {
    	setWillNotDraw(false);
    	this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mButtonPaint.setColor(fabColor);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setShadowLayer(10.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
    	setClickable(true);
    	if(mButtonPaint != null) {
	        canvas.drawCircle(getWidth()/2, getHeight()/2,(float) (getWidth()/2.6), mButtonPaint);
    	}
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
		{
			animate().setDuration(ANIMATION_DURATION).alpha(1f).scaleX(1).scaleY(1);
	    }
		else if(event.getAction() == MotionEvent.ACTION_DOWN)
	    {
			animate().setDuration(ANIMATION_DURATION).alpha(0.72f).scaleX(1.2f).scaleY(1.2f);
	    }
		return super.onTouchEvent(event);
	}
	
	public void hide()
	{
		mIsHidden = true;
		animate().setDuration(ANIMATION_DURATION).translationY(300f);
	}
	
	public void show()
	{
		mIsHidden = false;
		animate().setDuration(ANIMATION_DURATION).translationY(0f);
	}

	public boolean isHidden()
	{
		return mIsHidden;
	}
	
	
}
