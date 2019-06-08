package com.example.sensor01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener,OnItemSelectedListener {
    //private Button bt_create;
    String chosetype;
    Button startButton;
    private Button bt_read;
    private TextView tvv;
    private boolean open = false;
    private long time;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Spinner mSpn; //宣告 mSpn 為 Spinner 物件
    TextView t1;
    TextView t2;
    TextView total;
    TextView pass;
    int count = 1; // 題號
    boolean flag1=false,flag2=false;
    boolean match = false;
    private String str;
    ArrayList<String> qset = new ArrayList<String>();
    Button button1;
    ArrayList<String> arraySpinner = new ArrayList<String>();  //指定是String的型態
    private File file;
    //ArrayList<String> arraySpinner= new ArrayList<String>();

    /*
    String[] arraySpinner = new String[] {
            "1", "2", "3", "4", "5", "6", "7"
    }; */
    EditText edt;
    EditText op1;
    EditText  addq;
    EditText pwd;
    /** Called when the activity is first created. */




    ArrayAdapter<String> arrayadapter;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        //refresh();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        edt=(EditText) this.findViewById(R.id.editText1);
        //op1=(EditText) this.findViewById(R.id.op1);

        //pass = (TextView) this.findViewById(R.id.pw);

        //addq = (EditText) this.findViewById(R.id.addq);
        addq = (EditText) this.findViewById(R.id.addq);
        //bt_create = (Button) findViewById(R.id.bt_create);
        ArrayList<String> arraySpinner = new ArrayList<String>();
        arraySpinner.add("請選擇");
        //t2 = (TextView) findViewById(R.id.question);
        //total = (TextView) findViewById(R.id.total);
        bt_read = (Button) findViewById(R.id.bt_read);//获取到读取按钮组件
        tvv = (TextView) findViewById(R.id.tvv);//获取到TextView组件





        Spinner s = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        //t1=(TextView)findViewById(R.id.tv);
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

            while(sIterator.hasNext()){
                String key = sIterator.next();

                boolean  zero = false;
                JSONArray array = null;
                try {
                    array = root.getJSONArray(key);
                    if(array.get(0).equals("-1"))
                    {
                        zero  = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(zero!=true)
                  arraySpinner.add(key);

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

        Button nextPageBtn = (Button)findViewById(R.id.editQ);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , Page3.class);
                startActivity(intent);

            }
        });












    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        chosetype = parent.getSelectedItem().toString();
        //t1.setText(parent.getSelectedItem().toString());
        //t2.setText(arraySpinner.get(0));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    protected void onResume() {
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
        onCreate(null);
    }
        //@SuppressLint("SetTextI18n")
        private void refresh() {
            finish();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }



    @Override
    public void onClick(View v) {
            String response = chosetype;
            Intent intent = new Intent();
            intent.setClass(MainActivity.this  , Page2.class);
            Bundle bundle = new Bundle();
            bundle.putString("type",response);
            intent.putExtras(bundle);
            startActivity(intent);
    }

}
