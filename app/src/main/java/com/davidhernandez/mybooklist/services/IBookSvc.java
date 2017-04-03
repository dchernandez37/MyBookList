package com.davidhernandez.mybooklist.services;


import com.davidhernandez.mybooklist.domain.Book;

import java.util.List;

public interface IBookSvc {
    public Book create(Book book);
    public List<Book> retrieveAllBooks();
    public Book update(Book book);
    public Book delete(Book book);
}
