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
		Log.d("Receiver", "6666666�ػ��㲥");
		//�����ο���dao�ػ��������������µ����ݿ�s
		
		//�ػ��㲥��������  ����δ��ɾ��Ѿ��ػ���s
		//context.startService(new Intent(context, SaveTrafficService.class));
		SaveTrafficUtil.saveTrafficInfo(context);
		Log.d("Receiver", "save traffic to database!");
	}

}
