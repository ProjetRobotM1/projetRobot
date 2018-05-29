package com.projetRobot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
            listViewPersonne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                    Intent getposIntent = getIntent();
                    ArrayList<Scenario> listScenario = getListScenario();
                    int poscenario = getposIntent.getIntExtra("poscenario", -1);
                    int posGrP = getposIntent.getIntExtra("posGrP", -1);
                    int posPers = position;
                    Scenario scenarioSauvegarde = (Scenario)getposIntent.getSerializableExtra("scenarioSauvegarde");
                    Intent i = new Intent(context, CreateGrPActivity.class);
                    i.putExtra("poscenario", poscenario);
                    i.putExtra("posGrP", posGrP);
                    i.putExtra("posPers", posPers);
                    i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                    onDestroy();
                    startActivity(i);
                }});
            registerForContextMenu(listViewPersonne);
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
        validerListePers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();
                Intent i = getIntent();
                Scenario scenariocree = new Scenario();
                int poscenario = i.getIntExtra("poscenario", -1);
                int posGrP = i.getIntExtra("posGrP", -1);
                if (poscenario == -1) {
                    ArrayList<Scenario> listScenario = getListScenario();
                    poscenario = listScenario.size() - 1;
                    scenariocree = listScenario.get(poscenario);
                } else {
                    ArrayList<Scenario> listScenario = getListScenario();
                    scenariocree = listScenario.get(poscenario);

                }

                if (!personneName.getText().toString().isEmpty()) {

                    scenariocree.getList_listPersonne().get(posGrP).setName(personneName.getText().toString());
                    listScenToUpdate.remove(poscenario);
                    listScenToUpdate.add(poscenario, scenariocree);
                    setListScenario(listScenToUpdate);
                    Toast.makeText(getApplicationContext(), "Liste enregistrée", Toast.LENGTH_LONG).show();
                    Intent returntocreateScen = new Intent(context,CreateScenarioActivity.class);
                    Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                    returntocreateScen.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                    returntocreateScen.putExtra("poscenario", poscenario);
                    startActivity(returntocreateScen);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListGrPActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer un nom pour votre liste")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    onRestart();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewPersonne) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;


            menu.add("Supprimer");

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Supprimer") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Intent getposIntent = getIntent();
            ArrayList<Scenario> listScenario = getListScenario();
            int poscenario = getposIntent.getIntExtra("poscenario", -1);
            int posGrP = getposIntent.getIntExtra("posGrP", -1);
            int posPers = info.position;
            listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().remove(posPers);
            setListScenario(listScenario);
            Toast.makeText(getApplicationContext(), "Personne supprimée avec succès", Toast.LENGTH_LONG).show();
            recreate();
        } else {
            return false;
        }
        return true;
    }
    public void onBackPressed() {
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        int posGrP = i.getIntExtra("posGrP", -1);
        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
        ArrayList<Scenario> listScenario = getListScenario();
        if(!personneName.getText().toString().equals(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getName()) &&!personneName.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListGrPActivity.this);
            builder.setTitle("Attention")
                    .setMessage("Le nom a été changé veuillez valider le changement")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onRestart();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }else{
            Intent returntoCreateScenar=new Intent(context,CreateScenarioActivity.class);

            if(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getName()==""||listScenario.get(poscenario).getList_listPersonne().get(posGrP).getName()==null){
                listScenario.get(poscenario).getList_listPersonne().remove(posGrP);
                setListScenario(listScenario);
            }
            returntoCreateScenar.putExtra("poscenario",poscenario);
            returntoCreateScenar.putExtra("scenarioSauvegarde",scenarioSauvegarde);
            startActivity(returntoCreateScenar);

        }
    }
}
