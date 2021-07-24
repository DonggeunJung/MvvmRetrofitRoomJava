package com.example.mvvmretrofitroomjava.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvvmretrofitroomjava.R;
import com.example.mvvmretrofitroomjava.data.Book;
import com.example.mvvmretrofitroomjava.databinding.BookListItemBinding;
import com.example.mvvmretrofitroomjava.util.InjectorUtils;
import java.util.List;

public class BookRVAdapter extends RecyclerView.Adapter<BookRVAdapter.BookVH> {
    private BookViewModel viewModel;
    AppCompatActivity parent;
    RecyclerView.Adapter<BookVH> instance;

    // Constructor
    public BookRVAdapter(AppCompatActivity parent) {
        this.parent = parent;
        instance = this;

        // make Book list Observer object
        final Observer<List<Book>> booksObserver = new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable final List<Book> bookList) {
                // When Book list is changed update RecyclerView
                instance.notifyDataSetChanged();
            }
        };

        // Get the instance of ViewModel
        viewModel = new ViewModelProvider(parent, InjectorUtils.provideViewModelFactory()).get(BookViewModel.class);
        // Send Observer object to ViewModel
        viewModel.getBooks().observe(parent, booksObserver);
        // Request Card data list to Repository
        viewModel.reqBooks(parent);
    }

    // Return list items count
    @Override
    public int getItemCount() {
        // Get the school simple data items count
        List<Book> books = viewModel.getBooks().getValue();
        // When the data object is not exist return 0
        if( books == null )
            return 0;
        return books.size();
    }

    // Make ViewHolder & View binding object
    @Override
    public BookVH onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Get the view binding object of custom list item layout
        LayoutInflater inflater = LayoutInflater.from(parent);
        BookListItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.book_list_item, viewGroup, false);
        // Set the Lifecycle Owner of View binding to fragment
        binding.setLifecycleOwner((LifecycleOwner) parent);
        // Set ViewModel object to binding object as a variable
        binding.setViewModel(viewModel);

        // Make ViewHolder object
        return new BookVH(binding);
    }

    // When ViewHolder is binded set data to binding object
    @Override
    public void onBindViewHolder(BookVH viewHolder, int position) {
        // Set item index number to binding object
        viewHolder.bind(position);
    }

    // Reuse views
    public static class BookVH extends RecyclerView.ViewHolder {
        public BookListItemBinding binding;

        //public ViewHolder(View itemView) {
        public BookVH(BookListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int index) {
            binding.setIndex(index);
            binding.executePendingBindings();
        }
    }
}
