package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.projetRobot.Scenario;

import org.json.JSONArray;

import java.lang.reflect.Type;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createscenario_activity);
        scenario = (EditText) findViewById(R.id.newScenario);
        context = this.getApplicationContext();
        validerScenario=findViewById(R.id.validerScenario);
        validerScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();

                Scenario scenarioObject = new Scenario();
                if (!scenario.getText().toString().isEmpty()){
                    scenarioObject.setName(scenario.getText().toString());
                listScenToUpdate.add(scenarioObject);
                SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gsonpush = new Gson();
                String jsonpush = gsonpush.toJson(listScenToUpdate);
                editor.putString("testlist", jsonpush);
                editor.apply();
            }
            }
        });

    }
    public ArrayList<Scenario> getListScenario(){

        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson= new Gson();
        String json=sharedPreferences.getString("testlist",null);
        Type type =new TypeToken<ArrayList<Scenario>>(){}.getType();
        System.out.println("JSON"+json);

        ArrayList<Scenario> listScenarAffichee= gson.fromJson(json, type);

        if ( listScenarAffichee==null){
            listScenarAffichee= new ArrayList<>();
        }
        return listScenarAffichee;
    }



}

