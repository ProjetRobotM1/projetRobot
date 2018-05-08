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
    private Button addInter, editInter, addScenario;
    private Button listeScenario;
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

        listeScenario=findViewById(R.id.listeScenarioBtn);
        listeScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListScenarioActivity.class);
                startActivity(myIntent);
            }
        });

        addScenario=findViewById(R.id.addScenarioBtn);
        addScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, CreateScenarioActivity.class);
                startActivity(myIntent);
            }
        });

    }
}




