package ch8.db.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.os.IBinder;
import android.util.Log;
import ch8.db.dao.TrafficDao;
import ch8.utils.SaveTrafficUtil;

public class SaveTrafficService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//gq被插过 就更新 ，没有被插过 就插入
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SaveTrafficUtil.saveTrafficInfo(this);
		Log.d("Receiver", "save traffic to database!");
	
	}
	

}
