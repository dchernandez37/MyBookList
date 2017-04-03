package com.davidhernandez.mybooklist;

import android.content.Intent;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSVC;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ListView bookListView;

    private List<Book> mBooks = new ArrayList<>();
    private List<String> bookList = new ArrayList<>();


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // store inflated view in rootView
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // initialize Volley service
//        BookSvcVolleyImpl bookSvcVolley = new BookSvcVolleyImpl(rootView);
//        mBooks = bookSvcVolley.retrieveAllBooks();

        BookSVC bookSVC = new BookSVC(rootView);
        Intent intent = BookSVC.newIntent(getContext());
        getActivity().startService(intent);
        mBooks = bookSVC.getBooks();


        // initialize ListView
        bookListView = (ListView) rootView.findViewById(R.id.list_of_books);

        // create array adapter
        ArrayAdapter<String> bookListArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, bookList);

        // set the adapter
        bookListView.setAdapter(bookListArrayAdapter);


        // create click/tap handler
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an intent for the bookList Detail View
                Intent bookDetailView = new Intent(view.getContext(), BookDetails.class);

                // put string extras
                bookDetailView.putExtra("book",     mBooks.get(position).getTitle());
                bookDetailView.putExtra("author",   mBooks.get(position).getAuthor());
                bookDetailView.putExtra("currPage", mBooks.get(position).getCurrPage());
                bookDetailView.putExtra("numPages", mBooks.get(position).getPages());
                bookDetailView.putExtra("isbn",     mBooks.get(position).getISBN());

                startActivity(bookDetailView);
            }
        });
        return rootView;
    }

//    private ListView mBookListView;
//    private BookAdapter mAdapter;
//    private List<Book> mBooks = new ArrayList<>();
//
//    public MainActivityFragment() {
//
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//
//        // new getStudentTask()
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        updateUI();
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_main, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//
////        switch (item.getItemId()) {
////            case R.id.hide_action_bar:
////                return true;
////            case R.id.action_settings:
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
//    }
//
//    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.fragment_main, parent, false));
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }
}
