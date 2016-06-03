package ch2.activity;


import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mobilesafe.R;

public class SetUp1Activity extends BaseSetUpActivity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		((RadioButton)findViewById(R.id.rb_first)).setChecked(true);

		
	}
	
	
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		startActivityAndFinishSelf(SetUp2Activity.class);
       Toast.makeText(this, "setup2activity", 0).show();
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "当前页面已是第一页", 0).show();

	}

}
