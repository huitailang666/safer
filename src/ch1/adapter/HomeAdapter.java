package ch1.adapter;

import com.example.mobilesafe.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {
	
	private String TAG="HomeAdapter";
	
	int[] imageId = { R.drawable.safe, 
			 R.drawable.callmsgsafe, R.drawable.app, R.drawable.trojan, R.drawable.sysoptimize,R.drawable.taskmanager, 
			 R.drawable.netmanager,R.drawable.atools,R.drawable.settings };
	String[] names = { "手机防盗",   "通讯卫士","软件管家","手机杀毒","缓存清理","进程管理", 
			"流量统计", "高级工具", "设置中心" };
	
	private Context mcontext;

	public HomeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mcontext=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return 9;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "--->>"+position);
		View view=View.inflate(mcontext, R.layout.item_home, null);
		ImageView imageView=(ImageView)view.findViewById(R.id.iv_icon);
		TextView textView=(TextView)view.findViewById(R.id.tv_name);
		imageView.setImageResource(imageId[position]);
		textView.setText(names[position]);
		
		return view;
	}

}
