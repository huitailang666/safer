package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;
import ch8.db.dao.TrafficDao;

public class TestWanwanwan extends AndroidTestCase {
	private String TAG="TestWanwanwan";
	private Context context;
	
	public void test(){
		
		context=getContext();
		
		String path=context.getFilesDir().getAbsolutePath();
		Log.d(TAG, path);
		path=context.getCacheDir().getAbsolutePath();
		Log.d(TAG, path);
		
		
		
		
	}
	
	
	
	public void dbTestInsert(){
		
		boolean flag=true;
		TrafficDao dao=new TrafficDao(getContext());
		flag=dao.insertTodayTraffic(new String[]{"gprs_10m","WIFI_1K"});
		Log.d(TAG,"1:"+flag);
		
		flag=dao.insertTodayTraffic(new String[]{"gprs_4m","WIFI_123K"});
		Log.d(TAG,"2:"+flag);
		
		flag=dao.insertTodayTraffic(new String[]{"gprs_50m","WIFI_132K"});
		Log.d(TAG,"3:"+flag);
		
		flag=dao.insertTodayTraffic(new String[]{"gprs_160m","WIFI_1111K"});
		Log.d(TAG,"4:"+flag);
		
		
	}
	
	
	public void dbTestUpdate(){
		
		
		TrafficDao dao=new TrafficDao(getContext());
		dao.updateTodayTraffic(110,3);
		
	}
	
	public void dbTestGet(){
		
		
		TrafficDao dao=new TrafficDao(getContext());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String currentTime = dateFormat.format(new Date());
		Log.d(TAG,">>>:"+currentTime);
		Map<String,String> map=new HashMap<String, String>();
		
		map=dao.getTodayTraffic(currentTime);
		Log.d(TAG, map.toString());
		
	}
	

}
