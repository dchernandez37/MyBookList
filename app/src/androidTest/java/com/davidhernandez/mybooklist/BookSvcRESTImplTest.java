package com.davidhernandez.mybooklist;


import android.app.Application;
import android.support.test.filters.MediumTest;
import android.test.ApplicationTestCase;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSvcRESTImpl;

public class BookSvcRESTImplTest extends ApplicationTestCase<Application> {
    public BookSvcRESTImplTest() {
        super(Application.class);
    }

    @MediumTest
    public void testWriteObjectAndReadItBack() {
        BookSvcRESTImpl bookSvcREST = new BookSvcRESTImpl(null);

        // Add some books
        bookSvcREST.create(new Book("C++ How To Program", "Paul Deitel", "20", "800", "978-0-13-266236-9"));
        bookSvcREST.create(new Book("C++ Without Fear", "Brian Overland", "7", "624", "978-0-13-431430-3"));
        bookSvcREST.create(new Book("Core Java Volume I--Fundamentals", "Cay S Horstmann", "200", "1040", "978-0-13-417730-4"));
        bookSvcREST.create(new Book("Java How To Program", "Paul Deitel", "325", "1198", "978-81-203-5064-9"));
        bookSvcREST.create(new Book("Some Title", "Some Guy", "200", "1040", "978-0-13-417730-2"));   // this one gets deleted in test
        bookSvcREST.create(new Book("Some other Title", "Some other Guy", "200", "1040", "978-0-13-417730-2"));
    }
}
