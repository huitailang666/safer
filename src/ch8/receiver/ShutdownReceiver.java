package ch8.receiver;

import ch8.db.service.SaveTrafficService;
import ch8.utils.SaveTrafficUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;

public class ShutdownReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Receiver", "6666666关机广播");
		//将本次开机dao关机产生的流量更新到数据库s
		
		//关机广播开启服务  服务还未完成就已经关机啦s
		//context.startService(new Intent(context, SaveTrafficService.class));
		SaveTrafficUtil.saveTrafficInfo(context);
		Log.d("Receiver", "save traffic to database!");
	}

}
