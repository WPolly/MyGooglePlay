package com.xiaoshan.googleplayv2.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/2/28.
 * 20:30
 */
public class AppDetailSafeHolder extends BaseViewHolder<DetailBean> implements View.OnClickListener {
    @InjectView(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;
    @InjectView(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;
    @InjectView(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;
    private int mMeasuredHeight;
    private boolean isOpen;
    private ViewGroup.LayoutParams mSafeDesContainerLayoutParams;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_safe, null);
        ButterKnife.inject(this, rootView);
        rootView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void setViewData(DetailBean data) {
        List<DetailBean.SafeBean> safeBeen = data.safe;
        for (DetailBean.SafeBean safeBean : safeBeen) {
            String safeUrl = safeBean.safeUrl;
            ImageView ivSafeIcon = new ImageView(UIUtils.getContext());
            BitmapHelper.display(ivSafeIcon, safeUrl);
            mAppDetailSafePicContainer.addView(ivSafeIcon);
            //或许是当子视图加载到父控件后,才能得到自身的LayoutParams
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivSafeIcon.getLayoutParams();
            params.rightMargin = UIUtils.dip2Px(7);
            ivSafeIcon.setLayoutParams(params);

            LinearLayout llSingleDesContainer = new LinearLayout(UIUtils.getContext());
            llSingleDesContainer.setGravity(Gravity.CENTER_VERTICAL);
            ImageView ivDecIcon = new ImageView(UIUtils.getContext());
            TextView tvDesContent = new TextView(UIUtils.getContext());
            int padding = UIUtils.dip2Px(2);
            tvDesContent.setPadding(padding, padding, padding, padding);
            if (safeBean.safeDesColor == 0) {
                tvDesContent.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            } else {
                tvDesContent.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }
            tvDesContent.setTextSize(12);
            BitmapHelper.display(ivDecIcon, safeBean.safeDesUrl);
            tvDesContent.setText(safeBean.safeDes);
            llSingleDesContainer.addView(ivDecIcon);
            llSingleDesContainer.addView(tvDesContent);
            mAppDetailSafeDesContainer.addView(llSingleDesContainer);
        }

        mAppDetailSafeDesContainer.measure(0,0);
        mMeasuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();

        mSafeDesContainerLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
        mSafeDesContainerLayoutParams.height = 0;
        mAppDetailSafeDesContainer.setLayoutParams(mSafeDesContainerLayoutParams);
    }

    @Override
    public void onClick(View v) {
        int startHeight;
        int endHeight;
        if (isOpen) {
            startHeight = mMeasuredHeight;
            endHeight = 0;
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow,"rotation",180,0).setDuration(200).start();
        } else {
            startHeight = 0;
            endHeight = mMeasuredHeight;
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow,"rotation",0,180).setDuration(200).start();
        }
        doAnimator(startHeight, endHeight);
        isOpen = !isOpen;
    }

    private void doAnimator(int startHeight, int endHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSafeDesContainerLayoutParams.height = (int) animation.getAnimatedValue();
                mAppDetailSafeDesContainer.setLayoutParams(mSafeDesContainerLayoutParams);
            }
        });
        animator.start();
    }
}
