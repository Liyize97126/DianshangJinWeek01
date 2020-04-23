package com.bawei.dianshangjinweek01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.dianshangjinweek01.R;
import com.bawei.dianshangjinweek01.bean.ListDataBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 列表适配器
 */
public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.MyViewHouler> {
    //定义
    private Unbinder bind;
    //定义集合
    private List<ListDataBean.ResultBean> list = new ArrayList<>();
    public List<ListDataBean.ResultBean> getList() {
        return list;
    }
    //定义接口
    private OnRecyViewListener onRecyViewListener;
    public void setOnRecyViewListener(OnRecyViewListener onRecyViewListener) {
        this.onRecyViewListener = onRecyViewListener;
    }
    //方法实现
    @NonNull
    @Override
    public MyViewHouler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcontents, parent, false);
        return new MyViewHouler(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHouler holder, int position) {
        //获取数据
        ListDataBean.ResultBean resultBean = list.get(position);
        //Glide图片设置
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.zhanpict)
                .fallback(R.drawable.zhanpict)
                .error(R.drawable.zhanpict)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)));
        //设置文本
        holder.name.setText(resultBean.getName());
        //Glide加载图片
        Glide.with(holder.imageUrl.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(resultBean.getImageUrl())
                .into(holder.imageUrl);
        holder.director.setText(resultBean.getDirector());
        holder.starring.setText(resultBean.getStarring());
        holder.score.setText(resultBean.getScore() + "分");
        //传值
        holder.itemView.setTag(resultBean.getName());
        //设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyViewListener.onRecyView((String) v.getTag());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyViewHouler extends RecyclerView.ViewHolder {
        //定义
        @BindView(R.id.imageUrl)
        protected ImageView imageUrl;
        @BindView(R.id.name)
        protected TextView name;
        @BindView(R.id.director)
        protected TextView director;
        @BindView(R.id.starring)
        protected TextView starring;
        @BindView(R.id.score)
        protected TextView score;
        public MyViewHouler(@NonNull View itemView) {
            super(itemView);
            bind = ButterKnife.bind(this, itemView);
        }
    }
    //声明一个回调接口
    public interface OnRecyViewListener{
        void onRecyView(String name);
    }
    //取消绑定
    public void destroy(){
        bind.unbind();
    }
}
