package com.vincent.autoinsurance.widget;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	
	private int mChildCount=0;
	
	public FragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

//	@Override
//	public int getItemPosition(Object object) {
//		Fragment fragment= (Fragment) object;
//		if(fragment.equals(getItem(0))){
//			return 0;
//		}else if(fragment.equals(getItem(1))){
//			return 1;
//		}else if(fragment.equals(getItem(2))){
//			return 2;
//		}
//		return super.getItemPosition(object);
//	}

	@Override
	public void notifyDataSetChanged() {
		mChildCount=getCount();
		super.notifyDataSetChanged();
	}

}
