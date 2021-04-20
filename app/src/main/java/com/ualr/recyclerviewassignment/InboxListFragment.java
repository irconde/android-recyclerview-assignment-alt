package com.ualr.recyclerviewassignment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class InboxListFragment {

}
    private void initRecyclerView() {
        List<Inbox> items = DataGenerator.getInboxData(this);
        items.addAll( DataGenerator.getInboxData(this));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterListBasic(this, items);
        mAdapter.setOnItemClickListener(this);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addItem(DEFAULT_POS,DataGenerator.getRandomInboxItem(view.getContext()));
                recyclerView.scrollToPosition(0);
            }
        });
    }
