package fr.windea.tuto.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.windea.tuto.R;
import fr.windea.tuto.model.User;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 1;
    private static final String GAME_ACTIVITY_USER_USERNAME = "username";
    private static final String GAME_ACTIVITY_USER_SCORE = "score";

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    private SharedPreferences mPreferences;
    private int mScore;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            mScore = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(GAME_ACTIVITY_USER_SCORE, mScore).apply();
            greetUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();

        mPreferences = getPreferences(MODE_PRIVATE);

        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mPlayButton.setEnabled(false);

        greetUser();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setFirstName(mNameInput.getText().toString());
                mPreferences.edit().putString(GAME_ACTIVITY_USER_USERNAME, mUser.getFirstName()).apply();
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    protected void greetUser() {
        String firstname = mPreferences.getString(GAME_ACTIVITY_USER_USERNAME, null);
        if (firstname != null) {
            mScore = mPreferences.getInt(GAME_ACTIVITY_USER_SCORE, 0);
            mGreetingText.setText("Re, " + firstname + ". Ton dernier score est de " + mScore + ", tu reviens pour grind le ladder du discord ?");
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }
}
