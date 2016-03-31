package com.xiaoshan.googleplayv2;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.xiaoshan.googleplayv2.factory.FragmentFactory;
import com.xiaoshan.googleplayv2.receiver.ApkInstallStateReceiver;
import com.xiaoshan.googleplayv2.utils.UIUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mMainToolbar;
    private DrawerLayout mMainDrawer;
    private TabLayout mMainTab;
    private ViewPager mMainViewPager;
    private String[] mPagerTitles;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    //private long mCurrentTime;
    private ApkInstallStateReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener(); //顺序原因很重要,先添加监听会让onTabSelected的监听方法在Activity挂载到界面上就能够执行．．
        initData();
    }

    private void initView() {
        initToolbar();
        initDrawer();
        initTabLayout();
        mMainViewPager = ((ViewPager) findViewById(R.id.main_vp));
        ViewTreeObserver.OnGlobalLayoutListener victimListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mOnPageChangeListener.onPageSelected(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mMainViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mMainViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        };
        mMainViewPager.getViewTreeObserver().addOnGlobalLayoutListener(victimListener);
    }

    private void initData() {
        mPagerTitles = getResources().getStringArray(R.array.pager_title_main);
        mMainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        initApkInstallReceiver();
    }

    private void initApkInstallReceiver() {
        mMainTab.setupWithViewPager(mMainViewPager);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        mReceiver = new ApkInstallStateReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    private void initListener() {

        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               FragmentFactory.getFragmentInstance(position).getLoadingPager().loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        mMainViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }


    class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.getFragmentInstance(position);
        }

        @Override
        public int getCount() {
            return mPagerTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPagerTitles[position];
        }

    }

    private void initToolbar() {
        mMainToolbar = ((Toolbar) findViewById(R.id.main_toolbar));
        setSupportActionBar(mMainToolbar);
    }

    private void initDrawer() {
        mMainDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mMainDrawer, mMainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mMainDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_main);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initTabLayout() {
        mMainTab = ((TabLayout) findViewById(R.id.main_tab));
        mMainTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        int color = UIUtils.getColor(R.color.tab_bg);
        mMainTab.setBackgroundColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_home:
                break;
            case R.id.navigation_setting:
                break;
            case R.id.navigation_theme:
                break;
            case R.id.navigation_apk_manager:
                break;
            case R.id.navigation_feedback:
                break;
            case R.id.navigation_check_update:
                break;
            case R.id.navigation_about:
                break;
            case R.id.navigation_exit:
                break;
        }
        mMainDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (mMainDrawer.isDrawerOpen(GravityCompat.START)) {
            mMainDrawer.closeDrawer(GravityCompat.START);
            return;
        }

        Snackbar snackbar = Snackbar.make(mMainToolbar, R.string.ensure_exit_app, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorPrimaryDark);
        snackbar.setAction(R.string.yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setActionTextColor(UIUtils.getColor(R.color.colorAccent)).show();
//        if (System.currentTimeMillis() - mCurrentTime > 2000) {
//            Toast.makeText(this, "再按一次退出谷歌应用市场", Toast.LENGTH_SHORT).show();
//            mCurrentTime = System.currentTimeMillis();
//            return;
//        }
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mReceiver = null;
    }
}
