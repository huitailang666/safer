package ch8.db.service;

import java.util.Map;

public interface TrafficService {
	
	public boolean insertTodayTraffic(Object []params);
	
	public boolean updateTodayTraffic(long gprs,long wifi);
	
	public Map<String,String> getTodayTraffic(String dateString); 

}
