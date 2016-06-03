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
		//�����㲥
		//���� ��ô�´�activity��һ����ʾʱҪ�����ݿ��¼����ȡ
		SharedPreferences preferences=context.getSharedPreferences("trafficInfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean("newStartFromShutingDown", true);
		
		editor.putBoolean("haveRestarted", true);
		editor.commit();	
		
		Log.d("Receiver", "������");
		

	}

}
