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
 * Created by Hugo on 23/05/2018.
 */

public class CreateGrPActivity extends Activity {
    private EditText nom,prenom,age,profession,adresse,site,email;

    private Context context;
    private Button savePers;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpers_activity);
        context = this.getApplicationContext();
        nom = (EditText) findViewById(R.id.Nom);
        prenom = (EditText) findViewById(R.id.Prenom);
        age = (EditText) findViewById(R.id.Age);
        profession = (EditText) findViewById(R.id.Profession);
        adresse = (EditText) findViewById(R.id.Adresse);
        site = (EditText) findViewById(R.id.Site);
        email=(EditText) findViewById(R.id.Email);

        savePers = findViewById(R.id.validerPersonne);
        Intent i = getIntent();
        final int posPers = i.getIntExtra("posPers", -1);
        final int poscenario = i.getIntExtra("poscenario", -1);
        int posGrP = i.getIntExtra("posGrP", -1);
        if (posPers != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            nom.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getNom());
            prenom.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getPrenom());

            if(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getAge()!=null){
                age.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getAge());
            }
            if(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getProfession()!=null){
                profession.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getProfession());
            }
            if(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getAdresse()!=null){
                adresse.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getAdresse());
            }
            if(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getSite()!=null){
                site.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getSite());
            }
            if(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getEmail()!=null){
                email.setText(listScenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().get(posPers).getEmail());
            }
        }
        savePers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                if (!nom.getText().toString().isEmpty() && !prenom.getText().toString().isEmpty()) {
                    if (posPers == -1) {
                        ArrayList<Scenario> listscenario = getListScenario();
                        int poscenario = i.getIntExtra("poscenario", -1);
                        System.out.println("POS SCENARIO =" + poscenario);
                        int posGrP = i.getIntExtra("posGrP", -1);
                        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                        ConteneurListPersonne ListPers=listscenario.get(poscenario).getList_listPersonne().get(posGrP);
                        ArrayList<Personne> listPersonneToAdd=new ArrayList<>();
                        if(ListPers.getListPersonne()!=null){
                            listPersonneToAdd=ListPers.getListPersonne();
                        }
                        Personne personne=new Personne();
                        personne.setNom(nom.getText().toString());
                        personne.setPrenom(prenom.getText().toString());
                        personne.setProfession(profession.getText().toString());
                        personne.setAdresse(adresse.getText().toString());
                        personne.setSite(site.getText().toString());
                        personne.setEmail(email.getText().toString());
                        if(!age.getText().toString().isEmpty()){
                            String ageString=age.getText().toString();
                            Integer integer=Integer.decode(ageString);
                            personne.setAge(integer);
                        }else {
                            personne.setAge(null);
                        }

                        listPersonneToAdd.add(personne);
                        listscenario.get(poscenario).getList_listPersonne().get(posGrP).setListPersonne(listPersonneToAdd);
                        setListScenario(listscenario);
                        Intent returntolist = new Intent(context, ListGrPActivity.class);
                        returntolist.putExtra("poscenario", poscenario);
                        returntolist.putExtra("posGrP", posGrP);
                        returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                        startActivity(returntolist);
                    } else {
                        ArrayList<Scenario> listscenario = getListScenario();

                        int poscenario = i.getIntExtra("poscenario", -1);
                        int posGrP = i.getIntExtra("posGrP", -1);
                        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                        listscenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().remove(posPers);
                        Personne personne=new Personne();
                        personne.setNom(nom.getText().toString());
                        personne.setPrenom(prenom.getText().toString());
                        personne.setProfession(profession.getText().toString());
                        personne.setAdresse(adresse.getText().toString());
                        personne.setSite(site.getText().toString());
                        personne.setEmail(email.getText().toString());
                        if(!age.getText().toString().isEmpty()){
                            String ageString=age.getText().toString();
                            Integer integer=Integer.decode(ageString);
                            personne.setAge(integer);
                        }else {
                            personne.setAge(null);
                        }
                        listscenario.get(poscenario).getList_listPersonne().get(posGrP).getListPersonne().add(posPers,personne);
                        setListScenario(listscenario);
                        Intent returntolist = new Intent(context, ListGrPActivity.class);
                        returntolist.putExtra("poscenario", poscenario);
                        returntolist.putExtra("posGrP", posGrP);
                        returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                        startActivity(returntolist);


                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateGrPActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer un nom ET un prénom")
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
}


