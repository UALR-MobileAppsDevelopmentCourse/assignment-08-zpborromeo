package com.ualr.recyclerviewassignment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;

import java.util.List;

public class InboxViewModel extends ViewModel {
    private static final int NONE_SELECTED = -1;
    private MutableLiveData<List<Inbox>> inboxList = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedEmailIndex = new MutableLiveData<>(NONE_SELECTED);

    public LiveData<List<Inbox>> getInboxList() {return inboxList;}

    public void setInboxList(List<Inbox> inboxList) {this.inboxList.setValue(inboxList);}

    public LiveData<Integer> getSelectedIndex() {return selectedEmailIndex;}

    public void setSelectedEmailIndex(int selected) {this.selectedEmailIndex.setValue(selected);}

}