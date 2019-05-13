package com.quera.android.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.quera.android.dictionary.adapters.WordListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DictionaryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_page);


        List<MainActivity.Phrase> phrases= new ArrayList<>();

        MainActivity.SevenLearnDatabaseOpenHelper openHelper = new MainActivity.SevenLearnDatabaseOpenHelper(DictionaryPage.this);
        phrases = openHelper.getPosts();

        Collections.reverse(phrases);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler_view);
        WordListAdapter newsAdapter=new WordListAdapter(this,phrases);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);
    }
}
