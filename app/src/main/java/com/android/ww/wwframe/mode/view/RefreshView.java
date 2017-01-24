package com.android.ww.wwframe.mode.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.android.ww.wwframe.R;
import com.android.ww.wwframe.adapter.base.RvAdapter;
import com.android.ww.wwframe.bean.PagingBean;

import butterknife.BindView;
import butterknife.Optional;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/1/24.
 * 刷新View
 */

public class RefreshView extends BaseView {
    @Nullable
    @BindView(R.id.csr_layout)
    public CustomSwipeRefreshLayout csrLayout;
    @Nullable
    @BindView(R.id.crv)
    public CustomRecyclerView crv;

    protected RvAdapter adapter;
    private PagingBean paging;
    private CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener refreshLayoutListener;

    @Override
    public void attachView(@NonNull View view) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        if (crv==null||csrLayout==null) {
            Debug.e("the crv or csrLayout could is null!");
            return;
        }
        crv.setLayoutManager(llm);
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null,
                false);
        emptyView.setVisibility(View.VISIBLE);

        ScreenUtil.scale(emptyView);
        crv.addEmpty(emptyView);

        if (csrLayout != null) {
            csrLayout.setRefreshView(crv);

            attachListener(csrLayout);
        }
    }


    private void attachListener(@NonNull CustomSwipeRefreshLayout csrLayout) {
        csrLayout.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout
                .OnSwipeRefreshLayoutListener() {

            @Override
            public void onHeaderRefreshing() {
                if (refreshLayoutListener != null) {
                    refreshLayoutListener.onHeaderRefreshing();
                }
            }

            @Override
            public void onFooterRefreshing() {
                if (refreshLayoutListener != null) {
                    refreshLayoutListener.onFooterRefreshing();
                }
            }
        });
    }

    public PagingBean getPaging() {
        return paging;
    }

    public void setPaging(@NonNull PagingBean paging) {
        if (csrLayout!=null){
            this.paging = paging;
            if (paging.isBottom()) {
                csrLayout.setFooterRefreshAble(false);
            } else {
                csrLayout.setFooterRefreshAble(true);
            }
        }
    }

    @Optional
    public void refreshFinish() {
        if (csrLayout != null)
            csrLayout.setRefreshFinished();
    }

    @Optional
    public void refresh() {
        if (csrLayout != null)
            csrLayout.setRefreshing(true);
    }

    @Optional
    public void refreshDelay() {

        if (csrLayout != null) {
            csrLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            }, 200);
        }
    }

    @Optional
    public void setAdapter(RvAdapter adapter) {
        if (crv != null)
            crv.setAdapter(adapter);
        this.adapter = adapter;
    }

    @Optional
    public void setOnSwipeRefreshListener(CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener
                                                  refreshListener) {
        this.refreshLayoutListener = refreshListener;
    }
}
