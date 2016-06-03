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

		// 初始化手势识别器 1，在当前类中创建一个GestureDetector实例 2，创建一个Listener来实时监听当前面板操作手势。
		// 3，在初始化时，将Listener实例关联当前的GestureDetector实例。
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
							Toast.makeText(getApplicationContext(), "无效动作，移动太慢", 0).show();
							return true;
						}
						
						if((e2.getRawX()-e1.getRawX())>200){
							//从左向右滑动，显示上一个界面
							overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
							showPre();
							return true;
						}
						
						if((e1.getRawX()-e2.getRawX())>200){
							//从右向左滑动，显示下一个界面
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
	
	
	//用手势识别器去识别事件 4，利用onTouchEvent方法作为入口检测，通过传递MotionEvent参数来监听操作手势。
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
