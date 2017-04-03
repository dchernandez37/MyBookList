package com.davidhernandez.mybooklist;


import android.app.Application;
import android.support.test.filters.MediumTest;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSvcSioImpl;

import java.util.List;

public class BookSvcSioImplTest extends ApplicationTestCase<Application> {

    public BookSvcSioImplTest() {
        super(Application.class);
    }

    @MediumTest
    public void testWriteObjectAndReadItBack() {
        BookSvcSioImpl bookSvcSio = new BookSvcSioImpl(this.getContext());

        Book book1 = new Book("Test Title for Book", "Some Author", "1", "200", "978-0-13-266236-9");
        bookSvcSio.create(book1);

        BookSvcSioImpl bookSvcSioSecondInstance = new BookSvcSioImpl(this.getContext());
        List<Book> bookList = bookSvcSioSecondInstance.retrieveAllBooks();

        for(Book b : bookList) {
            Log.d("BookSvcSioImplTest: ", b.getTitle() + " " + b.getAuthor() + " " + b.getCurrPage() + " " + b.getPages() + " " + b.getISBN());
        }
    }
}
