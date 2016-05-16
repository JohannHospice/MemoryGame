package dvlps.memorygame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by qunnamed on 15/05/16.
 */
public class GameOver extends Activity {

    Integer score;
    TextView scoreAffichage;
    Button b_rejouer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        b_rejouer = (Button) findViewById(R.id.b_rejouer);

        score = (Integer) getIntent().getSerializableExtra("score");
        scoreAffichage = (TextView) findViewById((R.id.score));
        scoreAffichage.setText(score.toString());

        b_rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOver.this, MainActivity.class));
            }
        });




    }
}
