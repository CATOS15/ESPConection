package com.example.esp_connection;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    boolean espConnection = false;
    ConnectionObject esp;


    TextView distance;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        distance = findViewById(R.id.distance);
        button = findViewById(R.id.button);

        final Handler handler = new Handler();

        // On-click listner
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                espConnection = !espConnection;

                // Empty textView
                try {
                    // Setup bluetooth connection to ESP-32
                    if(espConnection){
                        esp = new ConnectionObject();
                        String espDeviceName = "ESP32test";
                        UUID espUUID =  UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
                        ESPLogic.startBT(esp,espDeviceName,espUUID);
                        ESPLogic.readDistance(esp,distance,handler);
                        button.setClickable(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}