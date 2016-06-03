package ch2.adapter;


import java.util.List;

import ch2.entity.ContactInfo;

import com.example.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	
	private List<ContactInfo> contactInfos;
	
	private Context context;
	
	public ContactAdapter(Context context,List<ContactInfo> contactInfos){
		this.context=context;
		this.contactInfos=contactInfos;
	}

	
	static class ViewHolder{
		public TextView NameTV;
		public TextView PhoneTV;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
		return contactInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
		return contactInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			//convertView=View.inflate(context, R.layout.item_list_contact_select, null);
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.item_list_contact_select, null);
			holder.NameTV=(TextView)convertView.findViewById(R.id.tv_name);
			holder.PhoneTV=(TextView)convertView.findViewById(R.id.tv_phone);
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder)convertView.getTag();
			
		}
		holder.NameTV.setText(contactInfos.get(position).getName());
		holder.PhoneTV.setText(contactInfos.get(position).getPhone());
		
		
		
		return convertView;
	}

}
