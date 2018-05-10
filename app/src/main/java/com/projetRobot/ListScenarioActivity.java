package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import  com.projetRobot.LauncherActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Hugo on 08/05/2018.
 */

public class ListScenarioActivity extends Activity {
    private Context context;
    //private   ArrayList<Scenario> listScenarAffichee = new ArrayList<Scenario>() ;

    ListView listView ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
       // System.out.println("LISTE SCENARIO : "+ listScenarAffichee);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listscenario_activity);
        context = this.getApplicationContext();

        listView =  findViewById(R.id.listViewScenario);

        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson= new Gson();
        String json=sharedPreferences.getString("testlist",null);
        Type type =new TypeToken<ArrayList<Scenario>>(){}.getType();
        System.out.println("JSON"+json);

            ArrayList<Scenario> listScenarAffichee= gson.fromJson(json, type);

        if ( listScenarAffichee==null){
             listScenarAffichee= new ArrayList<>();
        }
        System.out.println(json);





        ArrayAdapter<Scenario> adapter =new ArrayAdapter<Scenario>(this,
               android.R.layout.simple_list_item_1, android.R.id.text1, listScenarAffichee);


        listView.setAdapter(adapter);


    }
}

