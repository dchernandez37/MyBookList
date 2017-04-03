package com.davidhernandez.mybooklist.services;


import android.content.Context;
import android.util.Log;

import com.davidhernandez.mybooklist.domain.Book;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookSvcSioImpl implements IBookSvc {
    private final String TAG = BookSvcSioImpl.class.getSimpleName();
    private final String mFileName = "savedBooks.txt";
    private List<Book> mBooks;
    private Context mAppContext;

    public BookSvcSioImpl(Context context) {
        mBooks = new ArrayList<>();
        mAppContext = context;
        readFile();
    }

    @Override
    public Book create(Book book) {
        mBooks.add(book);
        writeFile();
        return book;
    }

    @Override
    public List<Book> retrieveAllBooks() {
        return mBooks;
    }

    @Override
    public Book update(Book book) {
        // find the book to delete
        int index = findBook(book);

        // update the book
        if(index != -1)
            mBooks.set(index, book);

        return book;
    }

    @Override
    public Book delete(Book book) {
        // find the book to delete
        int index = findBook(book);

        // delete the book from the list
        if(index != -1)
            mBooks.remove(index);

        return book;
    }


    // read from file
    private void readFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(mAppContext.openFileInput(mFileName));
            mBooks = (List<Book>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    // write to file
    private void writeFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mAppContext.openFileOutput(mFileName, Context.MODE_PRIVATE));
            oos.writeObject(mBooks);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    // get index
    private int findBook(Book book) {
        // search for book by title
        for(int i = 0; i < mBooks.size(); i++) {
            if(book.getTitle().equals(mBooks.get(i).getTitle())) {
                return i;
            }
        }

        // return -1 if not found
        return -1;
    }
}
