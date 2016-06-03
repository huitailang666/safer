package ch8.adapter;

import java.util.List;

import com.example.mobilesafe.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch8.entity.TrafficInfo;

public class TrafficAdapter extends BaseAdapter {

	private Context context;
	private List<TrafficInfo> trafficInfos;
	
	
	public TrafficAdapter(Context context,List<TrafficInfo> trafficInfos) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.trafficInfos=trafficInfos;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trafficInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trafficInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=View.inflate(context, R.layout.item_traffic_manager1, null);
			viewHolder.iv_traffic_ico=(ImageView)convertView.findViewById(R.id.imgv_traffic_appicon);
			viewHolder.tv_traffic_name=(TextView)convertView.findViewById(R.id.tv_traffic_appname);
			viewHolder.tv_traffic_received=(TextView)convertView.findViewById(R.id.tv_traffic_received);
			viewHolder.tv_traffic_transmitted=(TextView)convertView.findViewById(R.id.tv_traffic_transmitted);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder=(ViewHolder)convertView.getTag();
			
		}
		TrafficInfo trafficInfo=trafficInfos.get(position);
		viewHolder.iv_traffic_ico.setImageDrawable(trafficInfo.getIcon());
		viewHolder.tv_traffic_name.setText(trafficInfo.getName());
		long received=TrafficStats.getUidRxBytes(trafficInfo.getUid());
		//更新的流量存入实体 为了sort   timer 3s 一次顺便 更新trafficInfo实体的流量 用来排序
		trafficInfo.setReceived(received);
		viewHolder.tv_traffic_received.setText("R:"+Formatter.formatFileSize(context, received));
		long transmitted=TrafficStats.getUidTxBytes(trafficInfo.getUid());
		trafficInfo.setTransmitted(transmitted);
		viewHolder.tv_traffic_transmitted.setText("T:"+Formatter.formatFileSize(context, transmitted));
		
		
		
		
		
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView iv_traffic_ico;
		TextView tv_traffic_name;
		TextView tv_traffic_received;
		TextView tv_traffic_transmitted;
	}

}
