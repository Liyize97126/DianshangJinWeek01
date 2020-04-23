package com.bawei.dianshangjinweek01.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bawei.dianshangjinweek01.R;
import com.bawei.dianshangjinweek01.base.BaseActivity;
import com.bawei.dianshangjinweek01.base.BasePresenter;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    //定义
    private static final int REQUEST_CODE = 100;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void initView() {
    }
    @OnClick(R.id.push)
    protected void click(){
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收扫描结果
        if (requestCode == REQUEST_CODE && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //获取结果
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //打印日志
                    Log.i("Tag",result);
                    //粘性传值
                    EventBus.getDefault().postSticky(result);
                    //跳转页面
                    Intent intent = new Intent(MainActivity.this, ListShowActivity.class);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败！", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    protected void initDestroy() {
    }
}
