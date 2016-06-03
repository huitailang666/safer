package ch3.adapter;

import java.util.List;

import com.example.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import ch3.db.BlackNumberDao;
import ch3.entity.BlackContactInfo;

public class BlackContactAdapter extends BaseAdapter {

	private List<BlackContactInfo> contactInfos;
	private Context context;
	private BlackNumberDao dao;
	private BlackContactCallBack callBack;
	
	public void setCallBack(BlackContactCallBack callBack){
		this.callBack=callBack;
	}
	
	public BlackContactAdapter(List<BlackContactInfo> contactInfos,
			Context context) {
		super();
		this.contactInfos = contactInfos;
		this.context = context;
		dao = new BlackNumberDao(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return contactInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.item_list_blackcontact, null);
			holder = new ViewHolder();
			holder.nameTV = (TextView) convertView
					.findViewById(R.id.tv_black_name);
			holder.modeTV = (TextView) convertView
					.findViewById(R.id.tv_black_mode);
			holder.contactImgv = convertView.findViewById(R.id.view_black_icon);
			holder.deleteView = convertView
					.findViewById(R.id.view_black_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameTV.setText(contactInfos.get(position).getContactName() + "("
				+ contactInfos.get(position).getPhoneNumber() + ")");
		holder.modeTV.setText(contactInfos.get(position).getModeString((contactInfos.get(position).getMode())));
		holder.nameTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
		holder.modeTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
		holder.contactImgv.setBackgroundResource(R.drawable.brightpurple_contact_icon);
		holder.deleteView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean delete=dao.delete(contactInfos.get(position));
				if(delete){
					contactInfos.remove(contactInfos.get(position));
					BlackContactAdapter.this.notifyDataSetChanged();
					if(dao.getTotalNumber()==0){
						callBack.DataSizeChanged();
					}
					
			}else{
				Toast.makeText(context, "delete failed", 0).show();
			}
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		TextView nameTV;
		TextView modeTV;
		View contactImgv;
		View deleteView;
	}
	
	public interface BlackContactCallBack{
		void DataSizeChanged();
	}

}
