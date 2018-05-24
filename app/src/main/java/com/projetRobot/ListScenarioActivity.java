package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.projetRobot.LauncherActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Hugo on 08/05/2018.
 */

public class ListScenarioActivity extends Activity {
    private Context context;
    //private   ArrayList<Scenario> listScenarAffichee = new ArrayList<Scenario>() ;

    ListView listView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        // System.out.println("LISTE SCENARIO : "+ listScenarAffichee);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listscenario_activity);
        context = this.getApplicationContext();

        listView = findViewById(R.id.listViewScenario);

        final ArrayList<Scenario> listScenarAffichee = getListScenario();
        ArrayAdapter<Scenario> adapter = new ArrayAdapter<Scenario>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listScenarAffichee);


        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                ArrayList<Scenario> listScenario = getListScenario();
                Scenario scenarioSauvegarde = listScenario.get(position);
                System.out.println("Taille liste "+listScenario.size());
                System.out.println("POS "+position);
                Intent i = new Intent(context, CreateScenarioActivity.class);
                i.putExtra("poscenario",position);
                i.putExtra("activity",1);
                i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                onDestroy();
                startActivity(i);
            }});
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewScenario) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Scenario scenario = (Scenario) lv.getItemAtPosition(acmi.position);
            menu.add("Supprimer");

        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Modifier") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Intent i = new Intent(this, CreateScenarioActivity.class);
            i.putExtra("poscenario", info.position);
            onDestroy();
            startActivity(i);
        } else if (item.getTitle() == "Supprimer") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenarAffichee = getListScenario();
            listScenarAffichee.remove(info.position);
            setListScenario(listScenarAffichee);
            Toast.makeText(getApplicationContext(), "Scenario supprimé avec succès", Toast.LENGTH_LONG).show();
            recreate();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(context, LauncherActivity.class);
        startActivity(myIntent);
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

