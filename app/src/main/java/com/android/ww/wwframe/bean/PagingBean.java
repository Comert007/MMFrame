package com.android.ww.wwframe.bean;

public class PagingBean {

    private int total_page;
    private int current_page;
    private int perpage;
    private int total_items;


    public boolean isTop() {
        return current_page <= 1;
    }

    public boolean isBottom() {
        return current_page >= total_page;
    }

    public int nextPage() {
        return current_page + 1;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
}
