package ch5.adapter;

import java.util.List;

import com.example.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch5.entity.ScanAppInfo;

public class ScanVirusAdapter extends BaseAdapter {
	
	private List<ScanAppInfo> scanAppInfos;
	private Context context;
	

	public ScanVirusAdapter(List<ScanAppInfo> scanAppInfos,Context context) {
		// TODO Auto-generated constructor stub
		super();
		this.scanAppInfos=scanAppInfos;
		this.context=context;
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scanAppInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return scanAppInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.item_list_applock, null);
			holder=new ViewHolder();
			holder.appIconImgv=(ImageView)convertView.findViewById(R.id.imgv_appicon);
			holder.appNameTV=(TextView)convertView.findViewById(R.id.tv_appname);
			holder.scanIconImgv=(ImageView)convertView.findViewById(R.id.imgv_lock);
			
			
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		
		ScanAppInfo scanAppInfo=scanAppInfos.get(position);
		if(!scanAppInfo.isVirus){
			holder.scanIconImgv.setBackgroundResource(R.drawable.blue_right_icon);
			holder.appNameTV.setTextColor(context.getResources().getColor(R.color.black));
			holder.appNameTV.setText(scanAppInfo.appName);
			
		}else{
			holder.appNameTV.setTextColor(context.getResources().getColor(R.color.bright_red));
			holder.appNameTV.setText(scanAppInfo.appName+"("+scanAppInfo.description+")");
		}
		holder.appIconImgv.setImageDrawable(scanAppInfo.appicon);
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView appIconImgv;
		TextView appNameTV;
		ImageView scanIconImgv;
		
	}

}
