package com.example.mobilesafe;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import ch1.adapter.HomeAdapter;
import ch2.activity.SetUp1Activity;
import ch2.dialog.InputPasswordDialog;
import ch2.dialog.SetUpPasswordDialog;
import ch2.receiver.MyDeviceAdminReceiver;
import ch2.service.GPSLocationService;
import ch2.utils.MD5Utils;
import ch3.activity.SecurityPhoneActivity;
import ch4.activity.AppManagerActivity;
import ch5.activity.VirusScanActivity;
import ch6.activity.CacheClearListActivity;
import ch8.activity.TrafficActivity;


public class HomeActivity extends Activity {
	
	private GridView gv_home;
	private long mExitTime=0;
	private static String TAG="HomeActivity";
	
	private SharedPreferences sharedPreferences;
	private DevicePolicyManager policyManager;
	private ComponentName componentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the activity_home.xml layout is set for the content view
		setContentView(R.layout.activity_home);
		// the GridView is captured from the layout with findViewById(int)
		gv_home=(GridView)findViewById(R.id.gv_home);
		// The setAdapter() method then sets a custom adapter (HomeAdapter) as
		// the source for all items to be displayed in the grid
		gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
		// This anonymous instance
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			// parent indicates gridView \ view indicates each item \position
			// indicates ...

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				/*"手机防盗",   "通讯卫士","软件管家","手机杀毒","缓存清理","进程管理", 
			"流量统计", "高级工具", "设置中心"
				 * */
				switch (position) {
				case 0:
					if(!isPwdSet()){
					showSetupPwdDialog();
					}else{
						showInputPwdDialog();
					}
					break;
				case 1:
					startActivity(SecurityPhoneActivity.class);

					break;
				case 2:
					//软件管家
					startActivity(AppManagerActivity.class);
					
					break;
				case 3:
					//手机杀毒
					startActivity(VirusScanActivity.class);
					break;
				case 4:
					//缓存清理
					startActivity(CacheClearListActivity.class);
					
					break;
				case 5:
					Intent service=new Intent(HomeActivity.this, GPSLocationService.class);
					startService(service);

					break;
				case 6:
					//流量统计
					startActivity(TrafficActivity.class);

					break;
				case 7:

					break;

				case 8:
					break;
				}
			}
		});
		//获取设备管理员
		policyManager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
		//生情权限
		componentName=new ComponentName(HomeActivity.this, MyDeviceAdminReceiver.class);
		//如果没有权限  则申请权限
		boolean active=policyManager.isAdminActive(componentName);
		if(!active){
			//获取管理员权限
			Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "获取超级管理员权限，用于远程锁屏和清除数据");
			startActivity(intent);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if((System.currentTimeMillis()-mExitTime)<2000){
				System.exit(0);
			}else{
				Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime=System.currentTimeMillis();
			}
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	private boolean isPwdSet(){
		
		sharedPreferences=getSharedPreferences("password", 0);
		boolean flag=sharedPreferences.getBoolean("IsPwdSet", false);
		Log.d(TAG, "-->>>>>"+flag);
		return flag;
	}
	
	/**
	 * 弹出设置密码对话框
	 */
	private void showSetupPwdDialog() {
		final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(
				HomeActivity.this);
		setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {

			@Override
			public void ok() {
				// TODO Auto-generated method stub
				/*
				 * 判断两次密码是否一致
				 */
				String firstPwdET = setUpPasswordDialog.getmFirstPwdET()
						.getText().toString().trim();
				String affirmPwdET = setUpPasswordDialog.getmAffirmPwdET()
						.getText().toString().trim();
				Log.i(TAG, "--->>>" + firstPwdET + "--->>>" + affirmPwdET);
				if (!TextUtils.isEmpty(firstPwdET)
						&& !TextUtils.isEmpty(affirmPwdET)) {

					if (firstPwdET.equals(affirmPwdET)) {
						savePwd(MD5Utils.encode(firstPwdET));
						setUpPasswordDialog.dismiss();
						showInputPwdDialog();
					} else {
						Toast.makeText(HomeActivity.this, "两次密码不一致",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(HomeActivity.this, "密码不能为空",
							Toast.LENGTH_SHORT).show();

				}
			}

			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				setUpPasswordDialog.dismiss();

			}
		});
		setUpPasswordDialog.setCancelable(true);
		setUpPasswordDialog.show();
	}
	
	
	/**
	 * 弹出输入设置密码对话框
	 */
	private void showInputPwdDialog(){
		final InputPasswordDialog inputPasswordDialog=new InputPasswordDialog(HomeActivity.this);
		inputPasswordDialog.setCallBack(new InputPasswordDialog.MyCallBack() {
			
			@Override
			public void ok() {
				// TODO Auto-generated method stub
				String inputPwd=inputPasswordDialog.getmInputPwdET().getText().toString().trim();
				if(!TextUtils.isEmpty(inputPwd)){
					String pwd=getPwd();
					if(MD5Utils.encode(inputPwd).equals(pwd)){		
						Log.d(TAG, "--->>>"+pwd+"--->>>"+inputPwd);
						//进入防盗主界面
						inputPasswordDialog.dismiss();
						startActivity(SetUp1Activity.class);
					}else{
						Toast.makeText(HomeActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					
				}
			}
			
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				inputPasswordDialog.dismiss();
				
			}
		}); 
		
	
		inputPasswordDialog.setCancelable(true);
		inputPasswordDialog.show();
	}
	
	
	/**保存密码
	 * @param pwd
	 */
	private void savePwd(String pwd){
		sharedPreferences = getSharedPreferences(
				"password", 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("IsPwdSet", true);
		editor.putString("pwd", pwd);
		editor.commit();
	}
	
	/**获取密码
	 * @return
	 */
	private String getPwd(){
		sharedPreferences=getSharedPreferences("password", 0);
		return sharedPreferences.getString("pwd", "");
	}
	
	/**开启另一个activty 但不关闭自己s
	 * @param cls
	 */
	void startActivity(Class<?>cls){
		Intent intent=new Intent(HomeActivity.this,cls);
		startActivity(intent);
	}
	
}
