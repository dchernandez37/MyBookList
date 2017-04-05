package com.davidhernandez.mybooklist;


import android.app.Application;
import android.support.test.filters.MediumTest;
import android.test.ApplicationTestCase;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSVC;

public class BookSVCTest extends ApplicationTestCase<Application> {

    public BookSVCTest() {
        super(Application.class);
    }

    @MediumTest
    public void testWriteObjectAndReadItBack() {
        BookSVC bookSVC = new BookSVC(null);

        bookSVC.addBook(new Book("C++ How To Program", "Paul Deitel", "20", "800", "978-0-13-266236-9"));

    }
}
