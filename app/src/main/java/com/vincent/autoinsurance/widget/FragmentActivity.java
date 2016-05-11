package com.vincent.autoinsurance.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vincent.autoinsurance.Adapter.ListActivityAdapter;
import com.vincent.autoinsurance.Bean.AutoActivity;
import com.vincent.autoinsurance.Context.Config;
import com.vincent.autoinsurance.Context.MyApplication;
import com.vincent.autoinsurance.NetWork.GetActivityInfo;
import com.vincent.autoinsurance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2016/4/3 0003.
 */
public class FragmentActivity extends Fragment {

    private ListView listView;
    private List<AutoActivity> autoActivities=new ArrayList<>();
    private ListActivityAdapter listActivityAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fra_activity,null);

        listView= (ListView) view.findViewById(R.id.lvActivity);

        autoActivities=new ArrayList<>();

        listActivityAdapter=new ListActivityAdapter(autoActivities,getContext());

        listView.setAdapter(listActivityAdapter);

        return view;
    }

    public void init(){

        Log.i("debug","init()");
        autoActivities.clear();

        new GetActivityInfo(MyApplication.getRequestQueue(),Config.getCurrentCity(),new GetActivityInfo.SuccessCallback() {
            @Override
            public void onSuccess(List<AutoActivity> lists) {
                autoActivities=lists;
                Log.i("debug",autoActivities.get(1).getActivityImageUrl());
                listActivityAdapter.notifyDataSetChanged();
            }
        },new GetActivityInfo.FailCallback(){
            @Override
            public void onFail(String errorCode) {
                Log.i(Config.RESULT_INFO,errorCode);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        init();
    }
}
