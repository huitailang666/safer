package ch8.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import ch8.adapter.TrafficAdapter;
import ch8.db.dao.TrafficDao;
import ch8.entity.TrafficInfo;
import ch8.utils.ComparatorValuesByReceived;
import ch8.utils.CompartorValuesByTransmitted;
import ch8.utils.ResolveTools;
import ch8.utils.SaveTrafficUtil;

import com.example.mobilesafe.R;

public class TrafficActivity extends Activity {
	
	private String TAG="TrafficActivity";
	
	private RadioGroup radioGroup;
	private RadioButton radioButton_r;
	private RadioButton radioButton_t;
	
	private TextView wifi_tv;
	private TextView g_tv;
	private TextView wifi_today_tv;
	private TextView g_today_tv;
	private ListView lv_traffic_content;
	private TrafficAdapter adapter;
	
	private List<TrafficInfo> trafficInfos;
	
	private Timer timer;
	private TimerTask timerTask;
	
	//��Խӿڱ��
	private Comparator<TrafficInfo> comparator=new ComparatorValuesByReceived();//Ĭ�ϰ�received����s
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_traffic_manager);
		Log.d(TAG, "onCreate");
		
		initView();
		setTotalTraffic();
		trafficInfos=new ArrayList<TrafficInfo>();
		//sort
		ResolveTools.initResolveInfos(TrafficActivity.this, trafficInfos);
		//Collections.sort(trafficInfos, new ComparatorValues());
		adapter=new TrafficAdapter(TrafficActivity.this, trafficInfos);
		lv_traffic_content.setAdapter(adapter);
		
	
	}
	
	private void initView(){
		
		wifi_today_tv=(TextView)findViewById(R.id.traffic_wifi_today_tv);
		g_today_tv=(TextView)findViewById(R.id.traffic_234g_today_tv);
		wifi_tv=(TextView)findViewById(R.id.traffic_wifi_tv);
		g_tv=(TextView)findViewById(R.id.traffic_234g_tv);
		lv_traffic_content=(ListView)findViewById(R.id.traffic_content_lv);
		radioButton_r=(RadioButton)findViewById(R.id.radiobutton1);
		radioButton_r.setChecked(true);
		radioButton_t=(RadioButton)findViewById(R.id.radiobutton2);
		radioGroup=(RadioGroup)findViewById(R.id.radiogroup1);
		//��ѡ��ť���¼�������setOnCheckedChangeListener���Ե�ѡ��ť���м���
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==radioButton_r.getId()){
					//��receive����
					comparator=new ComparatorValuesByReceived();
				}else if(checkedId==radioButton_t.getId()){
					//��transmit����
					comparator=new CompartorValuesByTransmitted();
				}
			}
		});
		
	}
	
	
	
	public void setTotalTraffic(){
		
		long total_234g_received=TrafficStats.getMobileRxBytes();
		long total_234g_transmitted=TrafficStats.getMobileTxBytes();
		long total_234g=total_234g_received+total_234g_transmitted;
		
		long total=TrafficStats.getTotalRxBytes()+TrafficStats.getTotalTxBytes();
		long total_wifi=total-total_234g;
		
		wifi_tv.setText("wifi:"+Formatter.formatFileSize(TrafficActivity.this, total_wifi));
		g_tv.setText("234g:"+Formatter.formatFileSize(TrafficActivity.this, total_234g));
		
		TrafficDao dao=new TrafficDao(TrafficActivity.this);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String currentTime = dateFormat.format(new Date());
		Map<String,String> map= dao.getTodayTraffic(currentTime);
		if(map.get("gprs")==null||map.get("wifi")==null||map.get("gprs")==""||map.get("wifi")==null){
			Toast.makeText(TrafficActivity.this, "�����һ�β�ѯ���������ݿ⻹δ�����¼�¼�����ھ�ȥ���ӡ�����", 0).show();
			if(dao.insertTodayTraffic(new String[]{Long.toString(total_234g),Long.toString(total_wifi)})){
				Toast.makeText(TrafficActivity.this, "�¼�¼����ɹ�������", 0).show();
				
			}
		}else{
			//�����ǲ��ǿ�����ĵ�һ������activity
			SharedPreferences preferences=getSharedPreferences("trafficInfo", Context.MODE_PRIVATE);
			boolean flag=preferences.getBoolean("newStartFromShutingDown", false);
			if(flag){
				//if true 
				//�����ݿ��ȡ�����ۼ�ʹ�õ�����
				total_wifi=total_wifi+Long.parseLong(map.get("wifi"));
				total_234g=total_234g+Long.parseLong(map.get("gprs"));
				//֮��Ͳ��ǿ������һ��������
				SharedPreferences.Editor editor=preferences.edit();
				editor.putBoolean("newStartFromShutingDown", false);
				editor.commit();
				Toast.makeText(TrafficActivity.this, "�������һ������������档����", 0).show();
			}else{
				//ֱ�Ӷ�ȡandroid����ʱ�ṩ�����ݣ�
				//�����ݿ��ȡ�����ۼ�ʹ�õ�����
				//�ж��ǲ��ǰ�װAPP����  �����⴦��s
				boolean flag2=preferences.getBoolean("haveRestarted", false);
				if(flag2){
				total_wifi=total_wifi+Long.parseLong(map.get("wifi"));
				total_234g=total_234g+Long.parseLong(map.get("gprs"));
				}
				Toast.makeText(TrafficActivity.this, "����֮�����������������߸հ�װappû��������������", 0).show();
			}
			
			
		}
		wifi_today_tv.setText("����wifi:"+Formatter.formatFileSize(TrafficActivity.this, total_wifi));
		g_today_tv.setText("����234g:"+Formatter.formatFileSize(TrafficActivity.this,total_234g));
		
				
		
	}
	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "onStart");
		timer=new Timer();
		timerTask=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d(TAG, "timer up !");
				Log.d(TAG, "thread id in timer:"+Thread.currentThread().getId());  //���߳�
				Message message=Message.obtain();
				handler.sendMessage(message);
				
			}
		};
		timer.schedule(timerTask, 1000, 3000);
	
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onStop");
		if(timer!=null){
			timer.cancel();
			timer=null;
			timerTask=null;
		}
		
	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.d(TAG, "thread id in handler:"+Thread.currentThread().getId());
			//sort
			Collections.sort(trafficInfos, comparator);
			adapter.notifyDataSetChanged();
		}
		
		
		
	};


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Log.d(TAG, "onBackPressed");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	
	

}
