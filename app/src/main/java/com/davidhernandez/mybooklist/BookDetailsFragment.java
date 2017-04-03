package com.davidhernandez.mybooklist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookDetailsFragment extends Fragment {

    public BookDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set the view
        View rootView = inflater.inflate(R.layout.fragment_book_details, container, false);

        // grab the intent
        Intent displayBookInfo = getActivity().getIntent();

        Map<String, Integer> views = new HashMap<>();
        views.put("book", R.id.textView_Book);

        // update Book Title
        TextView bookTextView = (TextView) rootView.findViewById(R.id.textView_Book);
        bookTextView.setText(displayBookInfo.getStringExtra("book"));

        // update Current Page
        TextView currPageTextView = (TextView) rootView.findViewById(R.id.textView_CurrPage);
        currPageTextView.setText(displayBookInfo.getStringExtra("currPage"));

        // update Author
        TextView textView_Author = (TextView) rootView.findViewById(R.id.textView_Author);
        textView_Author.setText(displayBookInfo.getStringExtra("author"));

        // update Pages
        TextView textView_Pages = (TextView) rootView.findViewById(R.id.textView_Pages);
        textView_Pages.setText(displayBookInfo.getStringExtra("numPages"));

        // update ISBN
        TextView textView_ISBN = (TextView) rootView.findViewById(R.id.textView_ISBN);
        textView_ISBN.setText(displayBookInfo.getStringExtra("isbn"));

        return rootView;
    }
}
