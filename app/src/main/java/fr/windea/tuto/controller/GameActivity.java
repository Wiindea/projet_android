package fr.windea.tuto.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import fr.windea.tuto.R;
import fr.windea.tuto.model.Questions;
import fr.windea.tuto.model.QuestionsBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    public static final int TOTAL_QUESTION_NUMBER = 10;

    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;

    private QuestionsBank mQuestionsBank;
    private Questions mCurrentQuestion;
    private int mRemainingQuestions;

    private int mScore;
    private String mEnd;
    private boolean mEnableTouchEvents;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, TOTAL_QUESTION_NUMBER);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionsBank = this.generateQuestionsBank();

        if (savedInstanceState == null) {
            mScore = 0;
            mRemainingQuestions = mQuestionsBank.getNumberOfQuestions();
        } else {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }

        mEnableTouchEvents = true;

        mQuestion = findViewById(R.id.activity_game_question_text);
        mAnswer1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswer2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswer3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswer4 = findViewById(R.id.activity_game_answer4_btn);

        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);

        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        mCurrentQuestion = mQuestionsBank.getQuestions();
        this.displayQuestion(mCurrentQuestion);
    }

    private void displayQuestion(final Questions question) {
        List<String> choices = question.getChoiceList();

        mQuestion.setText(question.getQuestion());
        mAnswer1.setText(choices.get(0));
        mAnswer2.setText(choices.get(1));
        mAnswer3.setText(choices.get(2));
        mAnswer4.setText(choices.get(3));
    }

    private QuestionsBank generateQuestionsBank() {
        return new QuestionsBank(Arrays.asList(
            new Questions("Quel personnage a les plus gros boobs ?", Arrays.asList("Sona", "Miss Fortune", "Ahri", "Gragas ♥"), 1),
            new Questions("Quel perso, à sa sortie, est considéré comme ayant été le plus fort de tous les temps ?", Arrays.asList("Twisted Fate", "Ekko", "Irelia", "Yasuo"), 0),
            new Questions("Vous jouez Malphite, et un petit Veigar low life vous taunt sous sa tour. Que faire ?", Arrays.asList("Je flash ult ce pd", "Je flash ult putain !", "JE FLASH ET J'LE BUTE BORDEL !!", "KZGRFLASHTEKFDRKLULTFOREQPDZQJ!!!§!"), 3),
            new Questions("Quel est le nom de la forme alternative de Nidalee ?", Arrays.asList("Jaguar", "Tigre à dents de sabre", "Couguar ( ͡° ͜ʖ ͡°)", "Puma"), 2),
            new Questions("Lequel de ces personnages AD était également joué en AP en tournoi avant son rework  ?", Arrays.asList("SION", "MASTER YI", "TRISTANA", "WARWICK"), 1),
            new Questions("Quel champion a le plus bas win rate ?", Arrays.asList("Ornn", "Nidalee", "Ivern", "Nunu"), 0),
            new Questions("Quel champion a le meilleur win rate ?", Arrays.asList("Yasuo", "Shaco", "Draven", "Quinn"), 3),
            new Questions("Quel champion fait le moins de kill en moyenne ?", Arrays.asList("Soraka", "Taric", "Nautilus", "Shen"), 0),
            new Questions("Quel est la combinaison de champion pour un rôle le moins joué ?", Arrays.asList("Kayle mid", "Heimerdinger Top", "Corki adc", "Mundo jungle"), 2),
            new Questions("Qui est le champion le plus gros ?", Arrays.asList("Cho'gath", "Gragas", "Aurelion Sol", "Zac"), 2)));
    }

    @Override
    public void onClick(View view) {
        int answerIndex = (int) view.getTag();

        if (answerIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "GG !", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else
            Toast.makeText(this, "Tu pues", Toast.LENGTH_SHORT).show();

        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                if (--mRemainingQuestions > 0) {
                    mCurrentQuestion = mQuestionsBank.getQuestions();
                    displayQuestion(mCurrentQuestion);
                } else
                    endGame();
            }
        }, 2000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    public void endGame() {
        if (mScore < 0)
            mEnd = "Va falloir que tu m'expliques comment tu as fait";
        else if (mScore == 0)
            mEnd = "... c'est pas fou X)";
        else if (mScore <= TOTAL_QUESTION_NUMBER/2)
            mEnd = "(╯⸌⏠⸍）╯︵ ┻━┻";
        else if (mScore < TOTAL_QUESTION_NUMBER)
            mEnd = "Pas mal !";
        else if (mScore == TOTAL_QUESTION_NUMBER)
            mEnd = "Good job, perfect";
        else
            mEnd = "Euh... What ?";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terminé !")
                .setMessage("Votre score : " + mScore + "/" + TOTAL_QUESTION_NUMBER)
                .setPositiveButton(mEnd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
