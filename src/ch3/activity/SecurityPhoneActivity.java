package ch3.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch3.adapter.BlackContactAdapter;
import ch3.adapter.BlackContactAdapter.BlackContactCallBack;
import ch3.db.BlackNumberDao;
import ch3.entity.BlackContactInfo;

import com.example.mobilesafe.R;

public class SecurityPhoneActivity extends Activity implements OnClickListener {
	
	private FrameLayout haveBlackNumber;
	private FrameLayout noBlackNumber;
	private ListView listView;
	
	private BlackNumberDao dao;
	private List<BlackContactInfo> blackContactInfos=new ArrayList<BlackContactInfo>();
	private int pagenumber=0;
	private int pagesize=15;	
	private int totalnumber;
	private BlackContactAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_securityphone);
		initview();
		fillData();
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(totalnumber!=dao.getTotalNumber()){
			if(dao.getTotalNumber()>0){
				haveBlackNumber.setVisibility(View.VISIBLE);
				noBlackNumber.setVisibility(View.GONE);
			}else{
				haveBlackNumber.setVisibility(View.GONE);
				noBlackNumber.setVisibility(View.VISIBLE);
			}
			pagenumber=0;
			blackContactInfos.clear();
			blackContactInfos.addAll(dao.getPageBlackNumber(pagenumber, pagesize));
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
		}
		
		
	}
	
	
	private void fillData(){
		dao=new BlackNumberDao(SecurityPhoneActivity.this);
		totalnumber=dao.getTotalNumber();
		if(totalnumber==0){
			haveBlackNumber.setVisibility(View.GONE);
			noBlackNumber.setVisibility(View.VISIBLE);
		}else if(totalnumber>0){
			haveBlackNumber.setVisibility(View.VISIBLE);
			noBlackNumber.setVisibility(View.GONE);
			pagenumber=0;
			if(blackContactInfos.size()>0){
				blackContactInfos.clear();
			}
			blackContactInfos.addAll(dao.getPageBlackNumber(pagenumber, pagesize));
			
			if(adapter==null){
				adapter=new BlackContactAdapter(blackContactInfos, SecurityPhoneActivity.this);
				adapter.setCallBack(new BlackContactCallBack() {
					
					@Override
					public void DataSizeChanged() {
						// TODO Auto-generated method stub
						fillData();
					}
				});
			listView.setAdapter(adapter);	
			}
		else{
			adapter.notifyDataSetChanged();
		}
		}
	}
	
	
	private void initview(){
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		((TextView)findViewById(R.id.tv_title)).setText("Í¨Ñ¶ÎÀÊ¿");
		leftImgv.setOnClickListener(this);
		leftImgv.setImageResource(R.drawable.back);
		haveBlackNumber=(FrameLayout)findViewById(R.id.fl_haveblacknumber);
		noBlackNumber=(FrameLayout)findViewById(R.id.fl_noblacknumber);
		findViewById(R.id.btn_addblacknumber).setOnClickListener(this);
		listView=(ListView)findViewById(R.id.lv_blacknumbers);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					int lastVisablePosition=listView.getLastVisiblePosition();
					if(lastVisablePosition==blackContactInfos.size()-1){
						pagenumber++;
						if(pagenumber*pagesize>=totalnumber){
							Toast.makeText(SecurityPhoneActivity.this, "no more data", 0).show();
							
						}
					}else{
						blackContactInfos.addAll(dao.getPageBlackNumber(pagenumber, pagesize));
						adapter.notifyDataSetChanged();
					}
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgv_leftbtn:
			finish();
			break;

		case R.id.btn_addblacknumber:
			startActivity(new Intent(SecurityPhoneActivity.this, AddBlackNumberActivity.class));
			break;
		}
	}

}
