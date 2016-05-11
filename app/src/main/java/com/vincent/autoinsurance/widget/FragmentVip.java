package com.vincent.autoinsurance.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vincent.autoinsurance.R;

/**
 * Created by Vincent on 2016/4/3 0003.
 */
public class FragmentVip extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fra_vip,null);
        return view;
    }
}
