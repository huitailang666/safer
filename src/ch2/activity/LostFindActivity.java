package ch2.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mobilesafe.R;

public class LostFindActivity extends Activity implements OnClickListener{
	

	private SharedPreferences preferences;
	private TextView safePhoneTV;
	private TextView protectStatusTV;
	private ToggleButton toggleButton;
	private RelativeLayout interSetupRL;
	private String TAG="LostFindActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lostfind);
		
		if(!isSetup()){
			Log.d(TAG, "66666is not setup");
			startActivityAndFinishSelf(SetUp1Activity.class);
		}
		
		initView();
		
	}
	
	private void initView(){
		
		TextView titleTv=(TextView)findViewById(R.id.tv_title);
		titleTv.setText("手机防盗");
		ImageView leftImgv =(ImageView)findViewById(R.id.imgv_leftbtn);
		leftImgv.setOnClickListener(this);
		leftImgv.setImageResource(R.drawable.back);
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
		safePhoneTV=(TextView)findViewById(R.id.tv_safephone);
		preferences=getSharedPreferences("phone", 0);
		safePhoneTV.setText(preferences.getString("phoneNumber", ""));
		toggleButton=(ToggleButton)findViewById(R.id.togglebtn_lostfind);
		interSetupRL=(RelativeLayout)findViewById(R.id.rl_inter_setup_wizard);
		interSetupRL.setOnClickListener(this);
		protectStatusTV=(TextView)findViewById(R.id.tv_lostfind_protectstauts);
		
		preferences=getSharedPreferences("security", 0);
		boolean protecting=preferences.getBoolean("protecting", true);
		if(protecting){
			protectStatusTV.setText("防盗保护已开启");
			toggleButton.setChecked(true);
		}else{
			protectStatusTV.setText("防盗保护没有开启");
			toggleButton.setChecked(false);
		}
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					protectStatusTV.setText("防盗保护已开启");
				
				}else{
					protectStatusTV.setText("防盗保护没有开启");
					
				}
				preferences=getSharedPreferences("security", 0);
				SharedPreferences.Editor editor=preferences.edit();
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
		
		
	}
	
	private boolean isSetup(){
		preferences=getSharedPreferences("security", 0);
		return preferences.getBoolean("isSetUp", false);
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_inter_setup_wizard:
			startActivityAndFinishSelf(SetUp1Activity.class);
			break;

		case R.id.imgv_leftbtn:
			finish();
			break;
		}
	}
	
	/**
	 * @param cls    
	 */
	public void startActivityAndFinishSelf(Class<?>cls){
		Intent intent=new Intent(LostFindActivity.this, cls);
		startActivity(intent);
		finish();
	}
	

}
