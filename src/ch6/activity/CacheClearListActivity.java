package ch6.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch6.adapter.CacheCleanAdapter;
import ch6.entity.CacheInfo;

import com.example.mobilesafe.R;

public class CacheClearListActivity extends Activity implements OnClickListener {
	
	private String TAG="CacheClearListActivity";
	
	private static final int SCANNING=100;
	private static final int FINISH=101;
	
	private long catheMemory=0;
	
	private PackageManager pm;
	private TextView recommendTV;
	private TextView canCleanTV;
	
	private ListView cacheLV;
	private Button cacheBtn;
	
	private AnimationDrawable animation;
	
	private List<CacheInfo> catheInfos=new ArrayList<CacheInfo>();
	private List<CacheInfo> mCatheInfos=new ArrayList<CacheInfo>();
	
	private CacheCleanAdapter adapter;
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SCANNING :
					PackageInfo info=(PackageInfo)msg.obj;
					recommendTV.setText("正在扫描："+info.packageName);
					canCleanTV.setText("已扫描缓存："+Formatter.formatFileSize(CacheClearListActivity.this, catheMemory));
					
					mCatheInfos.clear();
					mCatheInfos.addAll(catheInfos);
					adapter.notifyDataSetChanged();
					cacheLV.setSelection(mCatheInfos.size());
					
				break;

			case FINISH:
					animation.stop();
					if(catheMemory>0){
						cacheBtn.setEnabled(true);
						
					}else{
						cacheBtn.setEnabled(false);
						Toast.makeText(CacheClearListActivity.this, "手机 纯净", 0).show();
						
					}
				break;
			}
			
			
		}
		
		
	};
	
	private Thread thread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cacheclearlist);
		pm=getPackageManager();
		initView();
	
	}
	
	private void initView(){
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.rose_red));
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		leftImgv.setImageResource(R.drawable.back);
		leftImgv.setOnClickListener(this);
		((TextView)findViewById(R.id.tv_title)).setText("缓存扫描");
		recommendTV=(TextView)findViewById(R.id.tv_recommend_clean);
		canCleanTV=(TextView)findViewById(R.id.tv_can_clean);
		cacheLV=(ListView)findViewById(R.id.lv_scancache);
		cacheBtn=(Button)findViewById(R.id.btn_cleanall);
		cacheBtn.setOnClickListener(this);
		animation=(AnimationDrawable)findViewById(R.id.imgv_broom).getBackground();
		animation.setOneShot(false);
		animation.start();
		adapter=new CacheCleanAdapter(CacheClearListActivity.this, mCatheInfos);
		cacheLV.setAdapter(adapter);
		fillData();
	}
	
	private void fillData(){
		thread=new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				catheInfos.clear();
				List<PackageInfo> packageInfos=pm.getInstalledPackages(0);
				
				for(PackageInfo info:packageInfos){
					//????????????/
					PackageInfo packageInfo=info;
					Log.d(TAG, packageInfo.toString());
					getCatheSize(packageInfo);
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					Message message=Message.obtain();
					message.what=SCANNING;
					message.obj=packageInfo;
					handler.sendMessage(message);
					
					
				}
				
				Message message=Message.obtain();
				message.what=FINISH;
				handler.sendMessage(message);
				
			}
			
		};
		thread.start();
		
		
		
	}
	
	
	/**获取摸个包名对应的应用程序的缓存大小
	 * @param info
	 */
	private void getCatheSize(PackageInfo info){
		Method method=null;
		/*//getPackageSizeInfo方法在sdk中隐藏了,使用反射机制来获取此方法
        //1.先获取PackageManager提供的所有方法
		Method[]methods=PackageManager.class.getMethods();
		//2.遍历所有方法找到getPackageSizeInfo方法
		for(Method method2:methods){
			if("getPackageSizeInfo".equals(method2.getName())){
				method=method2;
				break;
			}
			
		}*/
		//或者
		try {
			method=PackageManager.class.getDeclaredMethod("getPackageSizeInfo",String.class,IPackageStatsObserver.class);
			method.invoke(pm, info.packageName,new MyPackObserver(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgv_leftbtn:
			
				finish();
				
			break;

		case R.id.btn_cleanall:
			if(catheMemory>0){
				Toast.makeText(CacheClearListActivity.this, "清理缓存", 0).show();
			}
			break;
		
		}
		
	}
	
	
	private class MyPackObserver extends IPackageStatsObserver.Stub{
		
		private PackageInfo info;
		
		public MyPackObserver(PackageInfo info){
			this.info=info;
		}

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			long cathesize=pStats.cacheSize;
			long datasize=pStats.dataSize;
			long codesize=pStats.codeSize;
			Log.d(TAG, succeeded+"/cathesize:"+cathesize+"/datasize:"+datasize+"/codesize:"+codesize);
			if(cathesize>=0){
				
				CacheInfo cacheInfo=new CacheInfo();
				cacheInfo.cacheSize=cathesize;
				cacheInfo.packageName=info.packageName;
				cacheInfo.appName=info.applicationInfo.loadLabel(pm).toString();
				cacheInfo.appIcon=info.applicationInfo.loadIcon(pm);
				catheInfos.add(cacheInfo);
				catheMemory+=cathesize;
			}
			
		}
		
		
	}

}

