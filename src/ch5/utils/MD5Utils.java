package ch5.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import android.util.Log;

public class MD5Utils {
	static String TAG="MD5Utils";
	
	public static String getFileMD5(String path){
		try {
			MessageDigest digest=MessageDigest.getInstance("md5");
			File file=new File(path);
			InputStream in;
			
			//装饰着模式
			in=new BufferedInputStream(new FileInputStream(file));
			byte [] buffer=new byte[1024];
			int len=-1;
			while((len=in.read(buffer))!=-1){
				digest.update(buffer, 0, len);
				
			}
			in.close();
			byte []result=digest.digest();
			StringBuilder builder=new StringBuilder();
			for(byte b:result){
				int num=b&0xff;
				String hex=Integer.toHexString(num);
				if(hex.length()==1){
					builder.append("0"+hex);
				}else{
					builder.append(hex);
				}
				
			}
			Log.d(TAG, builder.toString());
			return builder.toString();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		

	}

}
