package com.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.AdapterListBasic;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.databinding.ActivityListMultiSelectionBinding;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.InboxFragment;
import com.ualr.recyclerviewassignment.ForwardFragment;


public class MainActivity extends AppCompatActivity {
    private static final String ID = MainActivity.class.getSimpleName();
    private InboxFragment inboxFragment;
    private FloatingActionButton mFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inboxFragment = (InboxFragment) getSupportFragmentManager().findFragmentById(R.id.inbox_fragment);

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFABClick();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //menu functionality - implemented switch cases to check for which menu item is being used
    //if the item is the delete action, we delete the email and show a snackbar message
    //if the item is the forward action, we pop up a forward fragment
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                onDeleteClicked();
                return true;
            case R.id.forward_action:
                onForwardClicked();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    //function to show snackbar message
    public void showSnack(String msg) {
        CoordinatorLayout parentView = findViewById(R.id.mainLayout);
        int duration = Snackbar.LENGTH_LONG;
        Snackbar sb = Snackbar.make(parentView, msg, duration);

        sb.show();
    }

    //moved functionality of FAB click to it's own function - if the FAB is clicked, add a new email
    public void onFABClick() {
        if (inboxFragment != null && inboxFragment.isInLayout()) {
            inboxFragment.addEmail();
        }
    }

    //if we delete an email, remove it from the inbox and show a message
    public void onDeleteClicked() {
        if (inboxFragment != null && inboxFragment.isInLayout()) {
            boolean emailDeleted = inboxFragment.deleteEmail();
            if (emailDeleted) {
                String deleteMsg = getResources().getString(R.string.email_deleted);
                showSnack(deleteMsg);
            }
        }
    }

    //if we forward an email, call forwardEmail() function and show a message
    public void onForwardClicked() {
        if (inboxFragment != null && inboxFragment.isInLayout()) {
            inboxFragment.forwardEmail();
        }
    }
}
