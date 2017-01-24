package com.android.ww.wwframe.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * extends RecyclerView.Adapter
 */
public abstract class RvAdapter<T> extends RecyclerView.Adapter<RvViewHolder<T>> {
    private List<T> datas;
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;

    public RvAdapter(Context context) {
        datas = new ArrayList<>();
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }


    public void appendList(List<T> list) {
        int size = datas.size();
        this.datas.addAll(list);
        int end = datas.size();
        notifyDataSetChanged();
    }

    public void appendListTop(List<T> list) {
        this.datas.addAll(0, list);
        notifyDataSetChanged();
    }

    public void update(T obj) {
        int index = this.datas.indexOf(obj);
        notifyDataSetChanged();
    }

    public void setItem(int position, T obj) {
        this.datas.set(position, obj);
    }

    public void addList(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        load = true;
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(T bean) {
        this.datas.add(bean);
        notifyDataSetChanged();
    }

    private boolean load = false;

    public boolean isLoad() {
        return load;
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    public void addFirstItem(T bean) {
        this.datas.add(0, bean);
        notifyDataSetChanged();
//        notifyItemInserted(0);
    }

    public void removeItem(T bean) {
        int index = this.datas.indexOf(bean);
        if (index > -1) {
            this.datas.remove(index);
            notifyDataSetChanged();
//            notifyItemRemoved(index);
        }
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getList() {
        return datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected abstract int getItemLayoutResId(int viewType);

    protected abstract RvViewHolder<T> getViewHolder(int viewType, View view);

    @Override
    public  RvViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getItemLayoutResId(viewType), parent, false);
        RvViewHolder holder = getViewHolder(viewType, view);
        if (itemClickListener != null)
            holder.setItemClickListener(itemClickListener);
        if (itemLongClickListener != null)
            holder.setItemLongClickListener(itemLongClickListener);
        return holder;
    }

    @Override
    public final void onBindViewHolder(RvViewHolder<T> holder, int position) {
        holder.onAttachData(position, getItem(position));
    }
}
