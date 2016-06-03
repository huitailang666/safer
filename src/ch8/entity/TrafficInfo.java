package ch8.entity;

import android.graphics.drawable.Drawable;

public class TrafficInfo {
	
	private String name;
	private Drawable icon;
	private int uid;
	private long received;
	private long transmitted;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public long getReceived() {
		return received;
	}
	public void setReceived(long received) {
		this.received = received;
	}
	public long getTransmitted() {
		return transmitted;
	}
	public void setTransmitted(long transmitted) {
		this.transmitted = transmitted;
	}
	
	
	

}
