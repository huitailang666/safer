package ch5.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class VirusScanActivity extends Activity implements OnClickListener {
	
	private TextView lastVirusScanTV;
	private RelativeLayout relativeLayout;
	
	private SharedPreferences preferences;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virusscan);
		
		preferences=getSharedPreferences("virusscan", MODE_PRIVATE);
		initView();
		
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String str=preferences.getString("lastVirusScan", "ªπŒ¥≤È…±≤°∂æ");
		lastVirusScanTV.setText(str);
	}
	
	private void initView(){
		lastVirusScanTV=(TextView)findViewById(R.id.tv_lastscantime);
		relativeLayout=(RelativeLayout)findViewById(R.id.rl_allscanvirus);
		relativeLayout.setOnClickListener(this);
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.light_blue));
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		((TextView)findViewById(R.id.tv_title)).setText("≤°∂æ≤È…±");
		leftImgv.setOnClickListener(this);
		leftImgv.setImageResource(R.drawable.back);
		
		
	}
	
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_allscanvirus:
			
			Intent intent=new Intent(VirusScanActivity.this, VirusScanSpeedActivity.class);
			startActivity(intent);
			
			break;
		
		case R.id.imgv_leftbtn:
			finish();

		
		}
		
	}
	
	

}
