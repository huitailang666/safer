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
		//����������Ǹ��ݶ�Ӧ��������intentָ��������Ȼ���ѯ����Ӧ��activity
        //��ô���������������õ�intent�����ǾͿ���֪��������Ҫ��ѯ����Ӧ�õ����activity��������������ͼ���activity
        //��Ϊ������Ӧ�ã��Ż��п��ܲ���������
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
				
				 //��ЩӦ�ò������������Ϣ�ģ��õ���ֵ�ͻ���-1
                //�����������ģ����ǾͲ��������뵽list����
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
		//����list
		
	}

}
