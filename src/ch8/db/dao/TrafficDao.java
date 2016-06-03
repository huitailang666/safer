package ch8.db.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ch8.db.TrafficOpenHelper;
import ch8.db.service.TrafficService;

public class TrafficDao implements TrafficService {

	private TrafficOpenHelper helper;

	public TrafficDao(Context context) {
		// TODO Auto-generated constructor stub
		helper = new TrafficOpenHelper(context);
	}

	// 添加今天用的流量
	@Override
	public boolean insertTodayTraffic(Object[] params) {
		// TODO Auto-generated method stub

		SQLiteDatabase database = helper.getWritableDatabase();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String currentTime = dateFormat.format(new Date());
		//如果今天已经插过gq  就不用插gq了
		Cursor cursor =database.rawQuery("select gprs from traffic where date=?", new String[]{currentTime});
		if(!cursor.moveToNext()){
			//gp没被插过  
			ContentValues values = new ContentValues();
			values.put("gprs", (String) params[0]);
			values.put("wifi", (String) params[1]);
			values.put("date", currentTime);
			database.insert("traffic", null, values);
			
			return true;
		}
		
		
		database.close();
		return false;//gp被插过，不用插了
	}

	
	//更新今天用的流量  
	public boolean updateTodayTraffic(long gprs, long wifi) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			String sql = "update traffic set gprs = ?,wifi =? where date =?";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			String currentTime = dateFormat.format(new Date());
			/*database.execSQL(sql,
					new Object[] { Long.toString(gprs), Long.toString(wifi),
							"datatime(" + currentTime + ")" });*/
			ContentValues values=new ContentValues();
			values.put("gprs", String.valueOf(gprs));
			values.put("wifi", String.valueOf(wifi));
			values.put("date", currentTime);
			database.update("traffic", values, "date = ?", new String[]{currentTime});
			Log.d("dao", "<<<:"+values.toString());
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	//获取某一天的流量
	// map   "gprs"和"wifi"
	@Override
	public Map<String,String> getTodayTraffic(String dateString) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String, String>();
		
		SQLiteDatabase database = helper.getReadableDatabase();
		long gprs = 0;
		long wifi = 0;
		Cursor cursor = database.rawQuery("select * from traffic where date=?",
				new String[] {dateString});
		
		/*Cursor cursor = database.rawQuery("select * from traffic where id=?",
				new String[] { "2" });*/
		
		int colums=cursor.getColumnCount();
		Log.d("", ">>>:"+colums);
		if(cursor.moveToNext()){
			
				
				String cols_value=cursor.getString(cursor.getColumnIndex("gprs"));
				map.put("gprs", cols_value);
				cols_value=cursor.getString(cursor.getColumnIndex("wifi"));
				map.put("wifi", cols_value);
				
			
		}
		Log.d("", ">>>:"+map.toString());
		
		return map;
	}

}
