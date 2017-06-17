#Permissions#

Since Android API V23 permissions are granted during run time and not during installation.

This also allows the application to receiver some and not all of their required permissions.

The user is only required to grant permission once but an application can always ask again.

- https://developer.android.com/training/permissions/requesting.html

Add permissions within the manifest file:

```xml
<!--app/manifests/AndroidManifest.xml -->
<uses-permission android:name="android.permission.READ_CONTACTS" />
```

```java
if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            Manifest.permission.READ_CONTACTS)) {
        // The user has denied this in the past; show an explanation
    } else {
        ActivityCompat.requestPermissions(thisActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }
}

// Have already been granted the permission.
```

The callback from the user for granting permission goes into the onRequestPermissionsResult

```java
@Override
public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
    switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
            	&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
            } else {
                // Permission denied.
            }
            return;
        }
    }
}
```

