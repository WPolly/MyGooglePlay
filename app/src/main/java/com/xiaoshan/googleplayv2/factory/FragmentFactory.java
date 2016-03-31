package com.xiaoshan.googleplayv2.factory;

import android.support.v4.util.SparseArrayCompat;

import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.fragment.AppFragment;
import com.xiaoshan.googleplayv2.fragment.CategoryFragment;
import com.xiaoshan.googleplayv2.fragment.GameFragment;
import com.xiaoshan.googleplayv2.fragment.HomeFragment;
import com.xiaoshan.googleplayv2.fragment.HotFragment;
import com.xiaoshan.googleplayv2.fragment.RecommendFragment;
import com.xiaoshan.googleplayv2.fragment.SubjectFragment;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:40
 * <p/>
 * <string-array name="pager_title_main">
 * <item>首页</item>
 * <item>应用</item>
 * <item>游戏</item>
 * <item>专题</item>
 * <item>推荐</item>
 * <item>分类</item>
 * <item>排行</item>
 * </string-array>
 */
public class FragmentFactory {

    /**
     * 替换原则
       1. 如果用到了： HashMap<Integer, E> hashMap = new HashMap<Integer, E>();可以替换为： SparseArray<E> sparseArray = new SparseArray<E>();
       2. 如果用到了：HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean> 可以替换为：SparseBooleanArray array = new SparseBooleanArray();
       3. 如果用到了：HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer> 可以替换为：SparseIntArray array = new SparseIntArray();
     */
    private static SparseArrayCompat<BaseFragment> cacheBaseFragments = new SparseArrayCompat<>();


    public static BaseFragment getFragmentInstance(int position) {

        BaseFragment fragment = cacheBaseFragments.get(position);

        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;

            case 1:
                fragment = new AppFragment();
                break;

            case 2:
                fragment = new GameFragment();
                break;

            case 3:
                fragment = new SubjectFragment();
                break;

            case 4:
                fragment = new RecommendFragment();
                break;

            case 5:
                fragment = new CategoryFragment();
                break;

            case 6:
                fragment = new HotFragment();
                break;
        }

        cacheBaseFragments.put(position, fragment);
        return fragment;
    }
}
