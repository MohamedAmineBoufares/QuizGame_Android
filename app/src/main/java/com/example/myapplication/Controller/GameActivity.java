package com.example.myapplication.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Question;
import com.example.myapplication.Model.QuestionBank;
import com.example.myapplication.R;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQstText;
    private Button mAnswerButton1,mAnswerButton2,mAnswerButton3,mAnswerButton4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDlE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

        mQstText = (TextView) findViewById(R.id.activity_game_question_txt);
        mAnswerButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        // Identifying the Buttons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        // Setting listeners on each Button
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion((mCurrentQuestion));
    }

    @Override
    public void onClick(View v) {

        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()){
            Toast.makeText(this, "NOICE !", Toast.LENGTH_SHORT).show();
            mScore++;
        } else{
            Toast.makeText(this, "NOPE :(",Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                if (--mNumberOfQuestions == 0){
                    endGame();
                } else{
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        },2000); // before going to the next question wait 2 seconds cause we used LENGTH_SHORT in the Toast !
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return  mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    public void endGame(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("FANTASTIC !")
                .setMessage("Your Score : "+mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDlE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void displayQuestion(final Question question){

        mQstText.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));

    }

    private QuestionBank generateQuestions(){

        Question qs1 = new Question("What's the name of the last Avengers Movie ?", Arrays.asList
                ("Infinity War","Miles Morales","Endgame","None"),2);
        Question qs2 = new Question("What's the name of the last Christopher Nolan movie ?", Arrays.asList
                ("Endgame","The Dark Knight","The Purge","TENET"),3);
        Question qs3 = new Question("Who dies in The Last Of Us Part 2 ?", Arrays.asList
                ("No One","Bruce Wayne","Thanos","Joel"),3);
        Question qs4 = new Question("According to HolleyWood, when is the end of the WORLD ?", Arrays.asList
                ("The world is Infinite","2020","2012","2021"),2);
        Question qs5 = new Question("What's the most awarded game in HISTORY ?", Arrays.asList
                ("The Last Of Us Part 2","FreeFire","Fortnite","The Witcher 3"),0);

        return new QuestionBank(Arrays.asList(qs1,qs2,qs3,qs4,qs5));
    }
}