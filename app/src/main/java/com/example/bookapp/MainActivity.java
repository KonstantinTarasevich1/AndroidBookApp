package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.Button;

import com.example.bookapp.SearchOnline.SearchOnlineFragment;
import com.example.bookapp.models.Book;

public class MainActivity extends AppCompatActivity
        implements OnBookInserted, onBookDeleted, onBookUpdated {

    ListViewFragment listViewFragment;

    public void loadFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            listViewFragment = new ListViewFragment();
            loadFragment(new InsertFragment(), R.id.frame_insert_container);
            loadFragment(listViewFragment, R.id.frame_view_container);
        }

        Button searchOnlineButton = findViewById(R.id.btn_search_online);
        searchOnlineButton.setOnClickListener(v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_view_container, new SearchOnlineFragment())
                .addToBackStack(null)
                .commit());
    }

    @Override
    public void bookInserted(Book b) {
        if (listViewFragment != null)
            listViewFragment.reloadData();
    }

    @Override
    public void bookDeleted(int id) {
        if (listViewFragment != null)
            listViewFragment.reloadData();
    }

    @Override
    public void bookUpdated(int id) {
        if (listViewFragment != null)
            listViewFragment.reloadData();
    }
}
