package com.projetRobot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by Hugo on 08/05/2018.
 */

public class DisplayScenarioActivity extends Activity {
    private Context context;
    private TextView scenario;
    private ListView listViewFAQ,listViewGroupePersonne,listViewGroupeArticle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayscenario_activity);
        Intent i = getIntent();
        int poscenario = i.getIntExtra("poscenario", -1);
        Scenario scenariocree=getListScenario().get(poscenario);


        scenario =  findViewById(R.id.nomScenario);
        scenario.setText(getListScenario().get(poscenario).getName());
        context = this.getApplicationContext();
        listViewFAQ = findViewById(R.id.listViewFAQ);


        listViewGroupePersonne=findViewById((R.id.listViewGroupePersonne));
        listViewGroupeArticle=findViewById((R.id.listViewGroupeArticle));
        ArrayList<Faq> listFAQ = new ArrayList<>();
        ArrayList<ConteneurListArticle> list_listArticle = new ArrayList<>();
        ArrayList<ConteneurListPersonne> list_listPersonne = new ArrayList<>();

        if (scenariocree.getFaq() != null) {
            listFAQ = scenariocree.getFaq();
        }
        ArrayList<String> listtoString = new ArrayList<>();
        for (int j = 0; j < listFAQ.size(); j++) {
            listtoString.add("FAQ " + (j + 1));
        }
        if(scenariocree.getList_listArticle() !=null){
            list_listArticle=scenariocree.getList_listArticle();
        }
        if(scenariocree.getList_listPersonne() !=null){
            list_listPersonne=scenariocree.getList_listPersonne();

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, listtoString);
        ArrayAdapter<ConteneurListArticle> adapterlistArticle = new ArrayAdapter<ConteneurListArticle>(context,
                android.R.layout.simple_list_item_1,android.R.id.text1,list_listArticle);
        ArrayAdapter<ConteneurListPersonne> adapterlistPersonne = new ArrayAdapter<ConteneurListPersonne>(context,
                android.R.layout.simple_list_item_1,android.R.id.text1,list_listPersonne);
        listViewFAQ.setAdapter(adapter);
        listViewFAQ.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listViewFAQ);

        listViewGroupePersonne.setAdapter(adapterlistPersonne);
        listViewGroupePersonne.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listViewGroupePersonne);
        listViewGroupeArticle.setAdapter(adapterlistArticle);
        listViewGroupeArticle.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listViewGroupeArticle);
        listViewFAQ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                ArrayList<Scenario> listScenario = getListScenario();
                Intent Intentgetpos = getIntent();
                int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                Scenario scenarioSauvegarde = (Scenario)Intentgetpos.getSerializableExtra("scenarioSauvegarde");
                Intent i = new Intent(context, DisplayFAQActivity.class);
                i.putExtra("poscenario", poscenario);
                i.putExtra("posfaq", position);
                i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(i);


            }
        });
        listViewGroupePersonne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                ArrayList<Scenario> listScenario = getListScenario();
                Intent Intentgetpos = getIntent();
                int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                Scenario scenarioSauvegarde = (Scenario)Intentgetpos.getSerializableExtra("scenarioSauvegarde");
                Intent i = new Intent(context, DisplayListePersonneActivity.class);
                i.putExtra("poscenario", poscenario);
                i.putExtra("posGrP", position);
                i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(i);


            }
        });
        listViewGroupeArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                ArrayList<Scenario> listScenario = getListScenario();
                Intent Intentgetpos = getIntent();
                int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                Scenario scenarioSauvegarde = (Scenario)Intentgetpos.getSerializableExtra("scenarioSauvegarde");
                Intent i = new Intent(context, DisplayListeArticleActivity.class);
                i.putExtra("poscenario", poscenario);
                i.putExtra("posGrA", position);
                i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(i);


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
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    @Override

    public void onBackPressed() {
        Intent returntoListe = new Intent(context, ListScenarioActivity.class);
        startActivity(returntoListe);
    }

}

