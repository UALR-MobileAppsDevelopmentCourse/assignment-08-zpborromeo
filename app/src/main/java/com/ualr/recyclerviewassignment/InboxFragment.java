package com.ualr.recyclerviewassignment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.Utils.AdapterListBasic;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;

public class InboxFragment extends Fragment implements AdapterListBasic.OnItemClickListener {
    private static final String ID = InboxFragment.class.getSimpleName();
    private static final String FORWARD_TAG = ForwardFragment.class.getSimpleName();
    private static final String FORWARD_KEY = "FORWARD_EMAIL";

    private Context mainContext;
    private RecyclerView mainRecyclerView;
    private AdapterListBasic mainAdapter;

    private InboxViewModel mainViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainContext = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inbox_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        List<Inbox> inboxList = DataGenerator.getInboxData(mainContext);
        mainViewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
        mainViewModel.setInboxList(inboxList);

        mainAdapter = new AdapterListBasic(mainContext, mainViewModel.getInboxList().getValue());
        mainAdapter.setOnItemClickListener(this);

        mainRecyclerView = view.findViewById(R.id.MainRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainContext);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(mainAdapter);


        mainViewModel.getInboxList().observe(getViewLifecycleOwner(), new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxList) {
                mainAdapter.updateInbox(inboxList);
            }
        });
    }

    @Override
    public void onItemClick(View view, Inbox obj, int position) {
        List<Inbox> curInbox = mainViewModel.getInboxList().getValue();
        clearAllSelections(curInbox);
        curInbox.get(position).toggleSelection();
        mainViewModel.setSelectedEmailIndex(position);
        mainViewModel.setInboxList(curInbox);
    }

    @Override
    public void onIconClick(View view, Inbox obj, int pos) {
        List<Inbox> curInbox = mainViewModel.getInboxList().getValue();
        clearAllSelections(curInbox);
        curInbox.get(pos).toggleSelection();
        mainViewModel.setSelectedEmailIndex(pos);
        mainViewModel.setInboxList(curInbox);
    }

    public void addEmail() {
        Inbox newEmail = DataGenerator.getRandomInboxItem(mainContext);
        List<Inbox> curInbox = mainViewModel.getInboxList().getValue();
        curInbox.add(0, newEmail);
        mainViewModel.setInboxList(curInbox);
    }

    public boolean deleteEmail() {
        int curSelectedPos = mainViewModel.getSelectedIndex().getValue();
        List<Inbox> curInbox = mainViewModel.getInboxList().getValue();

        if (curSelectedPos != -1 && curInbox != null) {
            curInbox.remove(curSelectedPos);
            clearAllSelections(curInbox);

            mainViewModel.setInboxList(curInbox);
            mainViewModel.setSelectedEmailIndex(-1);
            return true;
        }
        return false;
    }

    public int getSelectedEmailPosition() {
        return mainViewModel.getSelectedIndex().getValue();
    }

    public void clearAllSelections(List<Inbox> curInbox) {
        for (Inbox inbox: curInbox) {
            inbox.setSelected(false);
        }
    }

    public void forwardEmail() {
        ForwardFragment forwardFragment = ForwardFragment.newInstance(getSelectedEmailPosition());
        forwardFragment.show(getParentFragmentManager(), ID);
    }
}