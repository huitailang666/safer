package ch4.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import ch4.entity.AppInfo;

public class AppInfoParser {
	
	public static List<AppInfo> getAppInfos(Context context){
		
		List<AppInfo> appInfos=new ArrayList<AppInfo>();
		PackageManager pm=context.getPackageManager();
		List<PackageInfo> packageInfos=pm.getInstalledPackages(0);
		for(PackageInfo packageInfo:packageInfos){
			AppInfo appInfo=new AppInfo();
			appInfo.setPackageName(packageInfo.packageName);
			appInfo.setIcon(packageInfo.applicationInfo.loadIcon(pm));
			appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
			appInfo.setApkPatn(packageInfo.applicationInfo.sourceDir);
			File file=new File(appInfo.getApkPatn());
			appInfo.setAppSize(file.length());
			int flags=packageInfo.applicationInfo.flags;
			if((ApplicationInfo.FLAG_EXTERNAL_STORAGE&flags)!=0){
				appInfo.setInRoom(false);
			}else{
				appInfo.setInRoom(true);
			}
			if((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
				appInfo.setUserApp(false);
			}else{
				appInfo.setUserApp(true);
			}
			appInfos.add(appInfo);
			appInfo=null;
			
		}
		
		
		
		return appInfos;
	}

	
	
}
