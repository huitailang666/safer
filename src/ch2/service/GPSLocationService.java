package ch2.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class GPSLocationService extends Service {
	
	private LocationManager locationManager;
	private String TAG="GPSLocationService";
	private LocationListener locationListener;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
		
		
		locationListener=new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				StringBuilder sb=new StringBuilder();
				sb.append("accuracy:"+location.getAccuracy()+"\n");
				sb.append("speed:"+location.getSpeed()+"\n");
				sb.append("longitude:"+location.getLongitude()+"\n");
				sb.append("latitude:"+location.getLatitude()+"\n");
				String result=sb.toString();
				SharedPreferences preferences=getSharedPreferences("phone", 0);
				String safePhoneNumber=preferences.getString("phoneNumber", "");
				SmsManager.getDefault().sendTextMessage(safePhoneNumber, null, result, null, null);
				Log.d(TAG, result);
				Toast.makeText(GPSLocationService.this, result, 0).show();
				stopSelf();
				
			}
		};
		Criteria criteria=new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(true);
		String name=locationManager.getBestProvider(criteria, true);
		Toast.makeText(GPSLocationService.this,"best location provider:"+name, 0).show();
		locationManager.requestLocationUpdates(name, 0, 0, locationListener);
		
	
	}
	
	//×¢ÏúÎ»ÖÃ¼àÌýÆ÷
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationManager.removeUpdates(locationListener);
		locationListener=null;
	}

}
