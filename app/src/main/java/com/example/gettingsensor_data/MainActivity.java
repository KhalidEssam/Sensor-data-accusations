package com.example.gettingsensor_data;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private TextView GyroView;
    private SensorManager sensorManager;
    private SensorManager sensorManager1;
    private Sensor sensor;
    private Sensor sensor_acc;
    private Sensor sensor_gyro;
    private int start;
    private EditText textmsg;
    private EditText filename;
    private EditText classlabel;
    private EditText USERID;
    public String acc="";
    Editable name;
    Editable numb;
    public String gyro="";
    static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_accelerometer);
        GyroView = findViewById(R.id.text_Gyroscope);

        //accelerometer
        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener( MainActivity.this, sensor, sensorManager1.SENSOR_DELAY_NORMAL);

        //Gyroscope
        sensorManager1 =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager1.registerListener( MainActivity.this, sensor_gyro, sensorManager1.SENSOR_DELAY_NORMAL);



    }
//    /data/data/com.example.gettingsensor_data/files
//    E:\try\app\src\androidTest\java\com\example\gettingsensor_data
    public void writefiles(Editable filename) {
        // add-write text into file
        try {
            FileOutputStream acc_out=openFileOutput( filename+"_ACC.txt", MODE_PRIVATE);
            OutputStreamWriter acc_Writer=new OutputStreamWriter(acc_out);
            acc_Writer.write(acc);
            acc_Writer.close();




            FileOutputStream gyro_out=openFileOutput(filename+"_GYRO.txt", MODE_PRIVATE);
            OutputStreamWriter gyro_writer=new OutputStreamWriter(gyro_out);
            gyro_writer.write(gyro);
            gyro_writer.close();



            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (start == 1) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                textView.setText(sensorEvent.values[0] + "\n" + sensorEvent.values[1] + "\n" + sensorEvent.values[2]);
                acc+= sensorEvent.values[0] + "," + sensorEvent.values[1] + "," +  sensorEvent.values[2] + "," + USERID.getText() + "," +  classlabel.getText() +"\n" ;
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                GyroView.setText(sensorEvent.values[0] + "\n" + sensorEvent.values[1] + "\n" + sensorEvent.values[2]);
                gyro+= sensorEvent.values[0] + "," + sensorEvent.values[1] + "," +  sensorEvent.values[2] + "," + USERID.getText() + "," +  classlabel.getText()  +"\n" ;
            }
        }
        if (start == 0) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                textView.setText("Finished");

            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                GyroView.setText("Finished");
            }
        }
    }
//
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
//
    public void onClick(View view) {

       if(start ==0) {
           start = 1;
           filename = (EditText) findViewById(R.id.filename);
            name = filename.getText();

           classlabel = (EditText) findViewById(R.id.Label);
            numb = classlabel.getText();
           USERID = (EditText) findViewById(R.id.USERID);
       }
       else if(start ==1)
       {
           start = 2;
       }
       if(start ==2)
       {
           start = 0;
           writefiles(name);
           acc="";
           gyro="";
       }

    }
}