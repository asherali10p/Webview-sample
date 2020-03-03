# Webview-sample
This a sample app for Webview in android.

## Setup
- Initialise webview settings:

```
  webView.getSettings().setJavaScriptEnabled(true);
  webView.setWebViewClient(new CustomWebViewClient());
  webView.setWebChromeClient(new CustomWebChromeClient());
  webView.getSettings().setDomStorageEnabled(true);

```

- Check if app has required android runtime permissions:

```
  if (PermissionsUtil.hasPermissionsForCall(this)) {
            webView.loadUrl(BASE_URL);
        } else {
            PermissionsUtil.requestPermissions(this, REQUEST_PERMISSIONS);
        }

```
- To grant permissions required by webview, implement "onPermissionRequest" in "WebChromeClient":
```
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
```
