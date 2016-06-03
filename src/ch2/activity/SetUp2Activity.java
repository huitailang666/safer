package ch2.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mobilesafe.R;

public class SetUp2Activity extends BaseSetUpActivity {
	
	private SharedPreferences preferences;
	private TelephonyManager telephonyManager;
	private Button mBindSimBn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		initView();
	}
	
	private void initView(){
		((RadioButton)findViewById(R.id.rb_second)).setChecked(true);
		mBindSimBn=(Button)findViewById(R.id.btn_bind_sim);
		mBindSimBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bindSim();
			}
		});
		if(isBind()){
			mBindSimBn.setEnabled(false);
		}else{
			mBindSimBn.setEnabled(true);
		}
		
	}
	
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		if(!isBind()){
			Toast.makeText(this, "sim还没绑定！", 0).show();
			return;
		}
		startActivityAndFinishSelf(SetUp3Activity.class);
		//Toast.makeText(this, "SetUp3activity！", 0).show();	
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		startActivityAndFinishSelf(SetUp1Activity.class);
	}
	
	private void bindSim(){
		if(!isBind()){
			telephonyManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			String simSerialNumber=telephonyManager.getSimSerialNumber();
			
			preferences=getSharedPreferences("sim", 0);
			SharedPreferences.Editor editor=preferences.edit();
			editor.putBoolean("SimIsBind", true);
			editor.putString("simSerialNumber", simSerialNumber);
			editor.commit();
			mBindSimBn.setEnabled(false);
			Toast.makeText(this, "sim卡绑定成功:"+simSerialNumber, 0).show();
			
		}else{
			Toast.makeText(this, "sim卡已经绑定", 0).show();
			mBindSimBn.setEnabled(false);
		}
		
	}
	
	/**判断sim卡是否绑定s
	 * @return
	 */
	private boolean isBind(){
		preferences=getSharedPreferences("sim", 0);
		return preferences.getBoolean("SimIsBind", false);
		
	}

}
