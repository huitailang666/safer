package ch2.activity;



import java.util.TooManyListenersException;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mobilesafe.R;

public class SetUp4Activity extends BaseSetUpActivity {

	private TextView StatusTV;
	private ToggleButton toggleButton;
	private SharedPreferences preferences;
	private String TAG="SetUp4Activity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		initView();
	
	}
	
	private void initView(){
		((RadioButton)findViewById(R.id.rb_four)).setChecked(true);
		StatusTV=(TextView)findViewById(R.id.tv_setup4_status);
		toggleButton=(ToggleButton)findViewById(R.id.togglebtn_securityfunction);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					StatusTV.setText("防盗保护已开启");
				}else{
					StatusTV.setText("防盗保护没有开启");
				}
				SharedPreferences.Editor editor=preferences.edit();
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
		preferences=getSharedPreferences("security", 0);
		boolean flag=preferences.getBoolean("protecting", false);
		if(flag){
			StatusTV.setText("防盗保护已开启");
			toggleButton.setChecked(true);
		}else{
			StatusTV.setText("防盗保护没有开启");
			toggleButton.setChecked(false);
		}
		
		
		
	}
	
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean("isSetUp", true);
		editor.commit();
		Log.d(TAG, "xxxx");
		startActivityAndFinishSelf(LostFindActivity.class);
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Log.d(TAG, "xxxx6");
		startActivityAndFinishSelf(SetUp3Activity.class);
	}
	
	

}
