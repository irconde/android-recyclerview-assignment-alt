package com.ualr.recyclerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;

import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;

import com.ualr.recyclerviewassignment.databinding.ActivityMainBinding;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String INBOX_LIST_FRAGMENT_TAG = "InboxListFragment" ;


    private FloatingActionButton mFAB;

    private AdapterListBasic mAdapter;
    private InboxListFragment mFragment;
    private AdapterListBasic.OnItemClickListener mOnItemClickListener;
    private ActivityMainBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragment = new InboxListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, mFragment,INBOX_LIST_FRAGMENT_TAG);
        ft.commit();


        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragment.addInboxItem();

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_action:
                mFragment.removeInboxItem();
                displaySnackBar();
                return true;
            case R.id.forward_action:
                mFragment.forwardEmail();
                return true;
            default:
                return true;
        }

    }
    public void displaySnackBar(){
        CoordinatorLayout parentView = findViewById(R.id.lyt_parent);
        String message = getResources().getString(R.string.message_email_deleted);
        int message_duration = Snackbar.LENGTH_LONG;
        Snackbar snackbar = Snackbar.make(parentView, message, message_duration);
        snackbar.show();
    }


}