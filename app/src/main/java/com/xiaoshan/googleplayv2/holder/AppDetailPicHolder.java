package com.xiaoshan.googleplayv2.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.UIUtils;
import com.xiaoshan.googleplayv2.view.RatioLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/3/1.
 * 13:26
 */
public class AppDetailPicHolder extends BaseViewHolder<DetailBean> {
    @InjectView(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void setViewData(DetailBean data) {
        List<String> screenUrls = data.screen;
        for (String screenUrl : screenUrls) {
            ImageView ivScreen = new ImageView(UIUtils.getContext());
            int padding = UIUtils.dip2Px(6);
            ivScreen.setPadding(0, 0, padding, 0);
            ivScreen.setBackgroundColor(Color.WHITE);
            BitmapHelper.display(ivScreen,screenUrl);
            int displayWidth = UIUtils.getResource().getDisplayMetrics().widthPixels;
            int picContainerWidth = displayWidth - mAppDetailPicIvContainer.getPaddingLeft() - mAppDetailPicIvContainer.getPaddingRight();
            int ivWidth = picContainerWidth / 3;
            RatioLayout ratioLayout = new RatioLayout(UIUtils.getContext());
            ratioLayout.setRatio(0.6f);
            ratioLayout.setRelativeTo(RatioLayout.RELATIVE_WIDTH);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ivWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            ratioLayout.setLayoutParams(params);
            ratioLayout.addView(ivScreen);
            mAppDetailPicIvContainer.addView(ratioLayout);
        }
    }
}
