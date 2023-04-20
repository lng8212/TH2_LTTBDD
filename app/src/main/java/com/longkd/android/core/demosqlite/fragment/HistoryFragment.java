package com.longkd.android.core.demosqlite.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longkd.android.core.demosqlite.R;
import com.longkd.android.core.demosqlite.UpdateDeleteActivity;
import com.longkd.android.core.demosqlite.adapter.RecyclerViewAdapter;
import com.longkd.android.core.demosqlite.dal.SQLiteHelper;
import com.longkd.android.core.demosqlite.model.Item;

import java.util.List;

public class HistoryFragment extends Fragment implements RecyclerViewAdapter.ItemListener {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new RecyclerViewAdapter();
        db = new SQLiteHelper(getContext());

//        Item item = new Item(3, "Mua ao", "Mua sam", "600", "13/04/2023");
//        db.insertItem(item);
        List<Item> list = db.getItems();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list = db.getItems();
        adapter.setList(list);
    }
}