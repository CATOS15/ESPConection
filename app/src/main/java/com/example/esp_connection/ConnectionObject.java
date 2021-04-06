package com.example.esp_connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionObject {
    public BluetoothAdapter bluetoothAdapter;
    public BluetoothSocket socket;
    public BluetoothDevice device;
    public OutputStream outputStream;
    public InputStream inputStream;
    public Thread readThread;

    public byte[] readBuffer;
    public int readBufferPosition;
    public boolean stopReadWorker = false;


    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Thread getReadThread() {
        return readThread;
    }

    public void setReadThread(Thread readThread) {
        this.readThread = readThread;
    }

    public byte[] getReadBuffer() {
        return readBuffer;
    }

    public void setReadBuffer(byte[] readBuffer) {
        this.readBuffer = readBuffer;
    }

    public int getReadBufferPosition() {
        return readBufferPosition;
    }

    public void setReadBufferPosition(int readBufferPosition) {
        this.readBufferPosition = readBufferPosition;
    }

    public boolean isStopReadWorker() {
        return stopReadWorker;
    }

    public void setStopReadWorker(boolean stopReadWorker) {
        this.stopReadWorker = stopReadWorker;
    }
}
