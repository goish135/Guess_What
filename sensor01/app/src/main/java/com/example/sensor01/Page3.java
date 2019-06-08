package com.example.sensor01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View.OnClickListener;

public class Page3 extends AppCompatActivity  implements OnClickListener,OnItemSelectedListener{

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
    EditText edt;
    EditText op1;
    EditText  addq;
    EditText pwd;
    String chosetype;
    private LinearLayout my_layout;
    private LinearLayout my_layout2;
    //private LinearLayout big;

    private EditText editText1;
    private Button btn1;
    private Button btn2;
    private static final String TAG = "Page3";

    private int number =-1;
    private ArrayList<EditText> editTexts;
    private ArrayList<Button> updatebutton;
    private ArrayList<Button> deletebutton;
    private ArrayList<LinearLayout> layouts;

    /** Called when the activity is first created. **/
    ArrayAdapter<String> arrayadapter;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        editTexts = new ArrayList<>();


        startButton = findViewById(R.id.button1);
        //startButton.setOnClickListener(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        edt=(EditText) this.findViewById(R.id.editText1);
        //op1=(EditText) this.findViewById(R.id.op1);

        //pass = (TextView) this.findViewById(R.id.pw);

        //addq = (EditText) this.findViewById(R.id.addq);
        addq = (EditText) this.findViewById(R.id.addq);
        //bt_create = (Button) findViewById(R.id.bt_create);
        arraySpinner.add("請選擇");
        //t2 = (TextView) findViewById(R.id.question);
        //total = (TextView) findViewById(R.id.total);
        bt_read = (Button) findViewById(R.id.bt_read);//获取到读取按钮组件
        //tvv = (TextView) findViewById(R.id.tvv);//获取到TextView组件

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



