package fr.windea.tuto.model;

import java.util.List;

public class Questions {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Questions(String mQuestion, List<String> mChoiceList, int mAnswerIndex) {
        this.mQuestion = mQuestion;
        this.mChoiceList = mChoiceList;
        this.mAnswerIndex = mAnswerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }
}
