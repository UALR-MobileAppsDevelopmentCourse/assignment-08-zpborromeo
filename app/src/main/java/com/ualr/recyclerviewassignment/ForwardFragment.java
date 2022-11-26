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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ForwardFragment extends DialogFragment {

    private static final String ID = ForwardFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selectedIndex";
    private InboxViewModel mainViewModel;


    public static ForwardFragment newInstance(int selectedItemIndex) {
        ForwardFragment mfragment = new ForwardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTED_KEY, selectedItemIndex);
        mfragment.setArguments(bundle);
        return mfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //styling fragment to make everything appear
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
        mainViewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forward_fragment, container, false);
    }

    //remove dialog box after some period of time
    public void dismissDialog() {
        this.dismiss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstancedState){
        super.onViewCreated(view, savedInstancedState);

        //implementing SDF to show new forwarded date on email
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar c = Calendar.getInstance();
        String todayDate = sdf.format(c.getTime());

        final int selectedItemIndex = getArguments().getInt(SELECTED_KEY);
        final Inbox selectedInboxItem = mainViewModel.getInboxList().getValue().get(selectedItemIndex);

        Button forwardButton = view.findViewById(R.id.forward_btn);
        EditText forwardEmailTF = view.findViewById(R.id.forward_emailTF);
        EditText forwardNameTF = view.findViewById(R.id.forward_nameTF);
        EditText forwardMessageTF = view.findViewById(R.id.forward_contentTF);

        forwardEmailTF.setText(selectedInboxItem.getEmail());
        forwardNameTF.setText(selectedInboxItem.getFrom());
        forwardMessageTF.setText(selectedInboxItem.getMessage());

        forwardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //on click of the Send Button, we want to make a new inbox item and add it to the inbox
                String itemSender = forwardNameTF.getText().toString();
                String itemEmail = forwardEmailTF.getText().toString();
                String itemMessage = "Forwarded: " + forwardMessageTF.getText().toString();

                Inbox updatedInbox = new Inbox();
                updatedInbox.setEmailData(itemSender, itemEmail, itemMessage, todayDate, false);

                List<Inbox> curEmails = mainViewModel.getInboxList().getValue();
                curEmails.set(selectedItemIndex, updatedInbox);
                mainViewModel.setInboxList(curEmails);
                dismissDialog();
            }
        });
    }
}