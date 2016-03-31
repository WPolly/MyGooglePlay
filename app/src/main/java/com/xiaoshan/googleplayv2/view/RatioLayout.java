package com.xiaoshan.googleplayv2.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xiaoshan.googleplayv2.R;

/**
 * Created by xiaoshan on 2016/2/25.
 * 17:11
 */
public class RatioLayout extends FrameLayout {
    public static final int RELATIVE_WIDTH = 0;
    public static final int RELATIVE_HEIGHT = 1;

    //宽高比
    private float mRatio;
    private int mRelativeTo;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, 0);
        mRelativeTo = typedArray.getInt(R.styleable.RatioLayout_relativeTo, RELATIVE_WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelativeTo == RELATIVE_WIDTH) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int childWidth = width - getPaddingLeft() - getPaddingRight();
            int childHeight = (int) (childWidth / mRatio + 0.5f);
            int height = childHeight + getPaddingTop() + getPaddingBottom();
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);//没有效果啊???
            setMeasuredDimension(width, height);
        } else if (heightMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelativeTo == RELATIVE_HEIGHT) {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int childHeight = height - getPaddingTop() - getPaddingBottom();
            int childWidth = (int) (childHeight * mRatio + 0.5f);
            int width = childWidth + getPaddingLeft() + getPaddingRight();
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public float getRatio() {
        return mRatio;
    }

    public void setRatio(float ratio) {
        mRatio = ratio;
    }

    public int getRelativeTo() {
        return mRelativeTo;
    }

    public void setRelativeTo(int relativeTo) {
        mRelativeTo = relativeTo;
    }
}
