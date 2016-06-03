package ch3.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;
import ch3.db.BlackNumberDao;
import ch3.entity.BlackContactInfo;

public class TestBlackNumberDao extends AndroidTestCase {

	private Context context;
	private String TAG="TestBlackNumberDao";
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		context=getContext();
		super.setUp();
	}
	
	/**测试添加
	 * @throws Exception
	 */
	public void testAdd()throws Exception {
		BlackNumberDao dao=new BlackNumberDao(context);
		Random random=new Random(8979);
		for(int i=0;i<30;i++){
			BlackContactInfo info=new BlackContactInfo();
			info.setPhoneNumber(135000800+i+"");
			info.setContactName("sjh"+i);
			info.setMode(random.nextInt(3)+1);
			dao.add(info);
		}
	}
	
	
	/**测试删除
	 * @throws Exception
	 */
	public void testDelete() throws Exception{
		BlackNumberDao dao=new BlackNumberDao(context);
		BlackContactInfo info=new BlackContactInfo();
		for(int i=0;i<10;i++){
			info.setPhoneNumber(135000000+i+"");
			dao.delete(info);
		}
		
		
	}
	
	
	
	/**测试分页查询
	 * @throws Exception
	 */
	public void testGetPageBlackNumber() throws Exception{
		BlackNumberDao dao=new BlackNumberDao(context);
		List<BlackContactInfo> list=dao.getPageBlackNumber(2, 5);
		for(int i=0;i<list.size();i++){
			Log.d(TAG, "<><>"+list.get(i).getPhoneNumber());
		}
		
		
	}
	
	
	/**测试根据号码查询黑名单信息
	 * @throws Exception
	 */
	public void testGetBlackContactMode()throws Exception{
		BlackNumberDao dao=new BlackNumberDao(context);
		int mode=dao.getBlackContactMode(135000829+"");
		Log.d(TAG, "<><>"+mode);
		
	}
	
	
	/**测试号码是否在数据库中
	 * @throws Exception
	 */
	public void testIsNumberExist() throws Exception{
		BlackNumberDao dao=new BlackNumberDao(context);
		boolean isExist=dao.IsNumberExist(135000839+"");
		if(isExist){
			Log.d(TAG, "<><>y");
		}else{
			Log.d(TAG, "<><>n");
		}
		
		
	}
	
	public void testGetTotalNumber()throws Exception{
		BlackNumberDao dao=new BlackNumberDao(context);
		int count=dao.getTotalNumber();
		Log.d(TAG, "<><>"+count);
	}
	
}
