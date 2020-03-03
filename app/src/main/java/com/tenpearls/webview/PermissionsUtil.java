package com.tenpearls.webview;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PermissionsUtil {


    public static boolean hasPermissionsForCall(Activity activity) {

        return (ContextCompat.checkSelfPermission (activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)

                && (ContextCompat.checkSelfPermission (activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)

                && (ContextCompat.checkSelfPermission (activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                ;
    }


    public static boolean requestPermissions(Activity activity, int requestCode) {

        ArrayList<String> permissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission (activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            permissions.add (Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission (activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            permissions.add (Manifest.permission.RECORD_AUDIO);
        }

        if (ContextCompat.checkSelfPermission (activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            permissions.add (Manifest.permission.READ_PHONE_STATE);
        }


        if (permissions.isEmpty()) {
            return true;
        }

        ActivityCompat.requestPermissions (activity, permissions.toArray (new String[permissions.size ()]), requestCode);
        return false;
    }
}
