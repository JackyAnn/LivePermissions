package com.jackyann.livepermissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

class RequestHelper {

    public static boolean hasPermission(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasAllPermissions(@NonNull Activity activity, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity,
            @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }

        return false;
    }

}
