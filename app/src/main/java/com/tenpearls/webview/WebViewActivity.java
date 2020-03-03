package com.tenpearls.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Collections;

import static com.tenpearls.webview.Constants.BASE_URL;

public class WebViewActivity extends AppCompatActivity {


    private WebView webView;
    private ProgressBar progressBarLoading;
    private static final int REQUEST_PERMISSIONS = 101;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webView);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        setUpWebView();

        if (PermissionsUtil.hasPermissionsForCall(this)) {
            webView.loadUrl(BASE_URL);
        } else {
            PermissionsUtil.requestPermissions(this, REQUEST_PERMISSIONS);
        }


    }

    public void setUpWebView() {
        if (!isInternetConnectionAvailable(this)) {
            Toast.makeText(this,"Internet is not available",Toast.LENGTH_SHORT).show();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.getSettings().setDomStorageEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // This is for runtime permission on Marshmallow and above; It is not directly related to
        // PermissionRequest API.
        if (requestCode == REQUEST_PERMISSIONS) {

            if (Collections.singletonList(grantResults).contains(PackageManager.PERMISSION_DENIED)){
                Log.e(TAG, "permissions not granted.");
            }
            else if (webView != null) {
                webView.loadUrl(BASE_URL);
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onPermissionRequest(final PermissionRequest request) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    request.grant(request.getResources());
                }
            });
            }

    }

    class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBarLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBarLoading.setVisibility(View.GONE);
            if (url.contains(Constants.CODE_404)) {
                finish();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            try {

                if (url.contains(Constants.KEY_TELEPHONE_NUMBER)) {
                    openDialer(WebViewActivity.this);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
            catch (Exception expection) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

    }

    public static boolean isInternetConnectionAvailable (Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null) && netInfo.isAvailable() && netInfo.isConnectedOrConnecting();
        }	catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void openDialer(Activity activity) {

        if (activity == null)
            return;

        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(Constants.KEY_CUSTOMER_CARE_NUMBER));
        activity.startActivity(dialIntent);
    }
}
