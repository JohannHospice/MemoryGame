package dvlps.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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

    MediaPlayer mainMusic;

    Button b_blue, b_red, b_yellow, b_green, b_jouer;

    ArrayList<Integer> cpuChoices = new ArrayList<Integer>();
    ArrayList<Button>  buttons = new ArrayList<Button>();

    melodieASuivre melodie = new melodieASuivre();

    Handler handler = new Handler();

    Random r;

    Integer compteur=0;

    Boolean gagne;

    Button b_blue_bis;
    Button b_red_bis;
    Button b_yellow_bis;
    Button b_green_bis;

    TextView scoreAffiche;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMusic = MediaPlayer.create(this, R.raw.memory_game);
        mainMusic.start();
        mainMusic.setLooping(true);

        b_blue = (Button) findViewById(R.id.b_blue);
        b_red = (Button) findViewById(R.id.b_red);
        b_green = (Button) findViewById(R.id.b_green);
        b_yellow = (Button) findViewById(R.id.b_yellow);
        b_jouer = (Button) findViewById(R.id.b_jouer);

        scoreAffiche = (TextView) findViewById(R.id.score);

        buttons.clear();
        buttons.add(b_blue);
        buttons.add(b_red);
        buttons.add(b_yellow);
        buttons.add(b_green);


        r = new Random();

        //On bloque les boutons tant qu'on a pas appuyé sur le bouton jouer
        b_blue.setEnabled(false);
        b_red.setEnabled(false);
        b_green.setEnabled(false);
        b_yellow.setEnabled(false);

        b_blue_bis = b_blue;
        b_red_bis = b_red;
        b_yellow_bis = b_yellow;
        b_green_bis = b_green;

        b_jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b_jouer.setEnabled(false);
                score = 0;
                compteur = 0;
                cpuChoices.clear();
                melodie.start();
                melodie.exec();

                scoreAffiche.setText("Score : ");

                for (int btn : cpuChoices)
                    System.out.println(btn);


                final Toast toast = Toast.makeText(MainActivity.this, "Let's Play !", Toast.LENGTH_SHORT);
                toast.show();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);

                for (int i = 0; i<buttons.size(); i++){
                    final int finali = i;
                    buttons.get(i).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (comparaisonRep(finali)) {
                                melodie.exec();
                                final Toast toast = Toast.makeText(MainActivity.this,"A moi de jouer !", Toast.LENGTH_SHORT);
                                toast.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     toast.cancel();
                                 }
                                }, 500);
                            }
                        }
                    });
                }

                scoreMAJ();
            }

        });


    }

    public void scoreMAJ(){
        scoreAffiche.setText("Score : " + score);
    }


    public boolean comparaisonRep(int nbBtn) {
        if (nbBtn == cpuChoices.get(compteur)) {
            if (compteur == cpuChoices.size() - 1) {
                compteur = 0;
                score++;
                scoreMAJ();
                return true;
            }
            compteur++;
        } else {
            System.out.println("perdu");
            GameOver();
        }

        return false;
    }

    private void GameOver() {
        mainMusic.stop();

        for (Button btn : buttons)
            btn.setEnabled(false);
        final Toast toast = Toast.makeText(MainActivity.this,"GAME OVER !", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(MainActivity.this, GameOver.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }


    public class melodieASuivre extends Thread {
        private boolean run = false;
        private int vitesse = 1;

        public void run() {
            super.run();
            while (true) {
                if (run) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (Button btn : buttons)
                                btn.setEnabled(false);
                        }
                    });
                    for (int i = 0; i < cpuChoices.size(); i++) {
                        pause(cpuChoices.get(i));
                    }
                    int random = r.nextInt(4);
                    cpuChoices.add(random);
                    pause(random);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (Button btn : buttons)
                                btn.setEnabled(true);
                        }
                    });
                    run = false;



                }

            }
        }


        public void pause(final int b) {
            try {
                Thread.sleep(500 / vitesse);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        buttons.get(b).setPressed(true);
                    }
                });
                Thread.sleep(1000 / vitesse);
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

        public void exec() {
            run = true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainMusic.stop();
    }
}
