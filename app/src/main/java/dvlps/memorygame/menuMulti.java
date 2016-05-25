package dvlps.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuMulti extends Activity {

    private Button btn_local, btn_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_multi);

        btn_local = (Button) findViewById(R.id.btn_local);

        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
