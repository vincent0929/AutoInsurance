package com.vincent.autoinsurance.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vincent.autoinsurance.Context.Config;
import com.vincent.autoinsurance.NetWork.CheckTokenIsExpired;
import com.vincent.autoinsurance.R;
import com.vincent.autoinsurance.widget.FragmentActivity;
import com.vincent.autoinsurance.widget.FragmentAdapter;
import com.vincent.autoinsurance.widget.FragmentService;
import com.vincent.autoinsurance.widget.FragmentVip;
import com.vincent.autoinsurance.widget.SegmentView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private SegmentView mSegmentView;
    private ViewPager mViewPager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkTokenIsExpired();
    }

    public void checkTokenIsExpired() {
        if (Config.getCachedPhoneNumberMD5(this) != null) {
            new CheckTokenIsExpired(Config.getCachedPhoneNumberMD5(this), new CheckTokenIsExpired.SuccessCallback() {
                @Override
                public void onSuccess(String token) {
                    Config.cachePhoneNumberMd5(MainActivity.this, Config.getCachedPhoneNumberMD5(MainActivity.this));
                    Config.cachedToken(MainActivity.this, token);
                }
            }, new CheckTokenIsExpired.FailCallback() {
                @Override
                public void onFail(String errorCode) {
                    Intent intent = new Intent(MainActivity.this, AtyLogin.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(this, AtyLogin.class);
            startActivity(intent);
        }
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(new FragmentActivity());
        fragments.add(new FragmentService());
        fragments.add(new FragmentVip());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mSegmentView.setCurrentTab(0);
                        break;
                    case 1:
                        mSegmentView.setCurrentTab(1);
                        break;
                    case 2:
                        mSegmentView.setCurrentTab(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSegmentView = (SegmentView) findViewById(R.id.segmentview);
        mSegmentView.setOnSegmentViewClickListener(new SegmentView.OnSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View v, int position) {
                switch (position) {
                    case 0:
                        mViewPager.setCurrentItem(0);
                        break;
                    case 1:
                        mViewPager.setCurrentItem(1);
                        break;
                    case 2:
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_choose_city:
                intent = new Intent(this, AtyChooseCity.class);
                startActivityForResult(intent, Config.REQUST_CODE_CHOOSE_CITY);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Config.REQUST_CODE_CHOOSE_CITY) {
            if (resultCode == Config.RESULT_CODE_SUCCESS) {
                String city = data.getStringExtra(Config.SELECTED_CITY);
                Config.setCurrentCity(city);
                Log.i(Config.SELECTED_CITY, city);

                toolbar.getMenu().getItem(0).setTitle(city);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llChooseCity:
                Intent intent = new Intent(this, AtyChooseCity.class);
                startActivityForResult(intent, Config.REQUST_CODE_CHOOSE_CITY);
                break;
        }
    }

}
