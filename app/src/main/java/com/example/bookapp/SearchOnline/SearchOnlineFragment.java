package com.example.bookapp.SearchOnline;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchOnlineFragment extends Fragment {
    private EditText searchQuery;
    private Button searchButton;
    private RecyclerView searchResultsRecyclerView;
    private SearchOnlineResultsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_online, container, false);

        searchQuery = view.findViewById(R.id.searchQuery);
        searchButton = view.findViewById(R.id.searchButton);
        searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);

        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchOnlineResultsAdapter(new ArrayList<>(), this);
        searchResultsRecyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> performSearch());

        return view;
    }

    private void performSearch() {
        String query = searchQuery.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        String apiKey = getString(R.string.google_books_api_key);

        GoogleBooksApi api = RetrofitClient.getClient();
        api.searchBooks(query, apiKey).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GoogleBooksResponse.Item> items = response.body().items;
                    adapter.updateData(items);
                } else {
                    Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GoogleBooksResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void shareGoogleBookInfo(GoogleBooksResponse.Item book) {
        String bookTitle = book.volumeInfo.title;
        String bookAuthor = book.volumeInfo.authors != null ? String.join(", ", book.volumeInfo.authors) : "Unknown Author";

        String shareText = "Check out this book:\n" + bookTitle + "\nby " + bookAuthor;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}

