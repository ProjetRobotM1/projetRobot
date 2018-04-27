package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hugo on 27/04/2018.
 */

public class LauncherActivity  extends Activity {
    private Button addInter, editInter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        addInter=findViewById(R.id.addInterBtn);
        addInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, AddActionActivity.class);
                startActivity(myIntent);
            }
        });

    }
}




