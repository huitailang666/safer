package ch3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ch2.activity.ContactSelectActivity;
import ch3.db.BlackNumberDao;
import ch3.entity.BlackContactInfo;

import com.example.mobilesafe.R;

public class AddBlackNumberActivity extends Activity implements OnClickListener{
	
	private CheckBox smsCB;
	private CheckBox telCB;
	private EditText numET;
	private EditText nameET;
	private BlackNumberDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_blacknumber);
		initView();
		dao=new BlackNumberDao(AddBlackNumberActivity.this);
		
	
	}
	
	
	private void initView(){
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
		((TextView)findViewById(R.id.tv_title)).setText("添加黑名单");
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		leftImgv.setOnClickListener(this);
		leftImgv.setImageResource(R.drawable.back);
		smsCB=(CheckBox)findViewById(R.id.cb_blacknumber_sms);
		telCB=(CheckBox)findViewById(R.id.cb_blacknumber_tel);
		numET=(EditText)findViewById(R.id.et_balcknumber);
		nameET=(EditText)findViewById(R.id.et_blackname);
		findViewById(R.id.add_blacknum_btn).setOnClickListener(this);
		findViewById(R.id.add_fromcontact_btn).setOnClickListener(this);
		
		
		
		
		
		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_blacknum_btn:
			String number=numET.getText().toString().trim();
			String name=nameET.getText().toString().trim();
			if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)){
				Toast.makeText(AddBlackNumberActivity.this, "电话号码和名称不能为空", 0).show();
				return ;
			}else{
				BlackContactInfo blackContactInfo=new BlackContactInfo();
				blackContactInfo.setPhoneNumber(number);
				blackContactInfo.setContactName(name);
				if(smsCB.isChecked()&&telCB.isChecked()){
					blackContactInfo.setMode(3);
				}else if(smsCB.isChecked()&&!telCB.isChecked()){
					blackContactInfo.setMode(2);
				}else if(!smsCB.isChecked()&&telCB.isChecked()){
					blackContactInfo.setMode(1);
				}else{
					Toast.makeText(AddBlackNumberActivity.this, "请选择拦截模式", 0).show();
					return;
				}
				if(!dao.IsNumberExist(number)){
					dao.add(blackContactInfo);
				}else{
					Toast.makeText(AddBlackNumberActivity.this, "该号码已经被添加至黑名单", 0).show();
				}
				finish();
				
			}

			break;

		case R.id.add_fromcontact_btn:
			startActivityForResult(new Intent(AddBlackNumberActivity.this, ContactSelectActivity.class), 666);
			
			break;
			
			case R.id.imgv_leftbtn:
			finish();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==666&&resultCode==Activity.RESULT_OK){
			nameET.setText(data.getStringExtra("name"));
			numET.setText(data.getStringExtra("phone"));
		}
		
	}

}
