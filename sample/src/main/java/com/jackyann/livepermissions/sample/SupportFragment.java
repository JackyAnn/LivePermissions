package com.jackyann.livepermissions.sample;


import android.Manifest.permission;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jackyann.livepermissions.LivePermissions;
import com.jackyann.livepermissions.RequestCallback;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {
    private static final String TAG = SupportFragment.class.getSimpleName();

    public SupportFragment() {
        // Required empty public constructor
    }

    private void requestPermissions(){
        LivePermissions.with(getActivity())
                .requestCode(0)
                .requestPermissions(permission.WRITE_CONTACTS)
                .requestCallback(new RequestCallback() {
                    @Override
                    public boolean onShowRequestPermissionRationale(int requestCode, @NonNull List<String> permissions) {
                        Toast.makeText(getContext(), "permissions rationale!",  Toast.LENGTH_SHORT).show();
                        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName()+", permissions:"+ permissions);
                        return false;
                    }

                    @Override
                    public void onPermissionsGranted(int requestCode, @NonNull List<String> permissions) {
                        Toast.makeText(getContext(), "permissions granted!",  Toast.LENGTH_SHORT).show();
                        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getMethodName()+", permissions:"+ permissions);
                    }

                    @Override
                    public void onPermissionsDenied(int requestCode, @NonNull List<String> permissions) {
                        Toast.makeText(getContext(), "permissions denied!",  Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.supportfragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.test_fragment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
    }
}
