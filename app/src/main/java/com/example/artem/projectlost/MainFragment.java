package com.example.artem.projectlost;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<RecyclerItem> listItems;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_layout,null);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));//Необходим для работы с методами адаптера?
        listItems = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            listItems.add(new RecyclerItem("Item " + (i + 1), " " + (i+1)));
        }

        adapter = new RecyclerAdapter(listItems);
        recyclerView.setAdapter(adapter);

        return v;
    }
}
