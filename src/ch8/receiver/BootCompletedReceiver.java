package ch8.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//开机广播
		//开机 那么下次activity第一次显示时要从数据库记录中提取
		SharedPreferences preferences=context.getSharedPreferences("trafficInfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean("newStartFromShutingDown", true);
		
		editor.putBoolean("haveRestarted", true);
		editor.commit();	
		
		Log.d("Receiver", "开机了");
		

	}

}
