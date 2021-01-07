package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button[][] buttons = new Button[3][3]; // 2-D array to represent 9 buttons with index starting Button[0][0] till Button[2][2]

    private boolean player1Turn = true;// Represent it is the turn for Player 1 to move

    private int roundCount;// To count the total number of rounds, if no of rounds = 9 it means till now no one has won and all the buttons are filled ( DRAW )

    private int player1Points;// To count 1st Player score
    private int player2Points;// To count 2nd Player score

    private TextView textViewPlayer1;// To view score of the 1st Player
    private TextView textViewPlayer2;// To view score of the 2nd Player

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);//Calling XML page of main_activity

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i <= 2; i++)
        {
            for (int j = 0; j <= 2 ; j++)
            {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);

        buttonReset.setOnClickListener(new View.OnClickListener() // Onclicking Reset button, resetGame() will be called
        {
            @Override
            public void onClick(View v)
            {
                resetGame();// Calling the resetGame()
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (!((Button) v).getText().toString().equals(""))// if text on the button =" " it means no one have use this box till now
        {
            return;
        }

        if (player1Turn) // if Player1turn = true means write X on the button when a button is clicked
        {
            ((Button) v).setText("X");
        }
        else // if Player1turn = false means write O on the button when a button is clicked, now its turn for second player
        {
            ((Button) v).setText("O");
        }

        roundCount++;// when a button is clicked, one round gets over, next round gets start so roundcount is incremented by 1

        if (checkForWin()) // if CheckForWin = true meant three consecutive pattern is found
        {
            if (player1Turn)// if player1turn = true meant player1 has won
            {
                player1Wins();
            }
            else// if player1turn = false meant player2 has won
            {
                player2Wins();
            }
        }
        else if
        (roundCount == 9) // if Total number of rounds = 9 it meant all boxes are filled and still three consecutive pattern is not found and match is draw
        {
            draw();
        }
        else // If three consecutive pattern is not found and roundcount is less than 9 then its turn for player2 to move
        {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() // if CheckForWin = true meant three consecutive pattern is found
    {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                field[i][j] = buttons[i][j].getText().toString();// Storing the input of each button as a text
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(""))
            {
                return true; //it represents= vertically 3 conscutive pattern is found
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(""))
            {
                return true;//it represents= horizontally 3 conscutive pattern is found
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(""))
        {
            return true;//it represents= diagonally [left to right] 3 conscutive pattern is found
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(""))
        {
            return true;//it represents= diagonally [right to left] 3 conscutive pattern is found
        }

        return false;//it represents= 3 consecutive pattern is NOT found
    }

    private void player1Wins()//To display that Player 1 has won
    {
        player1Points++;//increases the score of player1 by one on winning
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();//Displays the text "Player 1 wins!"
        updatePointsText();//updated score is displayed on the scoreboard
        resetBoard();//To Clear all the boxes
    }

    private void player2Wins()//To display that Player 2 has won
    {
        player2Points++;//increases the score of player2 by one on winning
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();//Displays the text "Player 2 wins!"
        updatePointsText();//updated score is displayed on the scoreboard
        resetBoard();//To Clear all the boxes
    }

    private void draw()//To diplay match is Draw
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();// To display the text "Draw!"
        resetBoard();//all the box becomes empty
    }

    private void updatePointsText()// To display the score of Players on the Scoreboard
    {
        textViewPlayer1.setText("PLAYER-1=" + player1Points);//Displays Player 1 score
        textViewPlayer2.setText("PLAYER-2=" + player2Points);//Displays Player 2 score
    }

    private void resetBoard()//To Clear all the boxes
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                buttons[i][j].setText("");//To Display "" in each box
            }
        }

        roundCount = 0;//To start the roundcount again from 0
        player1Turn = true;// Turn for Player 1
    }

    private void resetGame()//To clear the score of the Players
    {
        player1Points = 0;//Player 1 score = 0
        player2Points = 0;// Player 2 score = 0
        updatePointsText();//display the updated score on the scoreboard
        resetBoard();//Clear all the boxes
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // Here we are sharing integer data with other activity by using Bundle

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        // Here we are recieving integer data from other activity by using Bundle

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}





