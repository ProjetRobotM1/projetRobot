package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Hugo on 27/04/2018.
 */

public class AddActionActivity extends Activity {

    private Button addInter, editInter;
    private Context context;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.addaction_activity);
        context = this.getApplicationContext();
    }
}