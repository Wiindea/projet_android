package fr.windea.tuto.model;

import java.util.Collections;
import java.util.List;

public class QuestionsBank {

    private List<Questions> mQuestionsList;
    private int mNextQuestionIndex;

    public QuestionsBank(List<Questions> mQuestionsList) {
        Collections.shuffle(mQuestionsList);
        this.mQuestionsList = mQuestionsList;
        mNextQuestionIndex = 0;
    }

    public Questions getQuestions() {
        if (mNextQuestionIndex == mQuestionsList.size())
            mNextQuestionIndex = 0;
        return mQuestionsList.get(mNextQuestionIndex++);
    }

    public int getNumberOfQuestions() {
        return mQuestionsList.size();
    }
}
