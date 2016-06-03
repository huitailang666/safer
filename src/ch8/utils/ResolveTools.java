package ch8.utils;

import java.util.ArrayList;
import java.util.List;

import ch8.entity.TrafficInfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;



public class ResolveTools {
	
	public static void initResolveInfos(Context context,List<TrafficInfo> trafficInfos){
		
		
		trafficInfos.clear();
		PackageManager manager=context.getPackageManager();
		Intent intent=new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		//这个方法就是根据对应的条件，intent指定条件，然后查询出相应的activity
        //那么根据我们上面设置的intent，我们就可以知道，我们要查询的是应用的入口activity而且是桌面上有图标的activity
        //因为这样的应用，才会有可能产生流量的
		List<ResolveInfo> resolveInfos=manager.queryIntentActivities(intent, 0);
		for(ResolveInfo info:resolveInfos){
			String name=info.loadLabel(manager).toString();
			Drawable icon=info.loadIcon(manager);
			String packageName=info.activityInfo.packageName;
			int uid=0;
			long received=0;
			long transmitted=0;
			try {
				PackageInfo packageInfo=manager.getPackageInfo(packageName, 0);
				uid=packageInfo.applicationInfo.uid;
				received=TrafficStats.getUidRxBytes(uid);
				transmitted=TrafficStats.getUidTxBytes(uid);
				
				 //有些应用不会产生流量信息的，拿到的值就会是-1
                //不产生流量的，我们就不把它加入到list里面
				if(received==-1&&transmitted==-1){
					continue;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			TrafficInfo trafficInfo=new TrafficInfo();
			trafficInfo.setIcon(icon);
			trafficInfo.setName(name);
			trafficInfo.setUid(uid);
			//
			trafficInfo.setReceived(received);
			trafficInfo.setTransmitted(transmitted);
			
			
			trafficInfos.add(trafficInfo);
			
	
			
			
		}
		//排序list
		
	}

}
