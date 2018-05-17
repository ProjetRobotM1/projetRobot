package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private Button validerScenario,ajouterFAQ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createscenario_activity);
        Intent i = getIntent();
        Scenario scenariocree=new Scenario();
        if(!i.getSerializableExtra("scenario").equals("null")) {
             scenariocree = (Scenario) i.getSerializableExtra("scenario");
        }
        scenario = (EditText) findViewById(R.id.newScenario);
        context = this.getApplicationContext();
        validerScenario=findViewById(R.id.validerScenario);
        ajouterFAQ=findViewById(R.id.addFAQ);
        validerScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();
                Intent i = getIntent();
                Scenario scenariocree=new Scenario();
                if(!i.getSerializableExtra("scenario").equals("null")) {
                    scenariocree = (Scenario) i.getSerializableExtra("scenario");
                }

                if (!scenario.getText().toString().isEmpty()){
                    scenariocree.setName(scenario.getText().toString());
                listScenToUpdate.add(scenariocree);
                SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gsonpush = new Gson();
                String jsonpush = gsonpush.toJson(listScenToUpdate);
                editor.putString("testlist", jsonpush);
                editor.apply();
            }
            }
        });
        ajouterFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListFAQActivity.class);
                myIntent.putExtra("pos",-1);
                Intent i = getIntent();
                Scenario scenariocree=new Scenario();
                if(!i.getSerializableExtra("scenario").equals("null")) {
                    scenariocree = (Scenario) i.getSerializableExtra("scenario");
                }
                myIntent.putExtra("scenario",scenariocree);
                scenariocree.setName("TEST");
                System.out.println("intent scenario: "+ scenariocree.toString());
                startActivity(myIntent);
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

