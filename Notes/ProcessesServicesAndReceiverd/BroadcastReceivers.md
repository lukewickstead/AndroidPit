# Broadcast Receivers #
## Implement ##

To receive a broadcast event, implement a class inheriting from BroadcastReceiver.

```java
private class ChargingBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        boolean isCharging = (action.equals(Intent.ACTION_POWER_CONNECTED));
        showCharging(isCharging);
    }
}
```

## Register ##

Register and register the receiver during the activity onResume and onPause methods.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	mChargingReceiver = new ChargingBroadcastReceiver();

	mChargingIntentFilter = new IntentFilter();
	mChargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
	mChargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
}

protected void onResume() {
        super.onPause();
		registerReceiver(mChargingReceiver, mChargingIntentFilter);
}

protected void onPause() {
        super.onPause();
        unregisterReceiver(mChargingReceiver);
}
```

## Manifest Registration ##

You can also register the broadcast receiver in the manifest

```xml
<receiver android:name=".AirplaneModeReceiver">
    <intent-filter>
        <action android:name="android.intent.action.AIRPLANE_MODE" />
    </intent-filter>
</receiver>
```

```java
public class AirplaneModeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean modeIsOn = intent.getBooleanExtra("state", false);
        Log.i("AirplaneModeReceiver", "Mode is " +(modeIsOn ? "on" : "off"));
    }
}
```

## Receiving A One Off Broadcast ##

The method registerReceiver can be passed null for the receiver to notify that it is a one shot synchronous call.

```java
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        showCharging(batteryManager.isCharging());
} else {
    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent currentBatteryStatusIntent = registerReceiver(null, ifilter);
    int batteryStatus = currentBatteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
    boolean isCharging = batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING
    	|| batteryStatus == BatteryManager.BATTERY_STATUS_FULL;
    showCharging(isCharging);
}
```