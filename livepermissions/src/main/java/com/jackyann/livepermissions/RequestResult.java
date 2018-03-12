package com.jackyann.livepermissions;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public final class RequestResult {

    private final int requestCode;
    private final String[] permissions;
    private final int[] grantResults;

    public RequestResult(@IntRange(from = 0) int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
    }

    @IntRange(from = 0)
    public int getRequestCode() {
        return requestCode;
    }

    @NonNull
    public String[] getPermissions() {
        return permissions;
    }

    @NonNull
    public int[] getGrantResults() {
        return grantResults;
    }
}
