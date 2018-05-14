package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Hugo on 14/05/2018.
 */

public class ModifScenario extends Activity {
    private Context context;
    private EditText editTextName;
    private Button closeButton;
    private Button saveScenarioButton;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifscenario_activity);
        context = this.getApplicationContext();
        Intent i = getIntent();
        Scenario scenario = (Scenario) i.getSerializableExtra("scenario");
        editTextName = findViewById(R.id.editTextName);
        editTextName.setText(scenario.getName());
        closeButton= findViewById(R.id.closebutton);
        saveScenarioButton=findViewById(R.id.savescenariobutton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context , ListScenarioActivity.class);
                startActivity(myIntent);
            }
        });
        saveScenarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                 int pos = i.getIntExtra("pos",0);
                Scenario newScenario= new Scenario(editTextName.getText().toString());
                ArrayList<Scenario> listScenarAffichee = getListScenario();
                listScenarAffichee.remove(pos);
                listScenarAffichee.add(pos,newScenario);
                setListScenario(listScenarAffichee);
                Toast.makeText(getApplicationContext(),"Scenario modifié avec succès",Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(context, ListScenarioActivity.class);
                startActivity(myIntent);

            }
        });






    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(context , ListScenarioActivity.class);
        startActivity(myIntent);
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



}
