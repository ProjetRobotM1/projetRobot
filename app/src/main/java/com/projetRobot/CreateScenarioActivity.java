package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.projetRobot.Scenario;

import java.util.ArrayList;

/**
 * Created by Hugo on 08/05/2018.
 */

public class CreateScenarioActivity extends Activity {
    private Context context;
    private EditText scenario;
    private Button validerScenario;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Scenario> listScenario = new ArrayList<Scenario>();

        scenario =  findViewById(R.id.newScenario);
        

        super.onCreate(savedInstanceState);

        setContentView(R.layout.createscenario_activity);
        context = this.getApplicationContext();
        validerScenario=findViewById(R.id.validerScenario);
        validerScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String nomScenario=scenario.getText().toString();
            Scenario scenarioCree = new Scenario(nomScenario);
            ListScenarioActivity.listScenario.add(scenarioCree);

            }
        });
    }
}

