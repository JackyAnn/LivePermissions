package com.jackyann.livepermissions;

import android.content.pm.PackageManager;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class RequestCallback implements OnRequestPermissionsResultCallback {

    @Override
    public final void onRequestPermissionsResult(@IntRange(from = 0) int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0, size = permissions.length; i < size; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(permission);
            } else {
                denied.add(permission);
            }
        }

        if (granted.size() == permissions.length) {
            onPermissionsGranted(requestCode, granted);
        } else {
            onPermissionsDenied(requestCode, denied);
        }
    }

    public boolean onShowRequestPermissionRationale(@IntRange(from = 0) int requestCode,
            @NonNull List<String> permissions) {
        return false;
    }

    public abstract void onPermissionsGranted(@IntRange(from = 0) int requestCode, @NonNull List<String> permissions);

    public abstract void onPermissionsDenied(@IntRange(from = 0) int requestCode, @NonNull List<String> permissions);

}
