package ch2.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class SmsLostFindReceiver extends BroadcastReceiver {
	
	private SharedPreferences preferences;
	private String TAG="SmsLostFindReceiver";
	
	//短信 广播接受者 s

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d(TAG, "receive message");
		// TODO Auto-generated method stub
		preferences=context.getSharedPreferences("security", 0);
		boolean flag=preferences.getBoolean("protecting", false);
		if(flag){
			//防盗保护开启
			Toast.makeText(context, "收到短信", Toast.LENGTH_LONG).show();
			DevicePolicyManager devicePolicyManager=(DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
			Object []objs=(Object [])intent.getExtras().get("pdus");
			for(Object obj:objs){
				SmsMessage smsMessage=SmsMessage.createFromPdu((byte [])obj);
				String sender=smsMessage.getOriginatingAddress();
				String body=smsMessage.getMessageBody();
				preferences=context.getSharedPreferences("phone", 0);
				String safePhoneNumber=preferences.getString("phoneNumber", "");
				if(!TextUtils.isEmpty(safePhoneNumber)&&sender.equals(safePhoneNumber)){
					if("#*location*#".equals(body)){
						Log.d(TAG, "location");
						
					}else if("#*lockScreen*#".equals(body)){
						Log.d(TAG, "lockScreen");
						
					}else if("#*wipedata*#".equals(body)){
						Log.d(TAG, "wipedata");
						
					}else if("#*alarm*#".equals(body)){
						Log.d(TAG, "alarm");
						
					}
					
				}
			}
			
			
		}

	}

}
