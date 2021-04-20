package com.ualr.recyclerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;

import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.databinding.ActivityListMultiSelectionBinding;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterListBasic.OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_POS = 0;

    private FloatingActionButton mFAB;
    private AdapterListBasic mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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

    @Override
    public void onItemClick(View view, Inbox obj, int position) {
        mAdapter.clearAllSelections();
        obj.toggleSelection();
        mAdapter.notifyItemChanged(position);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.delete_action:
//
//
//        }
        return true;
    }
    private class ScreenScrollPagerAdapter extends FragmentStateAdapter{

        public ScreenScrollPagerAdapter(@NonNull FragmentActivity fragmentActivity){
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment(int position){return null;}


        @Override
        public int getItemCount() {
            return 0;
        }
    }
}