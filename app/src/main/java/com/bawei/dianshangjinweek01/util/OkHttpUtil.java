package com.bawei.dianshangjinweek01.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.bawei.dianshangjinweek01.contact.IContact;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttpUtil工具类
 */
public class OkHttpUtil {
    //定义
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler();
    //单例
    private static final OkHttpUtil OK_HTTP_UTIL = new OkHttpUtil();
    private OkHttpUtil() {
        //定义拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        //OkHttp建造者模式创建对象
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)//读取超时
                .connectTimeout(5000, TimeUnit.MILLISECONDS)//连接超时
                .build();
    }
    public static OkHttpUtil getOkHttpUtil() {
        return OK_HTTP_UTIL;
    }
    //封装网络判断方法
    public boolean hasNet(){
        //判断网络
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isAvailable()){
            return true;
        }
        return false;
    }
    //请求方法
    public void modelRequest(String url, Map<String,String> params, final IContact.IModel iModel){
        //处理参数
        String link;
        if(params != null){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?");
            for (String key : params.keySet()) {
                stringBuffer.append(key + "=" + params.get(key) + "&");
            }
            String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
            link = url + substring;
        } else {
            link = url;
        }
        //发起请求
        Request request = new Request.Builder()
                .url(link)
                .get()
                .build();
        //请求结果回调
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                e.printStackTrace();
                //反馈
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iModel.modelFail(e.getMessage());
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //获取结果
                final String string = response.body().string();
                //反馈
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iModel.modelSuccess(string);
                    }
                });
            }
        });
    }
}
