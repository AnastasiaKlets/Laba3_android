package com.example.laba3.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laba3.R;
import com.example.laba3.model.Author;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_AUTHOR = "author_param";

    // TODO: Rename and change types of parameters
    private Author author;

    public BookInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param author Instance of author.
     * @return A new instance of fragment BookInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookInfoFragment newInstance(Author author) {
        BookInfoFragment fragment = new BookInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            author = (Author) getArguments().getSerializable(ARG_AUTHOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_book_info, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (author != null){
            ((TextView)view.findViewById(R.id.authorView)).setText(author.getName());
            ((TextView)view.findViewById(R.id.bookNameView)).setText(author.getBook());
            ((TextView)view.findViewById(R.id.publisherView)).setText(author.getPublishingHouse());
            ((TextView)view.findViewById(R.id.publishYearView)).setText(String.format("%d", author.getYearOfPublishing()));
            ((TextView)view.findViewById(R.id.pagesCountView)).setText(String.format("%d", author.getNumberOfPages()));
            ((TextView)view.findViewById(R.id.priceView)).setText(String.format("%.2f", author.getPrice()));
            ((TextView)view.findViewById(R.id.bindingTypeView)).setText(author.getBinding());

        }
    }


}