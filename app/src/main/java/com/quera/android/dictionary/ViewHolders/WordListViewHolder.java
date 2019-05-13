package com.quera.android.dictionary.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.quera.android.dictionary.R;

public class WordListViewHolder extends RecyclerView.ViewHolder {



    private String id;
    private String word;
    private String definition;

    private TextView id_text;
    private TextView word_text;
    private TextView definition_text;

    public WordListViewHolder(View itemView) {
        super(itemView);
        definition_text=(TextView) itemView.findViewById(R.id.column_definition_item);
        word_text=(TextView)itemView.findViewById(R.id.column_word_item);
        id_text=(TextView)itemView.findViewById(R.id.column_id_item);

    }


    public void setId(String id) {
        this.id_text.setText(id);
    }


    public void setWord(String word) {
        this.word_text.setText(word);
    }

    public void setDefinition(String definition) {
        this.definition_text.setText(definition);
    }


    public String getIdText(){
        return id_text.getText().toString();
    }

    public String getWordText(){
        return word_text.getText().toString();
    }

    public String getDefinitionText(){
        return definition_text.getText().toString();
    }
}
