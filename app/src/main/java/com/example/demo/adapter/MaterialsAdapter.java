package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.Materials;

import java.util.List;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MaterialsViewHolder> {

    List<Materials> materials;
    private Context context;
    public void setData(List<Materials> materials)
    {
        this.materials= materials;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MaterialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new MaterialsAdapter.MaterialsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materials_list, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialsViewHolder holder, int position) {
        holder.name.setText(materials.get(position).getName());
        holder.amount.setText(materials.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return materials==null?0:materials.size();
    }

    class  MaterialsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        public MaterialsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_material_name);
            amount = itemView.findViewById(R.id.tv_material_amount);
        }
    }
}
