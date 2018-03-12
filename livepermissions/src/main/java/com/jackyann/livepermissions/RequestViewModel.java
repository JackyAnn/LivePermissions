package com.jackyann.livepermissions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.Arrays;

final class RequestViewModel extends AndroidViewModel {

    private final MutableLiveData liveData = new MutableLiveData<RequestResult>();

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData getLiveData() {
        return liveData;
    }

    public RequestViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    static RequestViewModel get(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(RequestViewModel.class);
    }

    @MainThread
    void handle(@NonNull Request request) {
        final FragmentActivity activity = request.getActivity();

        final int requestCode = request.getRequestCode();
        final String[] permissions = request.getRequestPermissions();
        final RequestCallback callback = request.getRequestCallback();

        if (VERSION.SDK_INT < VERSION_CODES.M || RequestHelper.hasAllPermissions(activity, permissions)) {
            int[] grantResults = new int[permissions.length];
            request.dispatchRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (callback != null) {
            if (RequestHelper.shouldShowRequestPermissionRationale(activity, permissions)) {
                if (callback.onShowRequestPermissionRationale(requestCode, Arrays.asList(permissions))) {
                    return;
                }
            }
        }

        liveData.observe(activity, new Observer<RequestResult>() {
            @Override
            public void onChanged(@Nullable RequestResult requestResult) {
                liveData.removeObserver(this);
                int requestCode = requestResult.getRequestCode();
                String[] permissions = requestResult.getPermissions();
                int[] grantResults = requestResult.getGrantResults();
                request.dispatchRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        });
        RequestFragment.requestPermissions(request);
    }
}
