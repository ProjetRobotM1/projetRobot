package com.projetRobot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
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

/**
 * Created by Hugo on 29/05/2018.
 */

public class DisplayFAQActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayfaq_activity);
        Intent getpos = getIntent();
        int posFAQ = getpos.getIntExtra("posfaq", -1);
        int poscenario = getpos.getIntExtra("poscenario", -1);
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        Faq faq = scenario.getFaq().get(posFAQ);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutDisplayFAQ);
        final ArrayList<TextView> listTextViewRep=new ArrayList<>();
        ArrayList<TextView> listTextViewQuest=new ArrayList<>();
        for (int i = 0; i < faq.getQuestions().size(); i++) {
            TextView question = new TextView(DisplayFAQActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(20, 10, 20, 10);
            params.gravity = Gravity.LEFT;
            question.setLayoutParams(params);
            question.setTextSize(27);
            question.setText(faq.getQuestions().get(i));
            question.isClickable();
            question.setTextColor(Color.parseColor("#000000"));
            linearLayout.addView(question);
            listTextViewQuest.add(question);

            TextView reponse = new TextView(DisplayFAQActivity.this);
            LinearLayout.LayoutParams paramREP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramREP.setMargins(20, 10, 20, 10);
            paramREP.gravity = Gravity.RIGHT;
            reponse.setLayoutParams(paramREP);
            //reponse.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            reponse.setText(faq.getReponses().get(i));
            reponse.setVisibility(View.GONE);
            reponse.setTextSize(25);
            reponse.setTextColor(Color.parseColor("#a6a6a6"));
            listTextViewRep.add(reponse);
            linearLayout.addView(reponse);
            View line=new View(DisplayFAQActivity.this);
            LinearLayout.LayoutParams paramline = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            line.setLayoutParams(paramline);
            line.setBackgroundColor(Color.parseColor("#D8D8D8"));
            linearLayout.addView(line);

            for (int j = 0; j <listTextViewQuest.size() ; j++) {

                final int finalJ = j;
                listTextViewQuest.get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listTextViewRep.get(finalJ).getVisibility()==(View.GONE)) {

                            listTextViewRep.get(finalJ).setVisibility(View.VISIBLE);

                        }else {listTextViewRep.get(finalJ).setVisibility(View.GONE);

                        }
                    }
                });

            }
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
