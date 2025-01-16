package com.example.bookapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookapp.database.BookDatabase;
import com.example.bookapp.models.Book;

public class InsertFragment extends Fragment {

    protected EditText editName;
    protected EditText editAuthor;
    protected EditText editYear;
    protected EditText editAddressBought;
    protected Button btnInsert;
    OnBookInserted bookInserted;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bookInserted = (OnBookInserted) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_insert, container, false);
        editName = v.findViewById(R.id.editName);
        editAuthor = v.findViewById(R.id.editAuthor);
        editYear = v.findViewById(R.id.editYear);
        editAddressBought = v.findViewById(R.id.editAddressBought);
        btnInsert = v.findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(view -> {
            String name = editName.getText().toString();
            String author = editAuthor.getText().toString();
            if (name.isEmpty() || author.isEmpty()) {
                Toast.makeText(getContext(), "Попълни данни", Toast.LENGTH_LONG)
                        .show();
                return;
            }

            Thread t = new Thread(() -> {
                Book b = new Book();
                b.setAddressBought(editAddressBought.getText().toString());
                b.setName(name);
                b.setAuthor(author);
                b.setYear(editYear.getText().toString());

                BookDatabase.getInstance(getContext()).bookDao().insertBook(b);

                Log.d("BookInsertion", "Inserted book: " + b.getName());

                getActivity().runOnUiThread(() -> {
                    editName.setText("");
                    editAddressBought.setText("");
                    editYear.setText("");
                    editAuthor.setText("");
                    bookInserted.bookInserted(b);
                });
            });
            t.start();
        });
        return v;
    }
}
