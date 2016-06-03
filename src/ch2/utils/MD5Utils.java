package ch2.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class MD5Utils {
	
	private static String TAG="MD5Utils";

	public static String encode(String text){
		
		try {
			MessageDigest digest= MessageDigest.getInstance("MD5");
			digest.update(text.getBytes());
			return getHashString(digest);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public static String getHashString(MessageDigest digest){
		StringBuilder builder=new StringBuilder();
		for(byte b:digest.digest()){
			builder.append(Integer.toHexString((b>>4)&0xf));
			builder.append(Integer.toHexString(b&0xf));
		}
		Log.d(TAG, "--->>>"+builder.toString());
		return builder.toString();
	}
}
