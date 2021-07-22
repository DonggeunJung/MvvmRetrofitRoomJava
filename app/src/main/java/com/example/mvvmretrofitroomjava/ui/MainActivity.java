package com.example.mvvmretrofitroomjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import com.example.mvvmretrofitroomjava.R;
import com.example.mvvmretrofitroomjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        initList();
    }

    // Init RecyclerView adapter & Request School list to server
    protected void initList() {
        // Init RecyclerView adapter
        BookRVAdapter rvAdapter = new BookRVAdapter(this);
        binding.rvBook.setAdapter( rvAdapter );
        binding.rvBook.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        // Show Item Divider on RecyclerView
        binding.rvBook.addItemDecoration(new DividerItemDecoration(this, 1));
    }
}