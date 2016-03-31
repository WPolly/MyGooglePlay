package com.xiaoshan.googleplayv2.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.protocol.RecommendProtocol;
import com.xiaoshan.googleplayv2.utils.UIUtils;
import com.xiaoshan.googleplayv2.view.randomfly.ShakeListener;
import com.xiaoshan.googleplayv2.view.randomfly.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class RecommendFragment extends BaseFragment implements ShakeListener.OnShakeListener {

    private List<String> mData;
    private static final int PAGER_SIZE = 15;
    private StellarMap mStellarMap;
    private RecommendAdapter mAdapter;
    private ShakeListener mShakeListener;

    @Override
    public View initSuccessView() {
        mStellarMap = new StellarMap(UIUtils.getContext());
        mAdapter = new RecommendAdapter();
        mStellarMap.setAdapter(mAdapter);
        mStellarMap.setGroup(0, true);
        mStellarMap.setRegularity(15, 20);
        mStellarMap.setBackgroundResource(R.drawable.recommend_bg);

        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(this);
        return mStellarMap;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        RecommendProtocol protocol = new RecommendProtocol();
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
    public void onShake() {
        Vibrator vibrator = (Vibrator) UIUtils.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300);
        int currentGroup = mStellarMap.getCurrentGroup();
        if (currentGroup == mAdapter.getGroupCount() - 1) {
            currentGroup = 0;
        } else {
            currentGroup++;
        }

        mStellarMap.setGroup(currentGroup, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShakeListener != null) {
            mShakeListener.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.pause();
        }
    }

    class RecommendAdapter implements StellarMap.Adapter, View.OnClickListener {

        @Override
        public int getGroupCount() {
            int count = mData.size() / PAGER_SIZE;
            if (mData.size() % PAGER_SIZE > 0) {
                count++;
            }
            return count;
        }

        @Override
        public int getCount(int group) {
            if (group == getGroupCount() - 1) {
                if (mData.size() % PAGER_SIZE > 0) {
                    return mData.size() % PAGER_SIZE;
                }
            }
            return PAGER_SIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {

            int index = group * PAGER_SIZE + position;
            final String text = mData.get(index);
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(text);

            Random random = new Random();
            int textSize = random.nextInt(6) + 12;
            tv.setTextSize(textSize);

            int alpha = 255;
            int red = random.nextInt(180) + 30;
            int green = random.nextInt(180) + 30;
            int blue = random.nextInt(180) + 30;
            int argb = Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);

            int padding = UIUtils.dip2Px(5);
            tv.setPadding(padding, padding, padding, padding);

            tv.setOnClickListener(this);

            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }

        @Override
        public void onClick(View v) {
            String s = ((TextView) v).getText().toString();
            Toast.makeText(UIUtils.getContext(), s, Toast.LENGTH_SHORT).show();
        }

    }
}
