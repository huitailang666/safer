package ch4.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
	//Ӧ�ó������
	private String packageName;
	//Ӧ�ó���ͼ��
	private Drawable icon;
	//Ӧ�ó�������
	private String appName;
	//Ӧ�ó���·��
	private String apkPatn;
	//Ӧ�ó����С
	private long appSize;
	//�Ƿ����ֻ��洢
	private boolean isInRoom;
	//�Ƿ����û�Ӧ��
	private boolean isUserApp;
	//�Ƿ�ѡ��
	private boolean isSelected=false;
	//�õ�APPλ���ַ���
	public String getApplocation(boolean isInRoom){
		if(isInRoom){
			return "�ֻ��ڴ�";
		}else{
			return "�ⲿ�洢";
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
		builder.append("����").append(packageName).append(",").append(appName).append(",").append(appSize).append(",").append(apkPatn);
		
		return builder.toString();
	}
	
	
	

}
