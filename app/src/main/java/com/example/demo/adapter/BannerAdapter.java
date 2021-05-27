package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.xuexiang.xui.widget.imageview.ImageLoader;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder>{

    private String[] urls;//图片地址
    private Context context;
    public BannerAdapter(Context context, String[] urls){
        this.urls = urls;
        this.context = context;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iv_item, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        ImageView imageView = holder.imageView;
        ImageLoader.get().loadImage(imageView, urls[position]);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;//轮播条的 item 项
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_item);
        }
    }
}
