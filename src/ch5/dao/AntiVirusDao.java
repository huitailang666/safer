package ch5.dao;

import ch5.utils.AssetsDatabaseManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntiVirusDao {
	
	/**检测某个md5是否是病毒
	 * @param md5
	 * @return	null代表扫描安全
	 */
	public static String checkVirus(String md5,Context context){
		
		String desc=null;
		
		AssetsDatabaseManager.initManager(context);
		AssetsDatabaseManager manager= AssetsDatabaseManager.getManager();
		SQLiteDatabase db=manager.getDatabase("antivirus.db");
			
		Cursor cursor=db.rawQuery("select desc from datable where md5 =?", new String[]{md5});
		if(cursor.moveToNext()){
			desc=cursor.getString(0);
		}
		cursor.close();
		//db.close();
		
		return desc;
	}

}
