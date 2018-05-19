package com.projetRobot;

import android.app.Activity;
import android.content.Context;
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

import java.lang.reflect.Type;
import java.util.ArrayList;




/**
 * Created by Hugo on 08/05/2018.
 */

public class CreateScenarioActivity extends Activity {
    private Context context;
    private EditText scenario;
    private Button validerScenario,ajouterFAQ;
    private ListView listViewFAQ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createscenario_activity);
        Intent i = getIntent();
        Scenario scenariocree=new Scenario();
        int poscenario=i.getIntExtra("poscenario",-1);
        if(poscenario!=-1){
            ArrayList<Scenario> listScenario = getListScenario();
            scenariocree=listScenario.get(poscenario);
        }else{
            ArrayList<Scenario> listScenario = getListScenario();
            listScenario.add(scenariocree);
            setListScenario(listScenario);
        }





        scenario = (EditText) findViewById(R.id.newScenario);
        context = this.getApplicationContext();
        listViewFAQ=findViewById(R.id.listViewFAQ);
        validerScenario=findViewById(R.id.validerScenario);
        ajouterFAQ=findViewById(R.id.addFAQ);
        ArrayList<Faq> listFAQ=new ArrayList<>();
        if(scenariocree.getFaq()!=null) {
            listFAQ = scenariocree.getFaq();
        }
        ArrayList<String> listtoString=new ArrayList<>();
        for (int j = 0; j <listFAQ.size() ; j++) {
            listtoString.add("FAQ "+(j+1));
        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, listtoString);


        listViewFAQ.setAdapter(adapter);

        registerForContextMenu(listViewFAQ);


        validerScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();
                Intent i = getIntent();
                Scenario scenariocree=new Scenario();
                int poscenario=i.getIntExtra("poscenario",-1);
                if(poscenario==-1){
                    ArrayList<Scenario> listScenario = getListScenario();
                    poscenario=listScenario.size()-1;
                     scenariocree=listScenario.get(poscenario);
                }else{
                    ArrayList<Scenario> listScenario = getListScenario();
                     scenariocree=listScenario.get(poscenario);

                }


                if (!scenario.getText().toString().isEmpty()){
                    scenariocree.setName(scenario.getText().toString());
                    listScenToUpdate.remove(poscenario);
                    listScenToUpdate.add(poscenario,scenariocree);
                    setListScenario(listScenToUpdate);
            }
            }
        });
        ajouterFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListFAQActivity.class);
                ArrayList<Scenario> listScenario = getListScenario();
                Intent i = getIntent();
                int poscenario=i.getIntExtra("poscenario",-1);
                if(poscenario==-1) {

                    poscenario = listScenario.size() - 1;
                }

                myIntent.putExtra("pos",-1);

                Scenario scenariocree=new Scenario();

                myIntent.putExtra("poscenario",poscenario);

                scenariocree.setName("TEST");
                System.out.println("intent scenario: "+ scenariocree.toString());
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
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
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario=Intentgetpos.getIntExtra("poscenario",-1);
            if(poscenario==-1) {

                poscenario = listScenario.size() - 1;
            }
            Intent i = new Intent(this, ListFAQActivity.class);
            i.putExtra("poscenario", poscenario);
            i.putExtra("posfaq",info.position);
            startActivity(i);
        }
        else if(item.getTitle()=="Supprimer"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario=Intentgetpos.getIntExtra("poscenario",-1);
            if(poscenario==-1) {

                poscenario = listScenario.size() - 1;
            }
            listScenario.get(poscenario).getFaq().remove(info.position);
            setListScenario(listScenario);


            Toast.makeText(getApplicationContext(),"FAQ supprimé avec succès",Toast.LENGTH_LONG).show();
            recreate();
        }else{
            return false;
        }
        return true;
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
    public void setListScenario(ArrayList<Scenario> listScenToUpdate){
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gsonpush = new Gson();
        String jsonpush = gsonpush.toJson(listScenToUpdate);
        editor.putString("testlist", jsonpush);
        editor.apply();

    }
    @Override

    public void onBackPressed() {

        Intent myIntent = new Intent(context , LauncherActivity.class);
        startActivity(myIntent);
    }


}

