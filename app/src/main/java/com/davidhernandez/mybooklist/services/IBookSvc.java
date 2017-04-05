package com.davidhernandez.mybooklist.services;


import com.davidhernandez.mybooklist.domain.Book;

import java.util.List;

public interface IBookSvc {
    Book create(Book book);
    List<Book> retrieveAllBooks();
    Book update(Book book);
    Book delete(Book book);
}