        final Spinner s = (Spinner) findViewById(R.id.spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
                fos.close();//关Toast.makeText(getApplicationContext(), "创建成功！", Toast.LENGTH_SHORT).show();
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
            BufferedReader bf = new BufferedReader(isr);//将字
            // 符流放入缓存中
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



        Button button = findViewById(R.id.deleteType);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = edt.getText().toString();
                if(chosetype.equals("請選擇")) Toast.makeText(Page3.this, "未選擇類型", Toast.LENGTH_SHORT).show();
                else {
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
                    while (true) {
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
                    boolean repeatType = false;
                    //ArrayList<String> arraySpinner = new ArrayList<String>();
                    while (sIterator.hasNext()) {
                        // 获得key
                        String key = sIterator.next();
                        if(!key.equals(chosetype)) {
                            repeatType = true;
                            JSONArray array = null;

                            try {
                                array = root.getJSONArray(key);
                                root2.put(key, array);
                                //arraySpinner.add(key);
                                //Toast.makeText(getApplicationContext(), chosetype+" "+key, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, key+" "+str);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "delete"+chosetype, Toast.LENGTH_SHORT).show();
                            arraySpinner.remove(chosetype);
                            //adapter.remove(arraySpinner.getSelectedItem().toString());
                        }
                        FileOutputStream fos = null;//创建一个文件输出流
                        try {
                            fos = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.write(root2.toString().getBytes());//将生成的JSON数据写出
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.close();//关闭输出流
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Button deleteType = findViewById(R.id.button2);
        deleteType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edt.getText().toString();
                if(str.equals("")) Toast.makeText(Page3.this, "類型不能為空值", Toast.LENGTH_SHORT).show();
                else {
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
                    while (true) {
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
                    boolean repeatType = false;
                    while (sIterator.hasNext()) {
                        // 获得key
                        String key = sIterator.next();
                        if(key.equals(str))
                        {
                            repeatType = true;

                        }
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
                            root2.put(key, array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                    // 寫入 類型 和 (-1)

                    JSONArray languages2 = new JSONArray();

                    try {
                        //String default1  = op1.getText().toString();
                        languages2.put("-1");

                        root2.put(str, languages2);

                        FileOutputStream fos = new FileOutputStream(file);//创建一个文件输出流
                        fos.write(root2.toString().getBytes());//将生成的JSON数据写出
                        fos.close();//关闭输出流
                        if(repeatType==false)
                            arraySpinner.add(str);
                        else
                            Toast.makeText(getApplicationContext(), "重複類型!", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Button addQ= findViewById(R.id.addQ);
        addQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String test  = addq.getText().toString();   // 新增的題目
                String textvalue = chosetype; // 取得所選 的下拉式選單
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
                //tvv.setText("");
                while(sIterator.hasNext()){
                    // 获得key
                    String key = sIterator.next();
                    String value = null;
                    if(key.equals(textvalue))
                    {
                        try {
                            JSONArray array = root.getJSONArray(key);
                            boolean repeat = false;
                            boolean replace  = true;
                            for(int i=0;i<array.length();i++)
                            {

                                if(array.get(i).equals("-1"))
                                {
                                    array.put(i,test);
                                    replace = false;
                                }
                                if(array.get(i).equals(test))
                                {
                                    repeat = true;
                                }

                            }
                            if(test.equals(""))
                            {
                                Toast.makeText(Page3.this, "題目不能為空值", Toast.LENGTH_SHORT).show();
                            }
                            else if(repeat==false&&replace) array.put(test);
                            else if(replace)
                            {
                                Toast.makeText(Page3.this, "題目重複", Toast.LENGTH_SHORT).show();
                            }
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

        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopupWindow(view);

            }
        });






    }

    public void addView(String editview) {

        my_layout = findViewById(R.id.My_layout);
        //my_layout2 = findViewById(R.id.my_layout);
        my_layout2 = new LinearLayout(this);
        my_layout2.setOrientation(LinearLayout.HORIZONTAL);
        // big = findViewById(R.id.big);
        editText1 = new EditText(this);
        btn1 =  new Button(this);
        btn1.setText("更新");

        btn1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btn2 =  new Button(this);
        btn2.setText("刪除");
        btn2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        my_layout2.addView(editText1);
        my_layout2.addView(btn1);
        my_layout2.addView(btn2);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);

        number++;




        editText1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText1.setText(editview);
        editText1.setTop(10);
        editText1.setSingleLine(true);//只有一行
        editText1.setEllipsize(TextUtils.TruncateAt.valueOf("END"));//动隐藏尾部溢出数据，一般用于文字内容过长一行无法全部显示时
        editText1.setMovementMethod(LinkMovementMethod.getInstance());//设置textview滚动事件的
        editTexts.add(number,editText1);
        updatebutton.add(btn1);
        deletebutton.add(btn2);
        layouts.add(my_layout2);
        deletebutton.get(number).setOnClickListener(new View.OnClickListener() {
            int num = number;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                EditText editText = editTexts.get(num);
                my_layout2.removeView(editText);
                //editTexts.remove(editText);

                Button button1 = updatebutton.get(num);
                my_layout2.removeView(button1);
                //updatebutton.remove(button1);
                Toast.makeText(getApplicationContext(),"click"+num, Toast.LENGTH_SHORT).show();

                Button button2 = deletebutton.get(num);
                my_layout2.removeView(button2);
                //deletebutton.remove(button2);

                LinearLayout layout22 = layouts.get(num);

                my_layout.removeView(layout22);
                //layouts.remove(layout22 );
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
                    //tvv.setText("");
                    //String value = root.getString(chosetype);
                    JSONArray array = null;
                    array = root.getJSONArray(chosetype);
                    //Log.d(TAG, "刪除"+array.get(num));
                    for(int i=0;i<array.length();i++)
                    {
                        if(editTexts.get(num).getText().toString().equals(array.get(i).toString()))
                        {
                            //Log.d(TAG,"QQQ"+editTexts.get(num).getText().toString());
                            //Log.d(TAG,)
                            array.remove(i);
                        }
                    }
                    root.put(chosetype,array);

                    Log.d(TAG, array.toString());
                    if(array.length()==0)
                    {
                        array.put(0,"-1");
                        Log.d(TAG, "歸零");
                    }
                    FileOutputStream fos = new FileOutputStream(file);//创建一个文件输出流
                    fos.write(root.toString().getBytes());//将生成的JSON据写出
                    fos.close();//关闭输出流

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                number--;
            }
        });
        int num = number ;
        updatebutton.get(number).setOnClickListener(new View.OnClickListener(){
            int num = number ;
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"觸發按鈕"+num+" 更新"+editTexts.get(num).getText().toString(), Toast.LENGTH_SHORT).show();
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
                    //tvv.setText("");
                    //String value = root.getString(chosetype);
                    JSONArray array = null;
                    array = root.getJSONArray(chosetype);

                    // Toast.makeText(getApplicationContext(),num+editTexts.get(num).getText().toString(), Toast.LENGTH_SHORT).show();
                   array.put(num,editTexts.get(num).getText().toString());

                    FileOutputStream fos = new FileOutputStream(file);//创建一个文件输出流
                    fos.write(root.toString().getBytes());//将生成的JSON据写出
                    fos.close();//关闭输出流

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
        });
        Log.d(TAG, "addView: -----------"+number);


        my_layout.addView(my_layout2, my_layout.getChildCount() - 1);

        /*
        my_layout.addView(editText1,0);
        my_layout.addView(btn1,1);
        my_layout.addView(btn2,2);*/
        //big.addView(my_layout);

    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        chosetype = parent.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    protected void onResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
    @SuppressLint("SetTextI18n")

    private void openPopupWindow(View v){
        /*
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
          */
                //if(match) {

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
                        //tvv.setText("");
                        //String value = root.getString(chosetype);
                        JSONArray array = null;
                        if(chosetype.equals("請選擇"))
                        {
                            try {
                                my_layout.removeAllViews();
                            }catch(Exception a)
                            {

                            }
                        }


                        array = root.getJSONArray(chosetype);


                        updatebutton = new ArrayList<>();
                        deletebutton = new ArrayList<>();
                        layouts = new ArrayList<>();

                        updatebutton.clear();
                        deletebutton.clear();
                        layouts.clear();

                        try {
                            my_layout.removeAllViews();
                            my_layout2.removeAllViews();
                            Log.d(TAG, "ZZZZZZ..........Z");
                        }
                        catch(Exception e){
                            // no remove //
                        }
                        number = -1 ;
                        for(int i=0;i<array.length();i++)
                        {
                            //tvv.append(array.get(i).toString()+"\n");
                            String editvalue=array.get(i).toString();
                            addView(editvalue);
                        }


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //my_layout.removeAllViews();
                        for(int i=0;i<editTexts.size();i++)
                        {
                            //Log.d(TAG,"try"+editTexts.get(i).toString() );
                            try {
                                my_layout.removeView(editTexts.get(i));
                                my_layout.removeView(updatebutton.get(i));
                                my_layout.removeView(deletebutton.get(i));
                            }
                            catch(Exception a){
                               // do nothing
                            }
                        }
                        //Log.d(TAG, "ABC...Z"+number);
                    }



    }

    @Override
    public void onClick(View v) {
        String response = t1.getText().toString();

    }

}




