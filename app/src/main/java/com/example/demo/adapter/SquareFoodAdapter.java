package com.example.demo.adapter;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.demo.R;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.MenuItem;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.List;
import java.util.Objects;

public class SquareFoodAdapter extends RecyclerView.Adapter<SquareFoodAdapter.SquareFoodViewHold> {

    List<CookBook> cookBooks;
    private SquareFoodAdapter.SquareFoodOnClickListener onClickListener;
    private Context context;
    public void setData(List<CookBook> cookBooks)
    {
        this.cookBooks = cookBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SquareFoodViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new SquareFoodAdapter.SquareFoodViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guangchang_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SquareFoodViewHold holder, int position) {
        holder.foodTitle.setText(cookBooks.get(position).getName());
        String url = Objects.requireNonNull(cookBooks.get(position).getPic()).trim();

        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.icon_load_pic)
                .error(R.mipmap.icon_load_pic)
//                .transform( new RotateTransformation( context, 90f ))
                .into(holder.foodImg);

        CookBook cookBook1 = cookBooks.get(position);
        holder.foodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if ( onClickListener!= null)
                {
                    onClickListener.onItemClick(cookBook1, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cookBooks==null?0:cookBooks.size();
    }

    class SquareFoodViewHold extends RecyclerView.ViewHolder {

        TextView foodTitle;
        ImageView foodImg;
        View foodItem;
        public SquareFoodViewHold(@NonNull View itemView) {
            super(itemView);
            foodTitle = itemView.findViewById(R.id.tv_food_title);
            foodImg = itemView.findViewById(R.id.iv_food_img);
            foodItem = itemView.findViewById(R.id.food_item);

        }
    }

    public void setSquareFoodOnClickListener(SquareFoodAdapter.SquareFoodOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface SquareFoodOnClickListener {
        void onItemClick(CookBook cookBook , int position);
    }
}
