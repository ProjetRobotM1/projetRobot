package com.projetRobot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        question = (EditText) findViewById(R.id.addquestion);
        reponse = (EditText) findViewById(R.id.addreponse);
        saveQR = findViewById(R.id.validerQR);
        Intent i = getIntent();
        final int posQR = i.getIntExtra("posQR", -1);
        final int poscenario = i.getIntExtra("poscenario", -1);
        int posfaq = i.getIntExtra("posfaq", -1);
        if (posQR != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            question.setText(listScenario.get(poscenario).getFaq().get(posfaq).getQuestions().get(posQR));
            reponse.setText(listScenario.get(poscenario).getFaq().get(posfaq).getReponses().get(posQR));
        }
        saveQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                if (!question.getText().toString().isEmpty() && !reponse.getText().toString().isEmpty()) {
                    if (posQR == -1) {
                        ArrayList<Scenario> listscenario = getListScenario();

                        int poscenario = i.getIntExtra("poscenario", -1);
                        System.out.println("POS SCENARIO =" + poscenario);
                        int posFaq = i.getIntExtra("posfaq", -1);
                        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                        System.out.println("POS FAQ =" + posFaq);
                        Faq faq = listscenario.get(poscenario).getFaq().get(posFaq);
                        ArrayList<String> questiontoadd = faq.getQuestions();
                        questiontoadd.add(question.getText().toString());
                        ArrayList<String> reponsetoadd = faq.getReponses();
                        reponsetoadd.add(reponse.getText().toString());
                        listscenario.get(poscenario).getFaq().remove(posFaq);
                        listscenario.get(poscenario).getFaq().add(posFaq, faq);
                        setListScenario(listscenario);
                        Intent returntolist = new Intent(context, ListQRActivity.class);
                        returntolist.putExtra("poscenario", poscenario);
                        returntolist.putExtra("posfaq", posFaq);
                        returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                        startActivity(returntolist);
                    } else {
                        ArrayList<Scenario> listscenario = getListScenario();

                        int poscenario = i.getIntExtra("poscenario", -1);
                        int posFaq = i.getIntExtra("posfaq", -1);
                        Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                        listscenario.get(poscenario).getFaq().get(posFaq).getQuestions().remove(posQR);
                        listscenario.get(poscenario).getFaq().get(posFaq).getReponses().remove(posQR);
                        listscenario.get(poscenario).getFaq().get(posFaq).getQuestions().add(posQR, question.getText().toString());
                        listscenario.get(poscenario).getFaq().get(posFaq).getReponses().add(posQR, reponse.getText().toString());
                        setListScenario(listscenario);
                        Intent returntolist = new Intent(context, ListQRActivity.class);
                        returntolist.putExtra("poscenario", poscenario);
                        returntolist.putExtra("posfaq", posFaq);
                        returntolist.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                        startActivity(returntolist);


                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateFAQActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer une question ET une réponse")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    onRestart();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }


        });
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
