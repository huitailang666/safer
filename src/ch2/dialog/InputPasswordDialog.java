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

public class InputPasswordDialog extends Dialog implements android.view.View.OnClickListener{
	
	private Context context;
	private static String TAG="InputPasswordDialog";
	
	private TextView mTitleTV;
	private EditText mInputPwdET;
	private Button mOkBtn;
	private Button mCancelBtn;
	
	private MyCallBack myCallBack;
	
	public void setCallBack(MyCallBack myCallBack){
		this.myCallBack =myCallBack;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		initDialog();
	}


	public InputPasswordDialog(Context context) {
		
		super(context);
		this.context=context;
		
		// TODO Auto-generated constructor stub
	}
	
	public EditText getmInputPwdET() {
		return mInputPwdET;
	}


	public void setmInputPwdET(EditText mInputPwdET) {
		this.mInputPwdET = mInputPwdET;
	}


	public void initDialog(){
		View view=LayoutInflater.from(context).inflate(R.layout.input_password_dialog, null);
		setContentView(view);//加载自定义对话框的布局
		mTitleTV=(TextView)view.findViewById(R.id.tv_inputpwd_title);
		mInputPwdET=(EditText)view.findViewById(R.id.et_inputpwd);
		mOkBtn=(Button)view.findViewById(R.id.btn_input_ok);
		mCancelBtn=(Button)view.findViewById(R.id.btn_input_cancel);
		mOkBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_input_cancel:
			myCallBack.cancel();
			break;

		case R.id.btn_input_ok:
			myCallBack.ok();
			break;
		}
	}
	
	public interface MyCallBack{
		void ok();
		void cancel();
	}



}
