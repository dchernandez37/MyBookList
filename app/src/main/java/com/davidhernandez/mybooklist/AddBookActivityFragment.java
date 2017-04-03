package com.davidhernandez.mybooklist;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.davidhernandez.mybooklist.domain.Book;
import com.davidhernandez.mybooklist.services.BookSvcRESTImpl;
import com.davidhernandez.mybooklist.services.BookSvcSQLiteImpl;
import com.davidhernandez.mybooklist.services.BookSvcVolleyImpl;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddBookActivityFragment extends Fragment {
    private EditText mTitleEditText;
    private EditText mAuthorEditText;
    private EditText mCurrPageEditText;
    private EditText mPagesEditText;
    private EditText mISBNEditText;
    private Button mAddBookButton;

    public AddBookActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Title EditText =================================================
        mTitleEditText = (EditText) view.findViewById(R.id.titleEditText);

        // Author EditText ================================================
        mAuthorEditText = (EditText) view.findViewById(R.id.authorEditText);

        // Curr Page EditText ============================================
        mCurrPageEditText = (EditText) view.findViewById(R.id.currEditText);

        // Curr Page EditText ============================================
        mPagesEditText = (EditText) view.findViewById(R.id.pagesEditText);

        // Curr Page EditText ============================================
        mISBNEditText = (EditText) view.findViewById(R.id.isbnEditText);



        // Add Book Button ================================================
        mAddBookButton = (Button) view.findViewById(R.id.AddBookButton);
        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTitleEditText.getText().length() != 0 && mAuthorEditText.getText().length() != 0 &&
                        mCurrPageEditText.getText().length() != 0 && mPagesEditText.getText().length() != 0 && mISBNEditText.getText().length() != 0) {

//                    BookSvcRESTImpl bookSvcREST = new BookSvcRESTImpl(view);
//                    bookSvcREST.create(new Book(mTitleEditText.getText().toString(), mAuthorEditText.getText().toString(),
//                            mCurrPageEditText.getText().toString(), mPagesEditText.getText().toString(), mISBNEditText.getText().toString()));

                    BookSvcVolleyImpl bookSvcVolley = new BookSvcVolleyImpl(view);
                    bookSvcVolley.create(new Book(mTitleEditText.getText().toString(), mAuthorEditText.getText().toString(),
                            mCurrPageEditText.getText().toString(), mPagesEditText.getText().toString(), mISBNEditText.getText().toString()));

                    // send toast message that book has been added
                    Toast.makeText(getActivity(), (mTitleEditText.getText() + " added to list."), Toast.LENGTH_SHORT).show();

                    // navigate back to Main Activity
                    NavUtils.navigateUpFromSameTask(AddBookActivityFragment.super.getActivity());
                }
                else {
                    // create a new book and add to database
                    Toast.makeText(getActivity(), ("Please fill in all data"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
