package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Hugo on 23/05/2018.
 */

public class ListGrPActivity extends Activity {
    private Context context;
    private Button ajouterPersonne,validerListePers;
    private EditText personneName;

    ListView listViewPersonne;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listgrp_activity);
        context = this.getApplicationContext();
        ajouterPersonne=findViewById(R.id.ajouterPersonnebtn);
        personneName=findViewById(R.id.editTextNameGrP);
        validerListePers=findViewById(R.id.validerListPersonnes);
        listViewPersonne=findViewById(R.id.listViewPersonne);
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        ConteneurListPersonne currentListPersonne=new ConteneurListPersonne();
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        int posGrP = i.getIntExtra("posGrP", -1);
        if(posGrP!=-1){
            personneName.setText(scenario.getList_listPersonne().get(posGrP).getName());
        }
        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
        if (posGrP >= 0) {
            currentListPersonne = scenario.getList_listPersonne().get(posGrP);
        } else {
            ArrayList<ConteneurListPersonne> listPersonneToAdd=new ArrayList<>();
            ArrayList<Personne>listpersonne=new ArrayList<>();
            currentListPersonne.setListPersonne(listpersonne);

            if (scenario.getList_listPersonne() != null) {
                scenario.getList_listPersonne().add(currentListPersonne);
            } else {
                listPersonneToAdd.add(currentListPersonne);
                scenario.setList_listPersonne(listPersonneToAdd);
            }
            listScenar.remove(poscenario);
            listScenar.add(poscenario, scenario);
            setListScenario(listScenar);
            posGrP = scenario.getList_listPersonne().size()-1;

            currentListPersonne = scenario.getList_listPersonne().get(posGrP);
            Intent newintent = new Intent(this, ListGrPActivity.class);
            newintent.putExtra("posGrP", posGrP);
            newintent.putExtra("poscenario", poscenario);
            newintent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
            finish();
            startActivity(newintent);
        }
        if (posGrP >= 0) {
            ArrayList<Personne> listPersonneAffichee = listScenar.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne();

            ArrayAdapter<Personne> adapter = new ArrayAdapter<Personne>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, listPersonneAffichee);


            listViewPersonne.setAdapter(adapter);
        }
        ajouterPersonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                int posGrP = i.getIntExtra("posGrP", -1);


                Intent myIntent = new Intent(context, CreateGrPActivity.class);
                myIntent.putExtra("poscenario", poscenario);
                myIntent.putExtra("posGrP", posGrP);
                myIntent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(myIntent);
            }
        });


    }
    public ArrayList<Scenario> getListScenario() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("testlist", null);
        Type type = new TypeToken<ArrayList<Scenario>>() {
        }.getType();
        System.out.println("JSON" + json);

        ArrayList<Scenario> listScenarAffichee = gson.fromJson(json, type);

        if (listScenarAffichee == null) {
            listScenarAffichee = new ArrayList<>();
        }
        return listScenarAffichee;
    }

    public void setListScenario(ArrayList<Scenario> listScenToUpdate) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gsonpush = new Gson();
        String jsonpush = gsonpush.toJson(listScenToUpdate);
        editor.putString("testlist", jsonpush);
        editor.apply();

    }
}
