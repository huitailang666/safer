package ch3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InterceptSmsReceiver extends BroadcastReceiver {
	private String TAG="InterceptSmsReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "xxx666");

	}

}
