package com.projetRobot;

import android.content.Context;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.File;
import java.util.Date;

import com.qihancloud.opensdk.base.BindBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.FaceRecognizeBean;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.qihancloud.opensdk.function.unit.interfaces.media.FaceRecognizeListener;


public class FaceRecognizeActivity extends BindBaseActivity {
    MediaManager mediaManager;
    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imageView = new ImageView(this);
        setContentView(imageView);

        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);


//        /**
//         * 人脸识别
//         */
//       mediaManager.setMediaListener(new FaceRecognizeListener() {
//            @Override
//            public void recognizeResult(FaceRecognizeBean faceRecognizeBean) {
//                Bitmap bitmap = mediaManager.getVideoImage();
//                if(bitmap != null){
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        });
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
