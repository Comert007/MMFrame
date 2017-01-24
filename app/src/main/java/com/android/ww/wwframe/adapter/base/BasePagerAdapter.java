package com.android.ww.wwframe.adapter.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 10142 on 2016/10/19.
 */
public class BasePagerAdapter extends PagerAdapter {

    protected List<View> mData;

    public BasePagerAdapter(List<View> mData) {
        this.mData = mData;
    }

    public void addList(List<View> views){
        mData.addAll(views);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mData.get(position));
        return mData.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
