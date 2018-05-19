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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Hugo on 17/05/2018.
 */

public class ListFAQActivity extends Activity {

    private Context context;
    private Button ajouterQR;

    ListView listView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.listfaq_activity);
        context = this.getApplicationContext();

        ajouterQR = findViewById(R.id.ajouterQRbtn);

        Faq currentFaq = new Faq();
        listView = findViewById(R.id.listViewFAQ);
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        System.out.println("POS SCENARIO " + poscenario);
        int pos = i.getIntExtra("pos", -1);
        int posFAQ = i.getIntExtra("posfaq", -1);
        if (posFAQ >= 0) {
            currentFaq = scenario.getFaq().get(posFAQ);
        } else {
            ArrayList<Faq> faqtoadd = new ArrayList<>();
            ArrayList<String> listquestion = new ArrayList<>();
            ArrayList<String> listreponse = new ArrayList<>();
            currentFaq.setQuestions(listquestion);
            currentFaq.setReponses(listreponse);
            if (scenario.getFaq() != null) {
                scenario.getFaq().add(currentFaq);
            } else {
                faqtoadd.add(currentFaq);
                scenario.setFaq(faqtoadd);
            }
            listScenar.remove(poscenario);
            listScenar.add(poscenario, scenario);
            setListScenario(listScenar);
            posFAQ = scenario.getFaq().size() - 1;

            currentFaq = scenario.getFaq().get(posFAQ);
            Intent newintent = new Intent(this, ListFAQActivity.class);
            newintent.putExtra("posfaq", posFAQ);
            newintent.putExtra("poscenario", poscenario);
            finish();
            startActivity(newintent);
        }

        if (posFAQ >= 0) {
            ArrayList<String> listQuestionsaffichee = currentFaq.getQuestions();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, listQuestionsaffichee);


            listView.setAdapter(adapter);

            registerForContextMenu(listView);
        }
        ajouterQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);

                int posFAQ = i.getIntExtra("posfaq", -1);
                System.out.println("posfaq" + posFAQ);

                Intent myIntent = new Intent(context, CreateFAQActivity.class);
                myIntent.putExtra("poscenario", poscenario);
                myIntent.putExtra("posfaq", posFAQ);
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

    @Override
    public void onBackPressed() {
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        int posFAQ = i.getIntExtra("posfaq", -1);
        ArrayList<Scenario> listScenario = getListScenario();
        if (listScenario.get(poscenario).getFaq().get(posFAQ).getQuestions().isEmpty()) {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Annulation")
                    .setMessage("Voulez vous annuler la création de la FAQ ")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<Scenario> listScenario = getListScenario();
                            Intent i = getIntent();
                            int poscenario = i.getIntExtra("poscenario", -1);
                            int posFAQ = i.getIntExtra("posfaq", -1);
                            listScenario.get(poscenario).getFaq().remove(posFAQ);
                            setListScenario(listScenario);
                            Intent myIntent = new Intent(context, CreateScenarioActivity.class);
                            myIntent.putExtra("poscenario", poscenario);
                            startActivity(myIntent);
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();


        } else {


            Intent myIntent = new Intent(context, CreateScenarioActivity.class);
            myIntent.putExtra("poscenario", poscenario);
            startActivity(myIntent);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewFAQ) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;

            menu.setHeaderTitle("Choisissez l'action");
            menu.add("Modifier");
            menu.add("Supprimer");

        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Modifier"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Intent getposIntent=getIntent();
            ArrayList<Scenario>listScenario=getListScenario();
            int poscenario=getposIntent.getIntExtra("poscenario",-1);
            int posfaq=getposIntent.getIntExtra("posfaq",-1);
            int posQR=info.position;

            Intent i = new Intent(this, CreateFAQActivity.class);
            i.putExtra("poscenario", poscenario);
            i.putExtra("posfaq",posfaq);
            i.putExtra("posQR",posQR);

            onDestroy();
            startActivity(i);
        }
        else if(item.getTitle()=="Supprimer"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Intent getposIntent=getIntent();
            ArrayList<Scenario>listScenario=getListScenario();
            int poscenario=getposIntent.getIntExtra("poscenario",-1);
            int posfaq=getposIntent.getIntExtra("posfaq",-1);
            int posQR=info.position;
            listScenario.get(poscenario).getFaq().get(posfaq).getReponses().remove(posQR);
            listScenario.get(poscenario).getFaq().get(posfaq).getQuestions().remove(posQR);
            setListScenario(listScenario);
            Toast.makeText(getApplicationContext(),"Q/R supprimé avec succès",Toast.LENGTH_LONG).show();
            recreate();
        }else{
            return false;
        }
        return true;
    }
}
