package com.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ForwardDialogFragment extends DialogFragment  {
    private static final String SELECTED = "selectedInbox";
    private InboxViewModel mInboxViewModel;
    private InboxListFragment mInboxListFragment;

    public static ForwardDialogFragment newInstance(int selectedInbox) {
        ForwardDialogFragment mFragment = new ForwardDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(SELECTED, selectedInbox);
        mFragment.setArguments(arguments);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInboxViewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forward_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int selectedItemIndex = getArguments().getInt(SELECTED);
        final Inbox selectedInboxItem = mInboxViewModel.getInboxList().getValue().get(selectedItemIndex);
        final EditText email = view.findViewById(R.id.sender_email);
        final EditText name = view.findViewById(R.id.sender_name);
        final EditText text = view.findViewById(R.id.sender_text);


        email.setText(selectedInboxItem.getEmail());
        name.setText(selectedInboxItem.getFrom());
        text.setText(selectedInboxItem.getMessage());

        Button sendBtn = view.findViewById(R.id.send_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("dd, MMM, yyyy");
                Inbox forwardedItem = new Inbox();
                forwardedItem.setEmail(email.getText().toString());
                forwardedItem.setFrom(name.getText().toString());
                forwardedItem.setMessage(text.getText().toString());
                forwardedItem.setDate(df.format(Calendar.getInstance().getTime()));
                forwardedItem.setSelected(false);

                List<Inbox> currentInboxItems = mInboxViewModel.getInboxList().getValue();

                currentInboxItems.add(0,forwardedItem);
                mInboxViewModel.setInbox(currentInboxItems);
                dismissFragment();

            }
        });

    }
    public void dismissFragment() {
        this.dismiss();
    }
}
