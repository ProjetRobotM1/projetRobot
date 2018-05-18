package com.projetRobot;

import android.app.Activity;
import android.content.Context;
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
 * Created by Hugo on 17/05/2018.
 */

public class CreateFAQActivity extends Activity {
    private EditText question;
    private EditText reponse;
    private Context context;
    private Button saveQR;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creatfaqactivity);
        context = this.getApplicationContext();
        question= (EditText) findViewById(R.id.addquestion);
        reponse= (EditText) findViewById(R.id.addreponse);
        saveQR=findViewById(R.id.validerQR);
        saveQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();


                ArrayList<Scenario>listscenario=getListScenario();

                int poscenario = i.getIntExtra("poscenario", -1);
                System.out.println("POS SCENARIO ="+poscenario);
                int posFaq=i.getIntExtra("posfaq", -1);
                System.out.println("POS FAQ ="+posFaq);
                Faq faq=listscenario.get(poscenario).getFaq().get(posFaq);
                ArrayList<String> questiontoadd =faq.getQuestions();
                questiontoadd.add(question.getText().toString());
                ArrayList<String> reponsetoadd =faq.getReponses();
                reponsetoadd.add(reponse.getText().toString());
                listscenario.get(poscenario).getFaq().remove(posFaq);
                listscenario.get(poscenario).getFaq().add(posFaq,faq);
                setListScenario(listscenario);
                Intent returntolist=new Intent(context,ListFAQActivity.class);
                returntolist.putExtra("poscenario",poscenario);
                returntolist.putExtra("posfaq",posFaq);
                startActivity(returntolist);

            }


    });
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
