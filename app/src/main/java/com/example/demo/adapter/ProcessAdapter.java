package com.example.demo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.demo.R;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.Processes;

import java.util.List;

public class ProcessAdapter extends RecyclerView.Adapter<ProcessAdapter.ProcessViewHolder> {

    List<Processes> processes;
    private Context context;
    public void setData(List<Processes> process)
    {
        this.processes= process;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProcessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ProcessAdapter.ProcessViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_processes_list, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessViewHolder holder, int position) {
                if (processes.get(position).getContent()==null){
                    holder.title.setText(position+1+"."+"数据缺失");
                }else {
                    holder.title.setText(String.valueOf(position+1)+"."+processes.get(position).getContent());
                }



             Glide.with(context)
                .load(processes.get(position).getPic())

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.v("glide", "Error loading image", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return processes==null?0:processes.size();
    }

    class  ProcessViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        public ProcessViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_process_title);
            img = itemView.findViewById(R.id.iv_process_img);
        }
    }
}
