package com.example.laboratorio3;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class NumberIncrementService extends Service {

    private int currentNumber = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentNumber++;
            sendBroadcast();
            handler.postDelayed(this, 1000); // Incrementar cada segundo (1000 ms)
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(runnable, 1000); // Iniciar el incremento cada segundo
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Detener el incremento al destruir el servicio
    }

    private void sendBroadcast() {
        Intent intent = new Intent("number_incremented");
        intent.putExtra("current_number", currentNumber);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

