package dvlps.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuMulti extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_multi);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuMulti.this, MainActivity.class);
                intent.putExtra("vitesse", (double)1);
                intent.putExtra("niveau", 1);
                intent.putExtra("nbPlayer", 2);
                startActivity(intent);
            }
        };
        findViewById(R.id.btn_local).setOnClickListener(onClick);
        findViewById(R.id.btn_online).setOnClickListener(onClick);
    }


}
