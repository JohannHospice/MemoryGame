package dvlps.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;

public class menuNiveau extends Activity {

    private Button btn_easy, btn_medium, btn_hard;
    private double vitesse = 1;
    private String niveau;
    private int nbJoueur=1;
    MediaPlayer mainMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_niveau);


        mainMusic = MediaPlayer.create(this, R.raw.memory_game_music);
        mainMusic.start();
        mainMusic.setLooping(true);

        btn_easy = (Button) findViewById(R.id.btn_easy);
        btn_medium = (Button) findViewById(R.id.btn_medium);
        btn_hard = (Button) findViewById(R.id.btn_hard);

        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitesse = 3.5;
                niveau = "easy";
                jouer();
            }
        });

        btn_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitesse = 7;
                niveau = "medium";
                jouer();
            }
        });

        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitesse = 10.5 ;
                niveau = "hard";
                jouer();
            }
        });
    }

    public void jouer() {
        mainMusic.stop();
        Intent intent = new Intent(menuNiveau.this, MainActivity.class);
        intent.putExtra("vitesse", vitesse);
        intent.putExtra("niveau", niveau);
        intent.putExtra("nbPlayer", nbJoueur);
        startActivity(intent);
    }
}
