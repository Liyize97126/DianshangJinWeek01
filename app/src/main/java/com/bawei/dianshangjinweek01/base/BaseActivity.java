package com.bawei.dianshangjinweek01.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    protected BasePresenter basePresenter;
    private Unbinder bind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //绑定ButterKnife
        bind = ButterKnife.bind(this);
        basePresenter = initPresenter();
        initView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        initDestroy();
        //解绑
        bind.unbind();
        if(basePresenter != null){
            basePresenter.destroy();
            basePresenter = null;
        }
    }
    protected abstract int getLayoutId();
    protected abstract BasePresenter initPresenter();
    protected abstract void initView();
    protected abstract void initDestroy();
}
