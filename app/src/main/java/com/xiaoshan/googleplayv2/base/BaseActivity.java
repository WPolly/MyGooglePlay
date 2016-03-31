package com.xiaoshan.googleplayv2.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xiaoshan on 2016/3/1.
 * 17:07
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initData();
    }

    protected void init() {

    }

    protected abstract void initView();

    protected void initData() {

    }
}
