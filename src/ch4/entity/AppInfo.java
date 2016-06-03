package ch4.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
	//应用程序包名
	private String packageName;
	//应用程序图标
	private Drawable icon;
	//应用程序名称
	private String appName;
	//应用程序路径
	private String apkPatn;
	//应用程序大小
	private long appSize;
	//是否是手机存储
	private boolean isInRoom;
	//是否是用户应用
	private boolean isUserApp;
	//是否选中
	private boolean isSelected=false;
	//拿到APP位置字符串
	public String getApplocation(boolean isInRoom){
		if(isInRoom){
			return "手机内存";
		}else{
			return "外部存储";
		}
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getApkPatn() {
		return apkPatn;
	}
	public void setApkPatn(String apkPatn) {
		this.apkPatn = apkPatn;
	}
	public long getAppSize() {
		return appSize;
	}
	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}
	public boolean isInRoom() {
		return isInRoom;
	}
	public void setInRoom(boolean isInRoom) {
		this.isInRoom = isInRoom;
	}
	public boolean isUserApp() {
		return isUserApp;
	}
	public void setUserApp(boolean isUserApp) {
		this.isUserApp = isUserApp;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder=new StringBuilder();
		builder.append("包名").append(packageName).append(",").append(appName).append(",").append(appSize).append(",").append(apkPatn);
		
		return builder.toString();
	}
	
	
	

}
