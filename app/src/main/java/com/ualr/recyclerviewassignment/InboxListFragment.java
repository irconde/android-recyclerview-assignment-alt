package com.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.databinding.InboxListFragmentBinding;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;

public class InboxListFragment extends Fragment implements AdapterListBasic.OnItemClickListener {
    private static final int DEFAULT_POS = 0;
    private AdapterListBasic mAdapter;
    private RecyclerView recyclerView;
    private InboxViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inbox_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Inbox> items = DataGenerator.getInboxData((MainActivity)getActivity());
        items.addAll(DataGenerator.getInboxData((MainActivity)getActivity()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((MainActivity)getActivity());
        mAdapter = new AdapterListBasic((MainActivity) getActivity(), items);
        mAdapter.setOnItemClickListener(this);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, Inbox obj, int position) {
        mAdapter.clearAllSelections();
        obj.toggleSelection();
        mAdapter.notifyItemChanged(position);
    }


    public void addInboxItem(){
        mAdapter.addItem(DEFAULT_POS,DataGenerator.getRandomInboxItem(getActivity()));
        recyclerView.scrollToPosition(0);
    }

}
