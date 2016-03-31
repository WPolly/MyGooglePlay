package com.xiaoshan.googleplayv2.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.protocol.HotProtocol;
import com.xiaoshan.googleplayv2.utils.UIUtils;
import com.xiaoshan.googleplayv2.view.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class HotFragment extends BaseFragment implements View.OnClickListener {

    private List<String> mData;

    @Override
    public View initSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (String text : mData) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(text);
            int padding = UIUtils.dip2Px(3);
            tv.setPadding(padding, padding, padding, padding);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(18);
            tv.setGravity(Gravity.CENTER);

            GradientDrawable normalGradientDrawable = new GradientDrawable();
            normalGradientDrawable.setCornerRadius(UIUtils.dip2Px(7));
            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(180) + 20;
            int green = random.nextInt(180) + 20;
            int blue = random.nextInt(180) + 20;
            int argb = Color.argb(alpha, red, green, blue);
            normalGradientDrawable.setColor(argb);

            GradientDrawable pressedGradientDrawable = new GradientDrawable();
            pressedGradientDrawable.setCornerRadius(UIUtils.dip2Px(7));
            pressedGradientDrawable.setColor(Color.DKGRAY);

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedGradientDrawable);
            stateListDrawable.addState(new int[]{}, normalGradientDrawable);

            tv.setOnClickListener(this);

            //noinspection deprecation
            tv.setBackgroundDrawable(stateListDrawable);
            flowLayout.addView(tv);
        }

        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        HotProtocol protocol = new HotProtocol();
        try {
            mData = protocol.loadData(0);
            if (mData == null || mData.size() == 0) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
        return BaseLoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public void onClick(View v) {
        String s = ((TextView) v).getText().toString();
        Toast.makeText(UIUtils.getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
