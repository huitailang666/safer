package ch2.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mobilesafe.R;

public class SetUp3Activity extends BaseSetUpActivity {
	
	private Button AddContactBn;
	private EditText InputPhoneEt;
	private SharedPreferences preferences;
	private String TAG="SetUp3Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		initView();
		
	}
	
	private void initView(){
		AddContactBn=(Button)findViewById(R.id.btn_addcontact);
		InputPhoneEt=(EditText)findViewById(R.id.et_inputphone);
		AddContactBn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SetUp3Activity.this, "SelectContactActivity！", 0).show();
				//启动ContactSelectActivity视图    forResult方法
				//ContactInfoParser.getSystemContact(SetUp3Activity.this);
				Intent intent=new Intent(SetUp3Activity.this, ContactSelectActivity.class);
				startActivityForResult(intent, 1000);
				
				
			}
		});
		((RadioButton)findViewById(R.id.rb_third)).setChecked(true);
		preferences=getSharedPreferences("phone", 0);
		String safephoneNumber=preferences.getString("phoneNumber", "");
		if(!TextUtils.isEmpty(safephoneNumber)){
			InputPhoneEt.setText(safephoneNumber);
		}
		
	}
	
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		String phoneNumber=InputPhoneEt.getText().toString().trim();
		if(TextUtils.isEmpty(phoneNumber)){
			Toast.makeText(this, "请输入安全号码！", 0).show();
			return;
		}
		//保存安全号码
		preferences=getSharedPreferences("phone", 0);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString("phoneNumber", phoneNumber);
		editor.commit();
		//startActivityAndFinishSelf(SetUp4Activity.class);
		Toast.makeText(this, "SetUp4Activity！", 0).show();
		startActivityAndFinishSelf(SetUp4Activity.class);
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		startActivityAndFinishSelf(SetUp2Activity.class);

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// If the request went well (OK) and the request was 1000
		if((requestCode==1000)&&(resultCode==Activity.RESULT_OK)){
			Log.d(TAG, ">>>>get onActivityResult");
			
			InputPhoneEt.setText(data.getStringExtra("phone"));
			
		}
		
		
	}

}
