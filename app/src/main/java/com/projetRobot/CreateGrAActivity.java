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

public class CreateGrAActivity extends Activity {
    private EditText nom,description;

    private Context context;
    private Button saveArt;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createart_activity);
        context = this.getApplicationContext();
        nom = (EditText) findViewById(R.id.NomArticle);
        description = (EditText) findViewById(R.id.Description);

        saveArt = findViewById(R.id.validerArticle);
        Intent i = getIntent();
        final int posArt = i.getIntExtra("posArt", -1);
        final int poscenario = i.getIntExtra("poscenario", -1);
        int posGrA = i.getIntExtra("posGrA", -1);
        if (posArt != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            nom.setText(listScenario.get(poscenario).getList_listArticle().get(posGrA).getListArticle().get(posArt).getNom());
            description.setText(listScenario.get(poscenario).getList_listArticle().get(posGrA).getListArticle().get(posArt).getDescription());
        }
        saveArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                if (!nom.getText().toString().isEmpty() ){
                    if (posArt == -1) {
                        ArrayList<Scenario> listscenario = getListScenario();
                        int poscenario = i.getIntExtra("poscenario", -1);
                        System.out.println("POS SCENARIO =" + poscenario);
                        int posGrA = i.getIntExtra("posGrA", -1);
                        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                        ConteneurListArticle listArt=listscenario.get(poscenario).getList_listArticle().get(posGrA);
                        ArrayList<Article> listArticleToAdd=new ArrayList<>();
                        if(listArt.getListArticle()!=null){
                            listArticleToAdd=listArt.getListArticle();
                        }
                        Article article=new Article();
                        article.setNom(nom.getText().toString());
                        article.setDescription(description.getText().toString());


                        listArticleToAdd.add(article);
                        listscenario.get(poscenario).getList_listArticle().get(posGrA).setListArticle(listArticleToAdd);
                        setListScenario(listscenario);
                        Intent returntolist = new Intent(context, ListGrAActivity.class);
                        returntolist.putExtra("poscenario", poscenario);
                        returntolist.putExtra("posGrA", posGrA);
                        returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                        startActivity(returntolist);
                    } else {
                        ArrayList<Scenario> listscenario = getListScenario();

                        int poscenario = i.getIntExtra("poscenario", -1);
                        int posGrA = i.getIntExtra("posGrA", -1);
                        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                        listscenario.get(poscenario).getList_listArticle().get(posGrA).getListArticle().remove(posArt);
                        Article article=new Article();
                        article.setNom(nom.getText().toString());
                        article.setDescription(description.getText().toString());

                        listscenario.get(poscenario).getList_listArticle().get(posGrA).getListArticle().add(posArt,article);
                        setListScenario(listscenario);
                        Intent returntolist = new Intent(context, ListGrAActivity.class);
                        returntolist.putExtra("poscenario", poscenario);
                        returntolist.putExtra("posGrA", posGrA);
                        returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                        startActivity(returntolist);


                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateGrAActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer un nom d'article")
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


