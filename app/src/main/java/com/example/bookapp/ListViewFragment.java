package com.example.bookapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookapp.database.BookDatabase;
import com.example.bookapp.models.Book;
import com.example.bookapp.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private RecyclerView recyclerView;

    public static ListViewFragment newInstance(int columnCount) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    public void reloadData() {
        if (recyclerView != null) {
            Thread t = new Thread(() -> {
                List<Book> books = BookDatabase.getInstance(getContext())
                        .bookDao().getAllBooks();

                Log.d("BookList", "Fetched books: " + books.size());

                List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
                for (Book b : books) {
                    items.add(new PlaceholderContent.PlaceholderItem(b));
                }

                getActivity().runOnUiThread(() -> {
                    MyBooksRecyclerViewAdapter adapter = new MyBooksRecyclerViewAdapter(items);
                    recyclerView.setAdapter(adapter);
                });
            });
            t.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            reloadData();
        }
        return view;
    }
}
