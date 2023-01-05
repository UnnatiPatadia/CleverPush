package com.example.unnatiandroidpractical;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cleverpush.CleverPush;

public class SubscriptionActivity extends BaseActivity {

    public static final int REQUEST_CODE_ASK_PERMISSION = 219;
    Button btnSubscribe, btnUnsubscribe, btnGetNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        CleverPush.getInstance(this).requestLocationPermission();
        CleverPush.getInstance(this).initGeoFences();

        CleverPush.getInstance(this).enableDevelopmentMode();
        CleverPush.getInstance(this).init(getString(R.string.channel_id));
        CleverPush.getInstance(this).subscribe();

        findViewById();
        addListner();
    }

    private void addListner() {
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // subscribe
                    CleverPush.getInstance(SubscriptionActivity.this).subscribe();

                    /*try {
                        String SubscriptionId = CleverPush.getInstance(SubscriptionActivity.this).getSubscriptionId(SubscriptionActivity.this);
                        Toast.makeText(SubscriptionActivity.this, "Subscribed = " + SubscriptionId, Toast.LENGTH_LONG).show();
//                        binding.tvStatus.setText(CleverPush.getInstance(MainActivity.this).getSubscriptionId(this));
                    } catch (Exception e) {
                        Toast.makeText(SubscriptionActivity.this, "Please subscribe first", Toast.LENGTH_SHORT).show();
                    }*/
//                    boolean isSubscribe = CleverPush.getInstance(SubscriptionActivity.this).isSubscribed();
                    Toast.makeText(SubscriptionActivity.this, "Subscribed", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.d("catch", "Catch On btnSubscribe == " + e.getMessage());
                }
            }
        });

        btnUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // unsubscribe
                    CleverPush.getInstance(SubscriptionActivity.this).unsubscribe();

                    boolean isSubscribe = CleverPush.getInstance(SubscriptionActivity.this).isSubscribed();
                    Toast.makeText(SubscriptionActivity.this, "Unsubscribed", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.d("catch", "Catch On btnUnsubscribe == " + e.getMessage());
                }
            }
        });

        btnGetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                } catch (Exception e) {
                    Log.d("catch", "Catch On btnGetNotification == " + e.getMessage());
                }
            }
        });
    }

    private void findViewById() {
        btnSubscribe = findViewById(R.id.btnSubscribe);
        btnUnsubscribe = findViewById(R.id.btnUnsubscribe);
        btnGetNotification = findViewById(R.id.btnGetNotification);
    }

    @Override
    public void onBackPressed() {
        AskAndFinishActivity(getString(R.string.confirm_exit_form), this, true);
    }

    protected boolean CheckPermissions() {
        boolean flag = true;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSION);

            flag = false;
        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_CODE_ASK_PERMISSION);

            flag = false;
        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSION);

            flag = false;
        }

        return flag;
    }
}