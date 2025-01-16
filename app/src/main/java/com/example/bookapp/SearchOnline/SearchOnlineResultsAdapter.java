package com.example.bookapp.SearchOnline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;

import java.util.List;

public class SearchOnlineResultsAdapter extends RecyclerView.Adapter<SearchOnlineResultsAdapter.ViewHolder> {
    private List<GoogleBooksResponse.Item> items;
    private SearchOnlineFragment fragment;

    public SearchOnlineResultsAdapter(List<GoogleBooksResponse.Item> items, SearchOnlineFragment fragment) {
        this.items = items;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoogleBooksResponse.Item item = items.get(position);
        holder.title.setText(item.volumeInfo.title);
        holder.author.setText(item.volumeInfo.authors != null ? String.join(", ", item.volumeInfo.authors) : "Unknown Author");

        holder.shareButton.setOnClickListener(v -> {
            if (fragment != null) {
                fragment.shareGoogleBookInfo(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<GoogleBooksResponse.Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        Button shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.resultTitle);
            author = itemView.findViewById(R.id.resultAuthor);
            shareButton = itemView.findViewById(R.id.shareButton);
        }
    }
}


