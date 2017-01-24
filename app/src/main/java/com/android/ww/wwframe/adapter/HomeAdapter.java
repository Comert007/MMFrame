package com.android.ww.wwframe.adapter;

import android.content.Context;
import android.view.View;

import com.android.ww.wwframe.adapter.base.RvAdapter;
import com.android.ww.wwframe.adapter.base.RvViewHolder;

/**
 * Created by feng on 2017/1/24.
 */

public class HomeAdapter extends RvAdapter<String> {

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return 0;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return null;
    }
}
