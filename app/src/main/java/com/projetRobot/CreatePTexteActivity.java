package com.projetRobot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Hugo on 30/05/2018.
 */

public class CreatePTexteActivity extends Activity{
    private Context context;
    private EditText pTexte;
    private Button validerPTexte;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createptexte_activity);
        context = this.getApplicationContext();
        pTexte = (EditText) findViewById(R.id.addPtexte);
        validerPTexte = findViewById(R.id.validerPT);
        ArrayList<Scenario>listScenario=getListScenario();
        Intent i = getIntent();
         int poscenario = i.getIntExtra("poscenario", -1);
        if (poscenario == -1) {

            poscenario = listScenario.size() - 1;
        }

        if(listScenario.get(poscenario).getPresTexte()!=null){
            pTexte.setText(listScenario.get(poscenario).getPresTexte());
        }
        validerPTexte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pTexte.getText().toString().isEmpty()){
                    ArrayList<Scenario>listScenario=getListScenario();
                    Intent i = getIntent();
                    int poscenario = i.getIntExtra("poscenario", -1);
                    listScenario.get(poscenario).setPresTexte(pTexte.getText().toString());
                    setListScenario(listScenario);
                    Intent returntolist = new Intent(context, CreateScenarioActivity.class);
                    returntolist.putExtra("poscenario", poscenario);
                    Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                    returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                    startActivity(returntolist);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreatePTexteActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer un texte")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    onRestart();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }});

    }
    public void onBackPressed() {
        Intent getActivity = getIntent();
        ArrayList<Scenario> listScenario = getListScenario();
        int poscenario = getActivity.getIntExtra("poscenario", -1);
        if (poscenario == -1) {

            poscenario = listScenario.size() - 1;
        }
        Scenario scenarioSauvegarde = (Scenario) getActivity.getSerializableExtra("scenarioSauvegarde");
        if(listScenario.get(poscenario).getPresTexte()!=null) {
            if (!pTexte.getText().toString().equals(listScenario.get(poscenario).getPresTexte())) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Annulation")
                        .setMessage("Voulez vous annuler la création de la presentation texte ? (texte non validé)")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArrayList<Scenario> listScenario = getListScenario();
                                Intent i = getIntent();
                                int poscenario = i.getIntExtra("poscenario", -1);
                                if (poscenario == -1) {

                                    poscenario = listScenario.size() - 1;
                                }
                                Scenario scenarioSauvegarde = (Scenario) i.getSerializableExtra("scenarioSauvegarde");

                                Intent myIntent = new Intent(context, CreateScenarioActivity.class);
                                myIntent.putExtra("poscenario", poscenario);
                                myIntent.putExtra("scenarioSauvegarde", scenarioSauvegarde);
                                startActivity(myIntent);
                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();
            } else {
                Intent myIntent = new Intent(context, CreateScenarioActivity.class);
                myIntent.putExtra("poscenario", poscenario);
                myIntent.putExtra("scenarioSauvegarde", scenarioSauvegarde);
                startActivity(myIntent);

            }
        }else{
            if (!pTexte.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Annulation")
                    .setMessage("Voulez vous annuler la création de la presentation texte ? (texte non validé)")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<Scenario> listScenario = getListScenario();
                            Intent i = getIntent();
                            int poscenario = i.getIntExtra("poscenario", -1);
                            if (poscenario == -1) {

                                poscenario = listScenario.size() - 1;
                            }
                            Scenario scenarioSauvegarde = (Scenario) i.getSerializableExtra("scenarioSauvegarde");

                            Intent myIntent = new Intent(context, CreateScenarioActivity.class);
                            myIntent.putExtra("poscenario", poscenario);
                            myIntent.putExtra("scenarioSauvegarde", scenarioSauvegarde);
                            startActivity(myIntent);
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();

        }else {
            Intent myIntent = new Intent(context, CreateScenarioActivity.class);
            myIntent.putExtra("poscenario", poscenario);
            myIntent.putExtra("scenarioSauvegarde", scenarioSauvegarde);
            startActivity(myIntent);
        }


        }
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
