package com.jackyann.livepermissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build.VERSION_CODES;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.FragmentActivity;

/**
 * @hide
 */
@RestrictTo(Scope.LIBRARY_GROUP)
public final class RequestFragment extends Fragment {

    /**
     * @hide
     */
    @RestrictTo(Scope.LIBRARY_GROUP)
    private static final String REQUEST_FRAGMENT_TAG = "com.jackyann.livepermissions.PermissionRequest..request_fragment_tag";

    /**
     * @hide
     */
    @RestrictTo(Scope.LIBRARY_GROUP)
    public RequestFragment() {
    }

    @NonNull
    private static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @NonNull
    private static RequestFragment of(@NonNull Activity activity) {
        android.app.FragmentManager manager = activity.getFragmentManager();
        Fragment fragment = manager.findFragmentByTag(REQUEST_FRAGMENT_TAG);
        if (fragment == null) {
            RequestFragment requestFragment = newInstance();
            manager.beginTransaction().add(requestFragment, REQUEST_FRAGMENT_TAG).commitAllowingStateLoss();
            manager.executePendingTransactions();
            return requestFragment;
        }

        return (RequestFragment) manager.findFragmentByTag(REQUEST_FRAGMENT_TAG);
    }

    @MainThread
    @TargetApi(VERSION_CODES.M)
    static void requestPermissions(@NonNull Request request) {

        Activity activity = request.getActivity();
        RequestFragment requestFragment = of(activity);

        int requestCode = request.getRequestCode();
        String[] permissions = request.getRequestPermissions();
        requestFragment.requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RequestResult requestResult = new RequestResult(requestCode, permissions, grantResults);
        RequestViewModel.get((FragmentActivity) getActivity()).getLiveData().setValue(requestResult);
    }
}
