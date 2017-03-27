package com.example.yls.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import static com.example.yls.baidumap.R.id.btn_location;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextureMapView mMapView;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private Button btnLoc;
    private PoiSearch mPoiSearch;
    private EditText edtKey;
    private Button btnSearch;
    private Button btnPanorama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题栏


        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);


        initLocation();
        //mLocationClient.start();
        location();


        btnLoc = (Button) findViewById(btn_location);
        edtKey = (EditText) findViewById(R.id.edt_key);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnPanorama = (Button) findViewById(R.id.btn_panorama);
        btnPanorama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PanoramaActivity.class);
                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String textKey = edtKey.getText().toString().trim();
                mPoiSearch  = PoiSearch.newInstance();
                OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {
                        if (poiResult.getAllAddr() == null){
                            Toast.makeText(MainActivity.this, "请输入正确的检索地址", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //获取POI检索结果
                        //Toast.makeText(MainActivity.this,"11"+poiResult.getAllAddr(),Toast.LENGTH_SHORT).show();
                        //String poiname = poiResult.getAllPoi().get(0).name;
                        String poiadd = poiResult.getAllPoi().get(0).address;
                        String idString = poiResult.getAllPoi().get(0).uid;
                        Toast.makeText(MainActivity.this, "检索"+poiadd, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onGetPoiResult: "+idString);
                        /*textView.setText(
                                "第一条结果是：\n名称＝［"+
                                        poiname+
                                        "］\nID = ["+
                                        idString
                                        + "] \n地址＝［"+
                                        poiadd+
                                        "］");*/
                    }
                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                        //获取Place详情页检索结果
                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                    }
                };
                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city("广州").keyword(textKey).pageNum(10));

               // mPoiSearch.destroy();
            }
        });
        btnLoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              /*mPoiSearch  = PoiSearch.newInstance();
                OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {
                        //获取POI检索结果
                        Toast.makeText(MainActivity.this,"11"+poiResult.getAllAddr(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                        //获取Place详情页检索结果
                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                    }
                };
                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city("广州").keyword("学院").pageNum(10));
                mPoiSearch.destroy();*/



                /*MyLocationData locData = new MyLocationData.Builder().
                        direction(100).latitude(23.187858).longitude(113.360547).build();
                mMapView.getMap().setMyLocationData(locData);
                LatLng ll = new LatLng(23.187858, 113.360547);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mMapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
*/
                location();


            }
        });
    }

    private void location() {
        mLocationClient.start();
        Log.d(TAG, "onClick: mLocationClient.start();");
        BDLocation bd = new BDLocation();
        Log.d(TAG, "onClick: " + bd.getAltitude());
        Log.d(TAG, "onClick: " + bd.getLongitude());
        MyLocationData locData = new MyLocationData.Builder().
                direction(100).latitude(bd.getAltitude()).longitude(bd.getLongitude()).build();
        mMapView.getMap().setMyLocationData(locData);
        LatLng ll = new LatLng(23.187858, 113.360547);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mMapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}

