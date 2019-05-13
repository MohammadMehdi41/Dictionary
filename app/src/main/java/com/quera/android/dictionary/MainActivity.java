package com.quera.android.dictionary;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{


    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText word_text = (EditText) findViewById(R.id.edittext_word);
        final EditText definition_text = (EditText) findViewById(R.id.edittext_definition);
        final EditText search_text = (EditText) findViewById(R.id.search_edittext);
        final TextView answer_text = (TextView) findViewById(R.id.answer_textview);
        Button add_button = (Button)findViewById(R.id.add_btn);
        Button update_button = (Button)findViewById(R.id.update_btn);
        Button delete_button = (Button)findViewById(R.id.delete_btn);
        Button view_all_button = (Button)findViewById(R.id.viewAll_btn);
        Button quiz_button = (Button)findViewById(R.id.quiz_btn);
        Button search_button = (Button)findViewById(R.id.search_btn);

        final SevenLearnDatabaseOpenHelper openHelper=new SevenLearnDatabaseOpenHelper(MainActivity.this);



        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String main_word = word_text.getText().toString();

                String definition = definition_text.getText().toString();

                definition = definition.trim();


                if(!definition.isEmpty() && definition!="" && definition!=" "  && definition!="  "  && definition!="   "  && definition!="     "
                        && definition!="        "  && definition!="         "){
                    openHelper.addPosts(main_word,definition);
                }
            }
        });


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String main_word = word_text.getText().toString();
                String definition = definition_text.getText().toString();


                openHelper.deletePost(main_word);
            }
        });


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main_word = word_text.getText().toString();
                String definition = definition_text.getText().toString();


                openHelper.updatePost(main_word , definition);
            }
        });

        view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DictionaryPage.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        quiz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent myIntent = new Intent(MainActivity.this, QuizActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String main_word = search_text.getText().toString();


                Phrase phrase = new Phrase();
                phrase = openHelper.searchPost(main_word);
                if (phrase==null){
                    answer_text.setText("word doesn't exist");
                }
                else {
                    answer_text.setText(phrase.definition);
                }

            }
        });

    }

    public static class SevenLearnDatabaseOpenHelper extends SQLiteOpenHelper {

        private static final String TAG = "SevenLearnDatabaseOpen";


        private static final String DATABASE_NAME="wordlist.db";
        private static final int DATABASE_VERSION=1;

        private static final String POST_TABLE_NAME="list";


        public static final String COL_ID="ColumnId";
        public static final String COL_DEFINITION="Definition";
        public static final String COL_WORD="Word";





        private static final String SQL_COMMAND_CREATE_POST_TABLE="CREATE TABLE IF NOT EXISTS "+POST_TABLE_NAME+"("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_DEFINITION+" TEXT, "+
                COL_WORD+" TEXT); ";

        private Context context;

        public SevenLearnDatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(SQL_COMMAND_CREATE_POST_TABLE);
            }catch (SQLException e){
                Log.e(TAG, "onCreate: "+e.toString() );
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }




        public boolean addPost(String main_word , String definition){
            ContentValues cv=new ContentValues();
            cv.put(COL_WORD,main_word);
            cv.put(COL_DEFINITION,definition);



            Log.e(TAG, "addPost: "+main_word);
            Log.e(TAG, "addPost: "+definition);

            SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
            long isInserted=sqLiteDatabase.insert(POST_TABLE_NAME,null,cv);

            Log.i(TAG, "addPost: "+isInserted);

            if (isInserted>0){
                return true;
            }else{
                return false;
            }
        }

        public void addPosts(String main_word , String definition){

            if(!checkPostExists(main_word)){
                addPost(main_word, definition);
            }

        }

        public List<Phrase> getPosts(){
            List<Phrase> phrases=new ArrayList<>();

            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+POST_TABLE_NAME,null);
            cursor.moveToFirst();
            if (cursor.getCount()>0){
                while (!cursor.isAfterLast()){
                    Phrase phrase=new Phrase();
                    phrase.setId(cursor.getInt(0));
                    phrase.setDefinition(cursor.getString(1));
                    phrase.setWord(cursor.getString(2));

                    phrases.add(phrase);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            sqLiteDatabase.close();
            return phrases;
        }




        private boolean checkPostExists(String word){
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "
                    +POST_TABLE_NAME
                    +" WHERE "
                    +COL_WORD
                    +" = ?",new String[]{word});
            return cursor.moveToFirst();
        }

        private void deletePost(String word){
            SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
            sqLiteDatabase.delete(POST_TABLE_NAME,COL_WORD+" = ?",new String[]{String.valueOf(word)});
        }

        private Phrase searchPost(String main_word){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "
                    +POST_TABLE_NAME
                    +" WHERE "
                    +COL_WORD
                    +" = ?",new String[]{main_word});

            Phrase phrase = new Phrase();
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                phrase.setId(Integer.parseInt(cursor.getString(0)));
                phrase.setDefinition(cursor.getString(1));
                cursor.close();
            } else {
                phrase = null;
            }
            cursor.close();
            return phrase;

        }


        public int updatePost(String main_word , String definition) {


            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_DEFINITION, definition);



            if(checkPostExists(main_word)){
                return db.update(POST_TABLE_NAME, values, COL_WORD+ " = ?",
                        new String[]{String.valueOf(main_word)});

            }
            else {
                return 0;
            }
        }

        public int getPostCount() {
            String countQuery = "SELECT  * FROM " + POST_TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }


    }



    public static class Phrase {
        private String word;
        private String definition;
        private int id;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}



