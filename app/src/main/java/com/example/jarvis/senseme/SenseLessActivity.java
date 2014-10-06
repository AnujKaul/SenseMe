package com.example.jarvis.senseme;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Random;


public class SenseLessActivity extends Activity implements SensorEventListener{

    private SensorManager senseMan;
    private View view;
    private long lastUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sense_less);
        view =findViewById(R.id.textView);
        view.setBackgroundColor(Color.BLACK);

        senseMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

    }

    public void onSensorChanged(SensorEvent sEvent){
        //detects use of sensor in a particular setting
        if (sEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(sEvent);
        }
        if (sEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            getStepCount(sEvent);
        }

    }

    private void getStepCount(SensorEvent se){

    }


    //registers accelometer event do stuff here for accelometer settings
    private void getAccelerometer(SensorEvent sEvent){
        float[] accelValues =  sEvent.values;

        float x = accelValues[0];
        float y = accelValues[1];
        float z = accelValues[2];


        float accelSqRoot = (x*x + y*y + z*z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = sEvent.timestamp;

        if(accelSqRoot >= 2){
            if (actualTime - lastUpdate < 200){
                return;
            }
            lastUpdate =actualTime;

           // Toast.makeText(this, "Shaking your Booty!", Toast.LENGTH_SHORT).show();

            Random rand = new Random();

            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);

            System.out.println("Values  = > " + r + " " + g+ " " + b);

            view.setBackgroundColor(Color.rgb(r, g, b));


        }


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }


    protected void onResume() {
        super.onResume();
        // registering our class as a listener
        senseMan.registerListener(this, senseMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        senseMan.unregisterListener(this);
    }

}
