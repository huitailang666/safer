package com.example.mobilesafe;

import android.app.Application;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;



/*APPÿ�������Զ�ִ�д���s��mainfast�����ú�s
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
		
		//��ȡ����������״̬   ���� s
		boolean flag=preferences.getBoolean("protecting", true);
		
		if(flag){
			//�󶨵�sim���к�s
			String bindSim=preferences.getString("simSerialNumber", "");
			TelephonyManager manager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			String sim=manager.getSimSerialNumber();
			if(sim.equals(bindSim)){
				//������sim��
				Log.d(TAG, ">>>simû�仯");
				
			}else{
				Log.d(TAG, ">>>sim��������");
				//���Ͷ��Ÿ���ȫ����
				preferences=getSharedPreferences("phone", 0);
				String safeNumber=preferences.getString("phoneNumber", "");
				if(!TextUtils.isEmpty(safeNumber)){
					SmsManager smsManager=SmsManager.getDefault();
					smsManager.sendTextMessage(safeNumber, null, "�������xxx���ֻ�sim���Ѿ�������", null, null);
				}
				
				
			}
			
		}else{
			Log.d(TAG, ">>>����������δ����s");
		}
		
		
		
	}
}
