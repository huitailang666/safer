package ch8.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TrafficOpenHelper extends SQLiteOpenHelper {
	
	private static String DB_NAME="traffic.db";
	private static String TABLE_NAME="traffic";

	public TrafficOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//֧�ֵ��������ͣ��������ݣ��ַ������ͣ��������ͣ������Ƶ��������ͣ�
		db.execSQL("create table "+TABLE_NAME+"(id integer primary key autoincrement,gprs varchar(64),wifi varchar(64),date varchar(64))");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
