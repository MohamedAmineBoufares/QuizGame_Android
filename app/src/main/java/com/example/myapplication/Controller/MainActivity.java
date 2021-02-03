package com.example.myapplication.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.* ;

import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;

    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();

        mPreferences = getPreferences(MODE_PRIVATE);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.action_bar_main_play_btn);

        mPlayButton.setEnabled(false);

        mNameInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPlayButton.setEnabled(s.toString().length() != 0);

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String firstname = mNameInput.getText().toString();
                mUser.setFirstName(firstname);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstName()).apply();

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
                
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            int score = data.getIntExtra(GameActivity.BUNDlE_EXTRA_SCORE, 0);

            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();
            greetUser();


        }
    }

    private void greetUser() {

        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME,null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " +firstname + "!\nYour last score was " +score + ", will you do better this time?";
            mGreetingText.setText((fulltext));
            mNameInput.setText((firstname));
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }
}