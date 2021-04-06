package com.example.esp_connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.os.Handler;
import android.widget.TextView;

public class ESPLogic {

    static void startBT(ConnectionObject object,String bluetoothDevice,UUID uuid) throws Exception {
        // Instantiate bluetooth
        object.setBluetoothAdapter(BluetoothAdapter.getDefaultAdapter());
        if (object.getBluetoothAdapter() == null) {
            System.out.println("Device doen't support bluetooth");
        }

        // Get esp device
        Set<BluetoothDevice> pairedDevices = object.getBluetoothAdapter().getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                String deviceName = device.getName();
                if(deviceName.equals(bluetoothDevice))
                {
                    object.setDevice(device);
                    break;
                }
            }
        }
        object.setSocket(object.getDevice().createInsecureRfcommSocketToServiceRecord(uuid));
        try{
            object.getSocket().connect();
            object.setOutputStream(object.getSocket().getOutputStream());
            object.setInputStream(object.getSocket().getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static void stopBT(ConnectionObject object) throws Exception {

        if(object == null){
            try{
                object.getReadThread().stop();
                object = null;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }





    static int readDistance(ConnectionObject espConnection, TextView distance, Handler handler){
        final byte delimiter = 10;

        espConnection.setReadBufferPosition(0);
        espConnection.setReadBuffer(new byte[1024]);


        espConnection.setReadThread(new Thread(() -> {
            while(!Thread.currentThread().isInterrupted() && !espConnection.isStopReadWorker()){
                try{
                    int bytesAvailable = espConnection.getInputStream().available();
                    if(bytesAvailable > 0){
                        byte[] packetBytes = new byte[bytesAvailable];
                        espConnection.getInputStream().read(packetBytes);
                        for(int i=0;i<bytesAvailable;i++){
                            byte b = packetBytes[i];
                            if(b == delimiter){
                                byte[] encodedBytes = new byte[espConnection.getReadBufferPosition()];
                                System.arraycopy(espConnection.getReadBuffer(), 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                                espConnection.setReadBufferPosition(0);

                                handler.post(new Runnable()
                                {
                                    public void run()
                                    {
                                        distance.setText(data);
                                    }
                                });
                            }  else {
                                int currentPosition = espConnection.getReadBufferPosition() + 1;
                                espConnection.setReadBufferPosition(currentPosition);
                                espConnection.getReadBuffer()[espConnection.getReadBufferPosition()] = b;
                            }
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    espConnection.setStopReadWorker(true);
                }
            }
        }));

        espConnection.getReadThread().start();
        return 0;
    }




}
