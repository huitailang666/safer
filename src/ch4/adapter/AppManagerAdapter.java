package ch4.adapter;

import java.util.List;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ch4.entity.AppInfo;
import ch4.utils.DensityUtil;
import ch4.utils.EngineUtils;

import com.example.mobilesafe.R;
import com.example.mobilesafe.R.color;




public class AppManagerAdapter extends BaseAdapter {
	
	
	private Context context;
	private List<AppInfo> UserAppInfos;
	private List<AppInfo> SystemAppInfos;
	

	public AppManagerAdapter(Context context,
			List<AppInfo> userAppInfos, List<AppInfo> systemAppInfos) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.UserAppInfos=userAppInfos;
		this.SystemAppInfos=systemAppInfos;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (UserAppInfos.size()+SystemAppInfos.size()+2);
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(position==0){
			return null;
		}else if(position==UserAppInfos.size()+1){
			return null;
		}
		AppInfo appInfo;
		if(position<UserAppInfos.size()+1){
			//用户程序
			appInfo=UserAppInfos.get(position-1);
		}else{
			//系统程序
			int location=position-UserAppInfos.size()-2;
			appInfo=SystemAppInfos.get(location);
			
			
		}
		
		
		return appInfo;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//textview
		if(position==0){
			TextView textView=getTextView();
			textView.setText("用户程序："+UserAppInfos.size()+"个");
			return textView;
			
		}else if(position==UserAppInfos.size()+1){
			TextView textView=getTextView();
			textView.setText("系统程序："+SystemAppInfos.size()+"个");
			return textView;
			
		}
		
		AppInfo appInfo;
		if(position<UserAppInfos.size()+1){
			appInfo=UserAppInfos.get(position-1);
			
		}else{
			appInfo=SystemAppInfos.get(position-UserAppInfos.size()-2);
		}
		
		
		ViewHolder viewHolder=null;
		
		if(convertView!=null&convertView instanceof LinearLayout){
			viewHolder=(ViewHolder)convertView.getTag();
			
		}else{
			viewHolder=new ViewHolder();
			convertView=View.inflate(context, R.layout.item_appmanager_list, null);
			viewHolder.appIconImgv=(ImageView)convertView.findViewById(R.id.imgv_appicon);
			viewHolder.appLocationTV=(TextView)convertView.findViewById(R.id.tv_appisroom);
			viewHolder.appSizeTV=(TextView)convertView.findViewById(R.id.tv_appsize);
			viewHolder.appNameTV=(TextView)convertView.findViewById(R.id.tv_appname);
			viewHolder.luanchAppTV=(TextView)convertView.findViewById(R.id.tv_launch_app);
			viewHolder.settingAppTV=(TextView)convertView.findViewById(R.id.tv_setting_app);
			viewHolder.shareAppTV=(TextView)convertView.findViewById(R.id.tv_share_app);
			viewHolder.uninstallAppTV=(TextView)convertView.findViewById(R.id.tv_uninstall_app);
			viewHolder.appOptionLL=(LinearLayout)convertView.findViewById(R.id.ll_option_app);
			convertView.setTag(viewHolder);
			
			
		}
		
		
		if(appInfo!=null){
			viewHolder.appLocationTV.setText(appInfo.getApplocation(appInfo.isInRoom()));
			viewHolder.appIconImgv.setImageDrawable(appInfo.getIcon());
			viewHolder.appSizeTV.setText(Formatter.formatFileSize(context, appInfo.getAppSize()));
			viewHolder.appNameTV.setText(appInfo.getAppName());
			
			if(appInfo.isSelected()){
				viewHolder.appOptionLL.setVisibility(View.VISIBLE);
			}else{
				viewHolder.appOptionLL.setVisibility(View.GONE);
			}
			
		}
		
		
		MyClickListener listener=new MyClickListener(appInfo);
		viewHolder.luanchAppTV.setOnClickListener(listener);
		viewHolder.settingAppTV.setOnClickListener(listener);
		viewHolder.shareAppTV.setOnClickListener(listener);
		viewHolder.uninstallAppTV.setOnClickListener(listener);
		
		
		
		
		return convertView;
	}
	
	
	
	static class ViewHolder{
		
		TextView luanchAppTV;
		
		TextView uninstallAppTV;
		
		TextView shareAppTV;
		
		TextView settingAppTV;
		
		ImageView appIconImgv;
		
		TextView appLocationTV;
		
		TextView appSizeTV;
		
		TextView appNameTV;
		
		LinearLayout appOptionLL;
	}
	
	class MyClickListener implements OnClickListener{
		
		
		private AppInfo appInfo;
		
		public MyClickListener(AppInfo appInfo) {
			super();
			// TODO Auto-generated constructor stub
			this.appInfo=appInfo;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_launch_app:
				EngineUtils.startApplication(context, appInfo);
				break;

			case R.id.tv_share_app:
				EngineUtils.shareApplication(context, appInfo);
				break;
			case R.id.tv_setting_app:
				EngineUtils.settingAppDetail(context, appInfo);
				break;

			case R.id.tv_uninstall_app:
				if(appInfo.getPackageName().equals(context.getPackageName())){
					Toast.makeText(context, "你不能卸载自己！", 0).show();
					return;
				}
				EngineUtils.uninstallApplication(context, appInfo);
				break;


			
			}
		}
		
		
		
		
		
	}

	
	private TextView getTextView(){
		
		TextView textView=new TextView(context);
		textView.setBackgroundColor(context.getResources().getColor(color.graye5));
		textView.setPadding(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
		textView.setTextColor(context.getResources().getColor(color.black));
		
		return textView;
		
	}
	
	
	
}
