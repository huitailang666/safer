package ch4.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ch4.adapter.AppManagerAdapter;
import ch4.entity.AppInfo;
import ch4.utils.AppInfoParser;

import com.example.mobilesafe.R;

public class AppManagerActivity extends Activity implements OnClickListener {
	
	private String TAG="AppManagerActivity";
	
	private TextView phoneMemoryTV;
	private TextView SDMemoryTV;
	private ListView listView;
	private TextView appNumTV;
	
	private List<AppInfo> appInfos;
	private List<AppInfo> userAppInfos=new ArrayList<AppInfo>();
	private List<AppInfo> systemAppInfos=new ArrayList<AppInfo>();
	
	private AppManagerAdapter adapter;
	
	private UninstallReceiver receiver;
	
	
	private Handler mHandler=new Handler(){
		
		public void handleMessage(Message msg){
			switch (msg.what) {
			case 10:
				if(adapter==null){
					adapter=new AppManagerAdapter(AppManagerActivity.this, userAppInfos, systemAppInfos);
					
				}
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				Log.d(TAG, "msg.what=10");
				break;

			case 15:
				adapter.notifyDataSetChanged();
				break;
			}
		};
	
	
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);
		initView();
		receiver=new UninstallReceiver();
		IntentFilter filter=new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);
	
	}
	
	private void getMemoryFromPhone(){
		long avail_sd=Environment.getExternalStorageDirectory().getFreeSpace();
		long avail_rom=Environment.getDataDirectory().getFreeSpace();
		
		String str_avail_sd=Formatter.formatFileSize(AppManagerActivity.this, avail_sd);
		String str_avail_rom=Formatter.formatFileSize(AppManagerActivity.this, avail_rom);
		
		phoneMemoryTV.setText("剩余手机内存："+str_avail_rom);
		SDMemoryTV.setText("剩余SD卡内存:"+str_avail_sd);
		
		
	}
	
	private void initView(){
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_yellow));
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		((TextView)findViewById(R.id.tv_title)).setText("软件管家");
		leftImgv.setOnClickListener(this);
		leftImgv.setImageResource(R.drawable.back);
		phoneMemoryTV=(TextView)findViewById(R.id.tv_phonememory_appmanager);
		SDMemoryTV=(TextView)findViewById(R.id.tv_sdmemory_appmanager);
		appNumTV=(TextView)findViewById(R.id.tv_appnumber);
		listView=(ListView)findViewById(R.id.lv_appmanager);
		
		getMemoryFromPhone();
		initData();
		initListener();
		
	}
	
	private void initListener(){
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				if(adapter!=null){
					new Thread(){
						public void run(){
							
							AppInfo mAppInfo=(AppInfo)adapter.getItem(position);
							//记住当前条目的状态
							boolean flag=mAppInfo.isSelected();
							
							for(AppInfo appInfo:userAppInfos){
								appInfo.setSelected(false);
							}
							for(AppInfo appInfo:systemAppInfos){
								appInfo.setSelected(false);
							}
							if(mAppInfo!=null){
								if(flag){
									mAppInfo.setSelected(false);
								}else{
									mAppInfo.setSelected(true);
								}
								mHandler.sendEmptyMessage(15);
							}
							
						};
						
					}.start();
					
				}
				
			}
			
			
		});
		
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(firstVisibleItem>=userAppInfos.size()+1){
					appNumTV.setText("系统程序："+systemAppInfos.size()+"个");
				}else{
					appNumTV.setText("用户程序："+userAppInfos.size()+"个");
				}
			}
		});
		
	}
	
	
	private void initData(){
		
		appInfos=new ArrayList<AppInfo>();
		new Thread(){
			public void run(){
				appInfos.clear();
				userAppInfos.clear();
				systemAppInfos.clear();
				appInfos.addAll(AppInfoParser.getAppInfos(AppManagerActivity.this));
				Log.d(TAG, appInfos.toString());
				for(AppInfo appInfo:appInfos){
					if(appInfo.isUserApp()){
						userAppInfos.add(appInfo);
					}else{
						systemAppInfos.add(appInfo);
					}
				}
				mHandler.sendEmptyMessage(10);
				
			};
			
			
		}.start();
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgv_leftbtn:
			finish();
			break;

		}
	}
	
	
	class UninstallReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			initData();
			Log.d(TAG, "666收到卸载了");
		}
		
		
		
	}
	
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
		receiver=null;
		Log.d(TAG, "666广播卸载了");
		
	}
	
}
