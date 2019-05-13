package com.quera.android.dictionary.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quera.android.dictionary.MainActivity;
import com.quera.android.dictionary.R;
import com.quera.android.dictionary.ViewHolders.WordListViewHolder;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListViewHolder> {


    private Context context;
    private List<MainActivity.Phrase> phrases;

    public WordListAdapter(Context context, List<MainActivity.Phrase> phrases) {
        this.context = context;
        this.phrases = phrases;
    }


    @Override
    public WordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new WordListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(WordListViewHolder holder, int position) {
        MainActivity.Phrase phrase = phrases.get(position);


        holder.setWord(phrase.getWord());
        holder.setDefinition(phrase.getDefinition());
        holder.setId((String.valueOf(phrase.getId())));


    }

    @Override
    public int getItemCount() {
        return phrases.size();
    }




}

