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
    private Handler handler = new Handler();
    private Random random = new Random();
    private Button playButton;
    private Computer computer;
    private Integer currentIndex = 0;
    private TextView scoreTextInput;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        computer = new Computer((float) getIntent().getSerializableExtra("vitesse"));

        setContentView(R.layout.activity_main);

        scoreTextInput = (TextView) findViewById(R.id.score);

        playButton = (Button) findViewById(R.id.b_jouer);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startGame();}
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
                if (numButton == sequence.get(currentIndex))
                    if (currentIndex == sequence.size() - 1) {
                        currentIndex = 0;
                        score++;
                        updateScore();
                    } else {
                        currentIndex++;
                        computer.restart();
                        Toast.makeText(MainActivity.this, "A moi de jouer !", Toast.LENGTH_SHORT).show();
                    }
                else
                    endGame();
            }
        };
    }

    public void setEnablabledAllButtons(boolean enable) {
        for (Button btn : buttons)
            btn.setEnabled(enable);
    }

    private void startGame(){
        playButton.setEnabled(false);
        score = 0;
        currentIndex = 0;
        updateScore();
        sequence.clear();
        computer.start();
        Toast.makeText(MainActivity.this, "Let's Play !", Toast.LENGTH_SHORT).show();
    }

    private void endGame() {
        setEnablabledAllButtons(false);
        Toast.makeText(MainActivity.this, "GAME OVER !", Toast.LENGTH_SHORT).show();
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
