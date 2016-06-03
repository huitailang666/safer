package ch2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.mobilesafe.R;

public abstract class BaseSetUpActivity extends Activity {

	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// ��ʼ������ʶ���� 1���ڵ�ǰ���д���һ��GestureDetectorʵ�� 2������һ��Listener��ʵʱ������ǰ���������ơ�
		// 3���ڳ�ʼ��ʱ����Listenerʵ��������ǰ��GestureDetectorʵ����
		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {

					/*
					 * e1 The first down motion event that started the fling. e2
					 * The move motion event that triggered the current onFling.
					 * velocityX The velocity of this fling measured in pixels
					 * per second along the x axis. velocityY The velocity of
					 * this fling measured in pixels per second along the y
					 * axis.
					 */
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// TODO Auto-generated method stub
						if(Math.abs(velocityX)<200){
							Toast.makeText(getApplicationContext(), "��Ч�������ƶ�̫��", 0).show();
							return true;
						}
						
						if((e2.getRawX()-e1.getRawX())>200){
							//�������һ�������ʾ��һ������
							overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
							showPre();
							return true;
						}
						
						if((e1.getRawX()-e2.getRawX())>200){
							//�������󻬶�����ʾ��һ������
							overridePendingTransition(R.anim.next_in, R.anim.next_out);
							showNext();
							return true;
						}
						
						return super.onFling(e1, e2, velocityX, velocityY);
					}

				});
		
		
		
		
		

	}
	
	public abstract void showNext();
	public abstract void showPre();
	
	
	//������ʶ����ȥʶ���¼� 4������onTouchEvent������Ϊ��ڼ�⣬ͨ������MotionEvent�����������������ơ�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	
	/**
	 * @param cls    
	 */
	public void startActivityAndFinishSelf(Class<?>cls){
		Intent intent=new Intent(this, cls);
		startActivity(intent);
		finish();
	}
	

}
