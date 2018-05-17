package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
        ajouterQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Faq currentFAQ=new Faq();
                Intent i = getIntent();
                Scenario scenario = (Scenario) i.getSerializableExtra("scenario");
                int pos =i.getIntExtra("pos",-1);
                if(pos>=0){
                    currentFAQ = scenario.getFaq().get(pos);}
                Intent myIntent = new Intent(context, CreateFAQActivity.class);
                myIntent.putExtra("faq",currentFAQ);
                startActivity(myIntent);
            }
        });
        Faq currentFaq=new Faq();
        listView =  findViewById(R.id.listViewFAQ);
        Intent i = getIntent();
        Scenario scenario = (Scenario) i.getSerializableExtra("scenario");
        int pos =i.getIntExtra("pos",-1);
        if(pos>=0){
        Faq currentFAQ = scenario.getFaq().get(pos);}
        ArrayList<String> questions=new ArrayList<>();
        questions.add("TEST 1");
        questions.add("TEST 2");
        currentFaq.setQuestions(questions);
        ArrayList<String> listQuestionsaffichee = currentFaq.getQuestions();
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listQuestionsaffichee);


        listView.setAdapter(adapter);

        registerForContextMenu(listView);

    }
}
