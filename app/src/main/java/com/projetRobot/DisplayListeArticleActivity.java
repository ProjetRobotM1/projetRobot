package com.projetRobot;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Hugo on 29/05/2018.
 */

public class DisplayListeArticleActivity extends Activity {
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.displayla_activity);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.FRANCE);
                }
            }
        });


        Intent getpos = getIntent();
        int posGrA = getpos.getIntExtra("posGrA", -1);
        int poscenario = getpos.getIntExtra("poscenario", -1);
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        ArrayList<Article> listArt = scenario.getList_listArticle().get(posGrA).getListArticle();
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutDisplayListeArticle);
        final ArrayList<TextView> listTextViewArt = new ArrayList<>();
        final ArrayList<TextView> listDescription=new ArrayList<>();

        for (int i = 0; i < listArt.size();i++){
            TextView article = new TextView(DisplayListeArticleActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(20, 10, 20, 10);
            params.gravity = Gravity.LEFT;
            article.setLayoutParams(params);
            article.setTextSize(27);
            article.setText(listArt.get(i).getNom());
            article.setTextColor(Color.parseColor("#000000"));
            article.isClickable();
            linearLayout.addView(article);
            listTextViewArt.add(article);


            TextView desc = new TextView(DisplayListeArticleActivity.this);
            LinearLayout.LayoutParams paramdesc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramdesc.setMargins(20, 10, 20, 10);
            paramdesc.gravity = Gravity.LEFT;
            desc.setLayoutParams(paramdesc);
            desc.setTextSize(20);
            if (!listArt.get(i).getDescription().isEmpty()){
                desc.setText("Description: " + listArt.get(i).getDescription());
            }else {
                desc.setText("Description: Non renseignée" );
            }
            desc.setTextColor(Color.parseColor("#a6a6a6"));
            desc.setVisibility(View.GONE);
            listDescription.add(desc);
            linearLayout.addView(desc);

            View line=new View(DisplayListeArticleActivity.this);
            LinearLayout.LayoutParams paramline = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            line.setLayoutParams(paramline);
            line.setBackgroundColor(Color.parseColor("#D8D8D8"));
            linearLayout.addView(line);


        }
        for (int j = 0; j <listTextViewArt.size() ; j++) {

            final int finalJ = j;
            listTextViewArt.get(finalJ).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listDescription.get(finalJ).getVisibility() == View.GONE) {
                        listDescription.get(finalJ).setVisibility(View.VISIBLE);
                        t1.speak(listDescription.get(finalJ).getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

                    } else {
                        listDescription.get(finalJ).setVisibility(View.GONE);
                    }
                }
            });

        }


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
