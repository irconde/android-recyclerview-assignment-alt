package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;

import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.databinding.ActivityListMultiSelectionBinding;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list
// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity implements AdapterListBasic.OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton mFAB;

    private ActivityListMultiSelectionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityListMultiSelectionBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        // TODO 01. Generate the item list to be displayed using the DataGenerator class


        List<Inbox> items = DataGenerator.getInboxData(this);
        items.addAll( DataGenerator.getInboxData(this));
        items.addAll( DataGenerator.getInboxData(this));

        // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly

        RecyclerView inboxListView = mBinding.recyclerView;

        // TODO 04. Define the layout of each item in the list

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        inboxListView.setLayoutManager(layoutManager);

        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03
        final AdapterListBasic adapter = new AdapterListBasic(items);

        adapter.setOnItemClickListener(this);
        inboxListView.setAdapter(adapter);



        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 10. Invoke the method created to a new item to the top of the list so it's
                //  triggered when the user taps the Floating Action Button

                adapter.addItem(0,DataGenerator.getRandomInboxItem(view.getContext()));
                mBinding.recyclerView.scrollToPosition(0);
            }
        });
    }

    @Override
    public void onItemClick(View view, Inbox obj, int position) {


        Log.d(TAG, String.format("The user %s has tapped on the iem %d", obj.getFrom(), position));
    }
}