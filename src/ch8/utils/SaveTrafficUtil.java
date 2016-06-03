package ch8.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.net.TrafficStats;
import android.util.Log;
import ch8.db.dao.TrafficDao;

public class SaveTrafficUtil {
	
	
	/**
	 * @param context
	 * @return    1:�����¼�¼�� 2���������еļ�¼
	 */
	public static int saveTrafficInfo(Context context){
		
		
		long total_234g_received=TrafficStats.getMobileRxBytes();
		long total_234g_transmitted=TrafficStats.getMobileTxBytes();
		long total_234g=total_234g_received+total_234g_transmitted;
		
		long total=TrafficStats.getTotalRxBytes()+TrafficStats.getTotalTxBytes();
		long total_wifi=total-total_234g;
		TrafficDao dao=new TrafficDao(context);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String currentTime = dateFormat.format(new Date());
		Map<String,String> map= dao.getTodayTraffic(currentTime);
		if(dao.insertTodayTraffic(new String[]{Long.toString(total_234g),Long.toString(total_wifi)})){
			//gqû����������ڲ�����
			//���ǽ����һ�β�,֮��͸��£������ٲ�
			return 1;
		}else{
			
	
			total_234g=total_234g+Long.parseLong(map.get("gprs"));
			total_wifi=total_wifi+Long.parseLong(map.get("wifi"));	
			Log.d("today traffic", "gprs:"+total_234g+"/wifi:"+total_wifi);
			
			dao.updateTodayTraffic(total_234g, total_wifi);
			return 2;
		}
	}

}
