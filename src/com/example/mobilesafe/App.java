package com.example.mobilesafe;

import android.app.Application;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;



/*APP每次启动自动执行此类s在mainfast中配置好s
 * */

public class App extends Application {
	
	private static String TAG="App";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		correctSIM();
		
		
	}

	public void correctSIM(){
		
		SharedPreferences preferences=getSharedPreferences("sim", 0);
		
		//获取防盗保护的状态   待续 s
		boolean flag=preferences.getBoolean("protecting", true);
		
		if(flag){
			//绑定的sim序列号s
			String bindSim=preferences.getString("simSerialNumber", "");
			TelephonyManager manager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			String sim=manager.getSimSerialNumber();
			if(sim.equals(bindSim)){
				//更换了sim卡
				Log.d(TAG, ">>>sim没变化");
				
			}else{
				Log.d(TAG, ">>>sim卡更换了");
				//发送短信给安全号码
				preferences=getSharedPreferences("phone", 0);
				String safeNumber=preferences.getString("phoneNumber", "");
				if(!TextUtils.isEmpty(safeNumber)){
					SmsManager smsManager=SmsManager.getDefault();
					smsManager.sendTextMessage(safeNumber, null, "你的朋友xxx的手机sim卡已经被更换", null, null);
				}
				
				
			}
			
		}else{
			Log.d(TAG, ">>>防盗保护还未开启s");
		}
		
		
		
	}
}
