package ch2.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ch2.adapter.ContactAdapter;
import ch2.entity.ContactInfo;
import ch2.utils.ContactInfoParser;

import com.example.mobilesafe.R;

public class ContactSelectActivity extends Activity implements OnClickListener {
	
	private ListView listView;
	private List<ContactInfo> list;
	private ContactAdapter contactAdapter;
	private String TAG="ContactSelectActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_select);
		initView();
		
	}
	
	
	
	private void initView(){
		listView=(ListView)findViewById(R.id.lv_contact);
		//listView.setAdapter(new ContactAdapter(ContactSelectActivity.this));
		TextView titleTV=(TextView)findViewById(R.id.tv_title);
		titleTV.setText("选择联系人");
		ImageView leftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
		leftImgv.setImageResource(R.drawable.back);
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
		leftImgv.setOnClickListener(this);
		new Thread(){
			public void run(){
				list=ContactInfoParser.getSystemContact(ContactSelectActivity.this);
				
				handler.sendEmptyMessage(666);
				
			};
		}.start();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ContactInfo contactInfo=(ContactInfo)contactAdapter.getItem(position);
				Intent intent=new Intent();
				intent.putExtra("phone", contactInfo.getPhone());
				intent.putExtra("name", contactInfo.getName());
				setResult(RESULT_OK, intent);
				finish();
			}
			
			
		});

	}
	
	
	Handler handler=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case 666:
				//更新UI
				if(list!=null){
					contactAdapter=new ContactAdapter(ContactSelectActivity.this, list);
					listView.setAdapter(contactAdapter);
					Log.d(TAG, ">>>>>>>"+list.toString());
				}
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgv_leftbtn:
			finish();
			break;

		default:
			break;
		}
	}

}
