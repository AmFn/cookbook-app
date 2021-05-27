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
import com.example.demo.entity.MenuItem;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    List<MenuItem> menuItems;
    private MenuItemAdapter.MenuItemOnClickListener onClickListener;
    private Context context;


    public void setData(List<MenuItem> menuItems)
    {
        this.menuItems = menuItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new MenuItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        holder.menuName.setText(menuItems.get(position).getName());
        holder.menuIcon.setImageResource(menuItems.get(position).getIcon());
        MenuItem menuItem = menuItems.get(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if ( onClickListener!= null)
                {
                    onClickListener.onItemClick(menuItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems==null?0:menuItems.size();
    }

    public  static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView menuName;
        ImageView menuIcon;
        View item;
        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            menuIcon = itemView.findViewById(R.id.iv_menu_icon);
            menuName  = itemView.findViewById(R.id.tv_menu_item_name);
            item = itemView.findViewById(R.id.menu_item_main);
        }
    }


    public void setMenuItemOnClickListener(MenuItemAdapter.MenuItemOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface MenuItemOnClickListener {
        void onItemClick(MenuItem menuItem ,int position);
    }
}
