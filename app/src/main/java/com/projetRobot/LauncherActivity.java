package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Hugo on 27/04/2018.
 */

public class LauncherActivity  extends Activity {
    private Button addInter, editInter, addScenario;
    private Button listeScenario;
    private Context context;
    ListView listView ;
    public  ArrayList<Scenario> getListScenario() {
        return listScenario;
    }

     ArrayList<Scenario> listScenario;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();



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




