package com.projetRobot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import com.qihancloud.opensdk.*;

import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;
import com.qihancloud.opensdk.beans.FuncConstant;
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