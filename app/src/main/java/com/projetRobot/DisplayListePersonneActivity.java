package com.projetRobot;

import android.app.Activity;
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

public class DisplayListePersonneActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.displaylp_activity);

        Intent getpos = getIntent();
        int posGrP = getpos.getIntExtra("posGrP", -1);
        int poscenario = getpos.getIntExtra("poscenario", -1);
        ArrayList<Scenario> listScenar = getListScenario();
        Scenario scenario = listScenar.get(poscenario);
        ArrayList<Personne> listPers = scenario.getList_listPersonne().get(posGrP).getListPersonne();
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutDisplayListePersonne);
        final ArrayList<TextView> listTextViewPers = new ArrayList<>();
        final ArrayList<TextView> listAge=new ArrayList<>();
        final ArrayList<TextView> listemail=new ArrayList<>();
        final ArrayList<TextView> listProf=new ArrayList<>();
        final ArrayList<TextView> listAdr=new ArrayList<>();
        final ArrayList<TextView> listSite=new ArrayList<>();
        for (int i = 0; i < listPers.size();i++){
            TextView personne = new TextView(DisplayListePersonneActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(20, 10, 20, 10);
            params.gravity = Gravity.LEFT;
            personne.setLayoutParams(params);
            personne.setTextSize(27);
            personne.setText(listPers.get(i).getPrenom()+" "+listPers.get(i).getNom());
            personne.setTextColor(Color.parseColor("#000000"));
            personne.isClickable();
            linearLayout.addView(personne);
            listTextViewPers.add(personne);


                TextView age = new TextView(DisplayListePersonneActivity.this);
                LinearLayout.LayoutParams paramage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                paramage.setMargins(20, 10, 20, 10);
                paramage.gravity = Gravity.LEFT;
                age.setLayoutParams(paramage);
                age.setTextSize(20);
                if (listPers.get(i).getAge()!=null){
                age.setText("Age: " + listPers.get(i).getAge().toString());
                }else {
                    age.setText("Age: Non renseigné" );
                }
                age.setTextColor(Color.parseColor("#a6a6a6"));
                age.setVisibility(View.GONE);
                listAge.add(age);
                linearLayout.addView(age);


                TextView email = new TextView(DisplayListePersonneActivity.this);
                LinearLayout.LayoutParams paremail = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                paremail.setMargins(20, 10, 20, 10);
                paremail.gravity = Gravity.LEFT;
                email.setLayoutParams(paremail);
                email.setTextSize(20);
                if (!listPers.get(i).getEmail().isEmpty()){
                    email.setText("Email: " + listPers.get(i).getEmail());
                }else {
                    email.setText("Email: Non renseigné" );
                }
                email.setTextColor(Color.parseColor("#a6a6a6"));
                email.setVisibility(View.GONE);
                listemail.add(email);
                linearLayout.addView(email);



                TextView profession = new TextView(DisplayListePersonneActivity.this);
                LinearLayout.LayoutParams parprof = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parprof.setMargins(20, 10, 20, 10);
                parprof.gravity = Gravity.LEFT;
                profession.setLayoutParams(parprof);
                profession.setTextSize(20);
                if (!listPers.get(i).getProfession().isEmpty()){
                    profession.setText("Profession: " + listPers.get(i).getProfession());
                }else {
                    profession.setText("Profession: Non renseignée" );
                }
                profession.setVisibility(View.GONE);
                profession.setTextColor(Color.parseColor("#a6a6a6"));
                listProf.add(profession);
                linearLayout.addView(profession);

            TextView adresse = new TextView(DisplayListePersonneActivity.this);
            LinearLayout.LayoutParams paradre = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paradre.setMargins(20, 10, 20, 10);
            paradre.gravity = Gravity.LEFT;
            adresse.setLayoutParams(paradre);
            adresse.setTextSize(20);
            if (!listPers.get(i).getAdresse().isEmpty()){
                adresse.setText("Adresse: " + listPers.get(i).getAdresse());
            }else {
                adresse.setText("Adresse: Non renseignée" );
            }
            adresse.setTextColor(Color.parseColor("#a6a6a6"));
            adresse.setVisibility(View.GONE);
            listAdr.add(adresse);
            linearLayout.addView(adresse);

            TextView site = new TextView(DisplayListePersonneActivity.this);
            LinearLayout.LayoutParams parsite = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            parsite.setMargins(20, 10, 20, 10);
            parsite.gravity = Gravity.LEFT;
            site.setLayoutParams(parsite);
            site.setTextSize(20);
            if (!listPers.get(i).getSite().isEmpty()){
                site.setText("Site: " + listPers.get(i).getSite());
            }else {
                site.setText("Site: Non renseigné" );
            }
            site.setTextColor(Color.parseColor("#a6a6a6"));
            site.setVisibility(View.GONE);
            listSite.add(site);
            linearLayout.addView(site);
            View line=new View(DisplayListePersonneActivity.this);
            LinearLayout.LayoutParams paramline = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            line.setLayoutParams(paramline);
            line.setBackgroundColor(Color.parseColor("#D8D8D8"));
            linearLayout.addView(line);
        }
        for (int j = 0; j <listTextViewPers.size() ; j++) {

            final int finalJ = j;
            listTextViewPers.get(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listAge.get(finalJ).getVisibility() == View.GONE) {
                        listAge.get(finalJ).setVisibility(View.VISIBLE);
                        listemail.get(finalJ).setVisibility(View.VISIBLE);
                        listProf.get(finalJ).setVisibility(View.VISIBLE);
                        listAdr.get(finalJ).setVisibility(View.VISIBLE);
                        listSite.get(finalJ).setVisibility(View.VISIBLE);
                    } else {
                        listAge.get(finalJ).setVisibility(View.GONE);
                        listemail.get(finalJ).setVisibility(View.GONE);
                        listProf.get(finalJ).setVisibility(View.GONE);
                        listAdr.get(finalJ).setVisibility(View.GONE);
                        listSite.get(finalJ).setVisibility(View.GONE);
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
