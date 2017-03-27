package com.example.yls.baidumap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.location.BDLocation;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class PanoramaActivity extends AppCompatActivity {
    private PanoramaView mPanoramaView;
    private BMapManager mBMapMan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/*
        mBMapMan=new BMapManager(getApplication());
        mBMapMan.init("pCGn2cxoqTTLpVmGOo5GFLe8WaD5Ej1C", null);*/

        setContentView(R.layout.activity_panorama);
        mPanoramaView = (PanoramaView) findViewById(R.id.panorama);


        DemoApplication app = (DemoApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);

            app.mBMapManager.init(new DemoApplication.MyGeneralListener());
        }
        BDLocation bd = new BDLocation();
        double al = bd.getAltitude();
        double lt = bd.getLongitude();
        mPanoramaView.setPanorama(al,lt);

    }
    @Override
    protected void onPause() {
        super.onPause();
        mPanoramaView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoramaView.onResume();
    }

    @Override
    protected void onDestroy() {
        mPanoramaView.destroy();
        super.onDestroy();
    }
}
