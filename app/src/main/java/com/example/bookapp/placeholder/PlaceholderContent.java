package com.example.bookapp.placeholder;

import com.example.bookapp.models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlaceholderContent {

    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class PlaceholderItem {
        public final String id;

        public final String name;

        public final String addressBought;

        public final String author;

        public final String year;

        public PlaceholderItem(Book b) {
            this.id = ""+b.getId(); this.name = b.getName();
            this.addressBought = b.getAddressBought(); this.author = b.getAuthor();
            this.year = b.getYear();

        }

        @Override
        public String toString() {
            return this.name + "/t" + this.author;
        }
    }
}