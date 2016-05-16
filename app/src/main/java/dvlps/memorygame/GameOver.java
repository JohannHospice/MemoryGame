package dvlps.memorygame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends Activity {

    Integer score;
    TextView scoreAffichage;
    Button b_rejouer;
    Button b_share;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        b_rejouer = (Button) findViewById(R.id.b_rejouer);
        b_share = (Button) findViewById(R.id.b_share);

        score = (Integer) getIntent().getSerializableExtra("score");
        scoreAffichage = (TextView) findViewById((R.id.score));
        scoreAffichage.setText(score.toString());

        b_rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOver.this, MainActivity.class));
            }
        });

        b_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Memory Game Score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Try to beat me in Memory Game ! I did a score of " + score.toString() +  " ! (" + getApplicationContext().getResources().getString(R.string.github) + ")");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
