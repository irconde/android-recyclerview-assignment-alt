package com.ualr.recyclerviewassignment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ualr.recyclerviewassignment.model.Inbox;
import java.util.List;

public class InboxViewModel extends ViewModel {
    private static final int NOT_SELECTED = -1;
    private MutableLiveData<List<Inbox>> inbox = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedIndex = new MutableLiveData<>(NOT_SELECTED);

    public LiveData<List<Inbox>> getInboxList() {
        return inbox;
    }

    public LiveData<Integer> getSelectedIndex() {
        return selectedIndex;
    }

    public void setInbox(List<Inbox> inboxList){
        this.inbox.setValue(inboxList);
    }

    public void setSelectedIndex(int selected) {
        this.selectedIndex.setValue(selected);
    }
}
