package ch2.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import ch2.entity.ContactInfo;

public class ContactInfoParser {
	
	private static String TAG="ContactInfoParser";
	
	//读取联系人信息放到list<contactInfo>
	
	public static List<ContactInfo> getSystemContact(Context context){
		
		ContentResolver contentResolver=context.getContentResolver();
		Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
		Uri datauri=Uri.parse("content://com.android.contacts/data");
		List<ContactInfo> list=new ArrayList<ContactInfo>();
		Cursor cursor=contentResolver.query(uri,new String[]{"contact_id"}, null, null, null);
		while(cursor.moveToNext()){
			String id=cursor.getString(0);
			if(id!=null){
				Log.d(TAG, "<<<<id:"+id);
				ContactInfo contactInfo=new ContactInfo();
				contactInfo.setId(id);
				Cursor dataCursor = contentResolver.query(datauri,
						new String[] { "data1", "mimetype" },
						"raw_contact_id=?", new String[] { id }, null);
				while(dataCursor.moveToNext()){
					String data1=dataCursor.getString(0);
					String mimetype=dataCursor.getString(1);
					if("vnd.android.cursor.item/name".equals(mimetype)){
						Log.d(TAG, "<<<<name:"+data1);
						contactInfo.setName(data1);
					}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						Log.d(TAG, "<<<<phone:"+data1);
						contactInfo.setPhone(data1);
					}
				}
				list.add(contactInfo);
				dataCursor.close();
			}
			
		}
		cursor.close();
		return list;
	}

	

}
