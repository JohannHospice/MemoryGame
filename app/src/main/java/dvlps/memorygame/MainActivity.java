package dvlps.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.*;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private ArrayList<Integer> sequence = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<>();
    private TextView scoreTextInput;
    private Button playButton;
    private Handler handler = new Handler();
    private Random random = new Random();
    private Computer computer;
    private int currentSequenceIndex = 0;
    private int nbPlayer = 1;
    private int currentPlayer = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nbPlayer = (int) getIntent().getSerializableExtra("nbPlayer");
        computer = new Computer((float) getIntent().getSerializableExtra("vitesse"));
        setContentView(R.layout.activity_main);

        scoreTextInput = (TextView) findViewById(R.id.score);

        playButton = (Button) findViewById(R.id.b_jouer);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        buttons.add((Button) findViewById(R.id.b_blue));
        buttons.add((Button) findViewById(R.id.b_red));
        buttons.add((Button) findViewById(R.id.b_green));
        buttons.add((Button) findViewById(R.id.b_yellow));

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setEnabled(false);
            buttons.get(i).setOnClickListener(getButtonClickListener(i));
        }
    }

    public View.OnClickListener getButtonClickListener(final int numButton) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numButton == sequence.get(currentSequenceIndex))
                    if (currentSequenceIndex == sequence.size() - 1) {
                        currentSequenceIndex = 0;
                        score++;
                        updateScore();
                        incrementCurrentPlayer();
                        if(currentPlayer > 0)
                            Toast.makeText(MainActivity.this, "TURN TO PLAYER" + currentPlayer, Toast.LENGTH_SHORT).show();
                        {
                            computer.restart();
                            Toast.makeText(MainActivity.this, "TURN TO COMPUTER", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        currentSequenceIndex++;
                else
                    endGame();
            }
        };
    }

    /**
     * Increment the value of the current player according with the number of player
     * the computer is count like the number 0 player
     */
    private void incrementCurrentPlayer(){
        currentPlayer = (currentPlayer + 1) % (nbPlayer+1);
    }

    public void setEnablabledAllButtons(boolean enable) {
        for (Button btn : buttons)
            btn.setEnabled(enable);
    }

    private void startGame() {
        playButton.setEnabled(false);
        score = 0;
        currentSequenceIndex = 0;
        sequence.clear();
        computer.start();
        updateScore();
        Toast.makeText(MainActivity.this, "LET'S PLAY", Toast.LENGTH_SHORT).show();
    }

    private void endGame() {
        setEnablabledAllButtons(false);
        Toast.makeText(MainActivity.this, "PLAYER " + currentPlayer + " IS OVER", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, GameOver.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    public void updateScore() {
        scoreTextInput.setText("Score : " + score);
    }

    public class Computer extends Thread {
        private boolean run = true;
        private float speed = 1;

        public Computer(float speed) {
            this.speed = speed;
        }

        @Override
        public void run() {
            super.run();
            while (true)
                if (run) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setEnablabledAllButtons(false);
                        }
                    });
                    runSequence();
                    addToSequence(random.nextInt(4));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setEnablabledAllButtons(true);
                        }
                    });
                    incrementCurrentPlayer();
                    Toast.makeText(MainActivity.this, "TURN TO PLAYER" + currentPlayer, Toast.LENGTH_SHORT).show();
                    run = false;
                }
        }

        @Override
        public synchronized void start() {
            super.start();
        }

        public void restart() {
            run = true;
        }

        private void runSequence() {
            for (int choice : sequence)
                pressButton(choice);
        }

        private void addToSequence(int numButton) {
            pressButton(numButton);
            sequence.add(numButton);
        }

        private void pressButton(final int b) {
            try {
                Thread.sleep((long) (500 / speed));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        buttons.get(b).setPressed(true);
                    }
                });
                Thread.sleep((long) (1000 / speed));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        buttons.get(b).setPressed(false);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }
    }
}
