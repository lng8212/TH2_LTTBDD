package com.longkd.android.core.demosqlite.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longkd.android.core.demosqlite.R;
import com.longkd.android.core.demosqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeViewHolder> {
    private List<Item> list;

    private ItemListener itemListener;

    public RecyclerViewAdapter(){
        this.list = new ArrayList<>();
    }

    public void setItemListener(ItemListener listener) {
        itemListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title, category, price, date;


        public HomeViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            category = view.findViewById(R.id.tvCategory);
            price = view.findViewById(R.id.tvPrice);
            date = view.findViewById(R.id.tvDate);
            view.setOnClickListener(this);
        }

        public void bind(Item item) {
            title.setText(item.getTitle());
            category.setText(item.getCategory());
            price.setText(item.getPrice());
            date.setText(item.getDate());
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public Item getItem(int position) {
        return list.get(position);
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
