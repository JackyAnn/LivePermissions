package com.jackyann.livepermissions.sample;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.jackyann.livepermissions.LivePermissions;
import com.jackyann.livepermissions.RequestCallback;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.test_activity).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                //ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.WRITE_CONTACTS, permission.CAMERA}, 1);
                //ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.WRITE_CONTACTS, permission.CAMERA}, 1);
            }
        });
        //requestPermissions();
    }

    private void requestPermissions(){
        LivePermissions.with(MainActivity.this)
                .requestCode(0)
                .requestPermissions(permission.WRITE_CONTACTS)
                .requestCallback(new RequestCallback() {
                    @Override
                    public boolean onShowRequestPermissionRationale(int requestCode, @NonNull List<String> permissions) {
                        Toast.makeText(getApplicationContext(), "permissions rationale!",  Toast.LENGTH_SHORT).show();
                        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName()+", permissions:"+ permissions);
                        return false;
                    }

                    @Override
                    public void onPermissionsGranted(int requestCode, @NonNull List<String> permissions) {
                        Toast.makeText(getApplicationContext(), "permissions granted!",  Toast.LENGTH_SHORT).show();
                        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName()+", permissions:"+ permissions);
                    }

                    @Override
                    public void onPermissionsDenied(int requestCode, @NonNull List<String> permissions) {
                        Toast.makeText(getApplicationContext(), "permissions denied!",  Toast.LENGTH_SHORT).show();
                        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName()+", permissions:"+ permissions);
                    }
                })
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName()+", permissions:"+ Arrays.toString(permissions)+", grantResults:"+Arrays.toString(grantResults));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
