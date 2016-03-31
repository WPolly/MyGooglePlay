package com.xiaoshan.googleplayv2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/3/5.
 * 20:24
 */
public class CircleProgressView extends LinearLayout {
    @InjectView(R.id.circle_progress_iv_icon)
    ImageView mCircleProgressIvIcon;
    @InjectView(R.id.circle_progress_tv_note)
    TextView mCircleProgressTvNote;

    private Paint mPaint;
    private int mProgress;
    private boolean mCircleProgressEnable;
    private RectF mRectF;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View rootComponent = View.inflate(context, R.layout.component_circle_progress_view, this);
        ButterKnife.inject(this, rootComponent);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(UIUtils.dip2Px(2));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mCircleProgressEnable) {
            if (mRectF == null) {
                mRectF = new RectF(mCircleProgressIvIcon.getLeft(), mCircleProgressIvIcon.getTop(),
                        mCircleProgressIvIcon.getRight(), mCircleProgressIvIcon.getBottom());
            }
            canvas.drawArc(mRectF, -90, mProgress*360f/100, false, mPaint);
        }
    }

    public void setCircleProgressIconRes(int res) {
        mCircleProgressIvIcon.setImageResource(res);
    }

    public void setCircleProgressNote (int stringRes) {
        String note = UIUtils.getString(stringRes);
        mCircleProgressTvNote.setText(note);
    }

    public void setCircleProgressNote(String progress) {
        mCircleProgressTvNote.setText(progress);
    }

    public void setProgress (int progress) {
        mProgress = progress;
        invalidate();
    }

    public void setCircleProgressEnable(boolean circleProgressEnable) {
        mCircleProgressEnable = circleProgressEnable;
    }

}
