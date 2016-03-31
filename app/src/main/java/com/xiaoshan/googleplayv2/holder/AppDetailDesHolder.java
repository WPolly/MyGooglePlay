package com.xiaoshan.googleplayv2.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/3/1.
 * 15:37
 */
public class AppDetailDesHolder extends BaseViewHolder<DetailBean> implements View.OnClickListener {
    @InjectView(R.id.app_detail_des_tv_des)
    TextView mAppDetailDesTvDes;
    @InjectView(R.id.app_detail_des_tv_author)
    TextView mAppDetailDesTvAuthor;
    @InjectView(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;
    private int mOriginalDesTvHeight;
    private boolean isDesTvOpen;
    private int mCollapseDesTvHeight;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des, null);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void setViewData(DetailBean data) {
        mAppDetailDesTvAuthor.setText(data.author);
        mAppDetailDesTvDes.setText(data.des);
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mOriginalDesTvHeight = mAppDetailDesTvDes.getMeasuredHeight();
                mAppDetailDesTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mAppDetailDesTvDes.setLines(7);
                mAppDetailDesTvDes.measure(0, 0);
                mCollapseDesTvHeight = mAppDetailDesTvDes.getMeasuredHeight();
            }
        });
        mAppDetailDesTvDes.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (isDesTvOpen) {
            doAnimator(mOriginalDesTvHeight, mCollapseDesTvHeight);
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180).setDuration(200).start();
        } else {
            doAnimator(mCollapseDesTvHeight, mOriginalDesTvHeight);
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0).setDuration(200).start();
        }
        isDesTvOpen = !isDesTvOpen;
    }

    private void doAnimator(int startHeight, int endHeight) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", startHeight, endHeight);
        animator.setDuration(200).start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewParent parent = mAppDetailDesTvDes.getParent();
                while (!(parent instanceof ScrollView)) {
                    parent = parent.getParent();
                    if (parent == null)
                        break;
                }
                if (parent != null) {
                    ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
