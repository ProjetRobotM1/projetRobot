package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.qihancloud.opensdk.*;

import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;
import com.qihancloud.opensdk.beans.FuncConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 08/05/2018.
 */

public class ListScenarioActivity extends Activity {
    private Context context;
    public static List<Scenario> listScenario ;

    ListView listView ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        listView =  findViewById(R.id.list);
        super.onCreate(savedInstanceState);
        listScenario=new ArrayList<Scenario>();

        ArrayAdapter<Scenario> adapter =new ArrayAdapter<Scenario>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listScenario);
        setContentView(R.layout.listscenario_activity);
        listView.setAdapter(adapter);
        context = this.getApplicationContext();

    }
}

