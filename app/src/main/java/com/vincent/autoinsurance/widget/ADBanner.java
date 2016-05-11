package com.vincent.autoinsurance.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.vincent.autoinsurance.Context.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2016/4/4 0004.
 */
public class ADBanner extends LinearLayout {

    public ADBanner(Context context) {
        super(context);
        init();
    }

    public ADBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        ViewPager viewPager=new ViewPager(getContext());
        List<Fragment> fragments=new ArrayList<>();


    }
}
