package ch4.utils;



import com.stericson.RootTools.RootTools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;
import ch4.entity.AppInfo;

public class EngineUtils {
	
	
	//����Ӧ��
	public static void shareApplication(Context context,AppInfo appInfo){
		
		Intent intent=new Intent("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "�Ƽ���ʹ��һ�������"+appInfo.getAppName()+"����·����xxx"+appInfo.getPackageName());
		
		if(intent.resolveActivity(context.getPackageManager())!=null){
			context.startActivity(intent);
		}
		
		
		
	}
	
	
	//����Ӧ�ó���
	public static void startApplication(Context context,AppInfo appInfo){
		
		PackageManager pm=context.getPackageManager();
		Intent intent=pm.getLaunchIntentForPackage(appInfo.getPackageName());
		if(intent!=null){
			context.startActivity(intent);
		}else{
			Toast.makeText(context, "��Ӧ��û����������", 0).show();
		}
		
		
		
		
	}
	
	
	//����Ӧ������ҳ��
	public static void settingAppDetail(Context context,AppInfo appInfo){
		
		Intent intent=new Intent();
		intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:"+appInfo.getPackageName()));
		context.startActivity(intent);
		
		
		
		
	}
	
	//ж��Ӧ��
	public static void uninstallApplication(Context context,AppInfo appInfo){
		
		if(appInfo.isUserApp()){
			
			Intent intent=new Intent();
			intent.setAction(Intent.ACTION_DELETE);
			intent.setData(Uri.parse("package:"+appInfo.getPackageName()));
			context.startActivity(intent);
			
			
		}else{
			Toast.makeText(context, "delete system app", 0).show();
			if(!RootTools.isRootAvailable()){
				Toast.makeText(context, "no root authority", 0).show();
				return;
			}
			try {
				if(!RootTools.isAccessGiven()){
					Toast.makeText(context, "need to root С����", 0).show();
					return;
				}
				Toast.makeText(context, "ʹ��linux shell����ɾ��ϵͳ����", 0).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		
		
		
		
		
		
	}
	
	

}
