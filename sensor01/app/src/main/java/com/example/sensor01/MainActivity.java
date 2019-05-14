package com.example.sensor01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

public class MainActivity extends Activity implements SensorEventListener,OnClickListener,OnItemSelectedListener {
    //private Button bt_create;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        edt=(EditText) this.findViewById(R.id.editText1);
        op1=(EditText) this.findViewById(R.id.op1);

        pass = (TextView) this.findViewById(R.id.pw);

        //addq = (EditText) this.findViewById(R.id.addq);
        addq = (EditText) this.findViewById(R.id.addq);
        //bt_create = (Button) findViewById(R.id.bt_create);
        arraySpinner.add("請選擇");
        t2 = (TextView) findViewById(R.id.question);
        total = (TextView) findViewById(R.id.total);
        bt_read = (Button) findViewById(R.id.bt_read);//获取到读取按钮组件
        tvv = (TextView) findViewById(R.id.tvv);//获取到TextView组件

        //t2 = (TextView) findViewById(R.id.tv2);
        //arraySpinner.add("2");
        //arraySpinner.add("3");
        /*
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        */



        Spinner s = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        t1=(TextView)findViewById(R.id.tv);
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
            tvv.setText("");
            while(sIterator.hasNext()){
                String key = sIterator.next();
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

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //arraySpinner.add(edt.getText().toString());
                String str = edt.getText().toString();
                JSONObject root2 = new JSONObject();
                FileInputStream fis = null;//获取一个文件输入流
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                InputStreamReader isr = new InputStreamReader(fis);//读取文件内容
                BufferedReader bf = new BufferedReader(isr);//将字符流放入缓存中
                String line = "";//定义一个用来临时保存数据的变量
                StringBuilder sb = new StringBuilder();//实例化一个字符串序列化
                while(true){
                    try {
                        if (!((line = bf.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(line);//将数据添加到字符串序列化中
                }
                //关闭流
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject root = null;//用JSONObject进行解析
                try {
                    root = new JSONObject(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator<String> sIterator = root.keys();
                while(sIterator.hasNext()){
                    // 获得key
                    String key = sIterator.next();
                    // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                    String value = null;
                    try {
                        value = root.getString(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("key: "+key+",value"+value);
                    //t2.append("key:"+key+"value:"+value+"\n");
                    //tvv.append("key:"+key+"value:"+value+"\n");
                    JSONArray array = null;
                    try {
                        array = root.getJSONArray(key);
                        root2.put(key,array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*
                    for (int i=0; i<array.length(); i++){
                        try {
                            arraySpinner.add(array.get(i).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }*/
                }


                JSONArray languages2 = new JSONArray();
                try {
                    String default1  = op1.getText().toString();
                    languages2.put(default1);
                    //languages2.put(1,"test2");
                    //languages2.put(2,"test3");
                    root2.put(str,languages2);
                    arraySpinner.add(str);
                    FileOutputStream fos = new FileOutputStream(file);//创建一个文件输出流
                    fos.write(root2.toString().getBytes());//将生成的JSON数据写出
                    fos.close();//关闭输出流
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        Button button3= findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            //移除會從第一組開始移除由上往下
            @Override
            public void onClick(View v) {
                    arraySpinner.remove(t1.getText().toString());
            }
        }); */
        Button addQ= findViewById(R.id.addQ);
        addQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String test  = addq.getText().toString();   // 新增的題目
                String textvalue = t1.getText().toString(); // 取得所選 的下拉式選單
                FileInputStream fis = null;//获取一个文件输入流
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                InputStreamReader isr = new InputStreamReader(fis);//读取文件内容
                BufferedReader bf = new BufferedReader(isr);//将字符流放入缓存中
                String line = "";//定义一个用来临时保存数据的变量
                StringBuilder sb = new StringBuilder();//实例化一个字符串序列化
                while(true){
                    try {
                        if (!((line = bf.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(line);//将数据添加到字符串序列化中
                }
                //关闭流
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject root = null;//用JSONObject进行解析
                try {
                    root = new JSONObject(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator<String> sIterator = root.keys();
                tvv.setText("");
                while(sIterator.hasNext()){
                    // 获得key
                    String key = sIterator.next();
                    String value = null;
                    if(key.equals(textvalue))
                    {
                        try {
                            JSONArray array = root.getJSONArray(key);
                            array.put(test);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
                FileOutputStream fos = null;//创建一个文件输出流
                try {
                    fos = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(root.toString().getBytes());//将生成的JSON据写出
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();//关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //file = new File(getFilesDir(),"Test.json");
        /*
        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopupWindow(view);

            }
        });



    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        t1.setText(parent.getSelectedItem().toString());
        //t2.setText(arraySpinner.get(0));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        total.setText(Integer.toString(qset.size()));
        TextView tvX= findViewById(R.id.TextView01);
        TextView tvY= findViewById(R.id.TextView02);
        TextView tvZ= findViewById(R.id.TextView03);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if(open) {
            tvX.setText("X="+x);
            tvY.setText("Y="+y);
            tvZ.setText("Z="+z);
            if(count<=qset.size())
                t2.setText(Integer.toString(count)+"題: "+qset.get(count-1));
            else {
                t2.setText("試題結束");
                mSensorManager.unregisterListener(this,mAccelerometer);
                open = false;
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
                    t2.setText(Integer.toString(count));
                    flag1 = false;
                }
            }

            if(System.currentTimeMillis()-time>60000) {
                mSensorManager.unregisterListener(this,mAccelerometer);
                open = false;
            }
        }
    }
    private void openPopupWindow(View v){
        LayoutInflater layoutInflater = (LayoutInflater)
                getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                300,500
        );
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        pwd = (EditText) popupView.findViewById(R.id.pwd);
        Button buttonClose = (Button)popupView.findViewById(R.id.closepopupwindow);
        buttonClose.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pass.setText(pwd.getText().toString());
                if(pwd.getText().toString().equals("1125135"))
                {
                    match = true;
                    pass.setText("密碼正確");
                }
                popupWindow.dismiss();
                if(match) {

                    try {
                        FileInputStream fis = new FileInputStream(file);//获取一个文件输入流
                        InputStreamReader isr = new InputStreamReader(fis);//读取文件内容
                        BufferedReader bf = new BufferedReader(isr);//将字符流放入缓存中
                        String line;//定义一个用来临时保存数据的变量
                        StringBuilder sb = new StringBuilder();//实例化一个字符串序列化
                        while ((line = bf.readLine()) != null) {
                            sb.append(line);//将数据添加到字符串序列化中
                        }
                        //关闭流
                        fis.close();
                        isr.close();
                        bf.close();
                        JSONObject root = new JSONObject(sb.toString());//用JSONObject进行解析
                        Iterator<String> sIterator = root.keys();
                        tvv.setText("");
                        while (sIterator.hasNext()) {
                            // 获得key
                            String key = sIterator.next();
                            //arraySpinner.add(key);
                            // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                            String value = root.getString(key);
                            //System.out.println("key: "+key+",value"+value);
                            //t2.append("key:"+key+"value:"+value+"\n");
                            tvv.append("key:" + key + "value:" + value + "\n");
                        /*
                        JSONArray array = root.getJSONArray(key);
                        for (int i=0; i<array.length(); i++){
                            arraySpinner.add(array.get(i).toString());
                        }*/
                        }



                    /*
                    String cat = root.getString("cat");//获取字符串类型的键值对
                    //String catt= root.keys().toString();
                    //tvv.append(catt);
                    tvv.append("cat"+"="+cat+"\n");//显示数据
                    tvv.append("---------------"+"\n");//分割线
                    */
                        JSONArray array = root.getJSONArray("languages2");//获取JSON数据中的数组数据
                        tvv.setText(Integer.toString(array.length()) + "\n");
                        for (int i = 0; i < array.length(); i++) {
                            //JSONObject object = array.getJSONObject(i);//遍历得到数组中的各个对象
                        /*
                        int id = object.getInt("id");//获取第一个值
                        String ide = object.getString("ide");//获取第二个值
                        String name = object.getString("name");//获取第三个值
                        tvv.append("id"+"="+id+"\n");//显示数据
                        tvv.append("ide"+"="+ide+"\n");//显示数据
                        tvv.append("name"+"="+name+"\n");//显示数据
                        tvv.append("---------------"+"\n");//分割线
                        arraySpinner.add(ide+"\n");*/
                            //arraySpinner.add(array.get(i)+"\n");
                            //tvv.append(array.get(i)+"\n");
                        }
                        //tvv.append("Hello");
                        match = false;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }});

        popupWindow.showAsDropDown(startButton);
        popupWindow.setFocusable(true);
        popupWindow.update();

    }
    @Override
    public void onClick(View v) {
        if(!open)
        {
            String response = t1.getText().toString();
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
                tvv.setText("");
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
            open=true;

        }
    }
}
