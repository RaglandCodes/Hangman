package com.example.ragland.hangman;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String[] words = {
            "BOOKWORM",
            "GALVANIZE",
            "ESPIONAGE",
            "HYPHEN",
            "JAUNDICE",
            "UNWORTHY",
            "ZOMBIE",
            "ZODIAC",
            "TRANSPLANT",
            "QUORUM",
            "RICKSHAW",
            "OXIDISE",
            "JELLY",
            "JIUJITSU",
            "JUMBO",
            "FLYBY",
            "EMBEZZLE",
            "GIZMO",
            "EXODUS",
            "BOGGLE"
    } ;
    String chosenWord;
    char[] chosenWordArray;
    ArrayList<String> arrayDashes = new ArrayList<String>();
    int chosenWordLength;

    TextView test;
    TextView dashes, score;
    TextView wrongGuesses;
    TextView tvHighScore;

    EditText guessInput;
    String guessLetter;
    Button btnSubmit;
    Random randomIndex;

    int wordIndex, points = 0;
    int unguessedLetters;
    int highScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView) findViewById(R.id.test);
        dashes = (TextView) findViewById(R.id.dashes);
        score = (TextView) findViewById(R.id.score);
        tvHighScore = (TextView) findViewById(R.id.high_score);
        wrongGuesses = (TextView) findViewById(R.id.wrong_guesses);
        btnSubmit = (Button) findViewById(R.id.Submit);
        guessInput = (EditText) findViewById(R.id.guessInput);

        randomIndex = new Random();
      wordIndex = randomIndex.nextInt(20);

      chosenWord = words[wordIndex];
      chosenWordLength = chosenWord.length();
      unguessedLetters = chosenWord.length();
      chosenWordArray = chosenWord.toCharArray();

    //  test.setText(unguessedLetters);
     //   test.setText(chosenWord + " " + Integer.toString(chosenWordLength));

        for(int i=0; i< chosenWordLength; i++)
        {
            arrayDashes.add("_");
        }
        printDashes();

        preferences();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessLetter = guessInput.getText().toString();
                guessLetter = guessLetter.substring(0,1).toUpperCase();
                checkGuess();
            }
        });





    } // end of oncreate

    private void printDashes(){
        dashes.setText("");
        for(int i=0; i<arrayDashes.size(); i++)
        {
            dashes.append(arrayDashes.get(i)+ " ");
        }
    }
    private void checkGuess()
    {
        int exist = 0;
        for(int i = 0; i<chosenWordLength; i++)
        {
            if(chosenWordArray[i] == guessLetter.charAt(0))
            {
               arrayDashes.set(i, guessLetter);
               printDashes();
                unguessedLetters -= 1;
               exist = 1;

              // test.setText(unguessedLetters);
            }
        }

        if(unguessedLetters == 0)
        {
            score.setText("SCORE : " + points);
            preferences();
        }
        if(exist != 1)
        {
            wrongGuesses.append(guessLetter + " ");
            points += 1;
        }

    }

    private void preferences()
    {
        SharedPreferences pref= getSharedPreferences("prefs", MODE_PRIVATE);
        highScore = pref.getInt("highScore", 100);

        if(highScore == 100)
        {
         //   tvHighScore.setText("HIGH SCORE : -");
        }
        else if(highScore > points)
        {
            SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor.putInt("highScore", points);
            editor.apply();
            tvHighScore.setText("HIGH SCORE : " + points);
        }
        else{
            tvHighScore.setText("HIGH SCORE : " + highScore);

        }
        //highScore = pref.getInt("highScore", 777);

    }
}
