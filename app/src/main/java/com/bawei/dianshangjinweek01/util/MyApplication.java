package com.bawei.dianshangjinweek01.util;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 获取公共Context和Gson
 */
public class MyApplication extends Application {
    //定义
    private static Context context;
    private static final Gson GSON = new Gson();
    //封装
    public static Gson getGson() {
        return GSON;
    }
    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //设置
        context = this;
        ZXingLibrary.initDisplayOpinion(this);
    }
}
