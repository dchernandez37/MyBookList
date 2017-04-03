package com.davidhernandez.mybooklist.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.davidhernandez.mybooklist.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BookSvcSQLiteImpl extends SQLiteOpenHelper implements IBookSvc {
    private static final String DBNAME = "books.db";
    private static final int    DBVERSION = 1;
    private String createBookTable =
            "CREATE TABLE books" +
            "(" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    title TEXT NOT NULL," +
            "    author TEXT," +
            "    currpage TEXT," +
            "    pages TEXT," +
            "    isbn TEXT" +
            ")";

    public BookSvcSQLiteImpl(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createBookTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books");
        onCreate(sqLiteDatabase);
    }

    @Override
    public Book create(Book book) {
        // get the database object
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // create object to hold row values
        ContentValues values = new ContentValues();

        // add values for rows
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("currpage", book.getCurrPage());
        values.put("pages", book.getPages());
        values.put("isbn", book.getISBN());

        long rowIDOfInsertedRecord = sqLiteDatabase.insert("books", null, values);
        sqLiteDatabase.close();

        return book;
    }

    @Override
    public List<Book> retrieveAllBooks() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("books", new String[] {"id", "title", "author", "currpage", "pages", "isbn"}, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Book book = getBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return books;
    }

    private Book getBook(Cursor cursor) {
        Book book = new Book();
//        book.setID((UUID.fromString(cursor.getString(0))));
        book.setID((cursor.getString(0)));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));
        book.setCurrPage(cursor.getString(3));
        book.setPages(cursor.getString(4));
        book.setISBN(cursor.getString(5));
        return book;
    }

    @Override
    public Book update(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        // add values for row update
        values.put("id", "" + book.getID());
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("currpage", book.getCurrPage());
        values.put("pages", book.getPages());
        values.put("isbn", book.getISBN());

        int numberOfRowsUpdate = sqLiteDatabase.update("books", values, "id = ?", new String[] {String.valueOf(book.getID())});
        sqLiteDatabase.close();
        return book;
    }

    @Override
    public Book delete(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int rowsDeleted = sqLiteDatabase.delete("books", "id = ?", new String[] {String.valueOf(book.getID())});
        sqLiteDatabase.close();
        return null;
    }
}
