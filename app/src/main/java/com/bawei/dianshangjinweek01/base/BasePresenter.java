package com.bawei.dianshangjinweek01.base;

import com.bawei.dianshangjinweek01.contact.IContact;
import com.bawei.dianshangjinweek01.util.OkHttpUtil;

import java.util.Map;

public abstract class BasePresenter {
    //定义
    private IContact.IView iView;
    //构造
    public BasePresenter(IContact.IView iView) {
        this.iView = iView;
    }
    //请求方法
    public void request(String url, Map<String,String> params){
        //调用方法
        OkHttpUtil.getOkHttpUtil().modelRequest(url, params, new IContact.IModel() {
            @Override
            public void modelSuccess(String json) {
                iView.viewSuccess(json);
            }
            @Override
            public void modelFail(String err) {
                iView.viewFail(err);
            }
        });
    }
    //销毁
    public void destroy(){
        if(iView != null){
            iView = null;
        }
    }
}
