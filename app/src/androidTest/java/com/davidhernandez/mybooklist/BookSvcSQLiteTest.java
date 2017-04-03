package com.davidhernandez.mybooklist;


import android.app.Application;
import android.support.test.filters.MediumTest;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSvcSQLiteImpl;

import java.util.List;

public class BookSvcSQLiteTest extends ApplicationTestCase<Application> {
    public BookSvcSQLiteTest() {
        super(Application.class);
    }

    @MediumTest
    public void testWriteObjectAndReadItBack() {
        BookSvcSQLiteImpl bookSvcSQLite = new BookSvcSQLiteImpl(this.getContext());

        // Add some books
        bookSvcSQLite.create(new Book("C++ How To Program", "Paul Deitel", "20", "800", "978-0-13-266236-9"));
        bookSvcSQLite.create(new Book("C++ Without Fear", "Brian Overland", "7", "624", "978-0-13-431430-3"));
        bookSvcSQLite.create(new Book("Core Java Volume I--Fundamentals", "Cay S Horstmann", "200", "1040", "978-0-13-417730-4"));
        bookSvcSQLite.create(new Book("Java How To Program", "Paul Deitel", "325", "1198", "978-81-203-5064-9"));
        bookSvcSQLite.create(new Book("Some Title", "Some Guy", "200", "1040", "978-0-13-417730-2"));   // this one gets deleted in test
        bookSvcSQLite.create(new Book("Some other Title", "Some other Guy", "200", "1040", "978-0-13-417730-2"));


        BookSvcSQLiteImpl bookSvcSQLiteSecondInstance = new BookSvcSQLiteImpl(this.getContext());
        List<Book> bookList = bookSvcSQLiteSecondInstance.retrieveAllBooks();

        // clear the list if needed for testing
//        while (!bookList.isEmpty()) {
//            bookSvcSQLiteSecondInstance.delete(bookList.get(0));
//            bookList.remove(0);
//        }

        // test getting a book and updating it
        bookList.get(3).setTitle("New Updated Random Title");
        bookSvcSQLiteSecondInstance.update(bookList.get(3));

        // test deleting a book
        bookSvcSQLiteSecondInstance.delete(bookList.get(4));

        for(Book b : bookList) {
            Log.d("BookSvcSioImplTest: ", b.getTitle() + " " + b.getAuthor() + " " + b.getCurrPage() + " " + b.getPages() + " " + b.getISBN());
        }
    }
}
