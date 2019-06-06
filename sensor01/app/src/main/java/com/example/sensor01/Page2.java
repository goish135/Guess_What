package com.example.sensor01;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Page2 extends AppCompatActivity implements SensorEventListener {
    private File file;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private long time;
    private TextView tvv;
    int count = 1; // 題號
    boolean flag1=false;
    String response;
    ArrayList<String> qset = new ArrayList<String>();

    @Override
    public void onSensorChanged(SensorEvent event) {
        // total.setText(Integer.toString(qset.size()));
        TextView tvZ= findViewById(R.id.TextView03);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        if(count<=qset.size())
            tvv.setText(Integer.toString(count)+"題: "+qset.get(count-1));
        else {
            tvv.setText("試題結束");
            mSensorManager.unregisterListener(this,mAccelerometer);
            count = 1 ;
        }
        if (x*y<-35||x*y>35)
            tvZ.setText("上一題");
        else if(z<-5)
        {
            tvZ.setText("Correct");
            flag1 = true;
        }
        else if(z>5)
        {
            tvZ.setText("Pass");
            flag1 = true;
        }
        else if(z>=-1&&z<=1)
        {
            //flag2 = true;
            if(flag1==true)
            {
                count++;
                tvv.setText(Integer.toString(count));
                flag1 = false;
            }
        }


        if(System.currentTimeMillis()-time>60000) {
            mSensorManager.unregisterListener(this,mAccelerometer);
        }

    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_page2);
        //      Toolbar toolbar = findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
        //設定隱藏標題
//        getSupportActionBar().hide();
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        Bundle bundle = getIntent().getExtras();
        response = bundle.getString("type" );
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gamestart();
        tvv = (TextView) findViewById(R.id.tvv);//获取到TextView组件

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }



    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void gamestart(){
        file = new File(getFilesDir(),"Test.json");
        if(!file.exists()) {
            try {
                JSONObject root = new JSONObject();//实例一个JSONObject对象

                //root.put("cat","it");//对其添加一个数据

                JSONArray languages = new JSONArray();//实例一个JSON数组

                languages.put(0, "蜘蛛人");//将lan1对象添加到JSON数组中去，角标为0
                languages.put(1, "鋼鐵人");//将lan2对象添加到JSON数组中去，角标为1
                languages.put(2, "美國隊長");//将lan3对象添加到JSON数组中去，角标为2
                languages.put(3, "黑豹");
                languages.put(3, "雷神索爾");
                root.put("漫威英雄", languages);//然后将JSON数组添加到名为root的JSON对象中去

                FileOutputStream fos = new FileOutputStream(file);//创建一个文件输出流
                fos.write(root.toString().getBytes());//将生成的JSON据写出
                fos.close();//关闭输出流
                Toast.makeText(getApplicationContext(), "创建成功！", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fis = new FileInputStream(file);//获取一个文件输入流
            InputStreamReader isr = new InputStreamReader(fis);//读取文件内容
            BufferedReader bf = new BufferedReader(isr);//将字符流放入缓存中
            String line;//定义一个用来临时保存数据的变量
            StringBuilder sb = new StringBuilder();//实例化一个字符串序列化
            while((line = bf.readLine()) != null){
                sb.append(line);//将数据添加到字符串序列化中
            }

            fis.close();
            isr.close();
            bf.close();
            JSONObject root = new JSONObject(sb.toString());//用JSONObject进行解析
            Iterator<String> sIterator = root.keys();
            //tvv.setText("");
            qset.clear();
            while(sIterator.hasNext()){
                String key = sIterator.next();
                if(response.equals(key))
                {
                    JSONArray array = null;
                    array = root.getJSONArray(key);
                    Random ran = new Random();
                    qset.add(array.get(ran.nextInt(array.length())).toString());
                    for(int i =1;i<array.length();i++)
                    {
                        while(true) {
                            int index = ran.nextInt(array.length());
                            int check = 0;
                            for (int j = 0; j < i; j++) {
                                if (qset.get(j).equals(array.get(index).toString())) {
                                    check = 1;
                                }
                            }
                            if (check == 0) {qset.add(array.get(index).toString()); break;}
                        }
                    }
                }
                //arraySpinner.add(key);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        time=System.currentTimeMillis();
    }


}
