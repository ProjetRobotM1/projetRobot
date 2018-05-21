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
import com.projetRobot.Scenario;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by Hugo on 08/05/2018.
 */

public class CreateScenarioActivity extends Activity {
    private Context context;
    private EditText scenario;
    private Button validerScenario, ajouterFAQ,ajouterGroupePersonne,ajouterGroupeArticle;
    private ListView listViewFAQ,listViewGroupePersonne,listViewGroupeArticle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createscenario_activity);
        Intent i = getIntent();
        Scenario scenariocree = new Scenario();
        int poscenario = i.getIntExtra("poscenario", -1);
        if (poscenario != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            scenariocree = listScenario.get(poscenario);
        } else {
            ArrayList<Scenario> listScenario = getListScenario();
            listScenario.add(scenariocree);
            setListScenario(listScenario);
        }


        scenario = (EditText) findViewById(R.id.newScenario);
        if (poscenario != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            scenario.setText(listScenario.get(poscenario).getName());
        }
        context = this.getApplicationContext();
        listViewFAQ = findViewById(R.id.listViewFAQ);
        validerScenario = findViewById(R.id.validerScenario);
        ajouterFAQ = findViewById(R.id.addFAQ);
        ajouterGroupeArticle=findViewById((R.id.addGroupeArticle));
        ajouterGroupePersonne=findViewById((R.id.addGroupePersonne));
        listViewGroupePersonne=findViewById((R.id.listViewGroupePersonne));
        listViewGroupeArticle=findViewById((R.id.listViewGroupeArticle));
        ArrayList<Faq> listFAQ = new ArrayList<>();
        ArrayList<ConteneurListArticle> list_listArticle = new ArrayList<>();
        ArrayList<ConteneurListPersonne> list_listPersonne = new ArrayList<>();

        if (scenariocree.getFaq() != null) {
            listFAQ = scenariocree.getFaq();
        }
        ArrayList<String> listtoString = new ArrayList<>();
        for (int j = 0; j < listFAQ.size(); j++) {
            listtoString.add("FAQ " + (j + 1));
        }
        if(scenariocree.getList_listArticle() !=null){
            list_listArticle=scenariocree.getList_listArticle();
            list_listArticle.add(new ConteneurListArticle("testart",new ArrayList<Article>()));
        }
        list_listArticle.add(new ConteneurListArticle("testart",new ArrayList<Article>()));
        if(scenariocree.getList_listPersonne() !=null){
            list_listPersonne=scenariocree.getList_listPersonne();
            list_listPersonne.add(new ConteneurListPersonne("testpers",new ArrayList<Personne>()));
        }
        list_listPersonne.add(new ConteneurListPersonne("testpers",new ArrayList<Personne>()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, listtoString);
        ArrayAdapter<ConteneurListArticle> adapterlistArticle = new ArrayAdapter<ConteneurListArticle>(context,
                android.R.layout.simple_list_item_1,android.R.id.text1,list_listArticle);
        ArrayAdapter<ConteneurListPersonne> adapterlistPersonne = new ArrayAdapter<ConteneurListPersonne>(context,
                android.R.layout.simple_list_item_1,android.R.id.text1,list_listPersonne);
        listViewFAQ.setAdapter(adapter);
        listViewGroupePersonne.setAdapter(adapterlistPersonne);
        listViewGroupeArticle.setAdapter(adapterlistArticle);
        listViewFAQ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                ArrayList<Scenario> listScenario = getListScenario();
                Intent Intentgetpos = getIntent();
                int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                Intent i = new Intent(context, ListFAQActivity.class);
                i.putExtra("poscenario", poscenario);
                i.putExtra("posfaq", position);
                startActivity(i);


            }
        });
        registerForContextMenu(listViewFAQ);
        registerForContextMenu(listViewGroupePersonne);
        registerForContextMenu(listViewGroupeArticle);


        validerScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();
                Intent i = getIntent();
                Scenario scenariocree = new Scenario();
                int poscenario = i.getIntExtra("poscenario", -1);
                if (poscenario == -1) {
                    ArrayList<Scenario> listScenario = getListScenario();
                    poscenario = listScenario.size() - 1;
                    scenariocree = listScenario.get(poscenario);
                } else {
                    ArrayList<Scenario> listScenario = getListScenario();
                    scenariocree = listScenario.get(poscenario);

                }

                if (!scenario.getText().toString().isEmpty()) {

                    scenariocree.setName(scenario.getText().toString());
                    listScenToUpdate.remove(poscenario);
                    listScenToUpdate.add(poscenario, scenariocree);
                    setListScenario(listScenToUpdate);
                    Toast.makeText(getApplicationContext(), "Scénario enregistré", Toast.LENGTH_LONG).show();
                    Intent returntolauncher = new Intent(context,LauncherActivity.class);
                    startActivity(returntolauncher);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateScenarioActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer un nom pour votre scénario")
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
        ajouterFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListFAQActivity.class);
                ArrayList<Scenario> listScenario = getListScenario();
                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }

                myIntent.putExtra("pos", -1);

                Scenario scenariocree = new Scenario();

                myIntent.putExtra("poscenario", poscenario);

                scenariocree.setName("TEST");
                System.out.println("intent scenario: " + scenariocree.toString());
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewFAQ) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer FAQ");

        }
        if (v.getId() == R.id.listViewGroupeArticle) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer groupe article");

        }
        if (v.getId() == R.id.listViewGroupePersonne) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer groupe personne");

        }



    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Supprimer FAQ") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
            if (poscenario == -1) {

                poscenario = listScenario.size() - 1;
            }
            listScenario.get(poscenario).getFaq().remove(info.position);
            setListScenario(listScenario);


            Toast.makeText(getApplicationContext(), "FAQ supprimé avec succès", Toast.LENGTH_LONG).show();
            recreate();
        } else {
            return false;
        }
        return true;
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

    public void onBackPressed() {
        Intent getActivity = getIntent();
        ArrayList<Scenario> listScenario = getListScenario();
        int poscenario = getActivity.getIntExtra("poscenario", -1);
        if (poscenario == -1) {

            poscenario = listScenario.size() - 1;
        }

        if (getActivity.getIntExtra("activity", -1) == 0) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Annulation")
                    .setMessage("Voulez vous annuler la création de scénario ? (scénario non validé)")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<Scenario> listScenario = getListScenario();
                            Intent i = getIntent();
                            int poscenario = i.getIntExtra("poscenario", -1);
                            if (poscenario == -1) {

                                poscenario = listScenario.size() - 1;
                            }
                            listScenario.remove(poscenario);
                            setListScenario(listScenario);
                            Intent myIntent = new Intent(context, LauncherActivity.class);
                            startActivity(myIntent);
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();
        }
        Scenario scenarioSauvegarde = (Scenario)getActivity.getSerializableExtra("scenarioSauvegarde");
        System.out.println( "normalement 1 :"+getActivity.getIntExtra("activity", -1));

        setListScenario(listScenario);
        if (getActivity.getIntExtra("activity", -1) == 1 ) {
            System.out.println( "normalement true :" +scenarioSauvegarde.equals(listScenario.get(poscenario)));
            if(scenarioSauvegarde.equals(listScenario.get(poscenario))){
                System.out.println( "JE SUIS RENTRE JE SUIS UNE MERDE");

            listScenario.remove(poscenario);
            scenarioSauvegarde = (Scenario)getActivity.getSerializableExtra("scenarioSauvegarde");
            listScenario.add(poscenario,scenarioSauvegarde);
            setListScenario(listScenario);
            Intent myIntent = new Intent(context, ListScenarioActivity.class);
            startActivity(myIntent);

        }else{
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Annulation")
                        .setMessage("Voulez vous annuler la modification du scénario ? (modifications non validées)")
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
                                listScenario.remove(poscenario);
                                listScenario.add(poscenario, scenarioSauvegarde);
                                setListScenario(listScenario);
                                Intent myIntent = new Intent(context, ListScenarioActivity.class);
                                startActivity(myIntent);
                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
    }
    }


}

