package com.example.bookapp;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookapp.models.Book;
import com.example.bookapp.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.bookapp.databinding.FragmentListViewBinding;

import java.util.List;

public class MyBooksRecyclerViewAdapter extends RecyclerView.Adapter<MyBooksRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyBooksRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.viewAuthor.setText(mValues.get(position).author);
        holder.viewYear.setText(mValues.get(position).year);
        holder.viewName.setText(mValues.get(position).name);
        holder.viewAddressBought.setText(mValues.get(position).addressBought);
        holder.viewID.setText(mValues.get(position).id);

        holder.itemView.setOnClickListener(view->{
            Fragment fragment = new UpdateDeleteFragment();

            Book book = BookBuilder.Build(
                    Integer.parseInt(holder.viewID.getText().toString()),
                    holder.viewName.getText().toString(),
                    holder.viewAuthor.getText().toString(),
                    holder.viewAddressBought.getText().toString(),
                    holder.viewYear.getText().toString()
            );

            Bundle b = new Bundle();
            b.putSerializable("book", book);

            fragment.setArguments(b);
            ((MainActivity)view.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_update_delete_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewID, viewName, viewAuthor, viewYear, viewAddressBought;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentListViewBinding binding) {
            super(binding.getRoot());
            viewID = binding.viewID; viewName = binding.viewName;
            viewAuthor = binding.viewAuthor; viewYear = binding.viewYear;
            viewAddressBought = binding.viewAddressBought;
        }

        @Override
        public String toString() {
            return viewName.getText().toString() + "\t" + viewAuthor.getText().toString();
        }
    }
}