package com.jackyann.livepermissions;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

public class Request {

    private final FragmentActivity activity;
    private int requestCode;
    private String[] permissions;
    private RequestCallback requestCallback;

    Request(@NonNull FragmentActivity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity is null.");
        }
        this.activity = activity;
    }

    @NonNull
    protected FragmentActivity getActivity(){
        return activity;
    }

    @NonNull
    public Request requestCode(@IntRange(from = 0) int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @IntRange(from = 0)
    int getRequestCode() {
        return requestCode;
    }


    @NonNull
    public Request requestPermissions(@NonNull String... permissions) {
        this.permissions = permissions;
        return this;
    }

    @NonNull
    String[] getRequestPermissions() {
        return permissions;
    }

    @NonNull
    public Request requestCallback(@NonNull RequestCallback callback) {
        this.requestCallback = callback;
        return this;
    }

    @Nullable
    RequestCallback getRequestCallback() {
        return requestCallback;
    }


    @MainThread
    public void request() {
        if (permissions == null) {
            throw new IllegalArgumentException("Permissions is null.");
        }

        if(activity.isFinishing()){
            throw new IllegalStateException("Activity is finishing.");
        }

        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            if(activity.isDestroyed()){
                throw new IllegalStateException("Activity is destroyed.");
            }
        }
        assertMainThread("request");
        RequestViewModel.get(activity).handle(this);
    }

    private void assertMainThread(@NonNull String methodName) {
        if (!isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background thread");
        }
    }

    private boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @MainThread
    void dispatchRequestPermissionsResult(@IntRange(from = 0) int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCallback == null) {
            return;
        }
        requestCallback.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}


