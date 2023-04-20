package com.longkd.android.core.demosqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longkd.android.core.demosqlite.R;
import com.longkd.android.core.demosqlite.UpdateDeleteActivity;
import com.longkd.android.core.demosqlite.adapter.RecyclerViewAdapter;
import com.longkd.android.core.demosqlite.dal.SQLiteHelper;
import com.longkd.android.core.demosqlite.model.Item;

import java.util.List;

public class SearchFragment extends Fragment implements RecyclerViewAdapter.ItemListener {
    private SQLiteHelper db;
    private SearchView search;
    private Button btnFind;
    private TextView tvTong;
    private RecyclerView rcvList;
    private RecyclerViewAdapter adapter;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new SQLiteHelper(getContext());
        search = view.findViewById(R.id.search);
        btnFind = view.findViewById(R.id.btnSearch);
        tvTong = view.findViewById(R.id.tvTong);
        rcvList = view.findViewById(R.id.searchRecycleView);

        adapter = new RecyclerViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvList.setLayoutManager(manager);
        rcvList.setAdapter(adapter);
        adapter.setItemListener(this);
        setUpClick();
    }

    private  int tong(List<Item> list) {
        int tong = 0;
        for (Item i: list) {
            tong += Integer.parseInt(i.getPrice());
        }
        return tong;
    }

    private void setUpClick() {
        btnFind.setOnClickListener(view -> {

            String textContain = search.getQuery().toString();
            List<Item> list = db.getFromDateToDateAndName(textContain);
            adapter.setList(list);

            tvTong.setText("Tong tien: " + tong(list));
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}