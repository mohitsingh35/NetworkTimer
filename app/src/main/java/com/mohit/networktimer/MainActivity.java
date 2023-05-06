package com.mohit.networktimer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    CountDownTimer timer;
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Button button=findViewById(R.id.button);
        EditText editText=findViewById(R.id.edit11);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float num= Float.parseFloat((editText.getText().toString()));
                Toast.makeText(MainActivity.this, "Timer Set", Toast.LENGTH_SHORT).show();
                timer = new CountDownTimer((long) (num*60*1000),1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        turnOffNetwork();
                        Toast.makeText(MainActivity.this, "Timer Reached", Toast.LENGTH_SHORT).show();

                    }
                }.start();

            }

            private void turnOffNetwork() {
                NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifiInfo != null && wifiInfo.isConnected()) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wifiManager != null) {
                        wifiManager.setWifiEnabled(false);
                    }
                }

                NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileInfo != null && mobileInfo.isConnected()) {
                    TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            telephonyManager.setDataEnabled(false);
                        }
                    }
                }
            }
        });
    }
}
