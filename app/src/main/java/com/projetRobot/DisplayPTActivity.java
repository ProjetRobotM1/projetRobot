package com.projetRobot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Hugo on 30/05/2018.
 */

public class DisplayPTActivity extends Activity {
    TextView pT;
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaypt_activity);
        Intent getpos = getIntent();
        int poscenario = getpos.getIntExtra("poscenario", -1);
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        pT=findViewById(R.id.PT);
        pT.setText(scenario.getPresTexte());
        pT.setTextSize(35);
        pT.setTextColor(Color.parseColor("#000000"));
        pT.setClickable(true);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.FRANCE);
                }
            }
        });
        pT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getpos = getIntent();
                int poscenario = getpos.getIntExtra("poscenario", -1);
                ArrayList<Scenario> listScenar = getListScenario();
        t1.speak(listScenar.get(poscenario).getPresTexte(), TextToSpeech.QUEUE_FLUSH, null);
        }});
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
}