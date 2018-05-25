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

public class ListGrAActivity extends Activity {
    private Context context;
    private Button ajouterArticle,validerListeArt;
    private EditText articleName;

    ListView listViewArticle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listgra_activity);
        context = this.getApplicationContext();
        ajouterArticle=findViewById(R.id.ajouterArticlebtn);
        articleName=findViewById(R.id.editTextNameGrA);
        validerListeArt=findViewById(R.id.validerListArticles);
        listViewArticle=findViewById(R.id.listViewArticle);
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        ConteneurListArticle currentListArticle=new ConteneurListArticle();
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        int posGrA = i.getIntExtra("posGrA", -1);
        if(posGrA!=-1){
            articleName.setText(scenario.getList_listArticle().get(posGrA).getName());
        }
        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
        if (posGrA >= 0) {
            currentListArticle = scenario.getList_listArticle().get(posGrA);
        } else {
            ArrayList<ConteneurListArticle> listArticleToAdd=new ArrayList<>();
            ArrayList<Article>listArticle=new ArrayList<>();
            currentListArticle.setListArticle(listArticle);

            if (scenario.getList_listArticle() != null) {
                scenario.getList_listArticle().add(currentListArticle);
            } else {
                listArticleToAdd.add(currentListArticle);
                scenario.setList_listArticle(listArticleToAdd);
            }
            listScenar.remove(poscenario);
            listScenar.add(poscenario, scenario);
            setListScenario(listScenar);
            posGrA = scenario.getList_listArticle().size()-1;

            currentListArticle = scenario.getList_listArticle().get(posGrA);
            Intent newintent = new Intent(this, ListGrAActivity.class);
            newintent.putExtra("posGrA", posGrA);
            newintent.putExtra("poscenario", poscenario);
            newintent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
            finish();
            startActivity(newintent);
        }
        if (posGrA >= 0) {
            ArrayList<Article> listArticleAffichee = listScenar.get(poscenario).getList_listArticle().get(posGrA).getListArticle();

            ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, listArticleAffichee);


            listViewArticle.setAdapter(adapter);
            listViewArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                    Intent getposIntent = getIntent();
                    ArrayList<Scenario> listScenario = getListScenario();
                    int poscenario = getposIntent.getIntExtra("poscenario", -1);
                    int posGrA = getposIntent.getIntExtra("posGrA", -1);
                    int posArt = position;
                    Scenario scenarioSauvegarde = (Scenario)getposIntent.getSerializableExtra("scenarioSauvegarde");
                    Intent i = new Intent(context, CreateGrAActivity.class);
                    i.putExtra("poscenario", poscenario);
                    i.putExtra("posGrA", posGrA);
                    i.putExtra("posArt", posArt);
                    i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                    onDestroy();
                    startActivity(i);
                }});
            registerForContextMenu(listViewArticle);
        }
        ajouterArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                int posGrA = i.getIntExtra("posGrA", -1);


                Intent myIntent = new Intent(context, CreateGrAActivity.class);
                myIntent.putExtra("poscenario", poscenario);
                myIntent.putExtra("posGrA", posGrA);
                myIntent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(myIntent);
            }
        });
        validerListeArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();
                Intent i = getIntent();
                Scenario scenariocree = new Scenario();
                int poscenario = i.getIntExtra("poscenario", -1);
                int posGrA = i.getIntExtra("posGrA", -1);
                if (poscenario == -1) {
                    ArrayList<Scenario> listScenario = getListScenario();
                    poscenario = listScenario.size() - 1;
                    scenariocree = listScenario.get(poscenario);
                } else {
                    ArrayList<Scenario> listScenario = getListScenario();
                    scenariocree = listScenario.get(poscenario);

                }

                if (!articleName.getText().toString().isEmpty()) {

                    scenariocree.getList_listArticle().get(posGrA).setName(articleName.getText().toString());
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListGrAActivity.this);
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
        if (v.getId() == R.id.listViewArticle) {
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
            int posGrA = getposIntent.getIntExtra("posGrA", -1);
            int posArt = info.position;
            listScenario.get(poscenario).getList_listArticle().get(posGrA).getListArticle().remove(posArt);
            setListScenario(listScenario);
            Toast.makeText(getApplicationContext(), "Article supprimé avec succès", Toast.LENGTH_LONG).show();
            recreate();
        } else {
            return false;
        }
        return true;
    }
    public void onBackPressed() {
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        int posGrA = i.getIntExtra("posGrA", -1);
        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
        ArrayList<Scenario> listScenario = getListScenario();
        if(!articleName.getText().toString().equals(listScenario.get(poscenario).getList_listArticle().get(posGrA).getName()) && !articleName.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListGrAActivity.this);
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
            if(listScenario.get(poscenario).getList_listArticle().get(posGrA).getName()==""||listScenario.get(poscenario).getList_listArticle().get(posGrA).getName()==null){
                listScenario.get(poscenario).getList_listArticle().remove(posGrA);
                setListScenario(listScenario);
            }
            returntoCreateScenar.putExtra("poscenario",poscenario);
            returntoCreateScenar.putExtra("scenarioSauvegarde",scenarioSauvegarde);
            startActivity(returntoCreateScenar);

        }
    }
}
