package com.quera.android.dictionary;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class QuizActivity extends AppCompatActivity {



    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    List<MainActivity.Phrase> phrases = new ArrayList<>();





    MainActivity.SevenLearnDatabaseOpenHelper openHelper = new MainActivity.SevenLearnDatabaseOpenHelper(QuizActivity.this);
    phrases = openHelper.getPosts();


        if (phrases.size() >= 4) {

            hamdler(phrases);



    }


}

    public void hamdler(List <MainActivity.Phrase> phrases1){

        List<MainActivity.Phrase> phrases = phrases1;
        final Handler handler = new Handler();

        final List<MainActivity.Phrase> finalPhrases = phrases;
        final Runnable refresh = new Runnable() {
            @Override
            public void run() {
                makeQuiz(finalPhrases);
            }
        };
        handler.postDelayed(refresh, 8* 1000);


    }
    public void makeQuiz(final List<MainActivity.Phrase> phrases1 ){



        TextView question_text = (TextView) findViewById(R.id.quiz_question);
        TextView choice_one_text = (TextView) findViewById(R.id.choice_one);
        TextView choice_two_text = (TextView) findViewById(R.id.choice_two);
        TextView choice_three_text = (TextView) findViewById(R.id.choice_three);
        TextView choice_four_text = (TextView) findViewById(R.id.choice_four);
        final TextView current_score = (TextView)findViewById(R.id.current_score);

        List<TextView> choice_text = new ArrayList<>();
        choice_text.add(choice_four_text);
        choice_text.add(choice_three_text);
        choice_text.add(choice_two_text);
        choice_text.add(choice_one_text);



        String answer = new String();
        int number_wrong_one;
        int number_wrong_two;
        int number_wrong_three;


        List<MainActivity.Phrase> phrases = phrases1;
        Random r = new Random();
        int number_random = r.nextInt(phrases.size())   + 0;
        question_text.setText(phrases.get(number_random).getWord());
        answer = phrases.get(number_random).getDefinition();


        Random one = new Random();
        int number_random_one = one.nextInt(4 ) + 0;
        choice_text.get(number_random_one).setText(answer);


        number_wrong_one = randoNumber(number_random , phrases.size());
        number_wrong_two = randoNumber(number_random , number_wrong_one , phrases.size());
        number_wrong_three = randoNumber(number_random ,number_wrong_one , number_wrong_two, phrases.size());

        Log.e(TAG, "onCreate: "+number_wrong_one + number_wrong_two + number_wrong_three );

        for (int i = 0; i < 4; i++) {
            if (i != number_random_one) {
                choice_text.get(i).setText(phrases.get(number_wrong_one).getDefinition());

                i++;
                if (i != number_random_one) {
                    choice_text.get(i).setText(phrases.get(number_wrong_two).getDefinition());
                    i++;
                    if (i != number_random_one) {
                        choice_text.get(i).setText(phrases.get(number_wrong_three).getDefinition());
                        i++;
                    } else {
                        i++;
                        choice_text.get(i).setText(phrases.get(number_wrong_three).getDefinition());
                    }
                } else {
                    i++;
                    choice_text.get(i).setText(phrases.get(number_wrong_two).getDefinition());
                    i++;
                    if (i != number_random_one) {
                        choice_text.get(i).setText(phrases.get(number_wrong_three).getDefinition());
                        i++;
                    }
                }
            }
        }
        final int[] correct = {0};

        choice_text.get(number_random_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score=   Integer.parseInt((String) current_score.getText());
                score= score+10;
                current_score.setText(String.valueOf(score));
                correct[0] =1;
                hamdler(phrases1);

            }
        });

        hamdler(phrases1);
    }



    public int randoNumber(int number_random, int size) {

        int sw =0;
        int max = size ;
        int min = 0;
        Random one = new Random();
        int number_random_one = one.nextInt((max - min) ) + min;


        while (sw==0){
            if (number_random_one!=number_random){

                return number_random_one;
            }
            number_random_one = one.nextInt((max - min) ) + min;
        }
        return number_random_one;
    }

    public int randoNumber(int number_random, int number_wrong_one ,int size) {

        int sw=0;
        int max = size ;
        int min = 0;
        Random one = new Random();
        int number_random_one = one.nextInt((max - min) ) + min;



        while (sw==0){
            if (number_random_one!=number_random && number_random_one!=number_wrong_one){
                return number_random_one;
            }
            number_random_one = one.nextInt((max - min) ) + min;
        }
        return number_random_one;
    }

    public int randoNumber(int number_random, int number_wrong_one , int number_wrong_two ,int size) {

        int sw =0;
        int max = size ;
        int min = 0;
        Random one = new Random();
        int number_random_one = one.nextInt((max - min) ) + min;



        while (sw==0){
            if (number_random_one!=number_random && number_random_one!=number_wrong_one && number_random_one!=number_wrong_two){

                return number_random_one;
            }
            number_random_one = one.nextInt((max - min) ) + min;
        }
        return number_random_one;
    }


    
}
