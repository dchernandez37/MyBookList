package com.davidhernandez.mybooklist.services;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.davidhernandez.mybooklist.R;
import com.davidhernandez.mybooklist.domain.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookSvcRESTImpl implements IBookSvc {
//    final String mURL = "https://mybooklist-ce238.firebaseio.com/.json";  // old firebase
    final String mURL = "https://mybooklist-63e8c.firebaseio.com/books.json";   // new firebase

    private List<Book> mBooks;
    private List<String> bookList;

    private View mView;


    /* ================================= constructor =================================*/
    public BookSvcRESTImpl(View view) {
        mView = view;
        mBooks = new ArrayList<>();
        bookList = new ArrayList<>();
    }

    /* =============================== method overrides ==============================*/
    @Override
    public Book create(Book book) {
        // create a connection
        HttpURLConnection urlConnection = null;
        Log.i("Create Book", "Trying to create a book");

        try{
            Log.i("Create Book", "Creating book");

            // build json object
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("title", book.getTitle());
            jsonObject.accumulate("author", book.getAuthor());
            jsonObject.accumulate("currpage", book.getCurrPage());
            jsonObject.accumulate("pages", book.getPages());
            jsonObject.accumulate("isbn", book.getISBN());

            Log.i("Create Book", "Creating book jsonObject: " + jsonObject.toString());

            // create a connection and open it
            URL url = new URL(mURL);
            urlConnection = (HttpURLConnection)url.openConnection();

            // set connection to POST
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");

            Log.i("Create Book", "Response code: " + urlConnection.getResponseCode());

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(outputStream, "UTF-8");

            osw.write(jsonObject.toString());
            osw.flush();
            osw.close();

            Log.i("Create Book", "Response code: " + urlConnection.getResponseCode());

        } catch (Exception e) {
            Log.i("BookSvcRESTImpl", "Exception: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return book;
    }

    @Override
    public List<Book> retrieveAllBooks() {
        new HttpAsyncTask().execute(mURL);
        Log.i("RetrieveAllBooks", "Dave books: Checking your books");
        return mBooks;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book delete(Book book) {
        return null;
    }



    /* ====================== private HttpAsyncTask.java class ========================== */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private final String TAG = HttpAsyncTask.class.getSimpleName();

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String s;
                while((s = reader.readLine()) != null) {
                    result.append(s);
                }

            }catch (IOException e) {
                Log.i(TAG, "Exception: " + e.getMessage());
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "entering onPostExecute.");
            System.out.println("String Result: " + s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                int length = jsonArray.length();
                Log.i(TAG, "Number of entries: " + length);

                for(int i = 0; i < length; i++) {
                    // get the json object
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // log the id and title
                    Log.i(TAG, jsonObject.getString("title"));

                    // get the items
                    mBooks.add(new Book(
                            jsonObject.getString("title"),
                            jsonObject.getString("author"),
                            jsonObject.getString("currpage"),
                            jsonObject.getString("pages"),
                            jsonObject.getString("isbn"))
                    );
                }

                // add book titles to list for bookList Display
                for(Book book : mBooks) {
                    bookList.add(book.getTitle());
                }

                // create array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mView.getContext(), android.R.layout.simple_list_item_1, bookList);

                // initialize ListView
                if (mView != null) {
                    ListView bookListView = (ListView) mView.findViewById(R.id.list_of_books);
                    bookListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i(TAG, "exiting onPostExecute.");
            super.onPostExecute(s);
        }
    }
}
