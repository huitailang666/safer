package ch2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mobilesafe.App;

public class BootCompleteReceiver extends BroadcastReceiver {
	
	private static final String TAG=BootCompleteReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		((App)context.getApplicationContext()).correctSIM();
		Log.d("Receiver", "ch2 ¿ª»úÁË");
	}

}
