package com.xiaoshan.googleplayv2.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.HomeInfoBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by xiaoshan on 2016/2/24.
 * 13:08
 */
public class CarouselPictureViewHolder extends BaseViewHolder<HomeInfoBean> {

    private static final int PAGER_COUNT = 10000;
    private ViewPager mHomeViewPager;
    private LinearLayout mPictureIndicatorContainer;
    private ArrayList<String> mPictureUrls;

    @Override
    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_carousel_picture, null);
        mHomeViewPager = (ViewPager) rootView.findViewById(R.id.item_home_picture_pager);
        mPictureIndicatorContainer = (LinearLayout) rootView.findViewById(R.id.item_home_picture_container_indicator);
        return rootView;
    }

    @Override
    public void setViewData(HomeInfoBean data) {
        mPictureUrls = data.picture;
        mHomeViewPager.setAdapter(new HomePictureAdapter());

        //初始化指示点状态
        for (int i = 0; i < mPictureUrls.size(); i++) {
            ImageView pointIndicator = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = UIUtils.dip2Px(5);
            pointIndicator.setLayoutParams(params);
            pointIndicator.setImageResource(R.drawable.point_indicator);
            mPictureIndicatorContainer.addView(pointIndicator);
            pointIndicator.setEnabled(false);
            if (i == 0) {
                pointIndicator.setEnabled(true);
            }
        }

        //确定ViewPager初始位置
        int position = PAGER_COUNT / 2;
        while ((position % mPictureUrls.size()) != 0) {
            position ++;
        }
        mHomeViewPager.setCurrentItem(position);

        mHomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position % mPictureUrls.size();
                for (int i = 0; i < mPictureIndicatorContainer.getChildCount(); i++) {
                    View childView = mPictureIndicatorContainer.getChildAt(i);
                    childView.setEnabled(false);
                    if (i == index) {
                        childView.setEnabled(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final AutoScrollTask autoScrollTask = new AutoScrollTask();
        autoScrollTask.startScroll();

        mHomeViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stopScroll();
                        break;

                    case MotionEvent.ACTION_UP:
                        autoScrollTask.startScroll();
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        autoScrollTask.startScroll();
                        break;
                }

                return false;
            }
        });
    }

    class HomePictureAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return PAGER_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int index = position % mPictureUrls.size();
            ImageView imageView = new ImageView(UIUtils.getContext());
            BitmapHelper.display(imageView, mPictureUrls.get(index));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }
    }

    class AutoScrollTask implements Runnable {

        public void startScroll() {
            UIUtils.postTaskDelay(this,3000);
        }

        public void stopScroll() {
            UIUtils.removeTask(this);
        }

        @Override
        public void run() {
            int currentItem = mHomeViewPager.getCurrentItem();
            currentItem++;
            mHomeViewPager.setCurrentItem(currentItem);
            startScroll();
        }
    }
}
