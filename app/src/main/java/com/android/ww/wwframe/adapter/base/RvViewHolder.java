package com.android.ww.wwframe.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * Created by lenovo on 2016/1/26.
 */
public abstract class RvViewHolder<T> extends RecyclerView.ViewHolder implements View
        .OnClickListener, View.OnLongClickListener {
    protected int position;
    protected T bean;
    public ItemClickListener itemClickListener;
    public ItemLongClickListener itemLongClickListener;

    public RvViewHolder(View itemView) {
        super(itemView);
        ScreenUtil.scale(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        initView();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public void initView() {
        ButterKnife.bind(this, itemView);
    }

    public final void onAttachData(int position, T bean) {
        this.position = position;
        this.bean = bean;
        onBindData(position, bean);
    }

    public abstract void onBindData(int position, T bean);

    public Object getObj() {
        return null;
    }


    @Override
    public void onClick(View view) {
        if (itemClickListener != null)
            itemClickListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
        if (itemLongClickListener != null)
            itemLongClickListener.onItemLongClick(view,getAdapterPosition());
        return true;
    }
}
