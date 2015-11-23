package com.mss.please;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

//---add this---
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class MainActivity extends Activity {

	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

	private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId",
			ESTIMOTE_PROXIMITY_UUID, null, null);

	protected static final String TAG = "EstimoteiBeacon";

	// private static final int NOTIFICATION_ID = 123;

	BeaconManager beaconManager;

	// NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final TextView tx = (TextView) findViewById(R.id.textView1);
		// ---get references to all the views in the
		// activity---
        Log.d("test00", "Vinod");

		beaconManager = new BeaconManager(this);

		// ---by default you scan 5s and then wait 25s
		// for this demo, you will scan more
		// frequently---

		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
					beaconManager
							.setRangingListener(new BeaconManager.RangingListener() {

								@Override
								public void onBeaconsDiscovered(Region arg0,
										List<Beacon> beacons) {
									// TODO Auto-generated method stub
									Log.d("Test45", "test");
									Log.d("arg", String.valueOf(arg0));
									for (Beacon beacon : beacons) {
									   
										int major = beacon.getMajor();
										tx.setText(String.valueOf(major));
									}

								}
							});

				} catch (RemoteException e) {
					Log.e("error", "Cannot start ranging", e);
				}
			}
		});

		// ---called when beacons are found---
	}

	// ---stop ranging for beacons when activity is killed---
	@Override
	protected void onStop() {
		super.onStop();
		try {
			beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
		} catch (RemoteException e) {
			Log.e     
			(TAG, "Cannot stop", e);
		}
	}

}