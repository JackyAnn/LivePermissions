package com.jackyann.livepermissions;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

public final class LivePermissions {

    public static Request with(@NonNull FragmentActivity activity) {
        return new Request(activity);
    }
}
