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

public class CreateScenarioActivity extends Activity {
    private Context context;
    private EditText scenario;

    private Button validerScenario, ajouterFAQ,ajouterGroupePersonne,ajouterGroupeArticle,ajouterPT;
    private ListView listViewFAQ,listViewGroupePersonne,listViewGroupeArticle,presTexte;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createscenario_activity);
        Intent i = getIntent();
        Scenario scenariocree = new Scenario();
        int poscenario = i.getIntExtra("poscenario", -1);
        if (poscenario != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            scenariocree = listScenario.get(poscenario);
        } else {
            ArrayList<Scenario> listScenario = getListScenario();
            listScenario.add(scenariocree);
            setListScenario(listScenario);
        }


        scenario = (EditText) findViewById(R.id.newScenario);

        if (poscenario != -1) {
            ArrayList<Scenario> listScenario = getListScenario();
            scenario.setText(listScenario.get(poscenario).getName());
        }

        context = this.getApplicationContext();
        listViewFAQ = findViewById(R.id.listViewFAQ);
        presTexte=findViewById(R.id.textPT);
        validerScenario = findViewById(R.id.validerScenario);
        ajouterFAQ = findViewById(R.id.addFAQ);
        ajouterPT=findViewById(R.id.addPT);
        ajouterGroupeArticle=findViewById((R.id.addGroupeArticle));
        ajouterGroupePersonne=findViewById((R.id.addGroupePersonne));
        listViewGroupePersonne=findViewById((R.id.listViewGroupePersonne));
        listViewGroupeArticle=findViewById((R.id.listViewGroupeArticle));
        ArrayList<Faq> listFAQ = new ArrayList<>();
        ArrayList<String>listPT=new ArrayList<>();
        ArrayList<ConteneurListArticle> list_listArticle = new ArrayList<>();
        ArrayList<ConteneurListPersonne> list_listPersonne = new ArrayList<>();

        if (scenariocree.getFaq() != null) {
            listFAQ = scenariocree.getFaq();
        }
        if (scenariocree.getPresTexte()!=null&&!scenariocree.getPresTexte().equals("")) {
            listPT.add("PRESENTATION TEXTE");
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
        ArrayAdapter<String> adapterPT = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, listPT);
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
        presTexte.setAdapter(adapterPT);
        presTexte.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(presTexte);
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
                Intent i = new Intent(context, ListQRActivity.class);
                i.putExtra("poscenario", poscenario);
                i.putExtra("posfaq", position);
                i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(i);


            }
        });
        presTexte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position,long itemID) {
                ArrayList<Scenario> listScenario = getListScenario();
                Intent Intentgetpos = getIntent();
                int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                Scenario scenarioSauvegarde = (Scenario)Intentgetpos.getSerializableExtra("scenarioSauvegarde");
                Intent i = new Intent(context, CreatePTexteActivity.class);
                i.putExtra("poscenario", poscenario);
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
                Intent i = new Intent(context, ListGrPActivity.class);
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
                Intent i = new Intent(context, ListGrAActivity.class);
                i.putExtra("poscenario", poscenario);
                i.putExtra("posGrA", position);
                i.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                startActivity(i);


            }
        });
        registerForContextMenu(listViewFAQ);
        registerForContextMenu(listViewGroupePersonne);
        registerForContextMenu(listViewGroupeArticle);
        registerForContextMenu(presTexte);


        validerScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Scenario> listScenToUpdate = getListScenario();
                Intent i = getIntent();
                Scenario scenariocree = new Scenario();
                int poscenario = i.getIntExtra("poscenario", -1);
                if (poscenario == -1) {
                    ArrayList<Scenario> listScenario = getListScenario();
                    poscenario = listScenario.size() - 1;
                    scenariocree = listScenario.get(poscenario);
                } else {
                    ArrayList<Scenario> listScenario = getListScenario();
                    scenariocree = listScenario.get(poscenario);

                }

                if (!scenario.getText().toString().isEmpty()) {

                    scenariocree.setName(scenario.getText().toString());
                    listScenToUpdate.remove(poscenario);
                    listScenToUpdate.add(poscenario, scenariocree);
                    setListScenario(listScenToUpdate);
                    Toast.makeText(getApplicationContext(), "Scénario enregistré", Toast.LENGTH_LONG).show();
                    Intent returntolauncher = new Intent(context,LauncherActivity.class);
                    startActivity(returntolauncher);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateScenarioActivity.this);
                    builder.setTitle("ERREUR")
                            .setMessage("Vous devez entrer un nom pour votre scénario")
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
        ajouterFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListQRActivity.class);
                ArrayList<Scenario> listScenario = getListScenario();
                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }

                myIntent.putExtra("pos", -1);
                myIntent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                Scenario scenariocree = new Scenario();

                myIntent.putExtra("poscenario", poscenario);

                scenariocree.setName("TEST");
                System.out.println("intent scenario: " + scenariocree.toString());
                startActivity(myIntent);
            }
        });
        if (scenariocree.getPresTexte()!=null){

        if(!scenariocree.getPresTexte().equals("")){
            ajouterPT.setVisibility(View.GONE);
        }}
        ajouterPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, CreatePTexteActivity.class);
                ArrayList<Scenario> listScenario = getListScenario();
                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                myIntent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                myIntent.putExtra("poscenario", poscenario);
                startActivity(myIntent);
            }
        });
        ajouterGroupeArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListGrAActivity.class);
                ArrayList<Scenario> listScenario = getListScenario();
                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                myIntent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                myIntent.putExtra("poscenario", poscenario);
                startActivity(myIntent);
            }
        });
        ajouterGroupePersonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ListGrPActivity.class);
                ArrayList<Scenario> listScenario = getListScenario();
                Intent i = getIntent();
                int poscenario = i.getIntExtra("poscenario", -1);
                Scenario scenarioSauvegarde = (Scenario)i.getSerializableExtra("scenarioSauvegarde");
                if (poscenario == -1) {

                    poscenario = listScenario.size() - 1;
                }
                myIntent.putExtra("scenarioSauvegarde",scenarioSauvegarde);
                myIntent.putExtra("poscenario", poscenario);
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewFAQ) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer FAQ");

        }
        if (v.getId() == R.id.listViewGroupeArticle) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer groupe article");

        }
        if (v.getId() == R.id.listViewGroupePersonne) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer groupe personne");

        }
        if (v.getId() == R.id.textPT) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Supprimer Presentation Texte");

        }



    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Supprimer FAQ") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
            if (poscenario == -1) {

                poscenario = listScenario.size() - 1;
            }
            listScenario.get(poscenario).getFaq().remove(info.position);
            setListScenario(listScenario);


            Toast.makeText(getApplicationContext(), "FAQ supprimé avec succès", Toast.LENGTH_LONG).show();
            recreate();
        }
        if (item.getTitle() == "Supprimer groupe personne") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
            if (poscenario == -1) {

                poscenario = listScenario.size() - 1;
            }
            listScenario.get(poscenario).getList_listPersonne().remove(info.position);
            setListScenario(listScenario);


            Toast.makeText(getApplicationContext(), "Groupe supprimé avec succès", Toast.LENGTH_LONG).show();
            recreate();
        }
        if (item.getTitle() == "Supprimer groupe article") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
            if (poscenario == -1) {

                poscenario = listScenario.size() - 1;
            }
            listScenario.get(poscenario).getList_listArticle().remove(info.position);
            setListScenario(listScenario);


            Toast.makeText(getApplicationContext(), "Groupe supprimé avec succès", Toast.LENGTH_LONG).show();
            recreate();
        }  if (item.getTitle() == "Supprimer Presentation Texte") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            ArrayList<Scenario> listScenario = getListScenario();
            Intent Intentgetpos = getIntent();
            int poscenario = Intentgetpos.getIntExtra("poscenario", -1);
            if (poscenario == -1) {

                poscenario = listScenario.size() - 1;
            }
            listScenario.get(poscenario).setPresTexte("");
            setListScenario(listScenario);


            Toast.makeText(getApplicationContext(), "Presentation Texte supprimée avec succès", Toast.LENGTH_LONG).show();
            recreate();
        }
        else {
            return false;
        }
        return true;
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

    @Override

    public void onBackPressed() {
        Intent getActivity = getIntent();
        ArrayList<Scenario> listScenario = getListScenario();
        int poscenario = getActivity.getIntExtra("poscenario", -1);
        if (poscenario == -1) {

            poscenario = listScenario.size() - 1;
        }
        Scenario scenarioSauvegarde = (Scenario)getActivity.getSerializableExtra("scenarioSauvegarde");
        if (getActivity.getIntExtra("activity", -1) == 0 ||scenarioSauvegarde==null) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Annulation")
                    .setMessage("Voulez vous annuler la création de scénario ? (scénario non validé)")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<Scenario> listScenario = getListScenario();
                            Intent i = getIntent();
                            int poscenario = i.getIntExtra("poscenario", -1);
                            if (poscenario == -1) {

                                poscenario = listScenario.size() - 1;
                            }
                            listScenario.remove(poscenario);
                            setListScenario(listScenario);
                            Intent myIntent = new Intent(context, LauncherActivity.class);
                            startActivity(myIntent);
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();
        }

        System.out.println( "normalement 1 :"+getActivity.getIntExtra("activity", -1));

        setListScenario(listScenario);
        if (getActivity.getIntExtra("activity", -1) == 1 || getActivity.getIntExtra("activity", -1)==-1 && scenarioSauvegarde!=null ) {
            System.out.println( "normalement true :" +scenarioSauvegarde.equals(listScenario.get(poscenario)));
            if(scenarioSauvegarde.equals(listScenario.get(poscenario))){


            listScenario.remove(poscenario);
            scenarioSauvegarde = (Scenario)getActivity.getSerializableExtra("scenarioSauvegarde");
            listScenario.add(poscenario,scenarioSauvegarde);
            setListScenario(listScenario);
            Intent myIntent = new Intent(context, ListScenarioActivity.class);
            startActivity(myIntent);

        }else{
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Annulation")
                        .setMessage("Voulez vous annuler la modification du scénario ? (modifications non validées)")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ArrayList<Scenario> listScenario = getListScenario();

                                Intent i = getIntent();
                                int poscenario = i.getIntExtra("poscenario", -1);
                                if (poscenario == -1) {

                                    poscenario = listScenario.size() - 1;
                                }
                                Scenario scenarioSauvegarde = (Scenario) i.getSerializableExtra("scenarioSauvegarde");
                                listScenario.remove(poscenario);
                                listScenario.add(poscenario, scenarioSauvegarde);
                                setListScenario(listScenario);
                                Intent myIntent = new Intent(context, ListScenarioActivity.class);
                                startActivity(myIntent);
                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
    }
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

}

