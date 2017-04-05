package com.davidhernandez.mybooklist.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidhernandez.mybooklist.R;
import com.davidhernandez.mybooklist.domain.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class BookSVC extends Service {
    /* Class constants ============================================= */
    private static final String TAG = "BookService";
    private static final String FIREBASE_URL = "https://mybooklist-63e8c.firebaseio.com/books.json";   // new firebase
    private static final String API_KEY = "";

    /* singleton pattern =========================================== */
    private static BookSVC sBookSVC;
    private List<Book> mBooks = new ArrayList<>();
    private View mView;

    private List<String> mTitles = new ArrayList<>();

    /* constructors ================================================ */
    public BookSVC(View view) {
        super();
        mView = view;
    }

    public BookSVC() {
        this(null);
    }


    /* implement IntentService methods ============================= */
    public static Intent newIntent(Context context) {
        return new Intent(context, BookSVC.class);
    }

    public void broadcastIntent(Book book) {
        Intent intent = new Intent();
        intent.putExtra("book",     book.getTitle());
        intent.putExtra("author",   book.getAuthor());
        intent.putExtra("currPage", book.getCurrPage());
        intent.putExtra("numPages", book.getPages());
        intent.putExtra("isbn",     book.getISBN());
        intent.putExtra("id",       book.getID());

        intent.setAction("com.davidhernandez.mybooklist.MainActivityFragment");
        sendBroadcast(intent);
    }


    /* BookSVC main Methods ======================================== */
    public List<Book> getBooks() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, FIREBASE_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Request response: " + response);
                try {
                    parseBooks(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // add book titles to list for bookList Display
                for(Book book : mBooks) {
                    mTitles.add(book.getTitle());
                    System.out.println("Making sure the titles are good: " + book.getTitle());
                    System.out.println("Checking ID: " + book.getID());

                    // send broadcast
//                    broadcastIntent(book);
                }

                // set the adapter
                if (mView != null) {
                    ArrayAdapter<String> bookListArrayAdapter = new ArrayAdapter<>(mView.getContext(), android.R.layout.simple_list_item_1, mTitles);
                    ListView bookListView = (ListView) mView.findViewById(R.id.list_of_books);
                    bookListView.setAdapter(bookListArrayAdapter);
                    bookListArrayAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(mView.getContext()).add(request);


        return mBooks;
    }

    public void addBook(Book book) {
        JSONObject jsonBook = new JSONObject();

        try {
            // package book as json
            jsonBook.accumulate("title", book.getTitle());
            jsonBook.accumulate("author", book.getAuthor());
            jsonBook.accumulate("currpage", book.getCurrPage());
            jsonBook.accumulate("pages", book.getPages());
            jsonBook.accumulate("isbn", book.getISBN());
            jsonBook.accumulate("id", book.getID());

            System.out.println("Trying to add a new book: " + book.getTitle());

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, FIREBASE_URL, jsonBook,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                System.out.printf("New Book: %s", response.getString("title"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("title", "some book");
                    params.put("author", "name");
                    params.put("currpage", "1");
                    params.put("pages", "300");
                    params.put("isbn", "123456789");
                    params.put("id", "some id");
                    return params;
                }
            };

            Volley.newRequestQueue(mView.getContext()).add(postRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mBooks.add(book);
    }

    public Book getBook(UUID id) {
        for(Book book : mBooks) {
            if(book.getID().equals(id))
                return book;
        }
        return null;
    }

    /* BookSVC helper Methods ======================================== */
    private void parseBooks(JSONObject result) throws JSONException {
        JSONArray jsonArray = new JSONArray(result.names().toString());
        int length = jsonArray.length();

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            String name = jsonArray.getString(i);
            JSONObject jsonObject = result.getJSONObject(name);

            mBooks.add(new Book(
                    jsonObject.getString("title"),
                    jsonObject.getString("author"),
                    jsonObject.getString("currpage"),
                    jsonObject.getString("pages"),
                    jsonObject.getString("isbn"),
                    jsonObject.getString("id")
            ));
        }
    }

    /* Internal Binder class ======================================== */
    public class BookBinder extends Binder {
        BookSVC getService() {
            return BookSVC.this;
        }
    }

    private final IBinder mBinder = new BookBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
