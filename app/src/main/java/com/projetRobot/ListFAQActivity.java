package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

    ListView listView ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.listfaq_activity);
        context = this.getApplicationContext();

        ajouterQR=findViewById(R.id.ajouterQRbtn);

        Faq currentFaq=new Faq();
        listView =  findViewById(R.id.listViewFAQ);
        Intent i = getIntent();
        int poscenario=i.getIntExtra("poscenario",-1);
        ArrayList<Scenario> listScenar=getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        System.out.println("POS SCENARIO "+ poscenario);
        int pos =i.getIntExtra("pos",-1);
        int posFAQ=i.getIntExtra("posfaq",-1);
        if(posFAQ>=0){
        currentFaq = scenario.getFaq().get(posFAQ);
        }else{
            ArrayList<Faq>faqtoadd=new ArrayList<>();
            ArrayList<String>listquestion=new ArrayList<>();
            ArrayList<String>listreponse=new ArrayList<>();
            currentFaq.setQuestions(listquestion);
            currentFaq.setReponses(listreponse);

            faqtoadd.add(currentFaq);
            scenario.setFaq(faqtoadd);
            listScenar.remove(poscenario);
            listScenar.add(poscenario,scenario);
            setListScenario(listScenar);
            posFAQ=scenario.getFaq().size()-1;

            currentFaq = scenario.getFaq().get(posFAQ);
            Intent newintent=new Intent(this,ListFAQActivity.class);
            newintent.putExtra("posfaq",posFAQ);
            newintent.putExtra("poscenario",poscenario);
            finish();
            startActivity(newintent);
        }

        if(posFAQ>=0){
        ArrayList<String> listQuestionsaffichee = currentFaq.getQuestions();
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listQuestionsaffichee);


        listView.setAdapter(adapter);

        registerForContextMenu(listView);}
        ajouterQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int poscenario=i.getIntExtra("poscenario",-1);

                int posFAQ=i.getIntExtra("posfaq",-1);
                System.out.println("posfaq"+posFAQ);

                Intent myIntent = new Intent(context, CreateFAQActivity.class);
                myIntent.putExtra("poscenario",poscenario);
                myIntent.putExtra("posfaq",posFAQ);
                startActivity(myIntent);
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
