package com.davidhernandez.mybooklist;


import android.app.Application;
import android.support.test.filters.MediumTest;
import android.test.ApplicationTestCase;
import android.view.View;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSVC;

public class BookSVCTest extends ApplicationTestCase<Application> {

    public BookSVCTest() {
        super(Application.class);
    }

    @MediumTest
    public void testWriteObjectAndReadItBack() {
        View view = new View(getContext());
        BookSVC bookSVC = new BookSVC(view);

        bookSVC.addBook(new Book("Some other Title", "Some other Guy", "200", "1040", "978-0-13-417730-2"));
        bookSVC.addBook(new Book("C++ How To Program", "Paul Deitel", "20", "800", "978-0-13-266236-9"));
    }
}
