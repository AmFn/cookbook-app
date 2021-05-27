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
import com.example.demo.entity.User;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ArtistHolder>{

    private List<User> users;
    private Context context;
    private UserListAdapter.UserOnClickListener userOnClickListener;


    public void setData(List<User> users)
    {
        this.users = users;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ArtistHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        holder.name.setText(users.get(position).getNickName());


        Glide.with(context)
                .load(users.get(position).getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show();
                        Log.v("glide", "Error loading image", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.img);



        User user = users.get(position);
        holder.item_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (userOnClickListener != null)
                {
                    userOnClickListener.onArtistClick(user, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users==null?0:users.size();
    }

    static class  ArtistHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;
        View item_artist;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            img = itemView.findViewById(R.id.tv_img);
            item_artist = itemView.findViewById(R.id.item_user);
        }
    }

    public void setUserOnClickListener(UserListAdapter.UserOnClickListener onClickListener) {
        this.userOnClickListener = onClickListener;
    }

    public interface UserOnClickListener {

        void onArtistClick(User user, int positon);
    }

}
