package com.xiaoshan.googleplayv2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.xiaoshan.googleplayv2.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/3/3.
 * 19:58
 */
public class ProgressButton extends Button {

    private boolean isProgressButtonEnable;
    private int mProgress;
    private int mMaxProgress = 100;
    private GradientDrawable mDrawable;

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{Color.RED, Color.YELLOW, Color.GREEN});
        mDrawable.setCornerRadius(UIUtils.dip2Px(5));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isProgressButtonEnable) {
            int left = 0;
            int top = UIUtils.dip2Px(3);
            int measuredWidth = getMeasuredWidth();
            int right = (int) (((mProgress * 1.0f / mMaxProgress) * measuredWidth) + 0.5);
            int bottom = getMeasuredHeight();
            mDrawable.setBounds(left, top, right, bottom - UIUtils.dip2Px(3));
            mDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    public void setProgressButtonEnable(boolean progressButtonEnable) {
        isProgressButtonEnable = progressButtonEnable;
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }
}
