package ch4.utils;



import com.stericson.RootTools.RootTools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;
import ch4.entity.AppInfo;

public class EngineUtils {
	
	
	//分享应用
	public static void shareApplication(Context context,AppInfo appInfo){
		
		Intent intent=new Intent("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件："+appInfo.getAppName()+"下载路径：xxx"+appInfo.getPackageName());
		
		if(intent.resolveActivity(context.getPackageManager())!=null){
			context.startActivity(intent);
		}
		
		
		
	}
	
	
	//开启应用程序
	public static void startApplication(Context context,AppInfo appInfo){
		
		PackageManager pm=context.getPackageManager();
		Intent intent=pm.getLaunchIntentForPackage(appInfo.getPackageName());
		if(intent!=null){
			context.startActivity(intent);
		}else{
			Toast.makeText(context, "该应用没有启动界面", 0).show();
		}
		
		
		
		
	}
	
	
	//开启应用设置页面
	public static void settingAppDetail(Context context,AppInfo appInfo){
		
		Intent intent=new Intent();
		intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:"+appInfo.getPackageName()));
		context.startActivity(intent);
		
		
		
		
	}
	
	//卸载应用
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
					Toast.makeText(context, "need to root 小护卫", 0).show();
					return;
				}
				Toast.makeText(context, "使用linux shell命令删除系统程序", 0).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		
		
		
		
		
		
	}
	
	

}
