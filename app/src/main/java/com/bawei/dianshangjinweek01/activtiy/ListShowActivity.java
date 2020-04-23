package com.bawei.dianshangjinweek01.activtiy;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dianshangjinweek01.R;
import com.bawei.dianshangjinweek01.adapter.ListDataAdapter;
import com.bawei.dianshangjinweek01.base.BaseActivity;
import com.bawei.dianshangjinweek01.base.BasePresenter;
import com.bawei.dianshangjinweek01.bean.ListDataBean;
import com.bawei.dianshangjinweek01.contact.IContact;
import com.bawei.dianshangjinweek01.presenter.ListPresenter;
import com.bawei.dianshangjinweek01.util.MyApplication;
import com.bawei.dianshangjinweek01.util.OkHttpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class ListShowActivity extends BaseActivity {
    //定义
    @BindView(R.id.recy)
    protected RecyclerView recy;
    private ListDataAdapter listDataAdapter;
    private String httpUrl;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_show;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new ListPresenter(new IContact.IView() {
            @Override
            public void viewSuccess(String json) {
                ListDataBean listDataBean = MyApplication.getGson().fromJson(json, ListDataBean.class);
                listDataAdapter.getList().addAll(listDataBean.getResult());
                listDataAdapter.notifyDataSetChanged();
            }
            @Override
            public void viewFail(String err) {
                Toast.makeText(ListShowActivity.this,"参数错误！    " + err,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void initView() {
        //注册EventBus
        EventBus.getDefault().register(this);
        //设置适配器
        recy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listDataAdapter = new ListDataAdapter();
        recy.setAdapter(listDataAdapter);
        listDataAdapter.setOnRecyViewListener(new ListDataAdapter.OnRecyViewListener() {
            @Override
            public void onRecyView(String name) {
                Toast.makeText(ListShowActivity.this,"电影名称：" + name,Toast.LENGTH_LONG).show();
            }
        });
        //判断网络
        if(OkHttpUtil.getOkHttpUtil().hasNet()){
            //判断链接地址是否不为空
            if(!TextUtils.isEmpty(httpUrl)){
                //发起请求
                basePresenter.request(httpUrl,null);
            } else {
                Toast.makeText(ListShowActivity.this,"数据获取失败！",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ListShowActivity.this,"没有网络！",Toast.LENGTH_LONG).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getUrl(String url){
        //日志
        Log.i("Tag",url);
        //传送
        httpUrl = url;
    }
    @Override
    protected void initDestroy() {
        //取消注册
        EventBus.getDefault().unregister(this);
        //取消绑定
        if(listDataAdapter != null){
            listDataAdapter.destroy();
            listDataAdapter = null;
        }
    }
}
