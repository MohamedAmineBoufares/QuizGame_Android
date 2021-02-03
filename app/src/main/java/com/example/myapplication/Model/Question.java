package com.example.myapplication.Model;

import java.util.List;

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String mQuestion, List<String> mChoiceList, int mAnswerIndex) {
        this.mQuestion = mQuestion;
        this.mChoiceList = mChoiceList;
        this.mAnswerIndex = mAnswerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> mChoiceList) {
        if (mChoiceList == null){

            throw new IllegalArgumentException("The List is Empty MATE !");
        }
        this.mChoiceList = mChoiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int mAnswerIndex) {
        if (mAnswerIndex < 0 || mAnswerIndex >= mChoiceList.size()){
            throw new IllegalArgumentException("Answer does not EXIST !");
        }
        this.mAnswerIndex = mAnswerIndex;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mChoiceList=" + mChoiceList +
                ", mAnswerIndex=" + mAnswerIndex +
                '}';
    }
}
