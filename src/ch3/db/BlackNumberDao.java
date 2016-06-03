package ch3.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ch3.entity.BlackContactInfo;

public class BlackNumberDao {

	private BlackNumberOpenHelper openHelper;

	public BlackNumberDao(Context context) {
		// TODO Auto-generated constructor stub
		openHelper = new BlackNumberOpenHelper(context);
	}

	/**
	 * 添加数据s
	 * 
	 * @param blackContactInfo
	 * @return
	 */
	public boolean add(BlackContactInfo blackContactInfo) {
		SQLiteDatabase database = openHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		if (blackContactInfo.getPhoneNumber().startsWith("+86")) {
			blackContactInfo.setPhoneNumber((String) ((blackContactInfo
					.getPhoneNumber()).subSequence(3, blackContactInfo
					.getPhoneNumber().length())));
		}
		values.put("number", blackContactInfo.getPhoneNumber());
		values.put("name", blackContactInfo.getContactName());
		values.put("mode", blackContactInfo.getMode());
		long rowid = database.insert("blacknumber", null, values);
		if (rowid == -1) {
			return false;
		} else {
			return true;
		}

	}
	
	
	/**删除数据
	 * @param blackContactInfo
	 * @return
	 */
	public boolean delete(BlackContactInfo blackContactInfo){
		SQLiteDatabase database=openHelper.getWritableDatabase();
		int rownumber=database.delete("blacknumber", "number=?", new String[]{blackContactInfo.getPhoneNumber()});
		if(rownumber==0){
			return false;
		}else{
			return true;
		}

	}
	
	
	/**
	 * @param pagenumber    第几页   从0开始
	 * @param pagesize		每个页面的大小
	 * @return
	 */
	public List<BlackContactInfo> getPageBlackNumber(int pagenumber,int pagesize){
		SQLiteDatabase database=openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(
				"select number,mode,name from blacknumber limit ? offset ?",
				new String[] { String.valueOf(pagesize),
						String.valueOf(pagesize * pagenumber) });
		List<BlackContactInfo> blackContactInfos=new ArrayList<BlackContactInfo>();
		
		while(cursor.moveToNext()){
			BlackContactInfo blackContactInfo=new BlackContactInfo();
			blackContactInfo.setPhoneNumber(cursor.getString(0));
			blackContactInfo.setMode(cursor.getInt(1));
			blackContactInfo.setContactName(cursor.getString(2));
			blackContactInfos.add(blackContactInfo);
			
		}
		cursor.close();
		database.close();
		return blackContactInfos;
	}
	
	
	/**判断号码是否在黑名单中
	 * @param name
	 * @return
	 */
	public boolean IsNumberExist(String number){
		SQLiteDatabase database=openHelper.getReadableDatabase();
		Cursor cursor=database.query("blacknumber",new String[]{"number"}, "number=?", new String[]{number}, null, null, null);
		if(cursor.moveToNext()){
			cursor.close();
			database.close();
			return true;
		}
		cursor.close();
		database.close();
		return false;
	}
	
	
	/**根据号码查询黑名单信息s
	 * @param number
	 * @return
	 */
	public int getBlackContactMode(String number){
		SQLiteDatabase database=openHelper.getReadableDatabase();
		Cursor cursor=database.query("blacknumber",new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
		int mode=0;
		if(cursor.moveToNext()){
			mode=cursor.getInt(cursor.getColumnIndex("mode"));
		}
		cursor.close();
		database.close();
		return mode;
		
	}
	
	
	/**获取数据库的总条目个数
	 * @return
	 */
	public int getTotalNumber(){
		SQLiteDatabase database=openHelper.getReadableDatabase();
		Cursor cursor=database.rawQuery("select count(*) from blacknumber", null);
		cursor.moveToNext();
		int count=cursor.getInt(0);
		cursor.close();
		database.close();
		return count;

	}
	
	
}
