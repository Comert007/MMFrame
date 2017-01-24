package com.android.ww.wwframe.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.ww.wwframe.config.Constant;

/**
 * Created by feng on 2017/1/24.
 * 服务器返回数据结构
 */

public class ResponseBean {
    private String status;
    private int code;
    private String msg;
    private String data;
    private String need_relogin;

    public static ResponseBean parseObject(JSONObject json) {
        ResponseBean bean = new ResponseBean();
        try {
            bean.setStatus(json.getString("status"));
        } catch (Exception e) {
            bean.setStatus(Constant.STATUS_ERROR);
            bean.setMsg("数据解析错误");
        }

        try {
            bean.setCode(json.getInteger("code"));
        } catch (Exception e) {
            bean.setCode(Constant.CODE_ERROR);
            bean.setMsg("数据解析错误!");
        }

        try {
            String msg = json.getString("msg");
            if (TextUtils.isEmpty(msg))
                msg = "";
            bean.setMsg(msg);
        } catch (Exception e) {
            bean.setStatus(Constant.STATUS_ERROR);
            bean.setMsg("数据解析错误!");
        }

        try {
            String need_relogin = json.getString("need_relogin");
            if (!TextUtils.isEmpty(need_relogin)) {
                bean.need_relogin = need_relogin;
            }
        } catch (Exception e) {
            bean.setMsg("数据解析错误!");
        }

        try {
            String data = json.getString("data");
            if (!TextUtils.isEmpty(data)) {
                bean.setData(data);
            }

        } catch (Exception e) {
            bean.setStatus(Constant.STATUS_ERROR);
            bean.setMsg("数据解析错误!");
        }

        return bean;
    }

    //
    public JSONObject toJsonObject(ResponseBean bean) {
        String data = bean.getData();
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        return JSONObject.parseObject(data);
    }

    public JSONArray toJsonArray(ResponseBean bean) {
        String data = bean.getData();
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        return JSON.parseArray(data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNeed_relogin() {
        return need_relogin;
    }

    public void setNeed_relogin(String need_relogin) {
        this.need_relogin = need_relogin;
    }

    public boolean isNeedRelogin(){
        return "1".equals(need_relogin);
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                ", need_relogin='" + need_relogin + '\'' +
                '}';
    }
}
