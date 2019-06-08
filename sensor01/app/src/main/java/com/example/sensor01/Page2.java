package com.example.sensor01;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Page2 extends AppCompatActivity implements SensorEventListener {
    private File file;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private long time;
    private TextView tvv;
    private TextView board;
    int count = 1; // 題號
    boolean flag1=false;
    boolean beep = true;
    boolean pass = true;
    String response;
    ArrayList<String> qset = new ArrayList<String>();
    ArrayList<String> record = new ArrayList<String>();
    /*
    private SoundPool mSoundPool;
    private int streamID;
    private HashMap<String, Integer> mSoundMap;
    */
    private SoundPool soundPool;
    private SoundPool soundPool2;
    @Override
    public void onSensorChanged(SensorEvent event) {
        // total.setText(Integer.toString(qset.size()));
        TextView tvZ= findViewById(R.id.TextView03);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        boolean finish = false;

        if(count<=qset.size()){
            tvv.setText("第"+Integer.toString(count)+"題: "+qset.get(count-1));
            Log.d("debug",Integer.toString(count));}
        else {
            finish = true;
            tvv.setText("The End");
            mSensorManager.unregisterListener(this,mAccelerometer);
            count = 1 ;



            tvv.setText("");
            board.setText("");
            for(int i=0;i<qset.size();i++)
            {

                if(record.get(i).equals("1"))
                {
                    //tvv.setTextColor(android.graphics.Color.GREEN);
                    String text = "<font color='green'>"+qset.get(i)+"</font><br>";
                    board.append(Html.fromHtml(text));
                    Log.d("check","123");

                }
                else
                {
                    //tvv.setTextColor(Color.RED);
                    String text = "<font color='red'>"+qset.get(i)+"</font><br>";
                    board.append(Html.fromHtml(text));
                    Log.d("check","456");
                }
            }
            record.clear();
        }
        if (x*y<-35||x*y>35)
            tvZ.setText("上一題");
        else if(z<-5)
        {
            tvZ.setText("Correct");

            //Log.d("check", "correct");
            flag1 = true;

            if(beep)
            {

                try{

                soundPool.play(1,1, 1, 0, 0, 1);

                }
                catch(Exception e)
                {
                   Log.d("mes","播放失敗");
                    //Toast.makeText(getApplicationContext(), "GG", Toast.LENGTH_SHORT).show();
                    tvZ.setText("gg");
                }
                record.add("1");
                beep = false;
            }
        }
        else if(z>5)
        {
            tvZ.setText("Pass");

            flag1 = true;
            if(pass)
            {

                try{

                    soundPool2.play(1,1, 1, 0, 0, 1);

                }
                catch(Exception e)
                {
                    Log.d("mes","播放失敗");
                    //Toast.makeText(getApplicationContext(), "GG", Toast.LENGTH_SHORT).show();
                    tvZ.setText("gg");
                }
                record.add("0");
                beep = false;
            }
            pass = false;
        }
        else if(z>=-1&&z<=1)
        {
            //flag2 = true;
            beep = true;
            if(flag1==true)
            {
                beep = true ;
                pass = true;
                /*
                if(tvZ.getText().toString().equals("Correct"))
                {
                    record.add("1");
                    //streamID = mSoundMap.get("O.mp3");
                    //mSoundPool.play(streamID, 10, 10, 1, 0, 1.0f);
                }
                else
                {
                    record.add("0");
                    //streamID = mSoundMap.get("X.mp3");
                    //mSoundPool.play(streamID, 10, 10, 1, 0, 1.0f);
                }*/
                count++;
                //tvv.setText(Integer.toString(count));
                flag1 = false;
            }
        }


        if(System.currentTimeMillis()-time>60000&&!finish) {
            tvv.setText("遊戲結束");
            mSensorManager.unregisterListener(this,mAccelerometer);

            tvv.setText("");
            board.setText("");

            for(int i=0;i<qset.size();i++)
            {
                //board.append(qset.get(i));
                if(i<record.size())
                {
                    if(record.get(i).equals("1"))
                    {
                        String text = "<font color='green'>"+qset.get(i)+"</font><br>";
                        board.append(Html.fromHtml(text));
                    }
                    else
                    {
                        String text = "<font color='red'>"+qset.get(i)+"</font><br>";
                        board.append(Html.fromHtml(text));
                    }

                }
                else
                {
                    String text = "<font color='red'>"+qset.get(i)+"</font><br>";
                    board.append(Html.fromHtml(text));
                }


            }
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
        board = (TextView) findViewById(R.id.record);
        soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
        soundPool.load(this,R.raw.correct,1);
        soundPool2= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
        soundPool2.load(this,R.raw.pass,1);
        //mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //mSoundMap = new HashMap<>();
        /*
        try {
            streamID = mSoundPool.load(getApplicationContext().getAssets().openFd("beep/O.mp3"), 1);
            mSoundMap.put("beep1.mp3", streamID);
            streamID = mSoundPool.load(getApplicationContext().getAssets().openFd("beep/X.mp3"), 1);
            mSoundMap.put("beep2.mp3", streamID);
            // Log.i(TAG, "onCreate: streamID = " + streamID);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


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

   /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSoundPool.release();
        mSoundPool = null;
    }
    */
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
