package ch2.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class SetUpPasswordDialog extends Dialog implements android.view.View.OnClickListener{
	
	private Context context;
	
	private TextView mTitleTV;
	private EditText mFirstPwdET;
	private EditText mAffirmPwdET;
	private Button mOkBtn;
	private Button mCancelBtn;
	
	private MyCallBack myCallBack;
	
	private static String TAG="SetUpPasswordDialog";
	
	public EditText getmFirstPwdET() {
		return mFirstPwdET;
	}


	public void setmFirstPwdET(EditText mFirstPwdET) {
		this.mFirstPwdET = mFirstPwdET;
	}


	public EditText getmAffirmPwdET() {
		return mAffirmPwdET;
	}


	public void setmAffirmPwdET(EditText mAffirmPwdET) {
		this.mAffirmPwdET = mAffirmPwdET;
	}


	public void setCallBack(MyCallBack myCallBack){
		this.myCallBack =myCallBack;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		initDialog();
	}


	public SetUpPasswordDialog(Context context) {
		
		super(context);
		this.context=context;
		
		// TODO Auto-generated constructor stub
	}
	
	public void initDialog(){
		View view=LayoutInflater.from(context).inflate(R.layout.setup_password_dialog, null);
		setContentView(view);//加载自定义对话框的布局
		mTitleTV=(TextView)view.findViewById(R.id.tv_setuppwd_title);
		mFirstPwdET=(EditText)view.findViewById(R.id.et_firstpwd);
		mAffirmPwdET=(EditText)view.findViewById(R.id.et_affirm_pwd);
		mOkBtn=(Button)view.findViewById(R.id.btn_ok);
		mCancelBtn=(Button)view.findViewById(R.id.btn_cancel);
		mOkBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel:
			myCallBack.cancel();
			break;

		case R.id.btn_ok:
			myCallBack.ok();
			
			break;
		}
	}
	
	public interface MyCallBack{
		void ok();
		void cancel();
	}



}
