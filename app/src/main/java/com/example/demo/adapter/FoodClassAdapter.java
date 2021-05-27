package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.entity.CookBook;
import com.xuexiang.xui.widget.imageview.ImageLoader;

import java.util.List;

public class FoodClassAdapter extends RecyclerView.Adapter<FoodClassAdapter.FoodClassViewHolder> {
    List<CookBook> cookBooks;
    private Context context;
    private FoodClassAdapter.FoodClassOnClickListener onClickListener;
    public void setData(List<CookBook> cookBooks)
    {
        this.cookBooks = cookBooks;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new FoodClassAdapter.FoodClassViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_food_list, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodClassViewHolder holder, int position) {
            holder.content.setText(cookBooks.get(position).getContent());
            holder.title.setText(cookBooks.get(position).getName());
            ImageLoader.get().loadImage(holder.pic,cookBooks.get(position).getPic());
            CookBook cookBook = cookBooks.get(position);
            holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (onClickListener != null)
                {
                    onClickListener.onItemClick(cookBook,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cookBooks==null?0:cookBooks.size();
    }

    class  FoodClassViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView title;
        TextView content;
        View item;
        public FoodClassViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.iv_food_img);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            item = itemView.findViewById(R.id.card_view);
        }
    }

    public void setClassFoodOnClickListener(FoodClassAdapter.FoodClassOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface FoodClassOnClickListener {
        void onItemClick(CookBook cookBook , int position);
    }
}
