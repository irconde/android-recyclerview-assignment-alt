package com.ualr.recyclerviewassignment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;

import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;

public class InboxListFragment extends Fragment implements AdapterListBasic.OnItemClickListener {
    private static final int DEFAULT_POS = 0;
    private static final String TAG = InboxListFragment.class.getSimpleName();
    private AdapterListBasic mAdapter;
    private RecyclerView recyclerView;
    private Context mContext;
    private InboxViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        List<Inbox> items = DataGenerator.getInboxData(mContext);
        viewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
        viewModel.setInbox(items);

        mAdapter = new AdapterListBasic(mContext, viewModel.getInboxList().getValue());
        mAdapter.setOnItemClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        viewModel.getInboxList().observe(getViewLifecycleOwner(), new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxes) {
                mAdapter.updateItems(inboxes);
            }
        });
    }

    @Override
    public void onItemClick(View view, Inbox obj, int position) {

        mAdapter.clearAllSelections();
        obj.toggleSelection();
        mAdapter.notifyItemChanged(position);
        viewModel.setSelectedIndex(position);

    }


    public void addInboxItem(){

        mAdapter.addItem(DEFAULT_POS,DataGenerator.getRandomInboxItem(mContext));
        recyclerView.scrollToPosition(0);
    }

    public void removeInboxItem(){
        int currentItem = viewModel.getSelectedIndex().getValue();
        List<Inbox> current = viewModel.getInboxList().getValue();

        if (currentItem != -1 && current != null){
            mAdapter.removeItem(currentItem);
        }
    }

    public void forwardEmail() {
        ForwardDialogFragment forwardedFragment = ForwardDialogFragment.newInstance(viewModel.getSelectedIndex().getValue());
        forwardedFragment.show(getFragmentManager(), TAG);
        mAdapter.clearAllSelections();
    }





}
