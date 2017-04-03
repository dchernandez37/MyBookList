package com.davidhernandez.mybooklist.domain;


import java.util.UUID;

public class Book implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String mID;
    private String mTitle;
    private String mAuthor;
    private String mCurrPage;
    private String mPages;
    private String mISBN;

    public Book(String title, String author, String currPage, String pages, String ISBN, String uuid) {
        mID = uuid;
        mTitle = title;
        mAuthor = author;
        mCurrPage = currPage;
        mPages = pages;
        mISBN = ISBN;
    }

    public Book(String title, String author, String currPage, String pages, String ISBN) {
        this(title, author, currPage, pages, ISBN, UUID.randomUUID().toString());
    }

    public Book(String title, String author, String pages, String ISBN) {
        this(title, author, "1", pages, ISBN);
    }

    public Book() {
        this("", "", "", "", "");
    }

    public void setID(String value) {
        mID = value;
    }

    public String getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getCurrPage() {
        return mCurrPage;
    }

    public void setCurrPage(String currPage) {
        mCurrPage = currPage;
    }

    public String getPages() {
        return mPages;
    }

    public void setPages(String pages) {
        mPages = pages;
    }

    public String getISBN() {
        return mISBN;
    }

    public void setISBN(String ISBN) {
        mISBN = ISBN;
    }
}
