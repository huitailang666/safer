package ch5.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ch5.adapter.ScanVirusAdapter;
import ch5.dao.AntiVirusDao;
import ch5.entity.ScanAppInfo;
import ch5.utils.MD5Utils;

import com.example.mobilesafe.R;

public class VirusScanSpeedActivity extends Activity implements OnClickListener{

	private TextView processTV;
	private TextView scanAppTV;
	private Button cancelBtn;
	private ImageView scanningIcon;
	
	private RotateAnimation animation;
	
	private ListView listView;
	private ScanVirusAdapter adapter;
	private List<ScanAppInfo> scanAppInfos=new ArrayList<ScanAppInfo>();
	
	private PackageManager pm;
	private SharedPreferences preferences;
	
	private int total;
	private boolean flag;
	private boolean isStop;
	private int process;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virusscanspeed);
		
		pm=getPackageManager();
		preferences=getSharedPreferences("virusscan", MODE_PRIVATE);
		
		initView();
		new MyTask().execute("scan");
		
	}
	
	private void initView(){
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.light_blue));
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		((TextView)findViewById(R.id.tv_title)).setText("病毒查杀进度");
		leftImgv.setImageResource(R.drawable.back);
		leftImgv.setOnClickListener(this);
		processTV=(TextView)findViewById(R.id.tv_scanprocess);
		scanAppTV=(TextView)findViewById(R.id.tv_scansapp);
		cancelBtn=(Button)findViewById(R.id.btn_canclescan);
		cancelBtn.setOnClickListener(this);
		listView=(ListView)findViewById(R.id.lv_scanapps);
		adapter=new ScanVirusAdapter(scanAppInfos, this);
		listView.setAdapter(adapter);
		scanningIcon=(ImageView)findViewById(R.id.imgv_scanningicon);
		startAnim();
		
	}
	
	private void startAnim(){
		if(animation==null){
			animation=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			
		}
		animation.setRepeatCount(Animation.INFINITE);
		animation.setDuration(2000);
		scanningIcon.startAnimation(animation);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgv_leftbtn:
			finish();
			break;

		case R.id.btn_canclescan:
			if((process==total)&&(process>0)){
				//扫描已完成
				finish();
			}else if((process>0)&&(process<total)&&(isStop==false)){
				//取消扫描
				scanningIcon.clearAnimation();
				flag=false;
				cancelBtn.setBackgroundResource(R.drawable.restart_scan_btn);
			}else if(isStop){
				//重新扫描
				startAnim();
				new MyTask().execute("rescan");
				cancelBtn.setBackgroundResource(R.drawable.cancle_scan_btn_selector);
			}
			
			break;
		}
		
	}
	
	
	
	
	public class MyTask extends AsyncTask<String, ScanAppInfo, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			process=0;
			isStop=false;
			flag=true;
			scanAppInfos.clear();
			List<PackageInfo> installedPackages=pm.getInstalledPackages(0);
			total=installedPackages.size();
			for(PackageInfo info:installedPackages){
				
				if(!flag){
					isStop=true;
					return 911;
				}
				
				String apkpath=info.applicationInfo.sourceDir;
				String md5info=MD5Utils.getFileMD5(apkpath);
				String result=AntiVirusDao.checkVirus(md5info, VirusScanSpeedActivity.this);
				ScanAppInfo scanAppInfo=new ScanAppInfo();
				if(result==null){
					scanAppInfo.description="扫描安全";
					scanAppInfo.isVirus=false;
				}else{
					scanAppInfo.description=result;
					scanAppInfo.isVirus=true;
				}
				process++;
				scanAppInfo.packageName=info.packageName;
				scanAppInfo.appName=info.applicationInfo.loadLabel(pm).toString();
				scanAppInfo.appicon=info.applicationInfo.loadIcon(pm);
				
				publishProgress(scanAppInfo);
				
			}
			
			
			return 0;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			scanAppTV.setText("初始化杀毒引擎...");
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result==0){
			scanAppTV.setText("扫描完成");
			}else if(result==911){
				scanAppTV.setText("扫描暂停");
			}
			scanningIcon.clearAnimation();
			cancelBtn.setBackgroundResource(R.drawable.scan_complete);
			
			//保存此次杀毒时间s
			saveScanTime();
		}

		@Override
		protected void onProgressUpdate(ScanAppInfo... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			scanAppTV.setText("正在扫描"+values[0].appName);
			processTV.setText((process*100/total)+"%");
			scanAppInfos.add(values[0]);
			adapter.notifyDataSetChanged();
			listView.setSelection(scanAppInfos.size());
			
		}
		
		private void saveScanTime(){
			SharedPreferences.Editor editor=preferences.edit();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			String currentTime=dateFormat.format(new Date());
			currentTime="上次查杀："+currentTime;
			editor.putString("lastVirusScan", currentTime);
			editor.commit();
		}
		
		
		
	}
	

}
