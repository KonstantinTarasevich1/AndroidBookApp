package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookapp.database.BookDatabase;
import com.example.bookapp.models.Book;


public class UpdateDeleteFragment extends Fragment {

    String Id;
    EditText editName, editAuthor, editYear, editAddressBought;
    Button btnUpdate, btnDelete;
    onBookDeleted bookDeleted;
    onBookUpdated bookUpdated;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bookDeleted = (onBookDeleted) context;
        bookUpdated = (onBookUpdated) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_delete, container, false);
        editName = v.findViewById(R.id.editName);
        editAuthor = v.findViewById(R.id.editAuthor);
        editYear = v.findViewById(R.id.editYear);
        editAddressBought = v.findViewById(R.id.editAddressBought);
        btnUpdate = v.findViewById(R.id.btnUpdate);
        btnDelete = v.findViewById(R.id.btnDelete);

        if (getArguments() == null) {
            return v;
        }

        Book book = (Book) getArguments().getSerializable("book");
        Id = "" + book.getId();
        editName.setText(book.getName());
        editAuthor.setText(book.getAuthor());
        editYear.setText(book.getYear());
        editAddressBought.setText(book.getAddressBought());

        v.findViewById(R.id.btnShowMap).setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("addressBought", editAddressBought.getText().toString());
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtras(b);

            startActivity(intent, b);
        });

        btnDelete.setOnClickListener(v1 -> {
            Thread thread = new Thread(() -> {
                BookDatabase.getInstance(getContext())
                        .bookDao().deleteBook(book.getId());

                getActivity().runOnUiThread(() -> {
                    bookDeleted.bookDeleted(book.getId());
                });
            });
            thread.start();
        });

        btnUpdate.setOnClickListener(v1 -> {
            String name = editName.getText().toString().trim();
            String author = editAuthor.getText().toString().trim();
            String year = editYear.getText().toString().trim();
            String address = editAddressBought.getText().toString().trim();

            if (name.isEmpty() || author.isEmpty()) {
                Toast.makeText(getContext(), "Данните за име и автор не може да бъдат  празни.", Toast.LENGTH_LONG).show();
                return;
            }

            Thread thread = new Thread(() -> {
                BookDatabase.getInstance(getContext())
                        .bookDao()
                        .updateBook(book.getId(),
                                name,
                                author,
                                year,
                                address);

                getActivity().runOnUiThread(() -> {
                    bookUpdated.bookUpdated(book.getId());
                });
            });
            thread.start();
        });
        return v;
    }
}